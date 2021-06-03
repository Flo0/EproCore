package com.gestankbratwurst.eprocore.holograms.packetholograms;

import com.gestankbratwurst.eprocore.holograms.LineRepresentation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 17.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public interface IPacketArmorStand extends LineRepresentation {

  void showTo(Player player);

  void hideFrom(Player player);

  void updateFor(Player player);

  void moveTo(Location location);

  void setRepresentation(LineRepresentation representation);

}
