package com.volatil.dodbot;

import java.util.ArrayList;

import com.volatil.core.utils.Message;
import com.volatil.dod.game.RemoteGameLogic;

public class DoDPlayerHandler {
  public String connectedPlayer;
  private RemoteGameLogic game;
  private ArrayList<DoDPlayerHandler> players;

  public DoDPlayerHandler(String player, ArrayList<DoDPlayerHandler> players) {
    this.connectedPlayer = player;
    this.game = new RemoteGameLogic();
    this.players = players;
  }

  /**
   * Parses the message sent to the DoD bot and executes the command contained
   * within the game.
   * 
   * @param message The message sent to the bot.
   * @return String The response produced by the game.
   */
  public String executeMessage(String message) {
    String parsed = Message.parsePrivateMessage(message);
    String response = game.executeMove(parsed);
    if (response.contains("WIN") || response.contains("LOSE"))
      players.remove(this);

    return response;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof DoDPlayerHandler))
      return false;

    DoDPlayerHandler dph = (DoDPlayerHandler) obj;
    return this.connectedPlayer.equals(dph.connectedPlayer);
  }
}
