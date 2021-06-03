package com.gestankbratwurst.eprocore.events;

import java.util.UUID;
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
public class PlayerDataEvent extends Event {

  private static final HandlerList HANDLERS_LIST = new HandlerList();

  public PlayerDataEvent(final UUID playerID, final YamlConfiguration configuration) {
    this.playerID = playerID;
    this.configuration = configuration;
  }

  @Getter
  private final UUID playerID;
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
