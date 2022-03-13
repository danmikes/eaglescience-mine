package minesweeper.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Field {

  public int SIDE;
  public int BOMBS;
  public int SIZE;
  public List<List<Cell>> cells;
  public boolean play;

  public Field() {
  }

  public Field(int SIDE, int BOMBS) {
    this.SIDE = SIDE;
    this.BOMBS = BOMBS;
    this.SIZE = SIDE * SIDE;
    this.cells = new ArrayList<>(SIDE);
    for (int r = 0; r < SIDE; r++) {
      this.cells.add(new ArrayList<>());
      for (int c = 0; c < SIDE; c++) {
        this.cells.get(r).add(new Cell(r,c, placeBomb()));
      }
    }
    this.play = true;
    facesBombs();
  }

  public void showField() {
    for (List<Cell> row : this.cells) {
      for (Cell col : row) {
        col.setVal();
      }
    }
    System.out.println(this);
  }

  public void play() {
    System.out.println("enter triplet (rca); example: 11f");
    System.out.println("r=[0-" + (SIDE-1) + "] c=[0-" + (SIDE-1) + "] a=[f,o] (f=flag o=open)");
  }

  public void win() {
    System.out.println("you win");
    openAll();
  }

  public void lose() {
    System.out.println("you lose");
    openAll();
  }

  public void open(int r, int c) {
    if (isOpen(r,c)) return;
    if (isBomb(r,c)) {
      this.play = false;
      openAll();
      lose();
    } else if (allOpen()) {
      this.play = false;
      openAll();
      win();
    } else {
      for (int y = r-1; y <= r+1; y++) {
        for (int x = c-1; x <= c+1; x++) {
          if (isCell(y,x) && !isBomb(y,x)) {
            this.cells.get(y).get(x).open = true;
            open(y,x);
          }
        }
      }
    }
  }

  public void openAll() {
    for (int r = 0; r < SIDE; r++) {
      for (int c = 0; c < SIDE; c++) {
        if (!this.cells.get(r).get(c).open) {
          this.cells.get(r).get(c).open = true;
        }
      }
    }
  }

  public boolean allOpen() {
    int openCount = 0;
    for (int r = 0; r < SIDE; r++) {
      for (int c = 0; c < SIDE; c++) {
        if (isOpen(r,c)) {
          openCount++;
        }
      }
    }
    return openCount + BOMBS == this.SIZE;
  }

  public void flag(int r, int c) {
    if (!isOpen(r,c)) this.cells.get(r).get(c).flag = !this.cells.get(r).get(c).flag;
  }

  public boolean placeBomb() {
    return new Random().nextInt(SIZE / BOMBS + 1) < 1;
  }

  public void facesBombs() {
    for (int r = 0; r < SIDE; r++) {
      for (int c = 0; c < SIDE; c++) {
        this.cells.get(r).get(c).bombCount = countBombs(r,c);
      }
    }
  }

  public int countBombs(int r, int c) {
    int bombCount = 0;
    for (int y = r-1; y <= r+1; y++) {
      for (int x = c-1; x <= c+1; x++) {
        if (isCell(y,x) && isBomb(y,x)) {
          bombCount++;
        }
      }
    }
    return bombCount;
  }

  public boolean isOpen(int r, int c) {
    return this.cells.get(r).get(c).open;
  }

  public boolean isCell(int r, int c) {
    return r >= 0 && r < SIDE && c >= 0 && c < SIDE;
  }

  public boolean isBomb(int r, int c) {
    return this.cells.get(r).get(c).bomb;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Field field = (Field) o;
    return SIDE == field.SIDE && BOMBS == field.BOMBS && SIZE == field.SIZE && play == field.play && Objects.equals(cells, field.cells);
  }

  @Override
  public int hashCode() {
    return Objects.hash(SIDE, BOMBS, SIZE, cells, play);
  }

  @Override
  public String toString() {
    String string = "    ";
    for (int c = 0; c < SIDE; c++) {
      string += c + " ";
    }
    string += "\n   ";
    for (int c = 0; c < SIDE; c++) {
      string += "__";
    }
    string += "\n";
    for (int r = 0; r < SIDE; r++) {
      string += r + "  |";
      for (int c = 0; c < SIDE; c++) {
        string += this.cells.get(r).get(c).val + " ";
      }
      string += "\n";
    }
    return string;
  }
}
