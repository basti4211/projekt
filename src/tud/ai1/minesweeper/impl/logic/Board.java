package tud.ai1.minesweeper.impl.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import tud.ai1.minesweeper.impl.model.Coordinate;
import tud.ai1.minesweeper.impl.model.Field;
import tud.ai1.minesweeper.impl.model.ICoordinate;
import tud.ai1.minesweeper.util.Consts;
import tud.ai1.minesweeper.util.FileOperations;

/**
 * Diese Klasse repraesentiert das Spielfeld. Das Spielfeld besteht aus n mal m Feldeinheiten.
 *
 * @author Joshua Hatzinger
 * @version 2024-05-15
 */
public class Board {

  // Board-Einstellungen. Diese Werte dienen nur zur Erzeugnung eines zufaelligen Boards. Sie sind
  // irrelevant, falls ein Board aus einer Datei eingelesen wird. Die aktuelle Groesse eines
  // initialisierten Boards sollten Sie immer mit den Gettern 'getWidth()' und 'getHeight()'
  // abgefragen.
  private static int initWidth = 23;
  private static int initHeight = 20;
  private static int initMinesAmount = 10;

  /**
   * Zweidimensionales Array fuer alle Felder auf dem Board. Der Zugriff erfolgt wie bei einer
   * Matrix mit [y][x].
   */
  private Field[][] board;

  /**
   * Konstruktor, welcher ein Board erzeugt. Verwendet die oben festgelegten Eigenschaften und
   * initialisiert alle Felder sowie die gewuenschte Anzahl an Minen.
   */
  public Board() {
    this.initializeBoard();
    this.generateAllMines();
    this.calculateAllNumbers();
  }

  /*
   * TODO: Aufgabe 4.1d.
   */

  /*
   * TODO: Aufgabe 4.1c.
   */

  /*
   * TODO: Aufgabe 4.1a.
   */

  /**
   * Generiert alle Minen, ohne Duplikate. Die Anzahl wird in der Klassenvariable festgelegt.
   */
  private void generateAllMines() {
    for (int amount = 0; amount < initMinesAmount; amount++) {
      this.generateOneMine();
    }
  }

  /*
   * TODO: Aufgabe 4.1b.
   */

  /**
   * Berechnet fuer jedes einzelne Feld des gesamten Spielfeldes, wie viele Minen in seiner direkten
   * Umgebung liegen. Anschliessend wird der Wert jedes einzelnen Feldes aktualisiert.
   */
  private void calculateAllNumbers() {
    for (final Field[] row : this.board) {
      for (final Field field : row) {
        this.calculateOneNumber(field.getCoordinates());
      }
    }
  }

  /**
   * Aendert den Zustand eines durch eine {@link ICoordinate} beschriebenen Feldes auf dem
   * Spielfeld.
   *
   * @param coordinate {@link ICoordinate} des zu aendernden Feldes.
   * @return Ob das Feld jetzt mit einer Flagge markiert ist.
   */
  public boolean flag(final ICoordinate coordinate) {
    if (coordinate == null) {
      throw new IllegalArgumentException("Die Koordinate darf nicht null sein!");
    }

    try {
      return this.board[coordinate.getY()][coordinate.getX()].flag();
    } catch (UnsupportedOperationException ex) {
      System.out.println(
          "Es wurde eine UnsupportedOperationException gefangen; " + "Ausgabe auf der Konsole:");
      System.out.println("=> " + ex.getMessage().split(System.lineSeparator())[0]);
      return false;
    }
  }

  /**
   * Wenn auf eine Nummer geklickt wird und bereits so viele Flaggen um die Zahl gesetzt wurden, wie
   * Minen um sie liegen, werden alle umliegenden Felder geoeffnet.
   * 
   * @param coordinate {@link ICoordinate} des geklickten Feldes.
   */
  public void clickNumber(final ICoordinate coordinate) {
    if (coordinate == null) {
      throw new IllegalArgumentException("Die Koordinate darf nicht null sein!");
    }

    // Nur geoeffnete Felder sind relevant.
    if (!board[coordinate.getY()][coordinate.getX()].isRevealed()) {
      return;
    }

    // Feld darf keine Mine sein.
    if (board[coordinate.getY()][coordinate.getX()].isMined()) {
      return;
    }

    // Alle umliegenden Felder auflisten.
    LinkedList<Field> surroundings = new LinkedList<Field>();
    for (int y = Math.max(coordinate.getY() - 1, 0); y < Math.min(coordinate.getY() + 2,
        board.length); y++) {
      for (int x = Math.max(coordinate.getX() - 1, 0); x < Math.min(coordinate.getX() + 2,
          board[y].length); x++) {
        surroundings.add(board[y][x]);
      }
    }

    // Alle umliegenden Flaggen zaehlen.
    int flags = 0;
    for (Field f : surroundings) {
      if (f.isFlagged()) {
        flags++;
      }
    }

    // Wenn genug Flaggen gesetzt wurden, alle uebrigen umliegenden Felder oeffnen.
    if (flags == board[coordinate.getY()][coordinate.getX()].getNeighboringMines()) {
      for (Field f : surroundings) {
        if (f.isFlagged()) {
          continue;
        }
        this.reveal(f.getCoordinates());
      }
    }
  }

  /**
   * Oeffnet das uebergebene Feld. Ist das Feld bereits geoeffnet passiert nichts. Besitzt das
   * uebergebene Feld keine benachbarten Minen und ist auch selbst keine Mine, so werden die acht
   * umliegenden Felder geoeffnet.
   *
   * @param coordinate {@link ICoordinate} des zu oeffnenden Feldes.
   */
  public void reveal(final ICoordinate coordinate) {
    if (coordinate == null) {
      throw new IllegalArgumentException("Die Koordinate darf nicht null sein!");
    }
    final int x = coordinate.getX();
    final int y = coordinate.getY();
    final Field temp = this.board[y][x];

    if (temp.isRevealed()) {
      return;
    }
    temp.reveal();

    // Die umliegenden Felder duerfen nur geoeffnet werden, sofern sie keine Minen in ihrer
    // Nachbarschaft haben.
    if (temp.getNeighboringMines() > 0 || temp.isMined()) {
      return;
    }

    for (int j = Math.max(0, y - 1); j <= Math.min(this.board.length - 1, y + 1); j++) {
      for (int i = Math.max(0, x - 1); i <= Math.min(this.board[j].length - 1, x + 1); i++) {
        if (j == y && i == x) {
          continue;
        }
        this.reveal(this.board[j][i].getCoordinates());
      }
    }
  }

  /*
   * TODO: Aufgabe 4.2b.
   */

  /*
   * TODO: Aufgabe 4.2c.
   */

  /*
   * TODO: Aufgabe 4.2d.
   */

  /**
   * Oeffnet ein zufaelliges Feld, welches keine Mine und geschlossen ist. Der Parameter gibt an, ob
   * entweder das Feld gar keine benachbarten Minen hat oder auf jeden Fall benachbarte Minen
   * besitzen soll.
   * 
   * @param hasNeighboringMines Wenn true, dann soll das zu oeffnende Feld eine Anzahl an
   *        benachbarten Minen groesser als 0 besitzen. Wenn false, dann soll es genau gar keine
   *        benachbarte Minen (0) besitzen.
   * @return {@link ICoordinate} des zufaellig geoeffneten Feldes.
   */
  public ICoordinate triggerRevealRandomField(boolean hasNeighboringMines) {
    ICoordinate[] values = Arrays.stream(this.board) // Board zu Stream
        .flatMap(Arrays::stream) // Stream zu 1-dimensionaler Stream
        .filter(f -> !(f.isRevealed() || f.isMined())) // Nur Felder, die noch zu sind und ohne Mine
        // Wenn hasNeighboringMines true: nur die Felder, die in der Naehe einer Mine sind
        // Wenn hasNeighboringMines false: nur die Felder, die nicht in der Naehe einer Mine sind
        .filter(f -> !hasNeighboringMines ^ f.getNeighboringMines() > 0) //
        .map(Field::getCoordinates) // Field zu ICoordinate
        .toArray(ICoordinate[]::new);
    // .sorted((a, b) -> (int) (Math.random() * 2) - 1) // "Sortieren" mit Zufall, zum Mischen
    // .findFirst() // Gebe irgendeinen zurueck
    // .orElse(null); // Oder null, falls es keine gibt
    if (values.length > 0) {
      ICoordinate value = values[(int) (Math.random() * values.length)];
      this.reveal(value);
      return value;
    }
    return null;
  }

  /**
   * Flagged eine zufaellige, noch nicht markierte Mine. Gibt die {@link ICoordinate} des Feldes
   * zurueck oder null, falls kein passendes Feld gefunden wurde.
   * 
   * @return {@link ICoordinate}, welche geflaggt wurde oder null, falls es kein passendes Feld
   *         gibt.
   */
  public ICoordinate flagMine() {
    ICoordinate mine = this.getRandomMine();
    if (mine != null) {
      this.flag(mine);
    }

    return mine;
  }

  /**
   * Gibt die Hoehe des instantiierten Boards zurueck.
   * 
   * @return Hoehe des instantiierten Boards.
   */
  public int getHeight() {
    return this.board.length;
  }

  /**
   * Gibt die Breite des instantiierten Boards zurueck.
   * 
   * @return Breite des instantiierten Boards.
   */
  public int getWidth() {
    return this.board[0].length;
  }

  /*
   * TODO: Aufgabe 4.2a.
   */

  /**
   * Gibt ein Feld des instantiierten Boards zurueck passend zur uebgergebenen {@link ICoordinate}.
   * 
   * @param coordinate {@link ICoordinate}, fuer welche das Feld zurueckgegeben werden soll.
   * @return {@link Field} zur {@link ICoordinate}.
   */
  public Field getField(final ICoordinate coordinate) {
    if (coordinate == null) {
      throw new IllegalArgumentException("Uebergebene Koordinate ist null.");
    }

    if (coordinate.getY() >= this.board.length || coordinate.getY() < 0) {
      throw new IllegalArgumentException("Uebergebene Y Koordinate liegt ausserhalb des Boards.");
    }

    if (coordinate.getX() >= this.board[coordinate.getY()].length || coordinate.getX() < 0) {
      throw new IllegalArgumentException("Uebergebene X Koordinate liegt ausserhalb des Boards.");
    }

    return this.board[coordinate.getY()][coordinate.getX()];
  }

  /*
   * TODO: Aufgabe 4a.
   */

  /**
   * Setzt die Breite des Boards auf einen uebergebenen Wert, sofern dieser im gueltigen Bereich
   * liegt.
   * 
   * @param w Zu setzende Breite.
   */
  public static void setInitWidth(final int w) {
    if (w >= Consts.WIDTH_MIN && w <= Consts.WIDTH_MAX) {
      initWidth = w;
    }
  }

  /**
   * Setzt die Hoehe des Boards auf einen uebergebenen Wert, sofern dieser im gueltigen Bereich
   * liegt.
   * 
   * @param h Zu setzende Hoehe.
   */
  public static void setInitHeight(final int h) {
    if (h >= Consts.HEIGHT_MIN && h <= Consts.HEIGHT_MAX) {
      initHeight = h;
    }
  }

  /**
   * Setzt die Anzahl der Minen des Boards auf einen uebergebenen Wert, sofern dieser im gueltigen
   * Bereich liegt.
   * 
   * @param m Zu setzende Anzahl an Minen.
   */
  public static void setInitMinesAmount(final int m) {
    if (m >= 0 && m <= initWidth * initHeight) {
      initMinesAmount = m;
    }
  }

}
