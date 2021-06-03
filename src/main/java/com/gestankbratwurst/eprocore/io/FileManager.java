package com.gestankbratwurst.eprocore.io;

import com.gestankbratwurst.eprocore.TaskManager;
import com.gestankbratwurst.eprocore.events.ConfigurationInitEvent;
import com.gestankbratwurst.eprocore.events.ConfigurationLoadEvent;
import com.gestankbratwurst.eprocore.events.PlayerDataInitEvent;
import com.gestankbratwurst.eprocore.events.PlayerDataLoadEvent;
import com.gestankbratwurst.eprocore.events.PlayerDataSaveEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore5 and was created at the 03.06.2021
 *
 * EproCore5 can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class FileManager {

  private final Map<UUID, YamlConfiguration> playerDataMap = new ConcurrentHashMap<>();
  private final File playerDataFolder;
  private final File configFile;
  private YamlConfiguration pluginConfig;

  public FileManager(final JavaPlugin plugin) {
    final File mainFolder = plugin.getDataFolder();
    this.playerDataFolder = new File(mainFolder + File.separator + "playerdata");
    if (!this.playerDataFolder.exists()) {
      if (!this.playerDataFolder.mkdirs()) {
        throw new IllegalStateException();
      }
    }
    this.configFile = new File(mainFolder, "configuration.yml");
    this.loadConfig();
    final int ticksMins = EproConfigValues.getMinutesBetweenPlayerSaves() * 60 * 20;
    TaskManager.runTaskTimer(this::triggerFullSave, ticksMins, ticksMins);
  }

  private void loadConfig() {
    if (!this.configFile.exists()) {
      this.pluginConfig = YamlConfiguration.loadConfiguration(this.configFile);
    } else {
      this.pluginConfig = new YamlConfiguration();
      final ConfigurationInitEvent event = new ConfigurationInitEvent(this.pluginConfig);
      Bukkit.getPluginManager().callEvent(event);
    }
    final ConfigurationLoadEvent event = new ConfigurationLoadEvent(this.pluginConfig);
    Bukkit.getPluginManager().callEvent(event);
  }

  public void saveConfig() {
    try {
      this.pluginConfig.save(this.configFile);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  private File getPlayerFile(final UUID playerID) {
    return new File(this.playerDataFolder, playerID.toString() + ".yml");
  }

  private void triggerFullSave() {
    int delay = 0;
    for (final UUID playerID : new ArrayList<>(this.playerDataMap.keySet())) {
      TaskManager.runTaskLater(() -> this.savePlayerData(playerID), delay += 2);
    }
    TaskManager.runTaskLater(this::saveConfig, delay + 10);
  }

  public void loadPlayerData(final UUID playerID) {
    final File playerFile = this.getPlayerFile(playerID);
    final YamlConfiguration configuration;
    if (!playerFile.exists()) {
      configuration = new YamlConfiguration();
      final PlayerDataInitEvent event = new PlayerDataInitEvent(playerID, configuration);
      Bukkit.getPluginManager().callEvent(event);
    } else {
      configuration = YamlConfiguration.loadConfiguration(playerFile);
    }
    final PlayerDataLoadEvent event = new PlayerDataLoadEvent(playerID, configuration);
    Bukkit.getPluginManager().callEvent(event);
    this.playerDataMap.put(playerID, configuration);
  }

  public void savePlayerData(final UUID playerID) {
    final YamlConfiguration configuration = this.playerDataMap.get(playerID);
    if (configuration == null) {
      return;
    }
    final PlayerDataSaveEvent event = new PlayerDataSaveEvent(playerID, configuration);
    Bukkit.getPluginManager().callEvent(event);
    try {
      configuration.save(this.getPlayerFile(playerID));
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

}