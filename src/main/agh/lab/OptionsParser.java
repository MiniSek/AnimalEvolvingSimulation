package agh.lab;

public class OptionsParser {
    public MoveDirection[] parse(String[] rawMoves) throws IllegalArgumentException{
        MoveDirection[] convertedMoves = new MoveDirection[rawMoves.length];
        for(int i = 0; i < rawMoves.length; i++) {
            if(rawMoves[i].equals("f") || rawMoves[i].equals("forward"))
                convertedMoves[i] = MoveDirection.FORWARD;
            else if(rawMoves[i].equals("b") || rawMoves[i].equals("backward"))
                convertedMoves[i] = MoveDirection.BACKWARD;
            else if(rawMoves[i].equals("r") || rawMoves[i].equals("right"))
                convertedMoves[i] = MoveDirection.RIGHT;
            else if(rawMoves[i].equals("l") || rawMoves[i].equals("left"))
                convertedMoves[i] = MoveDirection.LEFT;
            else
                throw new IllegalArgumentException("'" + rawMoves[i] + "' is not legal move specification");
        }
        return convertedMoves;
    }
}
