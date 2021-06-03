package com.gestankbratwurst.eprocore.holograms;

import org.bukkit.Material;
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
public class SimpleTextLine implements LineRepresentation {

  public static SimpleTextLine of(final String text) {
    return new SimpleTextLine(text);
  }

  public SimpleTextLine(final String text) {
    this.text = text;
  }

  private final String text;

  @Override
  public boolean isText() {
    return true;
  }

  @Override
  public boolean isItem() {
    return false;
  }

  @Override
  public String getAsText() {
    return this.text;
  }

  @Override
  public ItemStack getAsItem() {
    return new ItemStack(Material.AIR);
  }
}
