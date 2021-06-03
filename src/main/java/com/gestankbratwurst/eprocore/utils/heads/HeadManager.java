package com.gestankbratwurst.eprocore.utils.heads;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 16.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class HeadManager {

  private static boolean initialized = false;

  public static void init() {
    if (initialized) {
      return;
    }
    new HeadManager();
    initialized = true;
  }

  private final Map<String, ItemStack> headMap;

  private HeadManager() {
    this.headMap = new HashMap<>();
    BaseHead.init(this);
  }

  public ItemStack getHeadOf(final EntityType type) {
    return this.getHead(type.toString());
  }

  public ItemStack getHead(final String base64) {
    return this.headMap.computeIfAbsent(base64, this::createHead).clone();
  }

  private ItemStack createHead(final String base64) {
    final ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
    final SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
    assert skullMeta != null;
    final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
    profile.getProperties().add(new ProfileProperty("textures", base64));
    skullMeta.setPlayerProfile(profile);
    skullItem.setItemMeta(skullMeta);
    skullMeta.setLore(Collections.singletonList(" "));
    return skullItem;
  }

}