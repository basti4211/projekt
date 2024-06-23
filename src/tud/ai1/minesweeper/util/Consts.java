package tud.ai1.minesweeper.util;

import tud.ai1.minesweeper.impl.model.Field;

/**
 * Global definierte Konstanten des gesamten Spiels.
 * 
 * @author Maximilian Kratz
 * @version 2020-05-13
 */
public interface Consts {

  /**
   * Name des Spiels. Dieser wird in der Leiste des Fensters angezeigt.
   */
  public static final String GAME_NAME = "Minesweeper";

  /*
   * Betriebssystem-Konstanten. Diese werden dafuer benoetigt, dass das Spiel unter Windows, Linux
   * und macOS lauffaehig ist. Fuer jedes Betriebssystem werden unterschiedliche Bibliotheken aus
   * diesen Ordnern geladen, wenn das Spiel gestartet wird.
   */
  public static final String WINDOWS_LIB_PATH = "org.lwjgl.librarypath";
  public static final String WINDOWS_USER_DIR = "/native/windows";
  public static final String MAC_LIB_PATH = "org.lwjgl.librarypath";
  public static final String MAC_USER_DIR = "/native/macosx";
  public static final String LINUX_LIB_PATH = "org.lwjgl.librarypath";
  public static final String LINUX_USER_DIR = "/native/";
  public static final boolean ALTERNATIVE_BOOL = false;
  public static final String OS_NAME = "os.name";
  public static final String USER_DIR = "user.dir";
  public static final String WINDOWS_OS_NAME = "windows";
  public static final String MAC_OS_NAME = "mac";

  /*
   * Schriftgroessen.
   */
  public static final int FONT_SIZE_GENERAL = 40;

  /**
   * Breite der grafischen Oberflaeche.
   */
  public static final int WINDOW_WIDTH = 1280;

  /**
   * Hoehe der grafischen Oberflaeche.
   */
  public static final int WINDOW_HEIGHT = 720;

  /*
   * States des Spiels und deren Zuordnung zu ints.
   */
  public static final int MAINMENU_STATE = 0;
  public static final int GAMEPLAY_STATE = 1;
  public static final int HIGHSCORE_STATE = 2;

  /*
   * Feld-Bezeichner. Diese geben an, wie der Zustand eines Feldes mit einem String repraesentiert
   * wird.
   */
  public static final String FIELD_FLAGGED = "f";
  public static final String FIELD_MINE = "+";
  public static final String FIELD_HIDDEN = "-";

  /**
   * Hilfe fuer den Start des Spiels. Wird diese Konstante auf 'true' gesetzt, so wird beim Starten
   * des Spiels automatisch ein freies {@link Field} geoeffnet. Dadurch wird verhindert, dass eine
   * Spieler*in mit dem ersten Klick direkt eine Mine oeffnet.
   */
  public static final boolean OPEN_FIRST_FIELD = false;

  /*
   * Board-Begrenzungen (Minima und Maxima).
   */
  public static final int WIDTH_MIN = 2;
  public static final int WIDTH_MAX = 32;
  public static final int HEIGHT_MIN = 2;
  public static final int HEIGHT_MAX = 20;

  /**
   * Groesse eines Feldes.
   */
  public static final int FIELD_SIZE = 32;

  /*
   * Board-Standard-Presets, welche von der GUI abgefragt werden.
   */
  // Easy.
  public static final int WIDTH_PRESET_EASY = 8;
  public static final int HEIGHT_PRESET_EASY = 8;
  public static final int MINES_PRESET_EASY = 10;

  // Medium.
  public static final int WIDTH_PRESET_MEDIUM = 16;
  public static final int HEIGHT_PRESET_MEDIUM = 16;
  public static final int MINES_PRESET_MEDIUM = 40;

  // Hard.
  public static final int WIDTH_PRESET_HARD = 30;
  public static final int HEIGHT_PRESET_HARD = 16;
  public static final int MINES_PRESET_HARD = 99;

  /*
   * Namen der Hintergrundbilder der States. Der Highscore-State verwendet das gleiche
   * Hintergrundbild, wie der Hauptmenue-State.
   */
  public static final String MENU_BACKGROUND_NAME = "menu";
  public static final String GAME_BACKGROUND_NAME = "game";

  /*
   * Field-Bildernamen fuer die verschiedenen States eines Feldes.
   */
  public static final String ENTITY_BACKGROUND = "closedField.png";
  public static final String ENTITY_HIGHLIGHT = "fieldHighlight.png";

  /*
   * Ordner-Pfade fuer die Gamestates und die Menue-Assets. Aus diesen Ordnern werden z. B. Bilder
   * geladen.
   */
  public static final String ASSETS_FOLDER = "assets";
  public static final String GAMEASSETS_FOLDER = ASSETS_FOLDER + "/gamestate/";
  public static final String MENUASSETS_FOLDER = ASSETS_FOLDER + "/menu/";

  /*
   * Highscore-State Einstellungen.
   */
  public static final String HIGHSCORE_FILE = Consts.ASSETS_FOLDER + "/highscoreFile.txt";
  public static final int HIGHSCORE_DISPLAYED_ENTRIES = 10;

  /*
   * Kosten fuer die verschiedenen Cheats.
   */
  public static final int CHEAT_COST_FLAG_MINE = 20;
  public static final int CHEAT_COST_OPEN_EMPTY = 30;
  public static final int CHEAT_COST_OPEN_FIELD = 15;

  /**
   * Standardpfad fuer Mapdatei.
   */
  public static final String DEFAULT_FILE_PATH = Consts.ASSETS_FOLDER + "/maps/default.map";
}
