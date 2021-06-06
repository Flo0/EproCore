package com.gestankbratwurst.eprocore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import com.gestankbratwurst.eprocore.message.Msg;
import java.util.concurrent.ThreadLocalRandom;
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
    Msg.sendInfo(sender, "Deine Distanz zum Spawn ist: {}", distance);
  }

  @Subcommand("mobs count")
  @CommandPermission("example.cool.count")
  public void onCount(final Player sender) {
    final int allEntities = sender.getWorld().getEntities().size();
    Msg.sendInfo(sender, "Es gibt {} Mobs in deiner Welt.", allEntities);
  }

  @Subcommand("mobs countmonster")
  @CommandPermission("example.cool.countmonster")
  public void onCountMonster(final Player sender) {
    final long allEntities = sender.getWorld().getEntities().stream().filter(Monster.class::isInstance).count();
    Msg.sendInfo(sender, "Es gibt {} Monster in deiner Welt.", allEntities);
  }

  @Subcommand("mobs error")
  @CommandPermission("example.cool.error")
  public void onError(final Player sender) {
    final int roll = ThreadLocalRandom.current().nextInt(100);
    Msg.sendError(sender, "Du hast eine {} gerollt.", roll);
  }

}