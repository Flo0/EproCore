package com.gestankbratwurst.eprocore.io;

import com.gestankbratwurst.eprocore.events.ConfigurationInitEvent;
import com.gestankbratwurst.eprocore.events.ConfigurationLoadEvent;
import com.gestankbratwurst.eprocore.events.PlayerDataInitEvent;
import com.gestankbratwurst.eprocore.events.PlayerDataLoadEvent;
import com.gestankbratwurst.eprocore.events.PlayerDataSaveEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore5 and was created at the 03.06.2021
 *
 * EproCore5 can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class DataListener implements Listener {

  @EventHandler
  public void onConfigInit(final ConfigurationInitEvent event) {
    final YamlConfiguration configuration = event.getConfiguration();
    configuration.set("MinutesBetweenPlayerSave", 15);
  }

  @EventHandler
  public void onConfigLoad(final ConfigurationLoadEvent event) {
    final YamlConfiguration configuration = event.getConfiguration();
    final int minutes = configuration.getInt("MinutesBetweenPlayerSave", 15);
    EproConfigValues.setMinutesBetweenPlayerSaves(minutes);
  }

  @EventHandler
  public void onPlayerDataInit(final PlayerDataInitEvent event) {
    final UUID playerID = event.getPlayerID();
    final YamlConfiguration playerConfig = event.getConfiguration();
    playerConfig.set("PlayerID", playerID.toString());

    final Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
    final LocalDateTime localDateTime = java.time.LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-u hh:mm:ss");
    final String dateString = localDateTime.format(formatter);

    playerConfig.set("InitDate", dateString);
  }

  @EventHandler
  public void onPlayerDataLoad(final PlayerDataLoadEvent event) {
    final UUID playerID = event.getPlayerID();
    final YamlConfiguration playerConfig = event.getConfiguration();
    
    final Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
    final LocalDateTime localDateTime = java.time.LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-u hh:mm:ss");
    final String dateString = localDateTime.format(formatter);

    playerConfig.set("LastLoad", dateString);
  }

  @EventHandler
  public void onPlayerDataSave(final PlayerDataSaveEvent event) {
    final UUID playerID = event.getPlayerID();
    final YamlConfiguration playerConfig = event.getConfiguration();

    final Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
    final LocalDateTime localDateTime = java.time.LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-u hh:mm:ss");
    final String dateString = localDateTime.format(formatter);

    playerConfig.set("LastSave", dateString);
  }

}
