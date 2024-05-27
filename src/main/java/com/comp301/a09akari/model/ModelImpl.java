package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary puzzleLibrary;
  private int activePuzzleIndex;
  private boolean[][] lampLocation;
  private List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException();
    }
    this.puzzleLibrary = library;
    this.activePuzzleIndex = 0;
    lampLocation =
        new boolean[this.puzzleLibrary.getPuzzle(this.activePuzzleIndex).getHeight()]
            [this.puzzleLibrary.getPuzzle(this.activePuzzleIndex).getWidth()];
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (!lampLocation[r][c]) {
      lampLocation[r][c] = true;
    }
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (lampLocation[r][c]) {
      lampLocation[r][c] = false;
    }
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    // go up
    for (int i = r; i >= 0; i--) {
      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[i][c]) {
        return true;
      }
    }
    // go down
    for (int i = r; i < getActivePuzzle().getHeight(); i++) {
      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[i][c]) {
        return true;
      }
    }
    // go left
    for (int i = c; i >= 0; i--) {
      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[r][i]) {
        return true;
      }
    }
    // go right
    for (int i = c; i < getActivePuzzle().getWidth(); i++) {
      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[r][i]) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return lampLocation[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (!lampLocation[r][c]) {
      throw new IllegalArgumentException();
    }
    // go up
    for (int i = r - 1; i >= 0; i--) {
      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[i][c]) {
        return true;
      }
    }
    // go down
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[i][c]) {
        return true;
      }
    }
    // go left
    for (int i = c - 1; i >= 0; i--) {
      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[r][i]) {
        return true;
      }
    }
    // go right
    for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
      if (lampLocation[r][i]) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(activePuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= puzzleLibrary.size()) {
      throw new IndexOutOfBoundsException();
    }
    activePuzzleIndex = index;
    lampLocation = new boolean[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    lampLocation = new boolean[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < getActivePuzzle().getWidth(); j++) {
        if (getActivePuzzle().getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        } else if (getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          if (lampLocation[i][j]) {
            if (isLampIllegal(i, j)) {
              return false;
            }
          }
          if (!isLit(i, j)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int counter = 0;
    // check above
    if (r - 1 >= 0) {
      if (lampLocation[r - 1][c]) {
        counter++;
      }
    }
    // check below
    if (r + 1 < getActivePuzzle().getHeight()) {
      if (lampLocation[r + 1][c]) {
        counter++;
      }
    }
    // check left
    if (c - 1 >= 0) {
      if (lampLocation[r][c - 1]) {
        counter++;
      }
    }
    // check right
    if (c + 1 < getActivePuzzle().getWidth()) {
      if (lampLocation[r][c + 1]) {
        counter++;
      }
    }
    return counter == getActivePuzzle().getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  public void notifyObservers() {
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }
}
