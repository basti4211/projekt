package tud.ai1.minesweeper.impl.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import tud.ai1.minesweeper.util.FileOperations;

/**
 * Klasse, welche fuer die Verwaltung der Highscores verantworlicht ist. Laedt, speichert und legt
 * neue {@link HighscoreEntry} in einer LinkedList ab.
 *
 * @author Andrej Felde, Daniel Stein, Nicklas Behler, Robert Jakobi, Lennart Fedler, Maximilian
 *         Kratz
 * @version 2020-05-26
 */
public class Highscore {

  /**
   * Maximale Anzahl an {@link HighscoreEntry} in einer Highscore-Liste.
   */
  public static final int MAX_ENTRIES = 10;

  /**
   * Highscore-Liste.
   */
  private List<HighscoreEntry> highscores;

  /**
   * Konstruktor der {@link Highscore}-Klasse.
   */
  public Highscore() {
    this.highscores = new LinkedList<>();
  }

  /**
   * Methode zum Erzeugen der Highscores aus dem gespeicherten Stringformat. Teilt den uebergebenen
   * String in einzelne Eintraege und speichert diese in der LinkedList ab.
   *
   * @param str String, der die vorherigen Highscores enthaelt.
   */
  public void initHighscore(final String str) {
    if (str == null) {
      throw new IllegalArgumentException("Uebergebener Parameter ist null!");
    }

    if (str.isEmpty()) {
      return;
    }

    for (final String line : str.split(System.lineSeparator())) {
      this.addEntry(new HighscoreEntry(line));
    }
  }

  // TODO: Aufgabe 5.2b.
  public void saveToFile(final String fileName) {
    // TODO
  }

  /**
   * Getter Methode fuer alle gespeicherten Highscores.
   *
   * @return Gibt die Highscore-Liste zurueck.
   */
  public List<HighscoreEntry> getHighscore() {
    return this.highscores;
  }

  // TODO: Aufgabe 5.2a.
  public void addEntry(final HighscoreEntry entry) {
    // TODO
  }

}
