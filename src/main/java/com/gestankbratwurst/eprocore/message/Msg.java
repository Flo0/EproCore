package com.gestankbratwurst.eprocore.message;

import org.bukkit.command.CommandSender;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore5 and was created at the 04.06.2021
 *
 * EproCore5 can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class Msg {

  public static final String EPRO_ERROR_PREFIX = "§9Epro §f>> ";
  public static final String EPRO_PREFIX = "§9Epro §f>> ";
  private static final char ELEMENT_START = '{';
  private static final char ELEMENT_END = '}';
  private static final String ERROR_PREFIX = "§c";
  private static final String MESSAGE_PREFIX = "§7";
  private static final String ELEMENT_PREFIX = "§e";

  public static void sendInfo(final CommandSender target, final String message, final Object... elements) {
    final StringBuilder messageBuilder = new StringBuilder(EPRO_PREFIX);
    messageBuilder.append(MESSAGE_PREFIX);
    int elementIndex = 0;
    for (int index = 0; index < message.length(); index++) {
      final char currentChar = message.charAt(index);
      if (index + 1 < message.length() && currentChar == ELEMENT_START && message.charAt(index + 1) == ELEMENT_END) {
        messageBuilder.append(ELEMENT_PREFIX);
        messageBuilder.append(elements[elementIndex++].toString());
        messageBuilder.append(MESSAGE_PREFIX);
        index++;
      } else {
        messageBuilder.append(currentChar);
      }
    }
    target.sendMessage(messageBuilder.toString());
  }

  public static void sendError(final CommandSender target, final String message, final Object... elements) {
    final StringBuilder messageBuilder = new StringBuilder(EPRO_ERROR_PREFIX);
    messageBuilder.append(ERROR_PREFIX);
    int elementIndex = 0;
    for (int index = 0; index < message.length(); index++) {
      final char currentChar = message.charAt(index);
      if (index + 1 < message.length() && currentChar == ELEMENT_START && message.charAt(index + 1) == ELEMENT_END) {
        messageBuilder.append(ELEMENT_PREFIX);
        messageBuilder.append(elements[elementIndex++].toString());
        messageBuilder.append(ERROR_PREFIX);
        index++;
      } else {
        messageBuilder.append(currentChar);
      }
    }
    target.sendMessage(messageBuilder.toString());
  }

}
