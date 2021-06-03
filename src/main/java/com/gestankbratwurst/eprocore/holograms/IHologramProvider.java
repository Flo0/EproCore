package com.gestankbratwurst.eprocore.holograms;

import org.bukkit.Location;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 15.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public interface IHologramProvider<T extends IHologram> {

  T createHologram(Location location);

}
