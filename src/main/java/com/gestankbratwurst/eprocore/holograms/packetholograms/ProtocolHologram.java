package com.gestankbratwurst.eprocore.holograms.packetholograms;

import com.gestankbratwurst.eprocore.holograms.IHologram;
import com.gestankbratwurst.eprocore.holograms.LineRepresentation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 15.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ProtocolHologram implements IHologram {

  private static final double MARKER_OFFSET = 0.275D;

  private final Set<Player> viewers;
  private final List<IPacketArmorStand> packetArmorStands;
  private final Location baseLocation;
  private final UUID hologramID;
  private boolean cleared = false;

  public ProtocolHologram(final Location baseLocation, final UUID hologramID) {
    this.viewers = new HashSet<>();
    this.packetArmorStands = new ArrayList<>();
    this.baseLocation = baseLocation;
    this.hologramID = hologramID;
  }

  @Override
  public UUID getHologramID() {
    return this.hologramID;
  }

  @Override
  public void addViewer(final Player player) {
    this.viewers.add(player);
    this.packetArmorStands.forEach(stand -> stand.showTo(player));
    this.cleared = false;
  }

  @Override
  public void removeViewer(final Player player) {
    this.viewers.remove(player);
    this.packetArmorStands.forEach(stand -> stand.hideFrom(player));
    if (this.viewers.isEmpty()) {
      this.cleared = true;
    }
  }

  @Override
  public void addLine(final LineRepresentation line) {
    final Location lineLocation = this.baseLocation.clone().add(0, this.packetArmorStands.size() * MARKER_OFFSET, 0);
    final IPacketArmorStand armorStand = this.createPacketStand(lineLocation, line);
    this.packetArmorStands.add(armorStand);
    this.viewers.forEach(armorStand::showTo);
  }

  @Override
  public void removeLine(final int index) {
    final IPacketArmorStand removed = this.packetArmorStands.remove(index);
    this.viewers.forEach(removed::hideFrom);
    this.reOrder();
  }

  @Override
  public List<LineRepresentation> getLines() {
    return new ArrayList<>(this.packetArmorStands);
  }

  @Override
  public LineRepresentation getLine(final int index) {
    return this.packetArmorStands.get(index);
  }

  @Override
  public void setLine(final LineRepresentation line, final int index) {
    final IPacketArmorStand armorStand = this.packetArmorStands.get(index);
    armorStand.setRepresentation(line);
    if (this.cleared) {
      return;
    }
    this.viewers.forEach(armorStand::updateFor);
  }

  @Override
  public void teleport(final Location location) {
    this.baseLocation.setWorld(location.getWorld());
    this.baseLocation.setX(location.getX());
    this.baseLocation.setY(location.getY());
    this.baseLocation.setZ(location.getZ());
    this.reOrder();
  }

  @Override
  public void cleanView() {
    for (final Player viewer : new ArrayList<>(this.viewers)) {
      this.removeViewer(viewer);
    }
  }

  @Override
  public boolean isAlive() {
    return false;
  }

  @Override
  public Location getCurrentLocation() {
    return this.baseLocation.clone();
  }

  @Override
  public void moveRelative(final double x, final double y, final double z) {
    if (this.cleared) {
      return;
    }
    this.teleport(this.getCurrentLocation().add(x, y, z));
  }

  private void reOrder() {
    for (int index = 0; index < this.packetArmorStands.size(); index++) {
      this.packetArmorStands.get(index).moveTo(this.baseLocation.clone().add(0, index * MARKER_OFFSET, 0));
    }
    if (this.cleared) {
      return;
    }
    for (final IPacketArmorStand armorStand : this.packetArmorStands) {
      this.viewers.forEach(armorStand::updateFor);
    }
  }

  private IPacketArmorStand createPacketStand(final Location location, final LineRepresentation representation) {
    return new ProtocolArmorStand(location, representation);
  }

}
