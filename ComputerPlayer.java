/**
 * Implements a computer player that chooses moves using
 *   game tree search with alpha-beta pruning
 * @author moon
 */
public class ComputerPlayer extends Player {
  private int depth;    // Look-ahead depth
  public final static int ROWS = 6;            // Board height
  public final static int COLS = 7;            // Board width
  
  
  /**
   * Constructs a computer player that uses alpha-beta pruning
   * @param name
   * @param maxDepth
   */
	public ComputerPlayer(String name, int maxDepth) {
		super(name);
		depth = maxDepth;
	}

	@Override
	public int getMove(Connect4State state, Connect4View view) {
		// Returns the computer play's choice using alpha-beta  search

		int move = pickMove(state, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE,view).move;
		view.reportMove(move, state.getPlayerToMove().getName());

		return move;
	}

	/**
	 * Uses game tree search with alpha-beta pruning to pick player's move
	 * low and high define the current range for the best move.
	 * The current player has another move choice which will get him at least low,
	 * and his opponent has another choice that will hold his losses to high.
	 * 
	 * @param state current state of the game
	 * @param depth number of moves to look ahead in game tree search
	 * @param low a value that the player can achieve by some other move
	 * @param high a value that the opponent can force by a different line of play
	 * @return the move chosen
	 */
	private Move pickMove (Connect4State state, int depth, int low, int high,Connect4View view) {
		Move currentMove;      // Hold current move and its value
		Move bestMove;         // Hold best move found and its value
		char[][] board = state.getBoard();
		int playerToMove = state.getPlayerNum();
		// A dummy move that will be replaced when a real move is evaluated, 
		// so the column number is irrelevant.
		bestMove = new Move(Integer.MIN_VALUE, 0);   
    //System.out.println("depth is "+depth);
		// Run through possible moves 
		for (int i=1;i<=COLS &&bestMove.value < high&&!state.isFull();i++){  
			if (state.isValidMove(i))               // See if legal move
			{
				// Make a scratch copy of state
				Connect4Game copy = new Connect4Game(playerToMove, state.getPlayers(), board);   
				copy.makeMove(i);             // Make the move
        //Below is for testing purpose and the extra view is used for display.
				//view.display(copy);
				// Find the value of this board by evaluating if game over or looking ahead if not

				if (copy.gameIsOver())
					// Evaluate the true score, and multiply it by a huge constant so that the 
					// program will choose a sure win over a potentially larger speculative win 
					// and a possible loss over a sure loss.  
					currentMove = new Move(2000, i);
				else if (depth > 0)               // reduce search depth 
				{
					currentMove = pickMove(copy, depth - 1, -high, -low,view);
					//System.out.println("Current Value is "+currentMove.value+" for "+i);
					currentMove.value = -currentMove.value;   // Good for opponent is bad for me
					//System.out.println("Current Value is "+currentMove.value+" for "+i);
					currentMove.move = i;                   // Remember move made
				}
				else  {  // Depth exhausted, so estimate who is winning by relative scores
					currentMove = new Move(copy.score(playerToMove), i);
					//testing for the search of depth2
					//System.out.println(copy.score(playerToMove) +"  for  "+  i);
					//System.out.println("for  "+copy.getPlayers()[playerToMove].getName());
				}
				if (currentMove.value > bestMove.value)  {  // Found a new best move?
					bestMove = currentMove;
					low = Math.max(low, bestMove.value);   // Update the low value, also
				}
			}
		}

		//System.out.println("Best move is"+bestMove.move);
		//System.out.println("High is "+high+"  low is  "+low);
	 return bestMove;
	}
	
}