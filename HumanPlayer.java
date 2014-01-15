/**
 * Holds information about and interacts with a human player
 * @author moon
 */

public class HumanPlayer extends Player {

	/**
	 * @param name name of the human player
	 */
	public HumanPlayer (String name) {
		super(name);
	}
	
	@Override
	public int getMove(Connect4State state, Connect4View view) {
	  // Get a move for the user 
	  return view.getUserMove(state);
	}

}