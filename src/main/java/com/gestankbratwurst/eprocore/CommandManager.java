package com.gestankbratwurst.eprocore;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import java.util.function.Consumer;
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
public class CommandManager {

  private static final JavaPlugin PLUGIN = JavaPlugin.getPlugin(EproCore.class);
  private static PaperCommandManager commandManager;

  private static PaperCommandManager getOrCreateCommandManager() {
    if (commandManager == null) {
      commandManager = new PaperCommandManager(PLUGIN);
    }
    return commandManager;
  }

  public static void registerCommand(final BaseCommand command) {
    getOrCreateCommandManager().registerCommand(command);
  }

  public static void apply(final Consumer<PaperCommandManager> commandManagerConsumer) {
    commandManagerConsumer.accept(getOrCreateCommandManager());
  }

}
