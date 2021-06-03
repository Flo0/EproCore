package com.gestankbratwurst.eprocore.holograms;

import org.bukkit.inventory.ItemStack;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 27.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public interface LineRepresentation {

  boolean isText();

  boolean isItem();

  String getAsText();

  ItemStack getAsItem();

}
