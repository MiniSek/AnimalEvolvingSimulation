package lab;

public class OptionsParser {
    public MoveDirection[] parse(String[] rawMoves) {
        int i=0, j=0;
        while(i<rawMoves.length) {
            if(rawMoves[i].equals("f") || rawMoves[i].equals("forward") || rawMoves[i].equals("b") ||
                    rawMoves[i].equals("backward") || rawMoves[i].equals("r") || rawMoves[i].equals("right") ||
                    rawMoves[i].equals("l") || rawMoves[i].equals("left")) {
               j++;
            }
            i++;
        }

        MoveDirection[] convertedMoves = new MoveDirection[j];
        i=0; j=0;

        while(i<rawMoves.length) {
            if(rawMoves[i].equals("f") || rawMoves[i].equals("forward")) {
                convertedMoves[j] = MoveDirection.FORWARD;
                j++;
            }
            else if(rawMoves[i].equals("b") || rawMoves[i].equals("backward")) {
                convertedMoves[j] = MoveDirection.BACKWARD;
                j++;
            }
            else if(rawMoves[i].equals("r") || rawMoves[i].equals("right")) {
                convertedMoves[j] = MoveDirection.RIGHT;
                j++;
            }
            else if(rawMoves[i].equals("l") || rawMoves[i].equals("left")) {
                convertedMoves[j] = MoveDirection.LEFT;
                j++;
            }
            i++;
        }
        return convertedMoves;
    }
}
