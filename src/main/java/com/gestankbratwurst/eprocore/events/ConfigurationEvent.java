package com.gestankbratwurst.eprocore.events;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore5 and was created at the 03.06.2021
 *
 * EproCore5 can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ConfigurationEvent extends Event {

  private static final HandlerList HANDLERS_LIST = new HandlerList();

  public ConfigurationEvent(final YamlConfiguration configuration) {
    this.configuration = configuration;
  }

  @Getter
  private final YamlConfiguration configuration;

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS_LIST;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS_LIST;
  }
  
}
