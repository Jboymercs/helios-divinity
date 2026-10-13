package com.unseen.helios.util.command;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.unseen.helios.config.WorldConfig;
import com.unseen.helios.util.HDLogger;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class CommandLocateHelios implements ICommand {
    private final List<String> aliases;

    public CommandLocateHelios() {
        aliases = new ArrayList<>();
        aliases.add("locateHeliosDivinity");
    }

    @Override
    public String getName() {
        return "locateHeliosDivinity";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "locateHeliosDivinity <mod location>";
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new WrongUsageException("locateHeliosDivinity <mod location>");
        } else {
            String s = args[0];
            if(s.equals("ManusAltar")) {
                BlockPos blockpos = findNearestPos(sender);

                if (blockpos != null) {
                    sender.sendMessage(new TextComponentTranslation("commands.locate.success", new Object[]{s, blockpos.getX(), blockpos.getZ()}));
                } else {
                    throw new CommandException("commands.locate.failure", s);
                }
            } else if(s.equals("ChampionGrave")) {
                BlockPos blockpos = findNearestPosChampion(sender);

                if (blockpos != null) {
                    sender.sendMessage(new TextComponentTranslation("commands.locate.success", new Object[]{s, blockpos.getX(), blockpos.getZ()}));
                } else {
                    throw new CommandException("commands.locate.failure", s);
                }
            }

        }
    }

    public static BlockPos findNearestPos(ICommandSender sender) {
        BlockPos resultpos = null;
        BlockPos pos = sender.getPosition();
        World world = sender.getEntityWorld();
        Chunk chunk = world.getChunk(pos);
        //probably laggy as hell but hey it works
        for (int i = -100; i < 100 + 1; i++) {
            for (int j = -100; j < 100 + 1; j++) {
                boolean c = IsManusAltarAtPos(world, chunk.x + i, chunk.z + j);
                if (c) {
                    resultpos = new BlockPos((chunk.x + i) << 4, 60, (chunk.z + j) << 4);
                    break;
                }
            }
        }
        return resultpos;
    }

    public static BlockPos findNearestPosChampion(ICommandSender sender) {
        BlockPos resultpos = null;
        BlockPos pos = sender.getPosition();
        World world = sender.getEntityWorld();
        Chunk chunk = world.getChunk(pos);
        //probably laggy as hell but hey it works
        for (int i = -100; i < 100 + 1; i++) {
            for (int j = -100; j < 100 + 1; j++) {
                boolean c = IsChampionAtPos(world, chunk.x + i, chunk.z + j);
                if (c) {
                    resultpos = new BlockPos((chunk.x + i) << 4, 60, (chunk.z + j) << 4);
                    break;
                }
            }
        }
        return resultpos;
    }

    protected static boolean IsManusAltarAtPos(World world, int chunkX, int chunkZ) {
        int spacing = WorldConfig.manus_altar_spacing;
        int separation = 16;
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0) {
            chunkX -= spacing - 1;
        }

        if (chunkZ < 0) {
            chunkZ -= spacing - 1;
        }

        int k = chunkX / spacing;
        int l = chunkZ / spacing;
        Random random = world.setRandomSeed(k, l, 56038247);
        k = k * spacing;
        l = l * spacing;
        k = k + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
        l = l + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;

        if (i == k && j == l && isAllowedDimensionTooSpawnIn(world.provider.getDimension())) {
            BlockPos pos = new BlockPos((i << 4), 0, (j << 4));
            return isAbleToSpawnHere(pos, world);
        } else {

            return false;
        }
    }

    protected static boolean IsChampionAtPos(World world, int chunkX, int chunkZ) {
        int spacing = WorldConfig.champion_grave_spacing;
        int separation = 16;
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0) {
            chunkX -= spacing - 1;
        }

        if (chunkZ < 0) {
            chunkZ -= spacing - 1;
        }

        int k = chunkX / spacing;
        int l = chunkZ / spacing;
        Random random = world.setRandomSeed(k, l, 76039936);
        k = k * spacing;
        l = l * spacing;
        k = k + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
        l = l + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;

        if (i == k && j == l && isAllowedDimensionTooSpawnIn(world.provider.getDimension())) {
            BlockPos pos = new BlockPos((i << 4), 0, (j << 4));
            return isAbleToSpawnHere(pos, world);
        } else {

            return false;
        }
    }

    public static boolean isAbleToSpawnHere(BlockPos pos, World world) {
        for(BiomeDictionary.Type types : getSpawnBiomeTypes()) {
            Biome biomeCurrently = world.provider.getBiomeForCoords(pos);
            if(BiomeDictionary.hasType(biomeCurrently, types)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllowedDimensionTooSpawnIn(int dimensionIn) {
        for(int i : WorldConfig.list_of_dimensions) {
            if(i == dimensionIn)
                return true;
        }

        return false;
    }

    private static List<BiomeDictionary.Type> lichTowerBiomeTypes;

    public static List<BiomeDictionary.Type> getSpawnBiomeTypes() {
        if(lichTowerBiomeTypes == null) {
            lichTowerBiomeTypes = Lists.newArrayList();

            for(String str : WorldConfig.biome_types_blacklist_manus) {
                try {
                    BiomeDictionary.Type type = BiomeDictionary.Type.getType(str);

                    if (type != null) lichTowerBiomeTypes.add(type);
                    else HDLogger.logError("Biome Type" + str + " is not correct", new NullPointerException());
                } catch (Exception e) {
                    HDLogger.logError(str + " is not a valid type name", e);
                }
            }
        }

        return lichTowerBiomeTypes;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "ManusAltar","ChampionGrave") : Collections.emptyList();
    }

    public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities) {
        return getListOfStringsMatchingLastWord(args, Arrays.asList(possibilities));
    }

    public static List<String> getListOfStringsMatchingLastWord(String[] inputArgs, Collection<?> possibleCompletions) {
        String s = inputArgs[inputArgs.length - 1];
        List<String> list = Lists.newArrayList();

        if (!possibleCompletions.isEmpty()) {
            for (String s1 : Iterables.transform(possibleCompletions, Functions.toStringFunction())) {
                if (doesStringStartWith(s, s1)) {
                    list.add(s1);
                }
            }

            if (list.isEmpty()) {
                for (Object object : possibleCompletions) {
                    if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation) object).getPath())) {
                        list.add(String.valueOf(object));
                    }
                }
            }
        }

        return list;
    }

    public static boolean doesStringStartWith(String original, String region)
    {
        return region.regionMatches(true, 0, original, 0, original.length());
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
