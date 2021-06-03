package com.gestankbratwurst.eprocore.io;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore5 and was created at the 03.06.2021
 *
 * EproCore5 can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
@AllArgsConstructor
public class IOListener implements Listener {

  private final FileManager fileManager;

  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {
    this.fileManager.loadPlayerData(event.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onQuit(final PlayerQuitEvent event) {
    this.fileManager.savePlayerData(event.getPlayer().getUniqueId());
  }

}