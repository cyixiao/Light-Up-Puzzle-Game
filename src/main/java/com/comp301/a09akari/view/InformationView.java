package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

public class InformationView implements FXComponent {
  private AlternateMvcController controller;

  public InformationView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    // Title: Akari: LIGHT UP
    HBox puzzleTitle = new HBox();
    Label lightUp = new Label("Akari: LIGHT UP");
    lightUp.getStyleClass().add("label-light-up");

    puzzleTitle.getChildren().add(lightUp);
    puzzleTitle.setAlignment(Pos.CENTER);

    // Changeable message
    HBox message = new HBox();

    String dimen =
        String.valueOf(controller.getActivePuzzle().getWidth())
            + " x "
            + String.valueOf(controller.getActivePuzzle().getHeight());

    Label solvingMessage =
        new Label("It is a " + dimen + " puzzle! Try to light up all the white squares!");
    Label successMessage = new Label("Congratulation! You've solved this " + dimen + " puzzle!");
    solvingMessage.getStyleClass().add("solving-message");
    successMessage.getStyleClass().add("success-message");

    if (controller.isSolved()) {
      message.getChildren().add(successMessage);
    } else {
      message.getChildren().add(solvingMessage);
    }

    // Puzzle Index Information
    HBox indexInfo = new HBox();
    Label totalNumStr = new Label("Total Puzzles:");
    Label activeIndexStr = new Label("Active Puzzle:");
    Label totalNum = new Label(Integer.toString(controller.getPuzzleLibrarySize()));
    Label activeIndex = new Label(Integer.toString(controller.getActivePuzzleIndex() + 1));

    totalNumStr.getStyleClass().add("total-num-str");
    activeIndexStr.getStyleClass().add("active-index-str");
    totalNum.getStyleClass().add("total-num");
    activeIndex.getStyleClass().add("active-index");

    indexInfo.getChildren().add(totalNumStr);
    indexInfo.getChildren().add(totalNum);
    indexInfo.getChildren().add(activeIndexStr);
    indexInfo.getChildren().add(activeIndex);

    // Add all to the layout
    layout.getChildren().add(puzzleTitle);
    layout.getChildren().add(message);
    layout.getChildren().add(indexInfo);

    return layout;
  }
}
