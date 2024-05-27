package com.comp301.a09akari.model;

import javafx.scene.control.Cell;

public class PuzzleImpl implements Puzzle {
  private int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board.clone();
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= getHeight() || c >= getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (board[r][c] == 0
        || board[r][c] == 1
        || board[r][c] == 2
        || board[r][c] == 3
        || board[r][c] == 4) {
      return CellType.CLUE;
    } else if (board[r][c] == 5) {
      return CellType.WALL;
    } else if (board[r][c] == 6) {
      return CellType.CORRIDOR;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r >= getHeight() || c >= getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getCellType(r, c) == CellType.CLUE) {
      return board[r][c];
    } else {
      throw new IllegalArgumentException();
    }
  }
}
