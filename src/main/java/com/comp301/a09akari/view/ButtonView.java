package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class ButtonView implements FXComponent {
  private AlternateMvcController controller;

  public ButtonView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    // Index Notification
    Label lastNotification = new Label("IT IS THE LAST PUZZLE!");
    lastNotification.getStyleClass().add("index-notification");
    lastNotification.setVisible(false);

    Label firstNotification = new Label("IT IS THE FIRST PUZZLE!");
    firstNotification.getStyleClass().add("index-notification");
    firstNotification.setVisible(false);

    StackPane notificationsPane = new StackPane();
    notificationsPane.getChildren().addAll(firstNotification, lastNotification);
    notificationsPane
        .visibleProperty()
        .bind(Bindings.or(firstNotification.visibleProperty(), lastNotification.visibleProperty()));
    notificationsPane.managedProperty().bind(notificationsPane.visibleProperty());

    // Change Puzzle
    HBox changePuzzle = new HBox();
    Label changePuzzleText = new Label("Change Puzzle: ");
    changePuzzleText.getStyleClass().add("change-puzzle-text");

    Button toPreviousPuzzle = new Button("<<--");
    Button toNextPuzzle = new Button("-->>");
    Button toRandomPuzzle = new Button("Random Puzzle");
    toPreviousPuzzle.setOnAction(
        (ActionEvent event) -> {
          if (controller.getActivePuzzleIndex() == 0) {
            firstNotification.setVisible(true);
          }
          controller.clickPrevPuzzle();
        });
    toNextPuzzle.setOnAction(
        (ActionEvent event) -> {
          if (controller.getActivePuzzleIndex() + 1 == controller.getPuzzleLibrarySize()) {
            lastNotification.setVisible(true);
          }
          controller.clickNextPuzzle();
        });
    toRandomPuzzle.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    toPreviousPuzzle.getStyleClass().add("change-puzzle-button-arrows");
    toNextPuzzle.getStyleClass().add("change-puzzle-button-arrows");
    toRandomPuzzle.getStyleClass().add("change-puzzle-button-random");

    changePuzzle.getChildren().add(changePuzzleText);
    changePuzzle.getChildren().add(toPreviousPuzzle);
    changePuzzle.getChildren().add(toNextPuzzle);
    changePuzzle.getChildren().add(toRandomPuzzle);

    // Reset Puzzle
    StackPane resetPuzzle = new StackPane();
    resetPuzzle.getStyleClass().add("reset-puzzle-pane");
    Button resetButton = new Button("Reset");
    resetButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    resetButton.getStyleClass().add("reset-puzzle-button");
    resetPuzzle.getChildren().add(resetButton);

    // Add all to the layout
    layout.getChildren().add(changePuzzle);
    layout.getChildren().add(resetPuzzle);
    layout.getChildren().add(notificationsPane);

    return layout;
  }
}
