package com.gestankbratwurst.eprocore.holograms;

import java.util.LinkedList;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class HologramRunnable implements Runnable {

  private final HologramManager hologramManager;
  private final LinkedList<HologramMoveWrapper> movingHolograms = new LinkedList<>();

  public void addHologram(final HologramMoveWrapper wrapper) {
    this.movingHolograms.add(wrapper);
  }

  public void addHologram(final IHologram hologram, final Vector velocity, final int ticks) {
    this.movingHolograms.add(new HologramMoveWrapper(hologram, velocity, ticks));
  }

  @Override
  public void run() {
    this.movingHolograms.removeIf((wrapper) -> wrapper.tick(this.hologramManager));
  }
}
