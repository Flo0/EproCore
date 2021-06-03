package com.gestankbratwurst.eprocore.holograms;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

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
public class HologramListener implements Listener {

  private final HologramManager hologramManager;

  @EventHandler
  public void onQuit(final PlayerQuitEvent event) {
    this.hologramManager.terminate(event.getPlayer());
  }

}
