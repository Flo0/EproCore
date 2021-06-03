package com.gestankbratwurst.eprocore.holograms;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 15.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public interface IHologram {

  UUID getHologramID();

  void addViewer(Player player);

  void removeViewer(Player player);

  void addLine(LineRepresentation line);

  void removeLine(int index);

  List<LineRepresentation> getLines();

  LineRepresentation getLine(int index);

  void setLine(LineRepresentation line, int index);

  void teleport(Location location);

  void cleanView();

  boolean isAlive();

  Location getCurrentLocation();

  void moveRelative(double x, double y, double z);

  default void addLine(final String text) {
    this.addLine(SimpleTextLine.of(text));
  }

  default void addLine(final ItemStack item) {
    this.addLine(SimpleItemLine.of(item));
  }

  default void setLine(final String text, final int index) {
    this.setLine(SimpleTextLine.of(text), index);
  }

  default void setLine(final ItemStack item, final int index) {
    this.setLine(SimpleItemLine.of(item), index);
  }

}