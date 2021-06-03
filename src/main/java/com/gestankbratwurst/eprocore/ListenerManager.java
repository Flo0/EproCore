package com.gestankbratwurst.eprocore;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
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
public class ListenerManager {

  private static final JavaPlugin PLUGIN = JavaPlugin.getPlugin(EproCore.class);

  public static void registerListener(Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, PLUGIN);
  }

}
