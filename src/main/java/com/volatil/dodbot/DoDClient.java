package com.volatil.dodbot;

import java.net.Socket;
import java.util.ArrayList;

import com.volatil.core.client.Client;
import com.volatil.core.client.bot.BotReceiver;
import com.volatil.core.client.bot.BotTransmitter;
import com.volatil.core.utils.ExitHandler;
import com.volatil.core.utils.Logger;
import com.volatil.core.utils.Message;
import com.volatil.core.utils.MessageQueue;

public class DoDClient extends Client {
  private MessageQueue<String> messages = new MessageQueue<String>();
  private ArrayList<DoDPlayerHandler> playerHandlers = new ArrayList<DoDPlayerHandler>();
  private BotReceiver incoming;
  private DoDBotTransmitter outgoing;
  private ExitHandler ex = new ExitHandler();
  private Logger log;

  public DoDClient(String[] args) {
    super(args);
    Socket serverSocket = getServerSocket();
    incoming = new BotReceiver(serverSocket, messages, log);
    outgoing = new DoDBotTransmitter(serverSocket, messages);
    this.log = log();
  }

  private class DoDBotTransmitter extends BotTransmitter {

    public DoDBotTransmitter(Socket serverSocket, MessageQueue<String> input) {
      super(serverSocket, input);
    }

    @Override
    protected String generateResponse(String message) {
      String origin = Message.extractOrigin(message);
      if (message.contains("JOIN")) {
        playerHandlers.add(new DoDPlayerHandler(origin, playerHandlers));
        return Message.generatePrivateMessage("Greetings.", origin);
      } else {
        for (DoDPlayerHandler p : playerHandlers) {
          if (p.connectedPlayer.equals(origin)) {
            return p.executeMessage(message);
          }
        }
      }
      return null;
    }

    public void sendGreeting() {
      out.println("Welcome to the Dungeon of Doom. Join a game with `JOIN` followed by my id!");
    }
  }

  @Override
  public void start() {
    log.info("Started.");
    outgoing.start();
    outgoing.sendGreeting();
    incoming.start();
    ex.start();
    while (true) {
      if (!outgoing.isAlive() || !incoming.isAlive() || !ex.isAlive())
        break;
    }
    cleanup();
  }

  @Override
  public void cleanup() {
    try {
      outgoing.interrupt();
      outgoing.join();
      incoming.interrupt();
      incoming.join();
      ex.interrupt();
      ex.join();
    } catch (InterruptedException e) {
      super.cleanup();
      log.error(e.getMessage());
    }
  }

  public static void main(String[] args) {
    DoDClient client = new DoDClient(args);
    client.start();
  }

}
