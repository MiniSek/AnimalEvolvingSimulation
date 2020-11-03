package agh.lab;

public class OptionsParser {
    public MoveDirection[] parse(String[] rawMoves) {
        int j=0;
        for(String rawMove : rawMoves) {
            if(rawMove.equals("f") || rawMove.equals("forward") || rawMove.equals("b") ||
                    rawMove.equals("backward") || rawMove.equals("r") || rawMove.equals("right") ||
                    rawMove.equals("l") || rawMove.equals("left")) {
               j++;
            }
        }

        MoveDirection[] convertedMoves = new MoveDirection[j];
        j=0;
        for(String rawMove : rawMoves) {
            if(rawMove.equals("f") || rawMove.equals("forward")) {
                convertedMoves[j] = MoveDirection.FORWARD;
                j++;
            }
            else if(rawMove.equals("b") || rawMove.equals("backward")) {
                convertedMoves[j] = MoveDirection.BACKWARD;
                j++;
            }
            else if(rawMove.equals("r") || rawMove.equals("right")) {
                convertedMoves[j] = MoveDirection.RIGHT;
                j++;
            }
            else if(rawMove.equals("l") || rawMove.equals("left")) {
                convertedMoves[j] = MoveDirection.LEFT;
                j++;
            }
        }
        return convertedMoves;
    }
}
