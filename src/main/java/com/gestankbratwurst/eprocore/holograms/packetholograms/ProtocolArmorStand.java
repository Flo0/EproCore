package com.gestankbratwurst.eprocore.holograms.packetholograms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers.ItemSlot;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.gestankbratwurst.eprocore.holograms.LineRepresentation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 17.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ProtocolArmorStand implements IPacketArmorStand {

  // Pls dont judge
  private final int entityID = ThreadLocalRandom.current().nextInt();
  private final UUID entityUID = UUID.randomUUID();
  private final PacketContainer showPacket;
  private final PacketContainer hidePacket;
  private PacketContainer metaDataPacket;
  private PacketContainer equipmentPacket;
  private PacketContainer movePacket;
  private LineRepresentation currentRepresentation;
  private Location currentLocation;

  public ProtocolArmorStand(final Location location, final LineRepresentation representation) {
    this.currentRepresentation = representation;
    this.currentLocation = location;
    this.showPacket = this.createSpawnPacket();
    this.hidePacket = this.createDeSpawnPacket();
    this.metaDataPacket = this.createMetaDataPacket();
    this.equipmentPacket = this.createEquipmentPacket();
    this.movePacket = this.createMovePacket();
  }

  @Override
  public void showTo(final Player player) {
    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, this.showPacket);
    } catch (final InvocationTargetException e) {
      e.printStackTrace();
    }
    this.updateFor(player);
  }

  @Override
  public void hideFrom(final Player player) {
    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, this.hidePacket);
    } catch (final InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateFor(final Player player) {
    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, this.metaDataPacket);
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, this.equipmentPacket);
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, this.movePacket);
    } catch (final InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void moveTo(final Location location) {
    this.currentLocation = location;
    this.movePacket = this.createMovePacket();
  }

  @Override
  public void setRepresentation(final LineRepresentation representation) {
    this.currentRepresentation = representation;
    this.metaDataPacket = this.createMetaDataPacket();
    this.equipmentPacket = this.createEquipmentPacket();
  }

  private PacketContainer createSpawnPacket() {
    final PacketContainer packetContainer = new PacketContainer(Server.SPAWN_ENTITY_LIVING);
    final StructureModifier<Integer> intMod = packetContainer.getIntegers();
    intMod.write(0, this.entityID);
    packetContainer.getUUIDs().write(0, this.entityUID);
    intMod.write(1, 1);
    final StructureModifier<Double> doubleMod = packetContainer.getDoubles();
    doubleMod.write(0, this.currentLocation.getX());
    doubleMod.write(1, this.currentLocation.getY());
    doubleMod.write(2, this.currentLocation.getZ());
    return packetContainer;
  }

  private PacketContainer createDeSpawnPacket() {
    final PacketContainer packetContainer = new PacketContainer(Server.ENTITY_DESTROY);
    packetContainer.getIntegerArrays().write(0, new int[]{this.entityID});
    return packetContainer;
  }

  private PacketContainer createEquipmentPacket() {
    final PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
    packetContainer.getIntegers().write(0, 0);
    final List<Pair<ItemSlot, ItemStack>> pairList = new ArrayList<>();
    pairList.add(new Pair<>(ItemSlot.HEAD, this.currentRepresentation.getAsItem()));
    packetContainer.getSlotStackPairLists().write(0, pairList);
    return packetContainer;
  }

  private PacketContainer createMetaDataPacket() {
    final PacketContainer packetContainer = new PacketContainer(Server.ENTITY_METADATA);
    final WrappedDataWatcher watcher = new WrappedDataWatcher();
    final Optional<Object> optionalText = Optional.of(WrappedChatComponent.fromText(this.currentRepresentation.getAsText()).getHandle());
    final WrappedDataWatcher.Serializer componentSerializer = WrappedDataWatcher.Registry.getChatComponentSerializer(true);
    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, componentSerializer), optionalText);
    final WrappedDataWatcher.Serializer boolSerializer = WrappedDataWatcher.Registry.get(Boolean.class);
    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, boolSerializer), true);
    final WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, byteSerializer), (byte) 0x20);
    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(14, byteSerializer), (byte) 0x10);
    packetContainer.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
    packetContainer.getIntegers().write(0, this.entityID);
    return packetContainer;
  }

  private PacketContainer createMovePacket() {
    final PacketContainer packetContainer = new PacketContainer(Server.ENTITY_TELEPORT);
    packetContainer.getIntegers().write(0, this.entityID);
    final StructureModifier<Double> doubleMod = packetContainer.getDoubles();
    doubleMod.write(0, this.currentLocation.getX());
    doubleMod.write(1, this.currentLocation.getY());
    doubleMod.write(2, this.currentLocation.getZ());
    packetContainer.getBooleans().write(0, false);
    return packetContainer;
  }

  @Override
  public boolean isText() {
    return this.currentRepresentation.isText();
  }

  @Override
  public boolean isItem() {
    return this.currentRepresentation.isItem();
  }

  @Override
  public String getAsText() {
    return this.currentRepresentation.getAsText();
  }

  @Override
  public ItemStack getAsItem() {
    return this.currentRepresentation.getAsItem();
  }
}
