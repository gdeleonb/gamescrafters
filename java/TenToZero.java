import java.util.ArrayList;

public class TenToZero {
    static int POSITIONS = 10;
    static int MAX_MOVES = 2;

    public static PosValue primitive(int pos) {
        return new PosValue(((pos == 0) ? "losing" : "undecided"), 0);
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
