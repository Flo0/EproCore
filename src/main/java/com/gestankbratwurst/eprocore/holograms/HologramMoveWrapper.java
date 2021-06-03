package com.gestankbratwurst.eprocore.holograms;


import org.bukkit.util.Vector;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 23.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class HologramMoveWrapper {

  private final IHologram hologram;
  private final Vector velocity;
  private int ticksLeft;

  public HologramMoveWrapper(final IHologram hologram, final Vector velocity, final int ticksLeft) {
    this.hologram = hologram;
    this.velocity = velocity;
    this.ticksLeft = ticksLeft;
  }

  public boolean tick(final HologramManager manager) {
    this.hologram.moveRelative(this.velocity.getX(), this.velocity.getY(), this.velocity.getZ());
    if (this.ticksLeft-- == 0) {
      manager.removeHologram(this.hologram.getHologramID());
      return true;
    }
    return false;
  }

}
