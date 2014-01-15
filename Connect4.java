/**
 * This program allows us to play connect4 game
 * The goal is to get 4 in a row/column/horizontally
 * This class is the "controller" part of the model-view-controller pattern.
 */

public class Connect4
{

  public static void main(String args[]) 
  {
    
    // Hold the view methods.  Also whether the update should be 
    // incremental (display changes as they occur) or all at once.
    Connect4View view = new Connect4ViewText(); 
    
    Player [] players = new Player[2];                // Array to hold the players
    
    // Initialize the game 

    players[0] = makePlayer(view, "first");
    players[1] = makePlayer(view, "second");
    
    // Hold current game state
    Connect4Game state = new Connect4Game(0, players, view);  

    view.display(state);

    while ((!state.gameIsOver())&&(!state.isFull())) 
    {
    	int move = state.getPlayerToMove().getMove(state, view);
    	state.makeMove(move);
    	//System.out.println(state.score(state.getPlayerNum())+" for "+state.getPlayerToMove().getName());
    	view.display(state);
    }
    
    if(state.isFull()&&!state.gameIsOver())
    	System.out.println("We have run out of space");
    else
    	System.out.println(state.getOtherPlayer().getName()+"  wins!");
  }
  
  /** 
   * Constructs a Connect4 player.  If the name contains "Computer" it
   * constructs a computer player; else a human player
   * @param view the view to use to communicate to the world
   * @param playerMsg the player to ask for 
   * @return
   */
  public static Player makePlayer(Connect4View view, String playerMsg) {
    String playerName = view.getAnswer("Enter the name of the " + playerMsg + 
    		" player." + "\n(Include 'Computer' in the name of a computer player) ");
    if(playerName.contains("Computer")) {
    	int depth = view.getIntAnswer("How far should I look ahead? ");
      return new ComputerPlayer(playerName, depth);
    }
    else
      return new HumanPlayer(playerName);
  }
}