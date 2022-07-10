package com.lothrazar.storagenetwork;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.storagenetwork.block.cable.export.GuiCableExportFilter;
import com.lothrazar.storagenetwork.block.cable.inputfilter.GuiCableImportFilter;
import com.lothrazar.storagenetwork.block.cable.linkfilter.GuiCableFilter;
import com.lothrazar.storagenetwork.block.collection.GuiCollectionFilter;
import com.lothrazar.storagenetwork.block.inventory.GuiNetworkInventory;
import com.lothrazar.storagenetwork.block.request.GuiNetworkTable;
import com.lothrazar.storagenetwork.item.remote.GuiNetworkCraftingRemote;
import com.lothrazar.storagenetwork.item.remote.GuiNetworkRemote;
import com.lothrazar.storagenetwork.registry.ClientEventRegistry;
import com.lothrazar.storagenetwork.registry.ConfigRegistry;
import com.lothrazar.storagenetwork.registry.PacketRegistry;
import com.lothrazar.storagenetwork.registry.SsnEvents;
import com.lothrazar.storagenetwork.registry.SsnRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(StorageNetworkMod.MODID)
public class StorageNetworkMod {

  public static final String MODID = "storagenetwork";
  public static final Logger LOGGER = LogManager.getLogger();
  public static ConfigRegistry CONFIG;

  public StorageNetworkMod() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(StorageNetworkMod::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    MinecraftForge.EVENT_BUS.register(new SsnRegistry.Tiles());
    MinecraftForge.EVENT_BUS.register(new SsnEvents());
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    SsnRegistry.BLOCKS.register(bus);
    SsnRegistry.ITEMS.register(bus);
    SsnRegistry.TILES.register(bus);
    SsnRegistry.CONTAINERS.register(bus);
  }

  private static void setup(FMLCommonSetupEvent event) {
    PacketRegistry.init();
    CONFIG = new ConfigRegistry(FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
    InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("charm").size(2).build());
  }

  private void setupClient(final FMLClientSetupEvent event) {
    MenuScreens.register(SsnRegistry.Menus.REQUEST.get(), GuiNetworkTable::new);
    MenuScreens.register(SsnRegistry.Menus.FILTER_KABEL.get(), GuiCableFilter::new);
    MenuScreens.register(SsnRegistry.Menus.IMPORT_FILTER_KABEL.get(), GuiCableImportFilter::new);
    MenuScreens.register(SsnRegistry.Menus.EXPORT_KABEL.get(), GuiCableExportFilter::new);
    MenuScreens.register(SsnRegistry.Menus.INVENTORY_REMOTE.get(), GuiNetworkRemote::new);
    MenuScreens.register(SsnRegistry.Menus.CRAFTING_REMOTE.get(), GuiNetworkCraftingRemote::new);
    MenuScreens.register(SsnRegistry.Menus.INVENTORY.get(), GuiNetworkInventory::new);
    MenuScreens.register(SsnRegistry.Menus.COLLECTOR.get(), GuiCollectionFilter::new);
    ClientRegistry.registerKeyBinding(ClientEventRegistry.INVENTORY_KEY);
  }

  public static void log(String s) {
    if (CONFIG.logspam()) {
      LOGGER.info(s);
    }
  }
}