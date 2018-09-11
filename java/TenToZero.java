import java.util.ArrayList;
import java.util.HashMap;

public class TenToZero {
    static int POSITIONS = 10;
    static int MAX_MOVES = 2;

    public static String primitive(int pos) {
        return (pos == 0) ? "losing" : "undecided";
    }

    public static ArrayList<Integer> generateMoves(int pos) {
        ArrayList<Integer> moves = new ArrayList<>(MAX_MOVES);
        moves.add(1);
        if (pos >= 2) moves.add(2);
        return moves;
    }

    public static int doMove(int pos, int move) {
        return pos - move;
    }
}
