package tud.ai1.minesweeper.impl.model.tests;

import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tud.ai1.minesweeper.impl.model.HighscoreEntry;
import tud.ai1.minesweeper.util.TestUtilities;

/**
 * Diese Klasse testet eine ausgewaehlte Methode aus {@link HighscoreEntry} und ist fuer die
 * Studierenden verfuegbar (public test).
 * 
 * @author Maximilian Kratz
 * @version 2020-05-24
 */
public class HighscoreEntryPublicTest {

  /**
   * Zu testendes Objekt.
   */
  private HighscoreEntry sut;

  /**
   * Datum und Zeitstempel fuer die Tests.
   */
  private LocalDateTime inputDate;

  /**
   * Input difficulty.
   */
  private int inputDifficulty;

  /**
   * Input duration.
   */
  private long inputDuration;

  /**
   * Wird vor jedem Testfall ausgefuehrt und initialisiert die (Referenz-)Variablen.
   */
  @Before
  public void setUp() {
    inputDate = LocalDateTime.parse("2007-12-03T10:15:30");
    inputDifficulty = 1;
    inputDuration = 1;
    sut = new HighscoreEntry(inputDate, inputDifficulty, inputDuration);
  }

  /**
   * Teardown-Methode. Wird nach jedem Test ausgefuehrt und loescht das vorhande
   * {@link HighscoreEntry}-Objekt.
   */
  @After
  public void tearDown() {
    sut = null;
  }

  /**
   * Testet die {@link HighscoreEntry#compareTo(HighscoreEntry)}-Methode auf Gleichheit.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testCompareToZero() {
    HighscoreEntry second = new HighscoreEntry(inputDate, inputDifficulty, inputDuration);
    assertEquals("Gleiche Daten geben nicht 0 zurueck.", 0, sut.compareTo(second));
  }

  /**
   * Testet die {@link HighscoreEntry#compareTo(HighscoreEntry)}-Methode auf difficulty groesser.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testCompareToDiffLarger() {
    final int secondDifficulty = 10;
    HighscoreEntry second = new HighscoreEntry(inputDate, secondDifficulty, inputDuration);
    Assert.assertTrue("Groessere difficulty hat nicht Wert >0 zurueckgegeben.",
        0 < sut.compareTo(second));
  }

  /**
   * Testet die {@link HighscoreEntry#compareTo(HighscoreEntry)}-Methode auf difficulty kleiner.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testCompareToDiffSmaller() {
    final int secondDifficulty = 0;
    HighscoreEntry second = new HighscoreEntry(inputDate, secondDifficulty, inputDuration);
    Assert.assertTrue("Kleinere difficulty hat nicht Wert <0 zurueckgegeben.",
        0 > sut.compareTo(second));
  }

  /**
   * Testet die {@link HighscoreEntry#compareTo(HighscoreEntry)}-Methode auf duration groesser.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testCompareToDurLarger() {
    final int secondDuration = 10;
    HighscoreEntry second = new HighscoreEntry(inputDate, inputDifficulty, secondDuration);
    Assert.assertTrue("Groessere duration hat nicht Wert <0 zurueckgegeben.",
        0 > sut.compareTo(second));
  }

  /**
   * Testet die {@link HighscoreEntry#compareTo(HighscoreEntry)}-Methode auf duration kleiner.
   */
  @Test(timeout = TestUtilities.Consts.TIMEOUT_MS)
  public void testCompareToDurSmaller() {
    final int secondDuration = 0;
    HighscoreEntry second = new HighscoreEntry(inputDate, inputDifficulty, secondDuration);
    Assert.assertTrue("Kleinere duration hat nicht Wert >0 zurueckgegeben.",
        0 < sut.compareTo(second));
  }

}
