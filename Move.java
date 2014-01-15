/**
 
 * 
 * @author moon

 * A simple class that allows a move and its value to be returned from a
 *   call to a method.
 * A case where public instance variables make sense.
 */

public class Move
{
  public int value;       // Game value of this move
  public int move;        // Number of pit to be emptied
  
  public Move(int value, int move)
  {
    this.value = value;
    this.move = move;
  }
}