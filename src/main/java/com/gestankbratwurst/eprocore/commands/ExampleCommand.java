package com.gestankbratwurst.eprocore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

@CommandAlias("cc|cool|coolcommand")
@CommandPermission("example.cool")
public class ExampleCommand extends BaseCommand {

  @HelpCommand
  public void onNoArgs(final Player sender) {
    sender.sendMessage("Commands:");
    sender.sendMessage("/cc help");
    sender.sendMessage("/cc distance");
    sender.sendMessage("/cc count");
    sender.sendMessage("/cc counthostile");
  }

  @Subcommand("distance")
  @CommandPermission("example.cool.distance")
  public void onDistance(final Player sender) {
    final int distance = (int) sender.getLocation().distance(sender.getWorld().getSpawnLocation());
    sender.sendMessage("Deine Distanz zum Spawn ist: §e" + distance);
  }

  @Subcommand("mobs count")
  @CommandPermission("example.cool.count")
  public void onCount(final Player sender) {
    final int allEntities = sender.getWorld().getEntities().size();
    sender.sendMessage("Es gibt §e" + allEntities + "§r Mobs in deiner Welt.");
  }

  @Subcommand("mobs countmonster")
  @CommandPermission("example.cool.countmonster")
  public void onCountMonster(final Player sender) {
    final long allEntities = sender.getWorld().getEntities().stream().filter(Monster.class::isInstance).count();
    sender.sendMessage("Es gibt §e" + allEntities + "§r Monster in deiner Welt.");
  }

}