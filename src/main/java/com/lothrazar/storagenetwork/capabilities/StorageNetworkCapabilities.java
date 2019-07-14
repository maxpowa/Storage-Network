package com.lothrazar.storagenetwork.capabilities;

import com.lothrazar.storagenetwork.api.capability.DefaultConnectable;
import com.lothrazar.storagenetwork.api.capability.IConnectable;
import com.lothrazar.storagenetwork.api.capability.IConnectableItemAutoIO;
import com.lothrazar.storagenetwork.api.capability.IConnectableLink;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class StorageNetworkCapabilities {

  @CapabilityInject(IConnectableLink.class)
  public static Capability<IConnectableLink> CONNECTABLE_ITEM_STORAGE_CAPABILITY = null;
  @CapabilityInject(IConnectable.class)
  public static Capability<IConnectable> CONNECTABLE_CAPABILITY = null;
  @CapabilityInject(IConnectableItemAutoIO.class)
  public static Capability<IConnectableItemAutoIO> CONNECTABLE_AUTO_IO = null;

  public static void initCapabilities() {
    CapabilityManager.INSTANCE.register(IConnectable.class, new CapabilityConnectable.Storage(), DefaultConnectable::new);
    CapabilityManager.INSTANCE.register(IConnectableLink.class, new CapabilityConnectableLink.Storage(), new CapabilityConnectableLink.Factory());
    CapabilityManager.INSTANCE.register(IConnectableItemAutoIO.class, new CapabilityConnectableAutoIO.Storage(), new CapabilityConnectableAutoIO.Factory());
  }
}