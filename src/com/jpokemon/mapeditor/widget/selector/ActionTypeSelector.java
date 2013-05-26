package com.jpokemon.mapeditor.widget.selector;

import org.jpokemon.action.ActionType;

public class ActionTypeSelector extends JPokemonSelector<ActionType> {
  @Override
  protected void reloadItems() {
    removeAllItems();

    for (ActionType actionType : ActionType.values()) {
      addElementToModel(actionType);
    }
  }

  private static final long serialVersionUID = 1L;
}