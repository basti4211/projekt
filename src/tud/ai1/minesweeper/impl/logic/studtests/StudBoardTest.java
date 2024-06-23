package tud.ai1.minesweeper.impl.logic.studtests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tud.ai1.minesweeper.impl.logic.Board;
import tud.ai1.minesweeper.impl.model.Coordinate;
import tud.ai1.minesweeper.impl.model.Field;
import tud.ai1.minesweeper.impl.model.ICoordinate;
import tud.ai1.minesweeper.util.TestUtilities.Consts;

/**
 * Testet die Methoden {@link Board#getField(tud.ai1.minesweeper.impl.model.ICoordinate)} und
 * {@link Board#triggerRevealRandomField(boolean)}.
 * 
 * @author Maximilian Kratz
 * @version 2020-07-01
 */
public class StudBoardTest {

  /**
   * {@link Board} zum Testen, welches keinerlei Minen hat. Alle
   * {@link tud.ai1.minesweeper.impl.model.Field} sind geschlossen und haben implizit keine
   * benachbarten Minen.
   */
  private Board noMinesBoard;

  /**
   * Zehn zufaellige {@link tud.ai1.minesweeper.impl.model.Field} auf diesem {@link Board} sind mit
   * einer Mine versehen. Es gibt keine Flaggen und keine
   * {@link tud.ai1.minesweeper.impl.model.Field} sind geoeffnet.
   */
  private Board someMinesBoard;

  /**
   * Es gibt genau drei relevante {@link tud.ai1.minesweeper.impl.model.Field} : (0, 0) ist eine
   * Mine. (1, 1) ist eine Flagge. (2, 2) ist geoeffnet.
   */
  private Board complexBoard;

  /**
   * Wird vor jedem Testfall ausgefuehrt und erstellt die verschiedenen {@link Board}, welche fuer
   * die Tests verwenden werden koennen.
   */
  @Before
  public void setUp() {
    // Board, welches nur geschlossene Felder besitzt (10 x 10).
    Board.setInitHeight(10);
    Board.setInitWidth(10);
    Board.setInitMinesAmount(0);
    noMinesBoard = new Board();

    // Board, welches einige Minen hat (10 x 10).
    Board.setInitHeight(10);
    Board.setInitWidth(10);
    Board.setInitMinesAmount(10);
    someMinesBoard = new Board();

    // Board, welches eine Mine, eine geoeffnetes Feld und eine Flagge beinhaltet (10 x 10).
    Board.setInitHeight(10);
    Board.setInitWidth(10);
    Board.setInitMinesAmount(0);
    complexBoard = new Board();
    complexBoard.getField(new Coordinate(0, 0)).setMined(true);
    complexBoard.getField(new Coordinate(0, 1)).setNeighboringMines(1);
    complexBoard.getField(new Coordinate(1, 0)).setNeighboringMines(1);
    complexBoard.getField(new Coordinate(1, 1)).flag();
    complexBoard.getField(new Coordinate(1, 1)).setNeighboringMines(1);
    complexBoard.getField(new Coordinate(2, 2)).reveal();
  }

  /**
   * Testet die Methode {@link Board#getField(tud.ai1.minesweeper.impl.model.ICoordinate)} auf
   * folgenden Testfall: Uebergebene {@link tud.ai1.minesweeper.impl.model.ICoordinate} ist null. Es
   * wird erwartet, dass eine passende Exception von der zu testenden Methode geworfen wird.
   */
  @Test(timeout = Consts.TIMEOUT_MS)
  public void testGetFieldNull() {
    try {
      // Fuehre zu testende Methode aus.
      noMinesBoard.getField(null);
      Assert.fail("Es wurde keine Exception geworfen!");
    } catch (final IllegalArgumentException ex) {
      // Es wurde eine passende Exception geworfen.
      // Pruefe Nachricht der Exception auf korrekten Inhalt.
      Assert.assertEquals("Nachricht der Exception ist nicht korrekt!",
          "Uebergebene Koordinate ist null.", ex.getMessage());
    } catch (final Exception ex) {
      Assert.fail("Es wurde eine falsche Exception geworfen!");
    }
  }

  /**
   * Testet die Methode {@link Board#getField(tud.ai1.minesweeper.impl.model.ICoordinate)} auf
   * folgenden Testfall: Uebergebene {@link tud.ai1.minesweeper.impl.model.ICoordinate} liegt
   * ausserhalb des Spielfeldes (Wert zu gross). Es wird erwartet, dass eine passende Exception von
   * der zu testenden Methode geworfen wird.
   */
  @Test(timeout = Consts.TIMEOUT_MS)
  public void testGetFieldTooLarge() {
    // TODO Aufgabe 7a!
  }

  /**
   * Testet die Methode {@link Board#getField(tud.ai1.minesweeper.impl.model.ICoordinate)} auf
   * folgenden Testfall: Uebergebene {@link tud.ai1.minesweeper.impl.model.ICoordinate} liegt
   * ausserhalb des Spielfeldes (Wert zu klein). Es wird erwartet, dass eine passende Exception von
   * der zu testenden Methode geworfen wird.
   */
  @Test(timeout = Consts.TIMEOUT_MS)
  public void testGetFieldTooSmall() {
    // TODO Aufgabe 7b!
  }

  /**
   * Testet die Methode {@link Board#getField(tud.ai1.minesweeper.impl.model.ICoordinate)} auf
   * folgenden Testfall: Uebergebene {@link tud.ai1.minesweeper.impl.model.ICoordinate} liegt im
   * Spielfeld. Es wird erwartet, dass das richtige {@link tud.ai1.minesweeper.impl.model.Field}
   * zurueckgegeben wird.
   */
  @Test(timeout = Consts.TIMEOUT_MS)
  public void testGetFieldNormal() {
    // TODO: Aufgabe 7c!
  }

  /**
   * Testet die Methode {@link Board#triggerRevealRandomField(boolean)} auf folgenden Testfall: Es
   * gibt keine Minen auf dem gesamten Spielfeld, der Parameter ist aber true. Es muss 'null' durch
   * die Methode zurueckgegeben werden, da es keine {@link tud.ai1.minesweeper.impl.model.Field} mit
   * benachbarten Minen gibt.
   */
  @Test(timeout = Consts.TIMEOUT_MS)
  public void testTriggerRandomFieldRevealNoMines() {
    Assert.assertNull("Es wurde nicht null zurueckgegeben!",
        noMinesBoard.triggerRevealRandomField(true));
  }

  /**
   * Testet die Methode {@link Board#triggerRevealRandomField(boolean)} auf folgenden Testfall: Es
   * gibt Minen auf dem Spielfeld, der uebergebene Parameter ist false. In jedem Fall soll die
   * Rueckgabe der Methode geprueft werden. Die Rueckgabe darf keine benachbarten Minen haben.
   * Weiterhin soll geprueft werden, ob das {@link tud.ai1.minesweeper.impl.model.Field} auf dem
   * {@link Board} auch geoeffnet wurde.
   */
  @Test(timeout = Consts.TIMEOUT_MS)
  public void testTriggerRandomFieldRevealFalse() {
    // TODO: Aufgabe 7d!
  }

  /**
   * Testet die Methode {@link Board#triggerRevealRandomField(boolean)} auf folgende Testfall: Es
   * gibt Minen auf dem Spielfeld, der uebergebene Parameter ist true. In jedem Fall soll die
   * Rueckgabe der Methode geprueft werden. Die Rueckgabe muss benachbarte Minen haben. Weiterhin
   * soll geprueft werden, ob das {@link tud.ai1.minesweeper.impl.model.Field} auf dem {@link Board}
   * auch geoeffnet wurde.
   */
  @Test(timeout = Consts.TIMEOUT_MS)
  public void testTriggerRandomFieldRevealTrue() {
    // TODO: Aufgabe 7e!
  }

}

