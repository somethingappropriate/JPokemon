package org.jpokemon.pokedex;

import java.util.Scanner;

import jpkmn.exceptions.LoadException;

import org.jpokemon.JPokemonConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A gadget which helps keep track of which Pokemon have been seen or caught in
 * the world. Pokedex maintains a status on each Pokemon it tracks.
 */
public class Pokedex implements JPokemonConstants {
  public Pokedex() {
    _data = new PokedexStatus[POKEMONNUMBER];

    for (int i = 0; i < POKEMONNUMBER; i++)
      _data[i] = PokedexStatus.NONE;
  }

  /**
   * Update the Pokedex with having seen a new Pokemon
   * 
   * @param num Pokemon number
   */
  public void saw(int num) {
    if (num < 1 || num > POKEMONNUMBER || _data[num - 1] == PokedexStatus.OWN)
      return;

    _data[num - 1] = PokedexStatus.SAW;
  }

  /**
   * Update the Pokedex with having caught a new Pokemon.
   * 
   * @param num Pokemon number
   */
  public void own(int num) {
    if (num < 1 || num > POKEMONNUMBER)
      return;

    _data[num - 1] = PokedexStatus.OWN;
  }

  /**
   * Gets the status of a Pokemon in the Pokedex
   * 
   * @param num Pokemon number
   * @return PokedexStatus which describes the Pokemon number in question
   */
  public PokedexStatus status(int num) {
    if (num < 1 || num > POKEMONNUMBER)
      throw new IllegalArgumentException("#" + num + " out of range");

    return _data[num - 1];
  }

  public JSONObject toJSON() {
    JSONObject data = new JSONObject();
    JSONArray own = new JSONArray();
    JSONArray saw = new JSONArray();

    for (int i = 0; i < POKEMONNUMBER; i++) {
      if (_data[i] == PokedexStatus.SAW)
        saw.put(i);
      else if (_data[i] == PokedexStatus.OWN)
        own.put(i);
    }

    try {
      data.put("saw", saw);
      data.put("own", own);
    } catch (JSONException e) {
      e.printStackTrace();
      data = null;
    }

    return data;
  }

  /**
   * Loads this Pokedex with data.
   * 
   * @param s String representation of the Pokedex data
   * @throws LoadException If an error occurs with the data being loaded
   */
  public void load(String s) throws LoadException {
    Scanner scan = new Scanner(s);

    if (!scan.hasNext() || !scan.next().equals("DEX:"))
      throw new LoadException("Improper format: " + s);

    try {
      int number;
      PokedexStatus status;
      String[] parts;

      while (scan.hasNext()) {
        parts = scan.next().split("-");
        number = Integer.parseInt(parts[0]);
        status = PokedexStatus.valueOf(Integer.parseInt(parts[1]));

        _data[number] = status;
      }
    } catch (NumberFormatException e) {
      throw new LoadException("Numbers inparsable");
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new LoadException("Entry loaded above indicated size");
    }
  }

  private PokedexStatus[] _data;
}