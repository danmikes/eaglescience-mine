package minesweeper.domain;

import java.util.Objects;

public class Cell {

  public int row;
  public int col;
  public int bombCount;
  public String val;
  public boolean bomb;
  public boolean flag;
  public boolean open;

  public Cell() {
  }

  public Cell(int row, int col, boolean bomb) {
    this.row = row;
    this.col = col;
    this.val = "#";
    this.bomb = bomb;
  }

  public void open() {
    this.open = true;
    this.setVal();
  }

  public void flag() {
    if (!this.open) this.flag = !this.flag;
    this.setVal();
  }

  public void setVal() {
    if (!this.open) {
      if (this.flag) {
        this.val = "F";
      } else {
        this.val = "#";
      }
    } else if (this.bomb) {
      this.val = "B";
    } else {
      if (this.bombCount == 0) {
        this.val = "0";
      } else {
        this.val = String.valueOf(this.bombCount);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return row == cell.row && col == cell.col && bombCount == cell.bombCount && bomb == cell.bomb && flag == cell.flag && open == cell.open;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col, bombCount, bomb, flag, open);
  }

  @Override
  public String toString() {
    return val;
  }
}
