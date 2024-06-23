package tud.ai1.minesweeper.impl.model;

import tud.ai1.minesweeper.impl.logic.Board;
import tud.ai1.minesweeper.util.Consts;

/**
 * Klasse der Feldeinheit. Das Spielfeld (Klasse {@link Board}) besteht nur aus diesen
 * Feldeinheiten. Alle wichtigen Informationen sind hier gespeichert.
 *
 * @author Artur Seitz
 * @author Dennis Schirmer
 * @author Mahmoud El-Hindi
 * @author Darjush Siahdohoni
 * @author Igor Cherepanov
 * @author Hermann Berket
 * @author Maximilian Kratz
 * @author Joshua Hatzinger
 * 
 * @version 2024-05-14
 */
public class Field extends AbstractField {
  /*
   * In dieser Klasse darf keine eigene Variable vom Typ {@link Coordinate} oder {@link ICoordinate}
   * erstellt werden. Fuer diese Vorkomnisse soll immer die abstrakte Superklasse benutzt werden.
   */

  /**
   * Beschreibt, ob auf diesem {@link Field} eine Mine liegt.
   */
  private boolean mined;

  /**
   * Status des {@link Field}.
   */
  private FieldState state;

  /**
   * Die Anzahl an Minen in den acht benachbarten {@link Field}.
   */
  private int neighboringMines;

   /*
   * TODO: Aufgabe 3a.
   */

  /*
   * TODO: Aufgabe 3b.
   */

  /*
   * TODO: Aufgabe 3c.
   */

  /*
   * TODO: Aufgabe 3d.
   */

  /**
   * Gibt einen String zurueck, welcher das {@link Field}  repraesentiert. 'f' = Mit Flagge
   * versehen. '-' = Verstecktes {@link Field}. '+' = Auf diesem {@link Field} liegt eine Mine. Im
   * Falle einer Mine wird anschließend die Zahl an benachbarten Minen angehaengt.
   * 
   * @return Zum {@link Field} passenden String. Dieser String repraesentiert den Zustand des
   *         {@link Field}.
   * @see tud.ai1.minesweeper.impl.model.AbstractField#toString()
   */
  @Override
  public String toString() {
    if (this.isFlagged()) {
      return Consts.FIELD_FLAGGED;
    }

    if (!this.isRevealed()) {
      return Consts.FIELD_HIDDEN;
    }

    return this.mined ? Consts.FIELD_MINE : String.valueOf(this.neighboringMines);
  }

  /**
   * Zeigt das {@link Field}, falls es vorher noch nicht gezeigt wurde. Triggert ausserdem noch
   * ein Update der GUI.
   */
  public void reveal() {
    this.state = FieldState.REVEALED;
    this.updateGui();
  }

  /**
   * Gibt true zurueck, falls das {@link Field} gezeigt ist.
   * 
   * @return True, wenn das {@link Field} gezeigt ist.
   */
  public boolean isRevealed() {
    return (this.state == FieldState.REVEALED);
  }

  /**
   * Versteckt das {@link Field}.
   */
  public void hide() {
    this.state = FieldState.HIDDEN;
    this.updateGui();
  }

}
