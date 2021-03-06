package org.jpokemon.overworld;

import java.util.ArrayList;
import java.util.List;

import org.jpokemon.pokemon.Pokemon;
import org.jpokemon.server.JPokemonServer;

import com.njkremer.Sqlite.DataConnectionException;
import com.njkremer.Sqlite.DataConnectionManager;
import com.njkremer.Sqlite.SqlStatement;

public class WildPokemon {
  private String map;
  private int number, levelmin, levelmax, flex;

  public Pokemon instantiate() {
    int level = (int) ((levelmax - levelmin + 1) * Math.random()) + levelmin;

    return new Pokemon(number, level);
  }

  public static List<WildPokemon> get(String map) {
    DataConnectionManager.init(JPokemonServer.databasepath);

    try {
      return SqlStatement.select(WildPokemon.class).where("map").eq(map).getList();
    }
    catch (DataConnectionException e) {
      e.printStackTrace();
    }

    return new ArrayList<WildPokemon>();
  }

  //@preformat
  public String getMap() {return map; } public void setMap(String m) {map = m; }
  public int getNumber() {return number; } public void setNumber(int n) {number = n; }
  public int getLevelmin() {return levelmin; } public void setLevelmin(int l) {levelmin = l; }
  public int getLevelmax() {return levelmax; } public void setLevelmax(int l) {levelmax = l; }
  public int getFlex() {return flex; } public void setFlex(int f) {flex = f; }
  //@format
}
