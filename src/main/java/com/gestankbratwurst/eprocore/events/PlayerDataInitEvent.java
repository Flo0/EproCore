package com.gestankbratwurst.eprocore.events;

import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore5 and was created at the 03.06.2021
 *
 * EproCore5 can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class PlayerDataInitEvent extends PlayerDataEvent {

  public PlayerDataInitEvent(final UUID playerID, final YamlConfiguration configuration) {
    super(playerID, configuration);
  }

}