package com.gestankbratwurst.eprocore;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore5 and was created at the 03.06.2021
 *
 * EproCore5 can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class TaskManager {

  private static final JavaPlugin PLUGIN = JavaPlugin.getPlugin(EproCore.class);

  public static BukkitTask runTask(Runnable runnable) {
    return Bukkit.getScheduler().runTask(PLUGIN, runnable);
  }

  public static BukkitTask runTaskTimer(Runnable runnable, int initialDelay, int intervalDelay) {
    return Bukkit.getScheduler().runTaskTimer(PLUGIN, runnable, initialDelay, initialDelay);
  }

  public static BukkitTask runTaskLater(Runnable runnable, int delay) {
    return Bukkit.getScheduler().runTaskLater(PLUGIN, runnable, delay);
  }

  public static BukkitTask runTaskLaterAsynchronously(Runnable runnable, int delay) {
    return Bukkit.getScheduler().runTaskLaterAsynchronously(PLUGIN, runnable, delay);
  }

  public static <T> Future<T> callMethodSync(Callable<T> callable) {
    return Bukkit.getScheduler().callSyncMethod(PLUGIN, callable);
  }

}
