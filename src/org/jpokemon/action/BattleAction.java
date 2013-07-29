package org.jpokemon.action;

import java.io.File;
import java.io.FileNotFoundException;

import org.jpokemon.activity.BattleActivity;
import org.jpokemon.activity.PlayerManager;
import org.jpokemon.activity.ServiceException;
import org.jpokemon.server.JPokemonServer;
import org.jpokemon.trainer.Player;
import org.jpokemon.trainer.Trainer;
import org.zachtaylor.jnodalxml.XmlNode;
import org.zachtaylor.jnodalxml.XmlParser;

public class BattleAction extends Action {
  public BattleAction(String data) {
    super(data);
  }

  public void execute(Player player) throws ServiceException {
    String fileName = player.record().replaceMacros(getData());
    String filePath = JPokemonServer.scriptedbattlepath + fileName + ".jpkmn";

    XmlNode trainerData;

    try {
      trainerData = XmlParser.parse(new File(filePath)).get(0);
    } catch (FileNotFoundException e) {
      throw new ServiceException("Trainer file not found: " + getData());
    }

    Trainer trainer = new Trainer(getData());
    trainer.loadXml(trainerData);

    if (!player.record().getTrainer(trainer.id())) {
      PlayerManager.addActivity(player, new BattleActivity(player, trainer));
    }
  }
}