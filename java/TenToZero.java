import java.util.ArrayList;

public class TenToZero implements Game {
    private int maxPos;
    private int highestMove;

    // Constructors
    public TenToZero() {
        this.maxPos = 10;
        this.highestMove = 2;
    }

    public TenToZero(int maxPos) {
        this.maxPos = maxPos;
        this.highestMove = 2;
    }

    public TenToZero(int maxPos, int highestMove) {
        this.maxPos = maxPos;
        this.highestMove = highestMove;
    }

    // Getters
    public int getMaxPos() { return this.maxPos; }
    public int getHighestMove() { return this.highestMove; }

    // Solver Functions
    public PosValue primitive(int pos) {
        return new PosValue(((pos == 0) ? "losing" : "undecided"), 0);
    }

    public ArrayList<Integer> generateMoves(int pos) {
        ArrayList<Integer> moves = new ArrayList<>(this.highestMove);
        for (int i = 1; i <= this.highestMove; i++) {
            if (pos >= i) moves.add(i);
        }
        return moves;
    }

    public int doMove(int pos, int move) {
        return pos - move;
    }

    public String posToString(int p) {
        return String.format("%d", p);
    }
}
