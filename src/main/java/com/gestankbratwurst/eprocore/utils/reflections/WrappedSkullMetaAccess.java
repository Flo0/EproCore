package com.gestankbratwurst.eprocore.utils.reflections;

import com.mojang.authlib.GameProfile;
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
public class WrappedSkullMetaAccess {

  private final SkullMeta meta;
  private final ReflectiveAccess<? extends SkullMeta> access;

  public WrappedSkullMetaAccess(final SkullMeta meta) {
    this.meta = meta;
    this.access = ReflectionHelper.getAccessFor(meta.getClass());
  }

  public void setGameProfile(final GameProfile gameProfile) {
    this.access.invokeMethod(this.meta, "setProfile", gameProfile);
  }

}
