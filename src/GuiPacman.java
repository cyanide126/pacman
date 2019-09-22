/**
 * GamePacman.java
 * This file is the GUI implementation of the game pacman. This is done through
 * eventhandling and calling methods from board.java.
 *
 * @author 	Yada Chuengsatiansup <ychuengs@ucsd.edu>
 * @since	2/27/19
 *
 * sources	JavaFX Oracle Docs (ImageView, Scene, Shapes, Stage, GridPane, StackPane, Rectangle, Text)
 */

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.util.Scanner;
import javafx.geometry.*;
import java.io.*;


/**
 * class GuiPacman
 *
 * The class GuiPacman reads the pacman board from Board.java
 * and dsiplays the board in a GUI using eventhandlers and panes
 * to interact and update the board.
 *
 */
public class GuiPacman extends Application

{

  private String outputBoard; // The filename for where to save the Board
  private Board board; // The Game Board
  private int direction = 90;

  // Fill colors to choose
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);



  /** Add your own Instance Variables here */

  StackPane sPane;
  GridPane gPane;

  /* 
   * Name:      start
   * Purpose:   Start and keep the game running.
   * Parameter: Stage primaryStage - javaFx Stage (GUI Window)
   * Return:    nothing
   */
  @Override
  public void start(Stage primaryStage) throws Exception
  {
    // Process Arguments and Initialize the Game Board
    processArgs(getParameters().getRaw().toArray(new String[0]));

    sPane = new StackPane();//Stackpane used for layers in the display
    gPane = new GridPane();//GirdPane used to display pacman grid

    //GridPane settings
    gPane.setAlignment(Pos.CENTER);
    gPane.setPadding(new Insets(11.5,12.5,13.5,14.5));
    gPane.setHgap(5.5);
    gPane.setVgap(5.5);

    //StackPane settings
    sPane.setStyle("-fx-background-color: rgb(0,0,0)");
    sPane.getChildren().add(0,gPane);
    sPane.setMaxSize(10,10);

    //Stage settings
    Scene scene = new Scene(sPane);
    primaryStage.setTitle("Pacman");
    primaryStage.setScene(scene);
    primaryStage.setMaxHeight(650);
    primaryStage.setMaxWidth(600);
    primaryStage.setMinHeight(650);
    primaryStage.setMinWidth(600);
    primaryStage.show();

    updateBoard();
    scene.setOnKeyPressed(new myKeyHandler());    

  }



    /* 
   * Name:      updateBoard
   * Purpose:   Updates the display of the pacman GUI display
   * Parameter: nothing
   * Return:    nothing
   */
  public void updateBoard() {

  	//Update Score
    Text txt = new Text();
    txt.setText("Score: " + board.getScore());
    txt.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 20));
    txt.setFill(COLOR_VALUE_LIGHT);
    gPane.add(txt, 4, 0, 4, 1);

    //Display Pacman game title
    Text pac = new Text();
    pac.setText("Pac-Man");
    pac.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 20));
    pac.setFill(COLOR_VALUE_LIGHT);
    gPane.add(pac, 0, 0, 4, 1);

    //Update pacman board
    for (int i = 0; i < board.GRID_SIZE; i++) {
      for (int j = 0; j < board.GRID_SIZE; j++) {
        Tile tile = new Tile(board.getGrid()[i][j]);
        gPane.add(tile.getNode(), j, i + 1);
      }
    }
  }



  /*
   * Name:       myKeyHandler
   *
   * Purpose:    The class myKeyHandler handles all the keyevents
   *			 in the pacman game such as up, down, left, etc.
   *
   *
   */
  private class myKeyHandler implements EventHandler<KeyEvent> {
  	//Instance variable used to store the state of the game
  	//so that nothing happens after game is over
    boolean gameOver = false;

   /* 
    * Name:      handle
    * Purpose:   handle the KeyEvent of user's input.
    * Parameter: KeyEvent e - User pressing keys
    * Return:    nothing
    */
    @Override
    public void handle (KeyEvent e) {

//      if (e.getCode().equals(KeyCode.Q)) {
  //      saveBoard();
    //    System.out.println("Quitting");
      //  System.exit(-1);
     // }

      //If game is over and game was not over before
      // display the gameover screen.
      if (board.isGameOver() && gameOver==false) {
        gameIsOver();
      } 
      //If game is not over process the keys pressed
      else if (!board.isGameOver()) {
        gPane.getChildren().clear();

        if (e.getCode().equals(KeyCode.S)) {
          saveBoard();
        }
        else if (e.getCode().equals(KeyCode.UP) && board.canMove(Direction.UP)) {
          board.move(Direction.UP);
          direction = 270;//Rotate pacman in direction that it is moving in.
          System.out.println("Moving Up");
        }
        else if (e.getCode().equals(KeyCode.DOWN) && board.canMove(Direction.DOWN)) {
          board.move(Direction.DOWN);
          direction = 90;
          System.out.println("Moving Down");
        }
        else if (e.getCode().equals(KeyCode.LEFT) && board.canMove(Direction.LEFT)) {
          board.move(Direction.LEFT);
          direction = 180;
          System.out.println("Moving Left");
        }
        else if (e.getCode().equals(KeyCode.RIGHT) && board.canMove(Direction.RIGHT)) {
          board.move(Direction.RIGHT);
          direction = 0;
          System.out.println("Moving Right");
        }
        else if (!e.getCode().equals(KeyCode.Q)) {
          printUsage();
        }
        updateBoard();
      }
    }


    /* 
     * Name:      gameIsOver
     * Purpose:   Check if the game is over and show the gameover board.
     * Parameter: nothing
     * Return:    nothing
     */
    private void gameIsOver() {

      gameOver = true;//update state of game

      //Rectangle setting
      Rectangle rOver = new Rectangle();
      rOver.setWidth(1000);
      rOver.setHeight(1000);
      rOver.setX(5);
      rOver.setY(5);
      rOver.setFill(COLOR_GAME_OVER);
      sPane.getChildren().add(1, rOver);

      //Game over text setting
      Text tOver = new Text();
      tOver.setText("Game Over!");
      tOver.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 50));
      tOver.setFill(COLOR_VALUE_DARK);
      sPane.getChildren().add(2, tOver);

    }

    /* 
     * Name:      saveBoard
     * Purpose:   Saves the board
     * Parameter: nothing
     * Return:    nothing
     */
    private void saveBoard() {
      try {
        board.saveBoard(outputBoard);
      } catch (Exception x) {
        System.out.println("Exception, unable to save board");
      }
      System.out.println("Saving Board to " + outputBoard);
    }
  } // End of Inner Class myKeyHandler.



  /*
   * Name:        Tile
   *
   * Purpose:     This class tile helps to make the tiles in the board 
   *              presented using JavaFX. Whenever a tile is needed,
   *              the constructor taking one char parameter is called
   *              and create certain ImageView fit to the char representation
   *              of the tile.
   * 
   *
   */
  private class Tile {

    private ImageView repr;   // This field is for the Rectangle of tile.
 
    /* 
     * Constructor
     *
     * Purpose:   match image to tileappearance in order to display the
     *			  pacman board in a GUI.
     * Parameter: char tileAppearance - the appearance of the pacman board
     			  at a certain grid location.
     *
     */
    public Tile(char tileAppearance) {
      Image image;
      switch (tileAppearance) {
        case 'Q':
          image = new Image("pinky_left.png");
          this.repr = new ImageView(image);
          break;

        case 'W':
          image = new Image("inky_down.png");
          this.repr = new ImageView(image);
          break;

        case 'E':
          image = new Image("blinky_left.png");
          this.repr = new ImageView(image);
          break;

        case 'R':
          image = new Image("clyde_up.png");
          this.repr = new ImageView(image);
          break;

        case ' ':
          image = new Image("dot_eaten.png");
          this.repr = new ImageView(image);
          break;

        case 'X':
          image = new Image("pacman_dead.png");
          this.repr = new ImageView(image);
          break;

        case '*':
          image = new Image("dot_uneaten.png");
          this.repr = new ImageView(image);
          break;

        case 'C':
          image = new Image("cherry.png");
          this.repr = new ImageView(image);
          break;

        case 'B':
          image = new Image("ghostblue.png");
          this.repr = new ImageView(image);
          break;

        default:
          image = new Image("pacman_right.png");
          this.repr = new ImageView(image);
          this.repr.setRotate(direction);
          break;
      }
      this.repr.setFitWidth(50);
      this.repr.setFitHeight(50);

    }

     /* 
     * Name:      getNode()
     * Purpose:   Getter for repr.
     * Parameter: nothing
     * Return:    ImageView this.repr
     */
    public ImageView getNode() {
      //TODO
      return this.repr;
    }

  }  // End of Inner class Tile




  /** DO NOT EDIT BELOW */

  // The method used to process the command line arguments
  private void processArgs(String[] args)
  {
    String inputBoard = null;   // The filename for where to load the Board
    int boardSize = 0;          // The Size of the Board

    // Arguments must come in pairs
    if((args.length % 2) != 0)
    {
      printUsage();
      System.exit(-1);
    }

    // Process all the arguments 
    for(int i = 0; i < args.length; i += 2)
    {
      if(args[i].equals("-i"))
      {   // We are processing the argument that specifies
        // the input file to be used to set the board
        inputBoard = args[i + 1];
      }
      else if(args[i].equals("-o"))
      {   // We are processing the argument that specifies
        // the output file to be used to save the board
        outputBoard = args[i + 1];
      }
      else if(args[i].equals("-s"))
      {   // We are processing the argument that specifies
        // the size of the Board
        boardSize = Integer.parseInt(args[i + 1]);
      }
      else
      {   // Incorrect Argument 
        printUsage();
        System.exit(-1);
      }
    }

    // Set the default output file if none specified
    if(outputBoard == null)
      outputBoard = "Pac-Man.board";
    // Set the default Board size if none specified or less than 2
    if(boardSize < 3)
      boardSize = 10;

    // Initialize the Game Board
    try{
      if(inputBoard != null)
        board = new Board(inputBoard);
      else
        board = new Board(boardSize);
    }
    catch (Exception e)
    {
      System.out.println(e.getClass().getName() + " was thrown while creating a " +
          "Board from file " + inputBoard);
      System.out.println("Either your Board(String, Random) " +
          "Constructor is broken or the file isn't " +
          "formated correctly");
      System.exit(-1);
    }
  }

  // Print the Usage Message 
  private static void printUsage()
  {
    System.out.println("GuiPacman");
    System.out.println("Usage:  GuiPacman [-i|o file ...]");
    System.out.println();
    System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
    System.out.println();
    System.out.println("  -i [file]  -> Specifies a Pacman board that should be loaded");
    System.out.println();
    System.out.println("  -o [file]  -> Specifies a file that should be used to save the Pac-Man board");
    System.out.println("                If none specified then the default \"Pac-Man.board\" file will be used");
    System.out.println("  -s [size]  -> Specifies the size of the Pac-Man board if an input file hasn't been");
    System.out.println("                specified.  If both -s and -i are used, then the size of the board");
    System.out.println("                will be determined by the input file. The default size is 10.");
  }
}


