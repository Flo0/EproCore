package com.gestankbratwurst.eprocore.utils;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of HopperMachines and was created at the 31.05.2021
 *
 * HopperMachines can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class ChunkTracker implements Listener {

  private static final Map<Player, Set<Long>> CHUNK_VIEW_MAP = Maps.newHashMap();
  private static final Set<BiConsumer<Player, Long>> CHUNK_LOAD_CONSUMER = new HashSet<>();
  private static final Set<BiConsumer<Player, Long>> CHUNK_UNLOAD_CONSUMER = new HashSet<>();
  private static final Map<Long, Set<Player>> CHUNK_PLAYER_MAP = new HashMap<>();

  public ChunkTracker(final JavaPlugin host) {
    Bukkit.getPluginManager().registerEvents(this, host);
    Bukkit.getOnlinePlayers().forEach((player) -> CHUNK_VIEW_MAP.put(player, Sets.newHashSet()));
    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(host, Server.MAP_CHUNK) {
      @Override
      public void onPacketSending(final PacketEvent event) {
        if (event.getPacketType() == Server.MAP_CHUNK) {
          if (event.getPacket().getMeta("phoenix-ignore").isPresent()) {
            return;
          }
          Bukkit.getScheduler().runTask(host, () -> {
            final PacketContainer packet = event.getPacket();
            final List<Integer> coords = packet.getIntegers().getValues();
            final Player player = event.getPlayer();
            final long chunkKey = (long) coords.get(0) & 4294967295L | ((long) coords.get(1) & 4294967295L) << 32;
            ChunkTracker.CHUNK_VIEW_MAP.get(player).add(chunkKey);
            ChunkTracker.CHUNK_PLAYER_MAP.computeIfAbsent(chunkKey, key -> new HashSet<>()).add(player);
            ChunkTracker.CHUNK_LOAD_CONSUMER.forEach(consumer -> consumer.accept(player, chunkKey));
          });
        }
      }
    });
    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(host, Server.UNLOAD_CHUNK) {
      @Override
      public void onPacketSending(final PacketEvent event) {
        if (event.getPacketType() == Server.UNLOAD_CHUNK) {
          if (event.getPacket().getMeta("phoenix-ignore").isPresent()) {
            return;
          }
          Bukkit.getScheduler().runTask(host, () -> {
            final PacketContainer packet = event.getPacket();
            final List<Integer> coords = packet.getIntegers().getValues();
            final Player player = event.getPlayer();
            final long chunkKey = (long) coords.get(0) & 4294967295L | ((long) coords.get(1) & 4294967295L) << 32;
            ChunkTracker.CHUNK_VIEW_MAP.get(player).remove(chunkKey);
            final Set<Player> chunkSet = ChunkTracker.CHUNK_PLAYER_MAP.get(chunkKey);
            if (chunkSet != null) {
              chunkSet.remove(player);
              if (chunkSet.isEmpty()) {
                ChunkTracker.CHUNK_PLAYER_MAP.remove(chunkKey);
              }
            }
            ChunkTracker.CHUNK_UNLOAD_CONSUMER.forEach(consumer -> consumer.accept(player, chunkKey));
          });
        }
      }
    });
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onJoin(final PlayerJoinEvent event) {
    CHUNK_VIEW_MAP.put(event.getPlayer(), Sets.newHashSet());
  }

  @EventHandler
  public void onQuit(final PlayerQuitEvent event) {
    CHUNK_VIEW_MAP.remove(event.getPlayer());
  }

  public static void onUnload(final BiConsumer<Player, Long> unloadConsumer) {
    CHUNK_UNLOAD_CONSUMER.add(unloadConsumer);
  }

  public static void onLoad(final BiConsumer<Player, Long> loadConsumer) {
    CHUNK_LOAD_CONSUMER.add(loadConsumer);
  }

  public static Set<Long> getChunkViews(final Player player) {
    return CHUNK_VIEW_MAP.get(player);
  }

  public static boolean isChunkInView(final Player player, final long chunkKey) {
    return CHUNK_VIEW_MAP.get(player).contains(chunkKey);
  }

  public static boolean isChunkInView(final Player player, final Chunk chunk) {
    return isChunkInView(player, UtilChunk.getChunkKey(chunk));
  }

  public static Set<Long> getChunkViewOf(final Player player) {
    return CHUNK_VIEW_MAP.get(player);
  }

  public static Set<Player> getViewingPlayers(final long chunkKey) {
    return CHUNK_PLAYER_MAP.getOrDefault(chunkKey, new HashSet<>());
  }
}
