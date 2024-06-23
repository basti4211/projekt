package tud.ai1.minesweeper.view.states;

import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import tud.ai1.minesweeper.impl.model.Highscore;
import tud.ai1.minesweeper.impl.model.HighscoreEntry;
import tud.ai1.minesweeper.util.Consts;
import tud.ai1.minesweeper.util.FileOperations;
import tud.ai1.minesweeper.util.Utilities;
import tud.ai1.minesweeper.view.entities.MenuButton;

/**
 * State fuer die Highscores.
 *
 * @author Joshua Hatzinger
 * @version 2024-05-28
 */
public class HighscoreState extends BasicGameState {

  private int stateID;
  private StateBasedEntityManager entityManager;
  private TrueTypeFont font;
  private LinkedList<HighscoreEntry> list;

  /**
   * Konstruktor. Erstellt ein neues {@link HighscoreState}-Objekt mit der uebergebenen state-ID.
   *
   * @param stateID Die Entity ID
   */
  public HighscoreState(final int stateID) {
    this.stateID = stateID;
    entityManager = StateBasedEntityManager.getInstance();
  }

  @Override
  public void init(final GameContainer gameContainer, final StateBasedGame stateBasedGame)
      throws SlickException {
    System.out.println("HighscoreState");

    this.font = new TrueTypeFont(
        new java.awt.Font("Arial", java.awt.Font.BOLD, Consts.FONT_SIZE_GENERAL), false);
  }

  @Override
  public void enter(final GameContainer gameContainer, final StateBasedGame stateBasedGame)
      throws SlickException {
    // Hintergrund setzen.
    Entity background = new Entity("background");
    background.setPosition(Utilities.getCenterPoint(gameContainer));

    // Hintergrund: Easteregg. Wenn boolsches Flag aktiviert ist, soll ein anderer Hintergrund
    // gewaehlt werden.
    if (!Consts.ALTERNATIVE_BOOL) {
      background.addComponent(new ImageRenderComponent(
          new Image(Consts.MENUASSETS_FOLDER + Consts.MENU_BACKGROUND_NAME + ".png")));
    } else {
      background.addComponent(
          new ImageRenderComponent(new Image(Consts.ASSETS_FOLDER + "/space/space_highscore.jpg")));
    }
    this.entityManager.addEntity(this.stateID, background);

    // Back Button setzen.
    MenuButton tmp = new MenuButton("BACK",
        new Vector2f(Consts.WINDOW_WIDTH - font.getWidth("BACK") + 10, 15), 0.1f,
        new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 35), false), 
        Consts.ALTERNATIVE_BOOL ? Color.white : Color.black);
    ANDEvent andE = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    andE.addAction(new ChangeStateAction(Consts.MAINMENU_STATE));
    tmp.addComponent(andE);
    entityManager.addEntity(stateID, tmp);

    // Erzeuge neues Highscore-Objekt.
    Highscore highscore = new Highscore();
    try {
      highscore.initHighscore(FileOperations.readFile(Consts.HIGHSCORE_FILE));
    } catch (Exception e) {
      System.out.println("Highscore-Datei existiert nicht, wird neu erzeugt");
    }
    list = (LinkedList<HighscoreEntry>) highscore.getHighscore();
  }

  @Override
  public void render(final GameContainer gameContainer, final StateBasedGame stateBasedGame,
      final Graphics graphics) throws SlickException {
    this.entityManager.renderEntities(gameContainer, stateBasedGame, graphics);

    graphics.setFont(font);
    // Implementiere Osterei.
    if (Consts.ALTERNATIVE_BOOL) {
      graphics.drawString("Statistics", 5, 2);
    } else {
      graphics.drawString("Highscore", 5, 2);
    }

    graphics.setColor(Consts.ALTERNATIVE_BOOL ? Color.white : Color.black);
    // Printe Eintraege auf Bildschirm.
    for (int i = 0; i < Consts.HIGHSCORE_DISPLAYED_ENTRIES; i++) {
      final String prefix = (i >= 9) ? "" : "0";

      if (i < list.size()) {
        graphics.drawString(prefix + (i + 1) + ": " + makePretty(list.get(i).toString()), 30,
            60 + (50 * i));
      } else {
        graphics.drawString(prefix + (i + 1) + ": -", 30, 60 + (50 * i));
      }
    }
  }

  /**
   * Manipuliere String, um ihn an die Darstellung im {@link HighscoreState} anzupassen.
   * 
   * @param s String, der formatiert werden soll.
   * @return Formatierter String.
   */
  private String makePretty(final String s) {
    String outputString = "";
    String[] list = s.split("\\;");
    String[] date = list[0].split("\\.");
    String difString = "";
    String timeString = "";

    if (Consts.ALTERNATIVE_BOOL) {
      //Easter Egg
      difString = "Difficulty: ";
      timeString = "Flight Duration: ";
    } else {
      difString = "Difficulty: ";
      timeString = "Time: ";
    }

    outputString += date[0] + "." + date[1] + "." + " - ";
    outputString += date[2].split(" ")[1] + " - " + difString + Utilities.diffNice(list[1]) + " - "
        + timeString + list[2] + "s";
    return outputString;
  }

  @Override
  public void update(final GameContainer gameContainer, final StateBasedGame stateBasedGame,
      final int delta) throws SlickException {
    this.entityManager.updateEntities(gameContainer, stateBasedGame, delta);
  }

  @Override
  public void leave(final GameContainer gc, final StateBasedGame sb) throws SlickException {
    this.entityManager.clearEntitiesFromState(stateID);
  }

  @Override
  public int getID() {
    return this.stateID;
  }

}
