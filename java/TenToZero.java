import java.util.ArrayList;

public class TenToZero implements Game {
    final int NUM_PLAYERS = 2;
    private int initPos;
    private int highestMove;

    // Constructors
    public TenToZero() {
        this.initPos = 10;
        this.highestMove = 2;
    }

    public TenToZero(int initPos) {
        this.initPos = initPos;
        this.highestMove = 2;
    }

    public TenToZero(int initPos, int highestMove) {
        this.initPos = initPos;
        this.highestMove = highestMove;
    }

    // Getters
    public int getInitialPos() { return this.initPos; }
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
        moves.trimToSize();
        return moves;
    }

    public int doMove(int pos, int move) {
        return pos - move;
    }

    public String posToString(int p) {
        return String.format("%d", p);
    }

    public String instructions() {
        return String.format("TenToZero is a game where you and your opponent take turns\nsubtracting 1 or 2 from the score (which starts at %d).\nThe player who gets it down to zero wins!\n", this.initPos);
    }

}
