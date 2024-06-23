package tud.ai1.minesweeper.view.states;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import java.time.LocalDateTime;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import tud.ai1.minesweeper.impl.logic.Board;
import tud.ai1.minesweeper.impl.logic.Cheats;
import tud.ai1.minesweeper.impl.model.Coordinate;
import tud.ai1.minesweeper.impl.model.Field;
import tud.ai1.minesweeper.impl.model.Highscore;
import tud.ai1.minesweeper.impl.model.HighscoreEntry;
import tud.ai1.minesweeper.util.Consts;
import tud.ai1.minesweeper.util.FileOperations;
import tud.ai1.minesweeper.util.Utilities;
import tud.ai1.minesweeper.view.entities.FieldEntity;
import tud.ai1.minesweeper.view.entities.MenuButton;

/**
 * State fuer den normalen Spielablauf.
 * 
 * @author Artur Seitz
 * @author Dennis Schirmer
 * @author Mahmoud El-Hindi
 * @author Darjush Siahdohoni
 * @author Igor Cherepanov
 * @author Hermann Berket
 * @author Niklas Vogel
 * @author Phil Reize
 * @author Maximilian Kratz
 * @version 2020-07-08
 */
public class GamePlayState extends BasicGameState {

  /**
   * Variable fuer die State-ID.
   */
  private int stateID;

  /**
   * StateBasedEntityManager.
   */
  private StateBasedEntityManager entityManager;

  /**
   * Variable fuer die verstrichene Zeit.
   */
  public static long time;

  /**
   * Fuehre Code nur einmal zum Ende des Spiels aus.
   */
  private boolean lastWill;

  /**
   * Variable fuer die aktuelle Anzahl von Minen.
   */
  public static int currMineAmount = 0;

  /**
   * Das Board.
   */
  public static Board board;

  /**
   * Wenn true, dann soll eine vorgefertigte Map aus einer Datei geladen werden.
   */
  public static boolean premadeMap = false;

  /**
   * Dateipfad, von welchem eine Map geladen werden soll.
   */
  public static String filePath = "";

  /**
   * Variable fuer die Schriftart.
   */
  private TrueTypeFont font;

  /**
   * Variable fuer die Farbe.
   */
  private Color currentColor = Color.black;

  /**
   * Action ausgefuehrt bei Klick des Next bzw. Back-Buttons.
   */
  private Event buttonAction = null;

  /**
   * Entity des Back bzw. Next Buttons.
   */
  private MenuButton buttonEntity = null;

  /**
   * Feld, das Bombenanzahl anzeigt.
   */
  private MenuButton bombButton = null;

  /**
   * True wenn das Spielfeld geoeffnet ist.
   */
  private static boolean fieldOpened;

  /**
   * True, wenn das Spiel vorbei ist.
   */
  public static boolean gameOver;

  /**
   * True, wenn das Spiel verloren wurde.
   */
  public static boolean gameLost;

  /**
   * Konstanten fuer openFields Methode.
   */
  private static final boolean OPEN_ONLY_MINES = true;
  private static final boolean OPEN_ALL_FIELDS = false;

  /**
   * Easteregg: Wenn boolean Flag gesetzt ist, dann soll ein anderer String angezeigt werden.
   */
  private static final String DISPLAY_MINE_NAME = Consts.ALTERNATIVE_BOOL ? "Asteroids" : "Mines";

  /**
   * Konstruktor.
   *
   * @param stateID Die Entity ID.
   */
  public GamePlayState(final int stateID) {
    this.stateID = stateID;
    entityManager = StateBasedEntityManager.getInstance();
  }

  @Override
  public void init(final GameContainer gameContainer, final StateBasedGame stateBasedGame)
      throws SlickException {
    System.out.println("GamePlayState");

    this.font = new TrueTypeFont(
        new java.awt.Font("Arial", java.awt.Font.BOLD, Consts.FONT_SIZE_GENERAL), false);
  }

  @Override
  public void enter(final GameContainer gameContainer, final StateBasedGame stateBasedGame)
      throws SlickException {
    time = 0;
    lastWill = false;
    this.currentColor = Consts.ALTERNATIVE_BOOL ? Color.white : Color.black;

    // Setze Status des Spielfelds zurueck.
    GamePlayState.fieldOpened = GamePlayState.gameOver = GamePlayState.gameLost = false;

    // Lade Board.
    if (premadeMap) {
      System.out.println("Lade vorgefertigte Map: " + filePath);
      GamePlayState.board = new Board(filePath);
    } else {
      GamePlayState.board = new Board();
    }

    // Erzeuge Hintergrund.
    Entity background = new Entity("background");
    background.setPosition(Utilities.getCenterPoint(gameContainer));

    // Hintergrund: Easteregg. Wenn boolsches Flag aktiviert ist, soll ein anderer Hintergrund
    // gewaehlt werden.
    if (!Consts.ALTERNATIVE_BOOL) {
      background.addComponent(new ImageRenderComponent(
          new Image(Consts.GAMEASSETS_FOLDER + Consts.GAME_BACKGROUND_NAME + ".png")));
    } else {
      background.addComponent(new ImageRenderComponent(
          new Image(Consts.ASSETS_FOLDER + "/space/space.jpg")));
    }
    this.entityManager.addEntity(this.stateID, background);

    // Erzeuge Spielfeld.
    this.createField();

    // Erzeuge Cheat-Buttons.
    this.createCheats();

    // Erzeuge Back Button + Minenzaehler.
    this.createMenuButtons();

    // Falls Flag gesetzt ist wird zur Hilfe das erste Feld geoeffnet.
    if (Consts.OPEN_FIRST_FIELD) {
      board.triggerRevealRandomField(false);
    }
  }

  /**
   * Erstelle die grafischen Felder des Boards.
   * 
   * @throws SlickException Wirft eine {@link SlickException}, falls Bilder nicht geladen werden
   *         koennen.
   */
  private void createField() throws SlickException {
    FieldEntity[][] field =
        new FieldEntity[GamePlayState.board.getWidth()][GamePlayState.board.getHeight()];

    /*
     * Erzeuge die Actions, die beim Rechts- bzw. Linksklick auf ein Feld ausgefuehrt werden sollen.
     */
    Action openField = new Action() {
      @Override
      public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i,
          Component component) {
        if (gameLost && GamePlayState.fieldOpened) {
          return;
        }
        FieldEntity entity = ((FieldEntity) component.getOwnerEntity());
        if (GamePlayState.board.getField(new Coordinate(entity.getX(), entity.getY()))
            .isFlagged()) {
          return;
        }
        GamePlayState.board.clickNumber(new Coordinate(entity.getX(), entity.getY()));
        GamePlayState.board.reveal(new Coordinate(entity.getX(), entity.getY()));
        GamePlayState.gameLost = GamePlayState.board.isLost();
        GamePlayState.gameOver = GamePlayState.gameLost || GamePlayState.board.isWon();
      }
    };
    Action flagField = new Action() {
      @Override
      public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i,
          Component component) {
        if (gameLost && GamePlayState.fieldOpened || gameOver) {
          return;
        }
        FieldEntity entity = ((FieldEntity) component.getOwnerEntity());
        Coordinate coordinate = new Coordinate(entity.getX(), entity.getY());
        entity.setFlagged(board.flag(coordinate));
      }
    };

    GamePlayState.currMineAmount = GamePlayState.board.getNumberOfMines();

    // Fuelle das Board mit grafischen Entities, uebergebe die zwei erstellten Actions.
    for (int x = 0; x < GamePlayState.board.getWidth(); x++) {
      for (int y = 0; y < GamePlayState.board.getHeight(); y++) {

        // Berechne die Positionen im Fenster, so dass das Feld zentriert ist.
        float xpos =
            16 + (Consts.WINDOW_WIDTH - GamePlayState.board.getWidth() * Consts.FIELD_SIZE) / 2
                + x * Consts.FIELD_SIZE;
        float ypos =
            (Consts.WINDOW_HEIGHT - GamePlayState.board.getHeight() * Consts.FIELD_SIZE - 70f) / 2
                + 70 + y * Consts.FIELD_SIZE;
        Vector2f positionVector = new Vector2f(xpos, ypos);

        /*
         * Die FieldEntities sind im Gegensatz zum Board in einem (x,y) basierten Array gespeichert,
         * nicht in (y,x).
         */
        FieldEntity button = new FieldEntity("button " + x + " " + y, positionVector,
            new Image(Consts.GAMEASSETS_FOLDER + Consts.ENTITY_BACKGROUND),
            new Image(Consts.GAMEASSETS_FOLDER + Consts.ENTITY_HIGHLIGHT), openField, flagField, x,
            y);
        entityManager.addEntity(stateID, button);
        GamePlayState.board.getField(new Coordinate(x, y)).setUpdater(button::updateUI);
        field[x][y] = button;
      }
    }
  }

  @Override
  public void render(final GameContainer gameContainer, final StateBasedGame stateBasedGame,
      final Graphics graphics) throws SlickException {
    this.entityManager.renderEntities(gameContainer, stateBasedGame, graphics);
    this.bombButton.changeText(DISPLAY_MINE_NAME + ": " + GamePlayState.currMineAmount);

    // Setze Schriftfarbe zurueck.
    graphics.setFont(font);
    graphics.setColor(this.currentColor);

    if (gameOver && !GamePlayState.gameLost) {
      // Easteregg: Wenn Flag true ist, dann anderen Namen verwenden.
      graphics.drawString((Consts.ALTERNATIVE_BOOL ? "GALAXY SAVED" : "GAME WON"), 5, 2);
      this.currentColor = Color.green;

      if (!lastWill) {
        // Entferne Action, die zum Hauptmenu fuehrt und fuege Action in Highscore-State hinzu.
        this.buttonEntity.removeComponent(this.buttonAction);
        lastWill = true;
        this.currentColor = Color.green;
        this.saveHighscore();
        this.openFields(OPEN_ALL_FIELDS);
        ANDEvent andE = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
        andE.addAction(new ChangeStateAction(Consts.HIGHSCORE_STATE));
        this.buttonEntity.addComponent(andE);
        this.buttonEntity.changeText("NEXT");
      }
    } else if (gameOver && GamePlayState.gameLost) {
      graphics.drawString("GAME LOST", 5, 2);

      if (!lastWill) {
        GamePlayState.gameLost = true;
        this.currentColor = Color.red;
        this.openFields(OPEN_ONLY_MINES);
        lastWill = true;
      }
    } else {
      graphics.drawString(Utilities.secInMin(time / 1000), 5, 2);
    }
  }

  @Override
  public void update(final GameContainer gameContainer, final StateBasedGame stateBasedGame,
      final int delta) throws SlickException {
    this.entityManager.updateEntities(gameContainer, stateBasedGame, delta);
    // Limitiere maximalen Zeitschritt auf 200ms.
    time += Math.min(delta, 200);
  }

  @Override
  public void leave(final GameContainer gc, final StateBasedGame sb) throws SlickException {
    this.entityManager.clearEntitiesFromState(stateID);
  }

  @Override
  public int getID() {
    return stateID;
  }

  /**
   * Speichert den {@link Highscore} zum Ende des Spiels ab.
   */
  private void saveHighscore() {
    Highscore h = new Highscore();
    try {
      h.initHighscore(FileOperations.readFile(Consts.HIGHSCORE_FILE));
    } catch (final Exception e) {
      System.out.println(
          "Fehler in initHighscore(), es wird eine Exception geworfen: " + e.getStackTrace());
    } finally {
      h.addEntry(new HighscoreEntry(LocalDateTime.now(), this.calculateDifficulty(), time / 1000));
      h.saveToFile(Consts.HIGHSCORE_FILE);
    }
  }

  /**
   * Berechnet die Schwierigkeit des Spiels aus den {@link Board} Parametern.
   * 
   * @return Die Schwierigkeit zwischen 0 und 100 als Double.
   */
  private double calculateDifficulty() {
    double diff = (double) GamePlayState.board.getNumberOfMines()
        / (double) (GamePlayState.board.getHeight() * GamePlayState.board.getWidth());
    // Wandle Prozent in Zahl zwischen 0 - 100.
    diff *= 100;
    // Nehme nur die ersten beiden Stellen nach dem Komma.
    diff = (double) ((int) (diff * 100)) / 100;
    // 100.0 Schwierigkeit wuerde sonst als 0 angezeigt werden.
    if (diff == 100.0) {
      diff = 99.99;
    }
    return diff;
  }

  /**
   * Erzeugt die graphischen Buttons des States inklusive Actions.
   */
  private void createMenuButtons() {
    // Back Button
    MenuButton tmp = new MenuButton("BACK",
        new Vector2f(Consts.WINDOW_WIDTH - font.getWidth("BACK") + 10, 15), 0.1f,
        new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 35), false), 
        Consts.ALTERNATIVE_BOOL ? Color.white : Color.black);
    ANDEvent andE = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    andE.addAction(new ChangeStateAction(Consts.MAINMENU_STATE));
    tmp.addComponent(andE);
    this.buttonEntity = tmp;
    this.buttonAction = andE;
    entityManager.addEntity(stateID, tmp);

    // Minen zaehler.
    this.bombButton = new MenuButton(DISPLAY_MINE_NAME + ": 0",
        new Vector2f(Consts.WINDOW_WIDTH - font.getWidth(DISPLAY_MINE_NAME + ": 10") + 60, 70),
        0.1f, new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 25), false),
        Consts.ALTERNATIVE_BOOL ? Color.white : Color.black);
    ANDEvent andEv = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    andEv.addAction(new Action() {
      @Override
      public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
        System.out.println("Read code to find easteregg!");
      }
    });
    this.bombButton.addComponent(andEv);
    entityManager.addEntity(stateID, this.bombButton);
  }

  /**
   * Erzeugt die grafischen Buttons zum ausfuehren der {@link Cheats}.
   */
  private void createCheats() {
    ANDEvent andEv;

    // Setze Actions fuer alle Cheats.
    Action cheat1 = new Action() {
      @Override
      public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
        if (Cheats.flagMine(GamePlayState.board)) {
          GamePlayState.time += Consts.CHEAT_COST_FLAG_MINE * 1000;
        }
      }
    };

    Action cheat2 = new Action() {
      @Override
      public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
        if (Cheats.triggerOpenSpace(GamePlayState.board)) {
          GamePlayState.time += Consts.CHEAT_COST_OPEN_EMPTY * 1000;
          GamePlayState.gameLost = GamePlayState.board.isLost();
          GamePlayState.gameOver = GamePlayState.gameLost || GamePlayState.board.isWon();
        }
      }
    };

    Action cheat3 = new Action() {
      @Override
      public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
        if (Cheats.openRandomNumberField(GamePlayState.board)) {
          GamePlayState.time += Consts.CHEAT_COST_OPEN_FIELD * 1000;
          GamePlayState.gameLost = GamePlayState.board.isLost();
          GamePlayState.gameOver = GamePlayState.gameLost || GamePlayState.board.isWon();
        }
      }
    };

    // Erzeuge MenuButtons fuer jeden Cheat.
    Action[] cheats = {cheat1, cheat2, cheat3};
    String[] cheatNames = {"Flag Mine", "Open Empty", "Open Field"};
    TrueTypeFont cheatFont =
        new TrueTypeFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 34), false);
    for (int i = 0; i < cheats.length; i++) {
      MenuButton m = new MenuButton(cheatNames[i], new Vector2f(270 + 280 * i, 20), 1.0f, cheatFont,
          Consts.ALTERNATIVE_BOOL ? Color.white : Color.black);
      andEv = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
      andEv.addAction(cheats[i]);
      m.addComponent(andEv);
      entityManager.addEntity(stateID, m);
    }
  }

  /**
   * Oeffnet das Spielfeld, je nach uebergebenem Wert.
   * 
   * @param onlyBombs Gibt an, ob nur die Bomben oder das gesamte {@link Field} geoeffnet werden
   *        soll.
   */
  private void openFields(final boolean onlyBombs) {
    if (GamePlayState.fieldOpened) {
      return;
    }
    GamePlayState.gameOver = true;
    for (int x = 0; x < GamePlayState.board.getWidth(); x++) {
      for (int y = 0; y < GamePlayState.board.getHeight(); y++) {
        Field field = GamePlayState.board.getField(new Coordinate(x, y));
        if (onlyBombs && !field.isMined() && !field.isFlagged()
            || onlyBombs && field.isMined() && field.isFlagged()) {
          continue;
        }
        // Falsche Flagge markieren.
        if (field.isFlagged() && onlyBombs && !field.isMined()) {
          field.reveal();
        }
        // Bomben ohne Flagge mit Flagge versehen.
        if (!onlyBombs && !field.isFlagged() && field.isMined()) {
          field.flag();
        }
        if (onlyBombs) {
          GamePlayState.board.reveal(new Coordinate(x, y));
        }
      }
    }
    GamePlayState.fieldOpened = true;
    GamePlayState.currMineAmount = 0;
  }

}
