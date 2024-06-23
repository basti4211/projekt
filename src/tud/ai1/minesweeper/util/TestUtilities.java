package tud.ai1.minesweeper.util;

import tud.ai1.minesweeper.impl.model.AbstractField;
import tud.ai1.minesweeper.impl.model.Field;

/**
 * Sammlung von Test-spezifischen Hilfskonstanten und Methoden. Wird nicht innerhalb der bewertenden
 * Tests verwendet.
 * 
 * @author Maximilian Kratz
 * @version 2020-05-14
 */
public interface TestUtilities {

  /**
   * Konstanten fuer die Tests.
   */
  public interface Consts {
    /**
     * Timeout limit fuer jeden Test in Millisekunden, welcher nicht fuer Bewertungen gedacht ist.
     */
    int TIMEOUT_MS = 1000;

    /**
     * Standard Exception Ausgabe-String.
     */
    String ERR_MSG_EX = "Es wurde eine Exception im Test gefangen. Ausgabe auf der Konsole.";

    /**
     * Fehlermeldung, wenn nicht null zurueckgegeben wurde.
     */
    String ERR_MSG_NOT_NULL = "Es wurde nicht null zurueckgegeben.";

    /**
     * Es wurde keine Exception geworfen als String.
     */
    String ERR_MSG_NO_EX = "Es wurde keine Exception geworfen.";

    /**
     * Rueckgabewert ist falsch als String.
     */
    String ERR_MSG_RET_WRONG = "Rueckgabewert ist falsch.";

    /**
     * Fehlermeldung fuer eine nicht vorhandene Methode.
     */
    String ERR_MSG_METHOD_MISSING = "Die erwartete Methode ist nicht vorhanden.";

    /**
     * Fehlermeldung fuer eine falsche Nachricht innerhalb der Exception.
     */
    String ERR_MSG_EX_MSG_WRONG = "Die Nachricht der Exception ist falsch.";

    /**
     * Fehlermeldung, wenn X-Koordinate des zurueckgegebenen {@link AbstractField} nicht korrekt
     * ist.
     */
    String ERR_MSG_X = "X Koordinate des zurueckgegebenen Feldes ist nicht korrekt.";

    /**
     * Fehlermeldung, wenn Y-Koordinate des zurueckgegebenen {@link AbstractField} nicht korrekt
     * ist.
     */
    String ERR_MSG_Y = "Y Koordinate des zurueckgegebenen Feldes ist nicht korrekt.";

    /**
     * Delta fuer die Tests mit Gleitkommazahlen.
     */
    double DELTA = 0.001;

    /**
     * Referenz fuer die Separation von Zeilen.
     */
    String REF_LINE_SEPARATOR = System.lineSeparator();
  }

  /*
   * Hilfsmethoden.
   */

  /**
   * Gibt true zurueck, wenn beide uebergebene, zwei dimensionale Arrays von {@link Field} gleich
   * sind.
   * 
   * @param a Erstes Board (zweidimensionales Array von {@link Field}).
   * @param b Zweites Board (zweidimensionaled Array von {@link Field}).
   * @return True, wenn beide Boards identisch sind.
   */
  public static boolean twoDimFieldsEquals(final Field[][] a, final Field[][] b) {
    // Teste, ob einer der beiden Parameter null ist.
    if (a == null || b == null) {
      return false;
    }

    // Pruefe, ob die Hoehe beider Arrays identisch ist.
    if (a.length != b.length) {
      return false;
    }

    // Iteriere ueber die Spalten.
    for (int i = 0; i < a.length; i++) {
      // Teste, ob die Anzahl der Zeilen in dieser Spalte identisch ist.
      if (a[i].length != b[i].length) {
        return false;
      }

      // Iteriere ueber die Felder einer Zeile.
      for (int j = 0; j < a[i].length; j++) {
        if (!fieldEquals(a[i][j], b[i][j])) {
          System.out.println("=> Feld [" + i + "][" + j + "] war unterschiedlich.");
          System.out.println("a: " + a[i][j].toString());
          System.out.println("b: " + b[i][j].toString());
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Gibt true zurueck, wenn beide uebergebenen Felder identisch sind.
   * 
   * @param a Erstes {@link Field}.
   * @param b ZWeites {@link Field}.
   * @return True, wenn beide {@link Field} identisch sind.
   */
  public static boolean fieldEquals(final Field a, final Field b) {
    // Pruefe toString-Gleichheit.
    if (!a.toString().equals(b.toString())) {
      return false;
    }

    // Pruefe Position.
    if (a.getCoordinates().getX() != b.getCoordinates().getX()
        || a.getCoordinates().getY() != b.getCoordinates().getY()) {
      return false;
    }

    // Pruefe Flaggenstatus.
    if (!a.isFlagged() == b.isFlagged()) {
      return false;
    }

    // Pruefe Minenstatus.
    if (!a.isMined() == b.isMined()) {
      return false;
    }

    // Pruefe, ob beide Felder geoeffnet sind.
    if (!a.isRevealed() == b.isRevealed()) {
      return false;
    }

    return true;
  }

  /**
   * Diese Methode erstellt eine 'Deep-copy' des uebergebenen, zweidimensionalen Arrays auf
   * {@link Field}.
   * 
   * @param field Zweidimensionales Array von '{@link Field}, welches kopiert werden soll.
   * @return Kopie des Eingabeparameters.
   */
  public static Field[][] copyField(final Field[][] field) {
    // Wenn uebergebenes Feld null ist, muss nichts gemacht werden.
    if (field == null) {
      return null;
    }

    // Create copy array.
    Field[][] copy = new Field[field.length][field[0].length];

    // Iteriere ueber alle Zeilen.
    for (int i = 0; i < copy.length; i++) {
      // Alternative Loesung, die allerdings nicht funktioniert, da die einzelnen Felder auch als
      // Objekte kopiert und nicht nur referenziert werden muessen.

      // copy[i] = Arrays.copyOf(field[i], field[i].length);

      // Iteriere ueber alle Spalten.
      for (int j = 0; j < copy[i].length; j++) {
        // Erstelle ein neues Feld.
        Field copiedField = new Field(field[i][j].getCoordinates());

        // Uebertrage Status, wenn Feld geoeffnet ist.
        if (field[i][j].isRevealed()) {
          copiedField.reveal();
        }

        // Uebertrage Status, ob Feld eine Mine ist.
        copiedField.setMined(field[i][j].isMined());

        // Uebertrage Status, wenn Feld eine Flagge ist.
        if (field[i][j].isFlagged()) {
          copiedField.flag();
        }

        // Setze Anzahl an benachbarten Minen.
        copiedField.setNeighboringMines(field[i][j].getNeighboringMines());

        // Schreibe aktuelles Feld in neues Array rein.
        copy[i][j] = copiedField;
      }
    }

    return copy;
  }

}
