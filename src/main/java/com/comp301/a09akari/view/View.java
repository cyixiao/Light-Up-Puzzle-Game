package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private final AlternateMvcController controller;

  public View(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    // Information View
    InformationView infoView = new InformationView(controller);
    layout.getChildren().add(infoView.render());

    // Puzzle View
    PuzzleView puzzleGrid = new PuzzleView(controller);
    layout.getChildren().add(puzzleGrid.render());

    // Button Operation View
    ButtonView buttonOperationView = new ButtonView(controller);
    layout.getChildren().add(buttonOperationView.render());

    return layout;
  }
}
