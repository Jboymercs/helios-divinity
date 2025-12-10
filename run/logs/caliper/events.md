# Event Analysis

This file contains an analysis on the various forge event busses. This first
table lists events that have listeners, in order of most listeners. Further
specifics on the listenrs. This data is anonymous, and is not automatically
submitted to any online service.

| Event Name                                   | Listener Count |
|----------------------------------------------|----------------|
| TickEvent$ClientTickEvent                    | 4              |
| PlayerEvent$PlayerLoggedInEvent              | 3              |
| TickEvent$RenderTickEvent                    | 2              |
| ConfigChangedEvent$OnConfigChangedEvent      | 2              |
| LivingHurtEvent                              | 2              |
| WorldEvent$Load                              | 2              |
| InputEvent$KeyInputEvent                     | 2              |
| WorldEvent$Save                              | 2              |
| GuiScreenEvent$InitGuiEvent$Post             | 1              |
| PlayerInteractEvent                          | 1              |
| PlaySoundEvent                               | 1              |
| TextureStitchEvent$Pre                       | 1              |
| LivingDropsEvent                             | 1              |
| AnvilUpdateEvent                             | 1              |
| AttachCapabilitiesEvent                      | 1              |
| TickEvent$PlayerTickEvent                    | 1              |
| WailaTooltipEvent                            | 1              |
| TickEvent$ServerTickEvent                    | 1              |
| FMLNetworkEvent$ClientConnectedToServerEvent | 1              |
| CommandEvent                                 | 1              |
| LivingAttackEvent                            | 1              |
| RenderWorldLastEvent                         | 1              |
| ColorHandlerEvent$Item                       | 1              |
| ChunkEvent$Unload                            | 1              |
| WorldEvent$Unload                            | 1              |
| LivingDeathEvent                             | 1              |
| TickEvent                                    | 1              |
| EntityJoinWorldEvent                         | 1              |
| ItemTooltipEvent                             | 1              |


## TickEvent$RenderTickEvent
| Owner           | Method            | Location                                     | Priority | Source                      | RecieveCanceled |
|-----------------|-------------------|----------------------------------------------|----------|-----------------------------|-----------------|
| Waila           | renderOverlay     | mcp.mobius.waila.overlay.WailaTickHandler    | normal   | Hwyla-1.8.26-B41_1.12.2.jar | false           |
| Helios Divinity | onRenderTickStart | com.unseen.helios.util.CameraPositionHandler | normal   | helios-1.12.2-0.0.2.jar     | false           |


## GuiScreenEvent$InitGuiEvent$Post
| Owner            | Method    | Location                   | Priority | Source                                                    | RecieveCanceled |
|------------------|-----------|----------------------------|----------|-----------------------------------------------------------|-----------------|
| Had Enough Items | onGuiInit | mezz.jei.input.MouseHelper | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar | false           |


## PlayerInteractEvent
| Owner     | Method           | Location                                 | Priority | Source                                   | RecieveCanceled |
|-----------|------------------|------------------------------------------|----------|------------------------------------------|-----------------|
| WorldEdit | onPlayerInteract | com.sk89q.worldedit.forge.ForgeWorldEdit | normal   | worldedit-forge-mc1.12.2-6.1.10-dist.jar | false           |


## PlayerEvent$PlayerLoggedInEvent
| Owner           | Method           | Location                                  | Priority | Source                                                     | RecieveCanceled |
|-----------------|------------------|-------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Waila           | onPlayerLoggedIn | mcp.mobius.waila.handlers.NetworkHandler  | normal   | Hwyla-1.8.26-B41_1.12.2.jar                                | false           |
| Minecraft Forge | playerLogin      | forge                                     | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
| Helios Divinity | onPlayerLoggedIn | com.unseen.helios.events.EventPlayerGreed | normal   | helios-1.12.2-0.0.2.jar                                    | false           |


## PlaySoundEvent
| Owner           | Method      | Location                                | Priority | Source                  | RecieveCanceled |
|-----------------|-------------|-----------------------------------------|----------|-------------------------|-----------------|
| Helios Divinity | onMusicPlay | com.unseen.helios.events.EventBossMusic | normal   | helios-1.12.2-0.0.2.jar | false           |


## TextureStitchEvent$Pre
| Owner            | Method             | Location                           | Priority | Source                                                    | RecieveCanceled |
|------------------|--------------------|------------------------------------|----------|-----------------------------------------------------------|-----------------|
| Had Enough Items | handleTextureRemap | mezz.jei.startup.ProxyCommonClient | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar | false           |


## LivingDropsEvent
| Owner           | Method       | Location                               | Priority | Source                  | RecieveCanceled |
|-----------------|--------------|----------------------------------------|----------|-------------------------|-----------------|
| Helios Divinity | onEntityDrop | com.unseen.helios.events.EventSlainMob | normal   | helios-1.12.2-0.0.2.jar | false           |


## ConfigChangedEvent$OnConfigChangedEvent
| Owner            | Method          | Location                           | Priority | Source                                                     | RecieveCanceled |
|------------------|-----------------|------------------------------------|----------|------------------------------------------------------------|-----------------|
| Had Enough Items | onConfigChanged | mezz.jei.startup.ProxyCommonClient | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar  | false           |
| Minecraft Forge  | onConfigChanged | forge                              | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## AnvilUpdateEvent
| Owner     | Method        | Location                        | Priority | Source                           | RecieveCanceled |
|-----------|---------------|---------------------------------|----------|----------------------------------|-----------------|
| Bookshelf | onAnvilUpdate | net.darkhax.bookshelf.Bookshelf | normal   | Bookshelf-1.12.2-2.3.590 (1).jar | false           |


## AttachCapabilitiesEvent
| Owner           | Method             | Location                                  | Priority | Source                  | RecieveCanceled |
|-----------------|--------------------|-------------------------------------------|----------|-------------------------|-----------------|
| Helios Divinity | attachCapabilities | com.unseen.helios.events.EventPlayerGreed | normal   | helios-1.12.2-0.0.2.jar | false           |


## LivingHurtEvent
| Owner           | Method                    | Location                                    | Priority | Source                  | RecieveCanceled |
|-----------------|---------------------------|---------------------------------------------|----------|-------------------------|-----------------|
| Helios Divinity | afterShieldAndBeforeArmor | com.unseen.helios.events.EventShieldHandler | normal   | helios-1.12.2-0.0.2.jar | false           |
| Helios Divinity | onPlayerWearingManus      | com.unseen.helios.events.EventPlayerGreed   | normal   | helios-1.12.2-0.0.2.jar | false           |


## TickEvent$PlayerTickEvent
| Owner           | Method       | Location                                  | Priority | Source                  | RecieveCanceled |
|-----------------|--------------|-------------------------------------------|----------|-------------------------|-----------------|
| Helios Divinity | onPlayerTick | com.unseen.helios.events.EventPlayerGreed | normal   | helios-1.12.2-0.0.2.jar | false           |


## WailaTooltipEvent
| Owner | Method    | Location                                  | Priority | Source                      | RecieveCanceled |
|-------|-----------|-------------------------------------------|----------|-----------------------------|-----------------|
| Waila | onTooltip | mcp.mobius.waila.overlay.WailaTickHandler | normal   | Hwyla-1.8.26-B41_1.12.2.jar | false           |


## TickEvent$ServerTickEvent
| Owner           | Method       | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|--------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onServerTick | net.minecraftforge.common.ForgeInternalHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## FMLNetworkEvent$ClientConnectedToServerEvent
| Owner            | Method                    | Location                           | Priority | Source                                                    | RecieveCanceled |
|------------------|---------------------------|------------------------------------|----------|-----------------------------------------------------------|-----------------|
| Had Enough Items | onClientConnectedToServer | mezz.jei.startup.ProxyCommonClient | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar | false           |


## CommandEvent
| Owner     | Method         | Location                                 | Priority | Source                                   | RecieveCanceled |
|-----------|----------------|------------------------------------------|----------|------------------------------------------|-----------------|
| WorldEdit | onCommandEvent | com.sk89q.worldedit.forge.ForgeWorldEdit | normal   | worldedit-forge-mc1.12.2-6.1.10-dist.jar | false           |


## LivingAttackEvent
| Owner           | Method          | Location                                    | Priority | Source                  | RecieveCanceled |
|-----------------|-----------------|---------------------------------------------|----------|-------------------------|-----------------|
| Helios Divinity | doShieldUpdates | com.unseen.helios.events.EventShieldHandler | normal   | helios-1.12.2-0.0.2.jar | false           |


## TickEvent$ClientTickEvent
| Owner           | Method        | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|---------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Bookshelf       | onClientTick  | net.darkhax.bookshelf.Bookshelf                | normal   | Bookshelf-1.12.2-2.3.590 (1).jar                           | false           |
| Waila           | tickClient    | mcp.mobius.waila.overlay.WailaTickHandler      | normal   | Hwyla-1.8.26-B41_1.12.2.jar                                | false           |
| Minecraft Forge | checkSettings | net.minecraftforge.common.ForgeInternalHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
| Helios Divinity | onClientTick  | com.unseen.helios.util.CameraPositionHandler   | normal   | helios-1.12.2-0.0.2.jar                                    | false           |


## RenderWorldLastEvent
| Owner | Method            | Location                                   | Priority | Source                      | RecieveCanceled |
|-------|-------------------|--------------------------------------------|----------|-----------------------------|-----------------|
| Waila | onRenderWorldLast | mcp.mobius.waila.overlay.DecoratorRenderer | normal   | Hwyla-1.8.26-B41_1.12.2.jar | false           |


## ColorHandlerEvent$Item
| Owner           | Method               | Location                                     | Priority | Source                                                     | RecieveCanceled |
|-----------------|----------------------|----------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | registerItemHandlers | net.minecraftforge.client.ForgeClientHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## ChunkEvent$Unload
| Owner           | Method        | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|---------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onChunkUnload | net.minecraftforge.common.ForgeInternalHandler | normal   | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## WorldEvent$Load
| Owner           | Method          | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|-----------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onDimensionLoad | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
| Bookshelf       | onWorldLoaded   | net.darkhax.bookshelf.Bookshelf                | normal   | Bookshelf-1.12.2-2.3.590 (1).jar                           | false           |


## WorldEvent$Unload
| Owner           | Method            | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|-------------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onDimensionUnload | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## InputEvent$KeyInputEvent
| Owner     | Method     | Location                             | Priority | Source                                   | RecieveCanceled |
|-----------|------------|--------------------------------------|----------|------------------------------------------|-----------------|
| WorldEdit | onKey      | com.sk89q.worldedit.forge.KeyHandler | normal   | worldedit-forge-mc1.12.2-6.1.10-dist.jar | false           |
| Waila     | onKeyEvent | mcp.mobius.waila.client.KeyEvent     | normal   | Hwyla-1.8.26-B41_1.12.2.jar              | false           |


## LivingDeathEvent
| Owner           | Method        | Location                               | Priority | Source                  | RecieveCanceled |
|-----------------|---------------|----------------------------------------|----------|-------------------------|-----------------|
| Helios Divinity | onPlayerDeath | com.unseen.helios.events.EventSlainMob | normal   | helios-1.12.2-0.0.2.jar | false           |


## TickEvent
| Owner     | Method    | Location                                  | Priority | Source                                   | RecieveCanceled |
|-----------|-----------|-------------------------------------------|----------|------------------------------------------|-----------------|
| WorldEdit | tickStart | com.sk89q.worldedit.forge.ThreadSafeCache | normal   | worldedit-forge-mc1.12.2-6.1.10-dist.jar | false           |


## WorldEvent$Save
| Owner            | Method          | Location                                       | Priority | Source                                                     | RecieveCanceled |
|------------------|-----------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge  | onDimensionSave | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |
| Had Enough Items | onWorldSave     | mezz.jei.startup.ProxyCommonClient             | normal   | had-enough-items-557549-4571247_mapped_stable_39-1.12.jar  | false           |


## EntityJoinWorldEvent
| Owner           | Method            | Location                                       | Priority | Source                                                     | RecieveCanceled |
|-----------------|-------------------|------------------------------------------------|----------|------------------------------------------------------------|-----------------|
| Minecraft Forge | onEntityJoinWorld | net.minecraftforge.common.ForgeInternalHandler | highest  | forge-1.12.2-14.23.5.2860_mapped_stable_39-1.12-recomp.jar | false           |


## ItemTooltipEvent
| Owner | Method       | Location                                        | Priority | Source                      | RecieveCanceled |
|-------|--------------|-------------------------------------------------|----------|-----------------------------|-----------------|
| Waila | tooltipEvent | mcp.mobius.waila.handlers.VanillaTooltipHandler | normal   | Hwyla-1.8.26-B41_1.12.2.jar | false           |
