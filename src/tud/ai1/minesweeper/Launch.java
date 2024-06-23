package tud.ai1.minesweeper;

import eea.engine.entity.StateBasedEntityManager;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import tud.ai1.minesweeper.util.Consts;
import tud.ai1.minesweeper.view.states.GamePlayState;
import tud.ai1.minesweeper.view.states.HighscoreState;
import tud.ai1.minesweeper.view.states.MainMenuState;

/**
 * Diese Klasse startet das Spiel "Minesweeper". Sie enthaelt drei States fuer das Menue, das
 * eigentliche Spiel und die Highscore-Ansicht.
 * 
 * @author Joshua Hatzinger
 * @version 2024-05-27
 */
public class Launch extends StateBasedGame {

  /**
   * Konstruktor der Klasse. Ruft den Super-Konstruktor mit dem Namen des Spiels auf.
   * 
   * @param name Name des Spiels.
   */
  public Launch(String name) {
    super(name);
  }

  /**
   * Main-Methode zum Starten des Spiels. Testet, auf welchem Betriebssystem das Programm gestartet
   * wurde und setzt systemabhaengige Bibliotheken.
   * 
   * @param args Laufzeit-Argumente, die nicht verwendet werden.
   * @throws SlickException Wirft eine {@link SlickException} wenn im Slick-Framework eine Ausnahme
   *         passiert.
   */
  public static void main(final String[] args) throws SlickException {
    // Setze den library Pfad abhaengig vom Betriebssystem.
    if (System.getProperty(Consts.OS_NAME).toLowerCase().contains(Consts.WINDOWS_OS_NAME)) {
      System.setProperty(Consts.WINDOWS_LIB_PATH,
          System.getProperty(Consts.USER_DIR) + Consts.WINDOWS_USER_DIR);
    } else if (System.getProperty(Consts.OS_NAME).toLowerCase().contains(Consts.MAC_OS_NAME)) {
      System.setProperty(Consts.MAC_LIB_PATH,
          System.getProperty(Consts.USER_DIR) + Consts.MAC_USER_DIR);
    } else {
      System.setProperty(Consts.LINUX_LIB_PATH, System.getProperty(Consts.USER_DIR)
          + Consts.LINUX_USER_DIR + System.getProperty(Consts.OS_NAME).toLowerCase());
    }

    // Setze dieses StateBasedGame in einen App Container (oder Fenster).
    AppGameContainer app;
    // Easteregg: Wenn flag gesetzt ist, dann sollte als Name Spacesweeper verwendet werden.
    if (!Consts.ALTERNATIVE_BOOL) {
      app = new AppGameContainer(new Launch(Consts.GAME_NAME));
    } else {
      app = new AppGameContainer(new Launch("Spacesweeper"));
    }

    // Legt die Einstellungen des Fensters fest und starte das Fenster (nicht aber im
    // Vollbildmodus).
    app.setDisplayMode(Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT, false);

    // Lege Bildwiederholrate fest (Maximum).
    app.setVSync(true);

    // Zeige FPS-Wert nicht im Spiel an.
    app.setShowFPS(false);

    // Starte Spiel.
    app.start();
  }

  /**
   * Initialisiert die GameStates im {@link StateBasedGame} und im {@link StateBasedEntityManager}.
   * 
   * @param arg0 GameContainer
   * @throws SlickException Wirft eine {@link SlickException}, wenn im Slick-Framework eine Ausnahme
   *         passiert.
   */
  @Override
  public void initStatesList(GameContainer arg0) throws SlickException {
    // Fuege dem StateBasedGame die States hinzu (der zuerst hinzugefuegte State wird als erster
    // State gestartet).
    addState(new MainMenuState(Consts.MAINMENU_STATE));
    addState(new GamePlayState(Consts.GAMEPLAY_STATE));
    addState(new HighscoreState(Consts.HIGHSCORE_STATE));

    // Fuege dem StateBasedEntityManager die States hinzu.
    StateBasedEntityManager.getInstance().addState(Consts.MAINMENU_STATE);
    StateBasedEntityManager.getInstance().addState(Consts.GAMEPLAY_STATE);
    StateBasedEntityManager.getInstance().addState(Consts.HIGHSCORE_STATE);
  }

}
