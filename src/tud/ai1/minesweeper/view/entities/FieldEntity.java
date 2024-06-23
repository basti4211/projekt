package tud.ai1.minesweeper.view.entities;

import eea.engine.action.Action;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseEnteredEvent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import tud.ai1.minesweeper.impl.model.Coordinate;
import tud.ai1.minesweeper.util.Consts;
import tud.ai1.minesweeper.view.states.GamePlayState;

/**
 * Stellt die grafische Representation eines einzelnen {@link tud.ai1.minesweeper.impl.model.Field}
 * da. Keine Implementierung jedweder Spiellogik. Alle grafischen Veraenderungen, die z. B. durch
 * Klicken entstehen, werden ueber Hooks eingelesen.
 * 
 * @author Phil Reize, Niklas Vogel
 * @version 2020-13-05
 */
public class FieldEntity extends Entity {
  /**
   * Ein Bild das zusaetzlich beim Mouseover ueber den Button gezeichnet wird.
   */
  private Image highlight;

  /**
   * Hilfsvariable die beschreibt ob das Mousover-Highlighting gezeichnet werden soll.
   */
  private boolean renderHighlighting;

  /**
   * ID der Entity.
   */
  private String entityID;

  /**
   * X Position im graphischen Spielfeld.
   */
  private int coordX;

  /**
   * Y Position im graphischen Spielfeld.
   */
  private int coordY;

  /**
   * Beschreibt ob der Button bereits geklickt wurde.
   */
  private boolean clicked;

  /**
   * Das Bild, das angezeigt werden soll, sobald der Button gedrueckt wurde: Bilder mit Zahlen 1-8
   * oder Bild der Mine.
   */
  private Image newImage;

  /**
   * Beschreibt ob das Flaggenbild gerendert werden soll.
   */
  private boolean flagged;

  /**
   * Das Bild der Flagge.
   */
  private Image flaggedImg;

  /**
   * Feld fuer das Easteregg. Wenn alternatives boolean true ist, muss 2 angehaengt werden.
   */
  private static final String adds = Consts.ALTERNATIVE_BOOL ? "2" : "";

  /**
   * Konstruktor.
   * <p>
   * Erstellt die grafische Instanz eines einzelnen {@link tud.ai1.minesweeper.impl.model.Field}.
   * </p>
   *
   * @param entityID Die Entity ID.
   * @param position Position im Fenster, an welcher das
   *        {@link tud.ai1.minesweeper.impl.model.Field} gezeichnet werden soll.
   * @param background Das Hintergrundbild des {@link tud.ai1.minesweeper.impl.model.Field}.
   * @param highlight Das Bild, das beim Hovern angezeigt werden soll.
   * @param mouseLeftClickAction Die Action, die bei einem Linksklick der Maus auf dieses
   *        {@link tud.ai1.minesweeper.impl.model.Field} ausgefuehrt werden soll.
   * @param mouseRightClickAction Die Action die bei einem rechtsclick der Mau auf dieses
   *        {@link tud.ai1.minesweeper.impl.model.Field} ausgefuehrt werden soll.
   * @param coordX X-Position des {@link tud.ai1.minesweeper.impl.model.Field} im Grid des UI.
   * @param coordY Y-Position des {@link tud.ai1.minesweeper.impl.model.Field} im Grid des UI.
   */
  public FieldEntity(String entityID, Vector2f position, Image background, Image highlight,
      Action mouseLeftClickAction, Action mouseRightClickAction, int coordX, int coordY) {
    super(entityID);

    this.coordX = coordX;
    this.coordY = coordY;
    this.clicked = false;
    this.newImage = null;
    this.flagged = false;
    this.entityID = entityID;

    setPosition(position);

    // Bilder aus Dateien laden.
    try {
      this.flaggedImg = new Image(Consts.GAMEASSETS_FOLDER + "flag.png");
    } catch (SlickException e) {
      e.printStackTrace();
    }

    if (highlight != null) {
      this.highlight = highlight;
    }

    if (background != null) {
      ImageRenderComponent renderComp = new ImageRenderComponent(background);
      addComponent(renderComp);
      renderComp.setOwnerEntity(this);
    }

    // Setze Events fuer Mausklicks.
    this.createMouseClickActions(mouseLeftClickAction, Input.MOUSE_LEFT_BUTTON);
    this.createMouseClickActions(mouseRightClickAction, Input.MOUSE_RIGHT_BUTTON);

    // Event fuer Highlighting der Felder beim Hovern.
    MouseEnteredEvent highlighting = new MouseEnteredEvent();
    highlighting.addAction((gc, sb, delta, component) -> this.renderHighlighting = true);
    addComponent(highlighting);

    // Event um das Highlighting zu beenden, wenn Maus das Feld verlaesst.
    Event endHighlighting = new Event("mouse_leave") {
      @Override
      protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
        Shape shape = this.getOwnerEntity().getShape();
        Vector2f mousePosition = new Vector2f(gc.getInput().getMouseX(), gc.getInput().getMouseY());
        return !(shape.contains(mousePosition.x, mousePosition.y));
      }
    };
    endHighlighting.addAction((gc, sb, delta, component) -> this.renderHighlighting = false);
    this.addComponent(endHighlighting);
  }

  @Override
  public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
    super.render(gc, sb, g);

    // Rendere das Zahlen-/Minebild nachdem der Button geklickt wurde.
    if (this.clicked) {
      if (this.newImage != null) {
        g.drawImage(this.newImage, this.getPosition().getX() - this.newImage.getWidth() / 2,
            this.getPosition().getY() - this.newImage.getHeight() / 2);
        return;
      }
    }

    // Rendere das Highlighting des Feldes.
    if (this.renderHighlighting) {
      if (this.highlight != null) {
        g.drawImage(this.highlight, this.getPosition().getX() - this.highlight.getWidth() / 2,
            this.getPosition().getY() - this.highlight.getHeight() / 2);
      }
    }

    // Rendere das Flaggensymbol.
    if (this.flagged) {
      if (this.flaggedImg != null) {
        g.drawImage(this.flaggedImg, this.getPosition().getX() - this.flaggedImg.getWidth() / 2,
            this.getPosition().getY() - this.flaggedImg.getHeight() / 2);
        return;
      }
    }
  }

  /**
   * Setzt die Actions, die bei einem Mausklick ausgefuehrt werden sollen.
   * 
   * @param action Die auszufuehrende Action.
   * @param button Integer der Maustaste identifiziert (Aus Input Enum).
   */
  private void createMouseClickActions(Action action, int button) {
    if (action != null) {
      Event mouseEnter = new MouseEnteredEvent();
      ANDEvent click = new ANDEvent(mouseEnter, new Event("" + button) {
        @Override
        protected boolean performAction(GameContainer gameContainer, StateBasedGame stateBasedGame,
            int i) {
          return gameContainer.getInput().isMousePressed(button);
        }
      });
      click.addAction(action);
      addComponent(click);
    }
  }

  /**
   * Holt die Y-Position im UI Grid.
   *
   * @return Y-Position.
   */
  public int getY() {
    return this.coordY;
  }

  /**
   * Holt die X-Position im UI Grid.
   *
   * @return X-Position.
   */
  public int getX() {
    return this.coordX;
  }

  /**
   * Gibt die Entity-ID aus.
   *
   * @return Entity-ID.
   */
  @Override
  public String toString() {
    return this.entityID;
  }

  /**
   * Wird von einem Spielfeld-Hook aufgerufen, sobald dieses sich geaendert hat, um seine Grafik zu
   * aktualisieren.
   * 
   * @param s String, welcher der {@link FieldEntity} sagt, wie sich das
   *        {@link tud.ai1.minesweeper.impl.model.Field} geaendert hat.
   */
  public void updateUI(String s) {
    switch (s) {
      case Consts.FIELD_FLAGGED:
        click("flag" + adds);
        GamePlayState.currMineAmount--;
        break;
      case Consts.FIELD_MINE:
        click("mine" + adds);
        GamePlayState.currMineAmount--;
        break;
      case Consts.FIELD_HIDDEN:
        this.flagged = false;
        GamePlayState.currMineAmount++;
        this.clicked = false;
        // Image muss auf null gesetzt werden, sonst spinnt minencounter, Keine Ahnung wieso.
        this.newImage = null;
        break;
      default:
        if (GamePlayState.gameOver) {
          click("falseflag" + FieldEntity.adds);
          break;
        }
        if (GamePlayState.board.getField(new Coordinate(this.getX(), this.getY())).isFlagged()) {
          GamePlayState.currMineAmount++;
        }
        click(s);
        break;
    }
  }

  /**
   * Sagt dem Button er wurde geklickt und welches Bild er jetzt darstellen soll.
   *
   * @param symbol Das neu darzustellende Bild.
   */
  public void click(String symbol) {
    this.clicked = true;
    try {
      // Erhoehe Minenzaehler, wenn Flagge durch Board automatisch entfernt wurde.
      if (this.newImage != null
          && this.newImage.getResourceReference()
              .equals(Consts.GAMEASSETS_FOLDER + "flag" + adds + ".png")
          && !GamePlayState.board.getField(new Coordinate(this.getX(), this.getY())).isFlagged()) {
        GamePlayState.currMineAmount++;
      }
      this.newImage = new Image(Consts.GAMEASSETS_FOLDER + symbol + ".png");
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }

  /**
   * Setzt 'clicked' auf false.
   */
  public void close() {
    this.clicked = false;
  }

  /**
   * Sagt dem Button, dass er mit einer Flagge versehen wurde.
   *
   * @param flagged Ob der Parameter geflagged ist.
   */
  public void setFlagged(boolean flagged) {
    this.flagged = flagged;
  }

}
