/**
 * A model class that implements Connect4State 
 * and holds information about the Connect4 game.
 * @author moon
 *
 */
public class Connect4Game implements Connect4State{
	private char [][] board;           // Hold the game board
  private int playerToMoveNum;    // 0 or 1 for first and second player
  private Player [] players;      // Array of the two players
  private Connect4View view;         // Holds the view, so can update display
                                  // as state changes (if desired)

	  
	  /**
	   * Constructs a game in the initial state
	   * @param playerNum the player whose move it is
	   * @param thePlayers the player objects
	   * @param aView the view in the model-view-controller model
	   */
	  public Connect4Game(int playerNum, Player [] thePlayers, Connect4View aView) {
	  	char[][] initBoard = new char[ROWS][COLS]; // Original pit contents
	    for(int i=0;i<ROWS;i++){
	    	for(int j=0;j<COLS;j++){
	    		initBoard[i][j]='.';
	    	}
	    }
	    initialize(playerNum, thePlayers, initBoard);
	    view = aView;
	  }
	  
	  /**
	   * Constructs a game given a board
	   * @param playerNum the player whose move it is
	   * @param thePlayers the player objects
	   * @param initBoard the board to copy into this state
	   */
	  public Connect4Game(int playerNum, Player [] thePlayers, char[][] initBoard)   {
	    initialize(playerNum, thePlayers, initBoard);
	  }

	  /**
	   * Initialization to call from both constructors
	   * @param playerNum the number of the player to move
	   * @param thePlayers the array of player objects
	   * @param initBoard the initial board to use
	   */
	  private void initialize(int playerNum, Player [] thePlayers, char [][] initBoard) {
	    board = new char[ROWS][COLS];
	    
	    for(int i=0;i<ROWS;i++){
	    	for(int j=0;j<COLS;j++){
	    		board[i][j]=initBoard[i][j];
	    	}
	    }
	    
	    playerToMoveNum = playerNum;
	    players = thePlayers;
	  }
	  
	  /**
	   * Gets a 2-D array representing the board.
	   * The first subscript is the row number and the second the column number.
	   * The bottom of the board is row 0 and the top is row ROWS-1.
	   * The left side of the board is column 0 and the right side is column COLS-1.
	   * @return the board
	   */
	  public char [][] getBoard(){
	    return board;
	  }
	  
	  /**
	   * Gets an array holding 2 Player objects
	   * @return the players
	   */
	  public Player [] getPlayers(){
	  	return players;
	  }
	  
	  /**
	   * Gets the number of the player whose move it is
	   * @return the number of the player whose move it is
	   */
	  public int getPlayerNum (){
	  	return playerToMoveNum;
	  }
	  
	  /**
	   * Gets the Player whose turn it is to move
	   * @return the Player whose turn it is to move
	   */
	  public Player getPlayerToMove(){
	  	return players[playerToMoveNum];
	  }
	  /**
	   * Gets the other player
	   * @return the other player
	   */
	  public Player getOtherPlayer(){
	  	return players[1-playerToMoveNum];
	  }

	  /**
	   * Is this move valid?
	   * @param col column where we want to move
	   * @return true if the move is valid
	   */
	  public boolean isValidMove(int col){
	  	return board[ROWS-1][col-1]=='.';
	  }
	  
	  /**
	   * Make a move, dropping a checker in the given column
	   * @param col the column to get the new checker
	   */
	  public void makeMove(int col){
	  	int i=ROWS-1;
	  	while(i>=0&&board[i][col-1]=='.')
	  		i--;
	  	board[i+1][col-1]=CHECKERS[playerToMoveNum];
	  	playerToMoveNum = 1 - playerToMoveNum;
	  }
	 
	  /**
	   * Is the board full?
	   * @return true if the board is full
	   */
	  public boolean isFull(){
	  	for(int i=1;i<=COLS;i++){
	  		if (isValidMove(i)){
	  			return false;
	  		}
	  	}
	  	return true;
	  }

	  /**
	   * Decides if the game is over
	   * @return true iff the game is over
	   */
	  public boolean gameIsOver(){
	  	for(char[] oneFour:possibleFour()){
	  		if(oneFour[0]==oneFour[1]&&oneFour[1]==oneFour[2]
	  				&&oneFour[2]==oneFour[3]&&oneFour[0]!='.'){
	  			//System.out.println("True");
	  			return true;
	  		}
	  	}
	  	return false;
	  }
	  

	  	
	  /**
	   * This method will return all possible four (in a row/column/diagonal)
	   * 
	   */
	  public char[][] possibleFour(){
	  	char[][] fours =new char[4*6+3*7+8*3][4];
	  	int j=0;
	  	//getting all the fours in a column
	  	for(int i=0;i<COLS;i++){
	  		for(int k=0;k<ROWS-3;k++){
	  			fours[j][0]=board[k][i];
	  			fours[j][1]=board[k+1][i];
	  			fours[j][2]=board[k+2][i];
	  			fours[j++][3]=board[k+3][i];
	  		}
	  	}
	  	//getting all the fours in a row
	  	for(int k=0;k<ROWS;k++){
	  		for(int i=0;i<COLS-3;i++){
	  			fours[j][0]=board[k][i];
	  			fours[j][1]=board[k][i+1];
	  			fours[j][2]=board[k][i+2];
	  			fours[j++][3]=board[k][i+3];
	  		}
	  	}
	  	//getting all the diagonals
	  	for(int k=0;k<ROWS-3;k++){
	  		for(int i=0;i<COLS-3;i++){
	  			fours[j][0]=board[k][i];
	  			fours[j][1]=board[k+1][i+1];
	  			fours[j][2]=board[k+2][i+2];
	  			fours[j++][3]=board[k+3][i+3];
	  		}
	  	}
	  	for(int k=0;k<ROWS-3;k++){
	  		for(int i=COLS-1;i>COLS-5;i--){
	  			fours[j][0]=board[k][i];
	  			fours[j][1]=board[k+1][i-1];
	  			fours[j][2]=board[k+2][i-2];
	  			fours[j++][3]=board[k+3][i-3];
	  		}
	  	}
	   return fours;	
	  }
	  /**
	   * The score analyzes the relative advantage of each player
	   * One in a un-blocked fours score 1
	   * Two in an un-blocked fours score 2
	   * Three in an un-blocked fours score 3
	   * @param player is the player number to be passed into
	   * @return the score of a particular player
	   */
	  public int score(int player)
	  {
	    int total1 = 0;  
	    int total2 = 0;

    	char mine=CHECKERS[player];
    	char others=CHECKERS[1-player];
    	//System.out.println("mine:"+mine+"  others:"+others);
	    for(char[] oneFour:possibleFour()){
	    	int countMine=0;
	    	int countOthers=0;
	    	for(int j=0;j<4;j++){
	    		if (oneFour[j]==mine)
	    			countMine++;
	    		if (oneFour[j]==others)
	    			countOthers++;
	    	}
	    	if(countOthers==0){
	    		if(countMine==3) //getting 3 is real good!
	    			total1+=17;
	    		if(countMine==2)
	    			total1+=3;
	    		if(countMine==1)
	    			total1+=1;
	    	}
	    	if(countMine==0){
	    		if(countOthers==3) //getting 3 is real good!
	    			total2+=20;
	    		if(countOthers==2)
	    			total2+=3;
	    		if(countOthers==1)
	    			total2+=1;
	    	}
	    }
	   //System.out.println("mine value is"+total1+"  other value is"+total2);
	    return total1-total2;
	  }
}