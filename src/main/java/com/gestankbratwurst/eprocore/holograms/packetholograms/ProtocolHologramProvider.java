package com.gestankbratwurst.eprocore.holograms.packetholograms;

import com.gestankbratwurst.eprocore.holograms.IHologramProvider;
import java.util.UUID;
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
public class ProtocolHologramProvider implements IHologramProvider<ProtocolHologram> {

  @Override
  public ProtocolHologram createHologram(final Location location) {
    return new ProtocolHologram(location, UUID.randomUUID());
  }

}
