package com.gestankbratwurst.eprocore;

import com.gestankbratwurst.eprocore.commands.ExampleCommand;
import com.gestankbratwurst.eprocore.holograms.HologramManager;
import com.gestankbratwurst.eprocore.io.DataListener;
import com.gestankbratwurst.eprocore.io.FileManager;
import com.gestankbratwurst.eprocore.io.IOListener;
import com.gestankbratwurst.eprocore.utils.ChunkTracker;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class EproCore extends JavaPlugin {

  @Getter
  private static HologramManager hologramManager;
  private FileManager fileManager;

  @Override
  public void onEnable() {
    new ChunkTracker(this);
    hologramManager = new HologramManager(this);
    this.fileManager = new FileManager(this);

    final IOListener ioListener = new IOListener(this.fileManager);
    ListenerManager.registerListener(ioListener);

    final DataListener dataListener = new DataListener();
    ListenerManager.registerListener(dataListener);

    CommandManager.registerCommand(new ExampleCommand());
  }

  @Override
  public void onDisable() {
    this.fileManager.saveConfig();
  }

}