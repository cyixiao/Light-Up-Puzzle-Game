package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class PuzzleView implements FXComponent {
  private AlternateMvcController controller;

  public PuzzleView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane layout = new GridPane();
    layout.getStyleClass().add("puzzle-grid");

    layout.setHgap(5);
    layout.setVgap(5);

    // Set Cells
    for (int i = 0; i < controller.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < controller.getActivePuzzle().getWidth(); j++) {
        if (controller.getActivePuzzle().getCellType(i, j) == CellType.WALL) {
          Label wall = new Label("");
          wall.getStyleClass().add("wall-cell");
          layout.add(wall, i, j);
        }
        if (controller.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          if (controller.isLamp(i, j)) {
            Button lamp = new Button();
            int finalI = i;
            int finalJ = j;
            lamp.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            if (controller.getIsLampIllegal(i, j)) {
              lamp.getStyleClass().add("illegal-lamp-cell-button");
            } else {
              lamp.getStyleClass().add("lamp-cell-button");
            }
            layout.add(lamp, i, j);
          } else if (controller.isLit(i, j)) {
            Button lit = new Button();
            int finalI = i;
            int finalJ = j;
            lit.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            lit.getStyleClass().add("lit-cell-button");
            layout.add(lit, i, j);
          } else {
            Button empty = new Button();
            int finalI = i;
            int finalJ = j;
            empty.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            empty.getStyleClass().add("empty-cell-button");
            layout.add(empty, i, j);
          }
        } else if (controller.getActivePuzzle().getCellType(i, j) == CellType.CLUE) {
          Label clue = new Label(Integer.toString(controller.getActivePuzzle().getClue(i, j)));
          if (controller.isClueSatisfied(i, j)) {
            clue.getStyleClass().add("clue-satisfied");
            layout.add(clue, i, j);
          } else {
            clue.getStyleClass().add("clue-not-satisfied");
            layout.add(clue, i, j);
          }
        }
      }
    }

    return layout;
  }
}
