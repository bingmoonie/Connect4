/**
 * A text implementation of the "view" part of a model-view-controller 
 *   version of a Connect4 program.  (See Kalah.java for history.)
 * See Connect4View interface for comments on the required methods.
 * 
 * @author moon
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class Connect4ViewText implements Connect4View {
	public final static int ROWS = 6;            // Board height
  public final static int COLS = 7;            // Board width
	public final static char EMPTY = '.';        // Indicate empty place
  public final static char CHECKER0 = 'X';     // Indicate the first player's checker
  public final static char CHECKER1 = 'O';     // Indicate second player's checker
  public final static char [] CHECKERS = {CHECKER0, CHECKER1};
  
	
	private Scanner input;   // Hold  the input stream
	
	public Connect4ViewText (){
		input = new Scanner(System.in);
	}

	@Override
	public void display (Connect4State state) 
  // Postcondition:  Displays the current board
  {
		char[][] board = state.getBoard();
		Player [] players = state.getPlayers();
		
    System.out.println(CHECKERS[0]+"  for  "+players[0].getName());
    System.out.println(CHECKERS[1]+"  for  "+players[1].getName());
    for(int i=ROWS-1;i>=0;i--){
    	for(int j=0;j<COLS;j++){
    		System.out.print(board[i][j]);
    	}
    	System.out.print("\n");
    }
  }
	
	@Override
	public int getUserMove(Connect4State state) {
		int col;  // The column under consideration
		
		System.out.println();
    
    col = getIntAnswer("Column to drop, " + state.getPlayerToMove().getName() + "? ");

    while ((col < 1) || (col> COLS) || 
    		!state.isValidMove(col)) 
    {
      System.out.println("Illegal move.  Try again.");
      col = getIntAnswer("Column to drop? ");
    }
    
    return col;

	}
	
	@Override
	public void reportMove (int bestMove, String name) {
		System.out.println("\n" + name + " drops in " + bestMove);
	}

  
	@Override
  public int getIntAnswer(String question) {
  	int answer = 0;
  	boolean valid = false;
  	
  	// Ask for a number
    System.out.print(question + " ");   
  	while(!valid) {
  		try {
        answer = input.nextInt();;
        valid = true;   // If got to here we have a valid integer
  		}
  		catch(InputMismatchException ex) {
  			reportToUser("That was not a valid integer");
  			valid = false;
  			input.nextLine();  // Throw away the rest of the line
  	    System.out.print(question + " ");   
  		}
  	}
  	input.nextLine();  // Throw away the rest of the line
  	
    return answer;
  }
  
  @Override
  public void reportToUser(String message) {
  // Reports something to the user
  	System.out.println(message);
  }

  @Override
  public String getAnswer(String message) {
  	System.out.print(message);
  	return input.nextLine();
  }
}