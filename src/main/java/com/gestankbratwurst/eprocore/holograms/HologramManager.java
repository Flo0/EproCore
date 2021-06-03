package com.gestankbratwurst.eprocore.holograms;

import com.gestankbratwurst.eprocore.holograms.packetholograms.ProtocolHologramProvider;
import com.gestankbratwurst.eprocore.utils.ChunkTracker;
import com.gestankbratwurst.eprocore.utils.UtilChunk;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 15.04.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class HologramManager {

  @Getter
  private final boolean enabled;
  private final IHologramProvider<?> hologramProvider;
  private final Map<UUID, IHologram> hologramMap;
  private final Map<Long, Set<IHologram>> chunkHologramMap;
  private final Map<UUID, Long> hologramIdChunkMap;
  private final HologramRunnable hologramRunnable;

  public HologramManager(final JavaPlugin plugin) {
    this.enabled = (this.hologramProvider = resolveProvider()) != null;
    this.hologramMap = new HashMap<>();
    this.chunkHologramMap = new HashMap<>();
    this.hologramIdChunkMap = new HashMap<>();
    this.hologramRunnable = new HologramRunnable(this);
    Bukkit.getScheduler().runTaskTimer(plugin, this.hologramRunnable, 1L, 1L);
    Bukkit.getPluginManager().registerEvents(new HologramListener(this), plugin);
    ChunkTracker.onLoad(this::showHologramsInChunk);
    ChunkTracker.onUnload(this::hideHologramsInChunk);
  }

  private void showHologramsInChunk(final Player player, final long chunkKey) {
    this.chunkHologramMap.getOrDefault(chunkKey, new HashSet<>()).forEach(hologram -> hologram.addViewer(player));
  }

  private void hideHologramsInChunk(final Player player, final Long chunkKey) {
    this.chunkHologramMap.getOrDefault(chunkKey, new HashSet<>()).forEach(hologram -> hologram.removeViewer(player));
  }

  public IHologram getHologram(final UUID hologramID) {
    return this.hologramMap.get(hologramID);
  }

  public void terminate(final Player player) {
    this.hologramMap.values().forEach(hologram -> hologram.removeViewer(player));
  }

  public IHologram createMovingHologram(final Location location, final Vector velocity, final int ticks) {
    final IHologram hologram = this.createHologram(location);
    this.hologramRunnable.addHologram(hologram, velocity, ticks);
    return hologram;
  }

  public IHologram createHologram(final Location location) {
    final IHologram hologram = this.hologramProvider.createHologram(location);

    final long chunkKey = UtilChunk.getChunkKey(location);

    this.hologramMap.put(hologram.getHologramID(), hologram);
    this.hologramIdChunkMap.put(hologram.getHologramID(), chunkKey);
    this.chunkHologramMap.computeIfAbsent(chunkKey, (key) -> new HashSet<>()).add(hologram);

    ChunkTracker.getViewingPlayers(chunkKey).forEach(hologram::addViewer);
    return hologram;
  }

  public IHologram removeHologram(final UUID hologramID) {
    final IHologram hologram = this.hologramMap.remove(hologramID);
    if (hologram != null) {
      hologram.cleanView();
    }
    final Long chunkKey = this.hologramIdChunkMap.remove(hologramID);
    if (chunkKey == null) {
      return hologram;
    }
    final Set<IHologram> chunkSet = this.chunkHologramMap.get(chunkKey);
    if (chunkSet == null) {
      return hologram;
    }
    chunkSet.remove(hologram);
    if (chunkSet.isEmpty()) {
      this.chunkHologramMap.remove(chunkKey);
    }
    return hologram;
  }

  private static IHologramProvider<?> resolveProvider() {
    if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
      return new ProtocolHologramProvider();
    } else {
      return null;
    }
  }

}