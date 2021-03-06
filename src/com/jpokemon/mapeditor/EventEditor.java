package com.jpokemon.mapeditor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jpokemon.trainer.Event;

import com.jpokemon.util.ui.button.JPokemonButton;
import com.jpokemon.util.ui.selector.EventSelector;

public class EventEditor implements MapEditComponent {
  public static final String BUTTON_NAME = "Events";

  public EventEditor() {
    JButton newEvent = new JPokemonButton("New");
    newEvent.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        onClickNewEvent();
      }
    });
    editorPanel.add(newEvent);

    eventSelector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        onEventSelect();
      }
    });
    editorPanel.add(eventSelector);

    descriptionField.setPreferredSize(new Dimension(160, 20));
    descriptionField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        onDescriptionFieldEnter();
      }
    });
    editorPanel.add(descriptionField);
  }

  @Override
  public JPanel getEditor() {
    readyToEdit = false;

    eventSelector.reload();

    if (eventSelector.getCurrentElement() != null) {
      descriptionField.setText(eventSelector.getCurrentElement().getDescription());
    }

    readyToEdit = true;
    return editorPanel;
  }

  @Override
  public Dimension getSize() {
    return new Dimension(200, 100);
  }

  private void onClickNewEvent() {
    Event.createNew();
    getEditor();
  }

  private void onEventSelect() {
    if (!readyToEdit) return;

    getEditor();
  }

  private void onDescriptionFieldEnter() {
    if (!readyToEdit) return;

    Event event = eventSelector.getCurrentElement();
    String description = descriptionField.getText();

    event.setDescription(description);
    event.commit();
    getEditor();
  }

  private boolean readyToEdit = false;
  private JPanel editorPanel = new JPanel();
  private JTextField descriptionField = new JTextField();
  private EventSelector eventSelector = new EventSelector();
}