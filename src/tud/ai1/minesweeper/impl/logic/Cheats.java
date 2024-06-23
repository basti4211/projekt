package tud.ai1.minesweeper.impl.logic;

import tud.ai1.minesweeper.impl.model.ICoordinate;

/**
 * Dieses Interface repraesentiert die Cheats.
 * 
 * @author Patrick Vogel, Maximilian Kratz
 * @version 2020-04-30
 */
public interface Cheats {

  static final String errorBoardIsNull = "Board darf nicht null sein.";
  static final String noCheatPossible = "Cheat nicht moeglich!";

  /**
   * Oeffnet ein zufaelliges, leeres (= keine benachbarten Minen und selbst keine Mine), noch nicht
   * geoeffnetes {@link tud.ai1.minesweeper.impl.model.Field} auf dem uebergebenen {@link Board},
   * falls das Spiel nicht bereits gewonnen oder verloren ist. Falls der Cheat nicht mehr moeglich
   * ist, wird eine Meldung auf der Konsole ausgegeben und false zurueckgegeben.
   * 
   * @param board {@link Board} auf dem das Feld geoeffnet werden soll.
   * @return True wenn Oeffnen erfolgreich war und false, wenn kein passendes
   *         {@link tud.ai1.minesweeper.impl.model.Field} gefunden wurde oder der Cheat nicht mehr
   *         moeglich ist.
   */
  public static boolean triggerOpenSpace(final Board board) {
    // TODO: Aufgabe 6a.
    return false;
  }

  /**
   * Versieht eine zufaellige, noch nicht markierte Mine mit einer Flagge, falls das Spiel nicht
   * bereits gewonnen oder verloren ist. Falls der Cheat nicht mehr moeglich ist, wird eine Meldung
   * auf der Konsole ausgegeben und false zurueckgegeben.
   * 
   * @param board {@link Board} auf dem die Mine geflagged werden soll.
   * @return True wenn Flaggen erfolgreich war und false, wenn kein passendes
   *         {@link tud.ai1.minesweeper.impl.model.Field} gefunden wurde oder der Cheat nicht mehr
   *         moeglich ist.
   */
  public static boolean flagMine(final Board board) {
    // TODO: Aufgabe 6b.
    return false;
  }

  /**
   * Oeffnet ein zufaelliges, nicht leeres (= keine Mine, aber benachbarte Minen), noch nicht
   * geoeffnetes {@link tud.ai1.minesweeper.impl.model.Field} auf dem uebergebenen {@link Board},
   * falls das Spiel nicht bereits gewonnen oder verloren ist. Falls der Cheat nicht mehr moeglich
   * ist, wird eine Meldung auf der Konsole ausgegeben und false zurueckgegeben.
   * 
   * @param board {@link Board} auf dem das Feld geoeffnet werden soll.
   * @return True wenn Oeffnen erfolgreich war und false, wenn kein passendes @link
   *         tud.ai1.minesweeper.impl.model.Field} gefunden wurde oder der Cheat nicht mehr moeglich
   *         ist.
   */
  public static boolean openRandomNumberField(final Board board) {
    // TODO: Aufgabe 6c.
    return false;
  }

}
