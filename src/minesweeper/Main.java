package minesweeper;

import minesweeper.domain.Field;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Field field = new Field(4,4);
    field.showField();

    do {
      field.play();
      Scanner scanner = new Scanner(System.in);
      String string = scanner.next();
      int r = Integer.parseInt(string.substring(0,1));
      int c = Integer.parseInt(string.substring(1,2));
      char a = string.charAt(2);
      switch (a) {
        case 'f':
          field.flag(r,c);
          break;
        case 'o':
          field.open(r,c);
          break;
      }
      field.showField();
    } while (field.play);
  }
}
