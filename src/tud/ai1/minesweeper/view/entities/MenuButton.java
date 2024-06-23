package tud.ai1.minesweeper.view.entities;

import eea.engine.entity.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Die Klasse stellt einen Menu-Button im Hauptmenue dar.
 * 
 * @author Artur Seitz
 * @author Dennis Schirmer
 * @author Mahmoud El-Hindi
 * @author Darjush Siahdohoni
 * @author Igor Cherepanov
 * @author Hermann Berket
 * @author Niklas Vogel
 * @author Phil Reize
 * @version 2020-05-13
 */
public class MenuButton extends Entity {

  // Text Style.
  private TrueTypeFont font;
  private String text;
  private Color textColor;

  /**
   * Konstruktor. Setzt die uebergebene Entity-ID in der Superklasse.
   *
   * @param entityID Die Entity ID
   */
  public MenuButton(final String entityID) {
    super(entityID);
  }

  /**
   * Konstruktor. Initialisiert einen neuen Button mit Entity-ID, Position, Skalierung, Schriftart
   * und Textfarbe.
   *
   * @param entityID Die Entity-ID.
   * @param position Position auf der Leinwand, an welcher der Button gezeichnet werden soll.
   * @param scale Skalierungsfaktor der Bilder.
   * @param font Text Style.
   * @param textColor Farbe des Textes.
   */
  public MenuButton(final String entityID, final Vector2f position, final float scale,
      final TrueTypeFont font, final Color textColor) {
    super(entityID);
    this.text = entityID;
    setPosition(position);
    this.setSize(new Vector2f(font.getWidth("" + getID()) * 2, font.getHeight() + 5));
    this.font = font;
    this.textColor = textColor;
  }

  /**
   * Setzt den Text des Buttons auf einen neuen, uebergebenen Wert.
   * 
   * @param text Text, der gesetzt werden soll.
   */
  public void changeText(final String text) {
    if (text == null) {
      throw new IllegalArgumentException("Uebergebener Text war null!");
    }

    this.text = text;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame,
      Graphics graphics) {
    super.render(gameContainer, stateBasedGame, graphics);
    graphics.setFont(font);
    graphics.setColor(this.textColor);
    graphics.drawString(this.text, getPosition().x, getPosition().y - 10);
  }

}
