package tud.ai1.minesweeper.impl.logic.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tud.ai1.minesweeper.impl.logic.Board;
import tud.ai1.minesweeper.impl.model.Coordinate;
import tud.ai1.minesweeper.impl.model.Field;
import tud.ai1.minesweeper.impl.model.ICoordinate;
import tud.ai1.minesweeper.util.TestUtilities;

/**
 * Diese Klasse testet zwei ausgewaehlte Methoden aus {@link Board} und ist fuer die Studierenden
 * verfuegbar (public test).
 * 
 * @author Maximilian Kratz
 * @version 2020-05-24
 */
public class BoardPublicTest {

  /**
   * Zu testendes Objekt.
   */
  private static Board sut;

  /**
   * Klasse {@link Board} fuer die Reflections.
   */
  private static Class<?> boardClass;

  /**
   * Methode fuer calculateOneNumber().
   */
  private static Method calc;

  /**
   * Methode fuer initializeBoard().
   */
  private static Method init;

  /**
   * Variable field fuer die Reflections. Wird benutzt um 'board' aus der Klasse {@link Board}
   * abzubilden.
   */
  private static java.lang.reflect.Field field;

  /**
   * Setup-Methode. Wird vor jedem Test ausgefuehrt und erstellt ein neues {@link Board}-Objekt.
   */
  @Before
  public void setUp() {
    // Hole Board-Klasse.
    try {
      boardClass = Class.forName("tud.ai1.minesweeper.impl.logic.Board");
    } catch (final ClassNotFoundException e) {
      Assert.fail("Die Klasse Board wurde entfernt.");
    }

    // Hole Methode calc...
    try {
      calc = boardClass.getDeclaredMethod("calculateOneNumber", ICoordinate.class);
      calc.trySetAccessible();
    } catch (final NoSuchMethodException e) {
      e.printStackTrace();
      Assert.fail("Die Signatur der Methode 'calculateOneNumber' ist nicht korrekt.");
    } catch (final SecurityException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (SecurityException).");
    }

    // Hole Methode init...
    try {
      init = boardClass.getDeclaredMethod("initializeBoard");
      init.trySetAccessible();
    } catch (final NoSuchMethodException e) {
      e.printStackTrace();
      Assert.fail("Die Signatur der Methode 'initializeBoard' ist nicht korrekt.");
    } catch (final SecurityException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (SecurityException).");
    }

    // Hole Feld 'board' und setze es sichtbar fuer alle.
    try {
      field = boardClass.getDeclaredField("board");
      field.setAccessible(true);
    } catch (final NoSuchFieldException ex) {
      Assert.fail("Die Klasse Board wurde an Stellen veraendert, die nicht haetten veraendert "
          + "werden duerfen.");
    }

    // Erzeuge neues Objekt.
    try {
      sut = new Board();
    } catch (final Exception ex) {
      ex.printStackTrace();
      Assert.fail("Der Konstruktor der Klasse Board hat eine Exception geworfen. "
          + "Pruefen Sie die Implementierung des Konstruktors und/oder die Implementierung "
          + "der Methoden, die Sie vom Konstruktor aus aufrufen. Tipp: Kommentieren Sie den "
          + "Inhalt des Konstruktors aus, damit die Tests Ihnen hilfreichere Fehlermeldungen "
          + "ausgeben koennen.");
    }

    // Initialisiere Board mit Felder.
    // Set up.
    Board.setInitHeight(3);
    Board.setInitWidth(3);
    Board.setInitMinesAmount(2);

    // Das Erstellen des Boards wird haendisch gemacht, damit nicht die komplette Loesung verraten
    // wird.
    Field[][] inputBoard = new Field[3][3];
    inputBoard[0][0] = new Field(new Coordinate(0, 0));
    inputBoard[0][1] = new Field(new Coordinate(1, 0));
    inputBoard[0][2] = new Field(new Coordinate(2, 0));
    inputBoard[1][0] = new Field(new Coordinate(0, 1));
    inputBoard[1][1] = new Field(new Coordinate(1, 1));
    inputBoard[1][2] = new Field(new Coordinate(2, 1));
    inputBoard[2][0] = new Field(new Coordinate(0, 2));
    inputBoard[2][1] = new Field(new Coordinate(1, 2));
    inputBoard[2][2] = new Field(new Coordinate(2, 2));

    // Setze einige Felder zu Minen.
    inputBoard[0][0].setMined(true);
    inputBoard[1][0].setMined(true);
    inputBoard[2][0].setMined(true);
    inputBoard[2][1].setMined(true);
    inputBoard[1][2].setMined(true);

    // Speichere board in Board.
    try {
      field.set(sut, inputBoard);
    } catch (final IllegalArgumentException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (IllegalArgumentException).");
    } catch (final IllegalAccessException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (IllegalAccesException).");
    }
  }

  /**
   * Testet die Methode 'calculateOneNumber()' auf Randfaelle.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testcalculateOneNumberEdge() {
    try {
      calc.invoke(sut, new Coordinate(0, 0));
      calc.invoke(sut, new Coordinate(1, 0));
      calc.invoke(sut, new Coordinate(2, 0));
      calc.invoke(sut, new Coordinate(0, 1));
      calc.invoke(sut, new Coordinate(1, 1));
      calc.invoke(sut, new Coordinate(2, 1));
      calc.invoke(sut, new Coordinate(0, 2));
      calc.invoke(sut, new Coordinate(1, 2));
      calc.invoke(sut, new Coordinate(2, 2));
    } catch (final Exception ex) {
      ex.printStackTrace();
      Assert.fail("Es ist eine Exception beim Aufruf von 'calculateOneNumber' geworfen worden, "
          + "was nicht passieren sollte.");
    }
  }

  /**
   * Testet die Methode 'calculateOneNumber()' auf die korrekte Anzahl an Minen in der
   * Nachbarschaft.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testcalculateOneNumberNumber() {
    try {
      calc.invoke(sut, new Coordinate(0, 0));
      calc.invoke(sut, new Coordinate(1, 0));
      calc.invoke(sut, new Coordinate(2, 0));
      calc.invoke(sut, new Coordinate(0, 1));
      calc.invoke(sut, new Coordinate(1, 1));
      calc.invoke(sut, new Coordinate(2, 1));
      calc.invoke(sut, new Coordinate(0, 2));
      calc.invoke(sut, new Coordinate(1, 2));
      calc.invoke(sut, new Coordinate(2, 2));

      Assert.assertEquals("Die Anzahl an benachbarten Minen wird nicht korrekt berechnet.", 5,
          sut.getField(new Coordinate(1, 1)).getNeighboringMines());
    } catch (final Exception ex) {
      ex.printStackTrace();
      Assert.fail("Es ist eine Exception beim Aufruf von 'calculateOneNumber' geworfen worden, "
          + "was nicht passieren sollte.");
    }
  }

  /**
   * Testet, ob (x,y) in 'Board.initializeBoard()' vertauscht wurde.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testInitializedBoardFlipped() {
    Board.setInitHeight(2);
    Board.setInitWidth(3);

    invokeInit(sut);
    Field[][] output = getInvokedField(sut);

    Assert.assertEquals("X- und Y-Dimensionen des Boards sind nicht korrekt.", 2, output.length);
    Assert.assertEquals("X- und Y-Dimensionen des Boards sind nicht korrekt.", 3, output[0].length);
  }

  /**
   * Testet, ob die {@link Coordinate} in 'initializeBoard' korrekt gesetzt wurden.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testInitializedBoardCoor() {
    invokeInit(sut);
    Field[][] output = getInvokedField(sut);

    Assert.assertEquals("(x,y) wurde nicht richtig gesetzt.", 1,
        output[0][1].getCoordinates().getX());
    Assert.assertEquals("(x,y) wurde nicht richtig gesetzt.", 2,
        output[2][0].getCoordinates().getY());
  }

  /**
   * Fuehrt die private Methode 'initializeBoard()' aus der Klasse {@link Board} auf einem
   * uebergebenen Objekt sut aus.
   * 
   * @param sut Uebergebenes {@link Board}-Objekt, auf welchem die Methode ausgefuehrt werden soll.
   */
  private static void invokeInit(final Board sut) {
    try {
      init.invoke(sut);
    } catch (final IllegalArgumentException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (IllegalArgumentException).");
    } catch (final IllegalAccessException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (IllegalAccesException).");
    } catch (final InvocationTargetException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (InvocationTargetException).");
    }
  }

  /**
   * Holt das zweidimensionale Array 'board' aus einem uebergebenen {@link Board}-Objekt heraus und
   * gibt es zurueck.
   * 
   * @param sut {@link Board}-Objekt, aus welchem das zweidimensionale Array extrahiert werden soll.
   * @return Extrahiertes zweidimensionales Array.
   */
  private static Field[][] getInvokedField(final Board sut) {
    Field[][] output = null;

    try {
      output = (Field[][]) field.get(sut);
    } catch (final IllegalArgumentException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (IllegalArgumentException).");
    } catch (final IllegalAccessException e) {
      e.printStackTrace();
      Assert.fail("Es ist ein Test-Fehler aufgetreten (IllegalAccesException).");
    }

    return output;
  }

}
