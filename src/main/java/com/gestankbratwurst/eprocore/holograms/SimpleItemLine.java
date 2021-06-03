package com.gestankbratwurst.eprocore.holograms;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 27.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class SimpleItemLine implements LineRepresentation {

  public static SimpleItemLine of(final ItemStack itemStack) {
    return new SimpleItemLine(itemStack);
  }

  public SimpleItemLine(final ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  private final ItemStack itemStack;

  @Override
  public boolean isText() {
    return false;
  }

  @Override
  public boolean isItem() {
    return true;
  }

  @Override
  public String getAsText() {
    final ItemMeta meta = this.itemStack.getItemMeta();
    return meta == null || !meta.hasDisplayName() ? this.itemStack.getType().toString() : meta.getDisplayName();
  }

  @Override
  public ItemStack getAsItem() {
    return this.itemStack.clone();
  }
}
