package tud.ai1.minesweeper.impl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Klasse die einen einzelnen {@link HighscoreEntry} repraesentiert.
 *
 * @author Andrej Felde, Daniel Stein, Nicklas Behler, Lennart Fedler
 * @version 2020-05-13
 */
public class HighscoreEntry implements Comparable<HighscoreEntry> {

  /**
   * Datum des gespielten Spiels.
   */
  private final LocalDateTime date;

  /**
   * Gebrauchte Zeit in Millisekunden.
   */
  private final long duration;

  /**
   * Schwierigkeit des Spiels.
   */
  private final double difficulty;

  /**
   * Separator zum Parsen und Schreiben der Highscore-Datei.
   */
  private static final String separator = ";";

  /**
   * Formatter um String in LocalDateTime zu formatieren.
   */
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

  // TODO: Aufgabe 5.1b.
  public HighscoreEntry(final LocalDateTime date, final double difficulty, final long duration) {
    // TODO: Das hier entfernen, bevor Implementierung begonnen wird:
    this.duration = -1;
    this.difficulty = -1;
    this.date = null;
  }

  // TODO: Aufgabe 5.1c.
  public HighscoreEntry(final String data) {
    // TODO: Das hier entfernen, bevor Implementierung begonnen wird:
    this.duration = -1;
    this.difficulty = -1;
    this.date = null;
  }

  // TODO Aufgabe 5.1a.
  public void validate(final LocalDateTime date, final double difficulty, final long duration) {
    // TODO
  }

  // TODO Aufgabe 5.1d.
  @Override
  public boolean equals(final Object obj) {
    // TODO: Das hier entfernen, bevor Implementierung begonnen wird:
    return false;
  }

  /**
   * Getter fuer date als String.
   *
   * @return String - Spieldatum.
   */
  public String getDate() {
    return this.date.format(formatter);
  }

  /**
   * Getter fuer die difficulty.
   *
   * @return Schwierigkeit des Feldes,
   */
  public double getDifficulty() {
    return this.difficulty;
  }

  /**
   * Getter fuer die Dauer.
   *
   * @return Gebrauchte Zeit in Millisekunden,
   */
  public long getDuration() {
    return this.duration;
  }

  /**
   * Getter um Format zum Speichern zu bekommen.
   *
   * @return String dd.MM.yyyy hh:mm.
   */
  private String dateToSaveFormat() {
    return this.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
  }


  // TODO Aufgabe 5.1e.
  @Override
  public int compareTo(final HighscoreEntry other) {
    // TODO: Das hier entfernen, bevor Implementierung begonnen wird:
    return -1;
  }

  /**
   * Diese Methode gibt die String-Repraesentation des Objektes zurueck.
   *
   * @return String-Repraesentation des Objektes.
   */
  @Override
  public String toString() {
    return this.dateToSaveFormat() + separator + this.difficulty + separator + this.duration;
  }

}
