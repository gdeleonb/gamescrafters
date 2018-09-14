import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class OddEven implements Game {
    final int NUM_PLAYERS = 2;
    private int maxPos;
    private int highestMove;
    private HashMap<Integer, Position> intToPos;
    private HashMap<Position, Integer> posToInt;

    // Constructors
    public OddEven() {
        this.maxPos = 30;
        this.highestMove = 3;
        this.intToPos = new HashMap<>(this.maxPos, 1);
        this.posToInt = new HashMap<>(this.maxPos, 1);

        for (int i = this.maxPos; i >= 0; i-=2) {
            int sticksLeft = i/2;

            if (i == this.maxPos) {
                Position evenPos = new Position(sticksLeft, "even", "even");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);
            } else if (sticksLeft % 2 == 0 ) {
                Position evenPos = new Position(sticksLeft, "even", "odd");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);

                Position oddPos = new Position(sticksLeft, "odd", "even");
                intToPos.put(i+1, oddPos);
                posToInt.put(oddPos, i+1);
            } else {
                Position evenPos = new Position(sticksLeft, "even", "even");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);

                Position oddPos = new Position(sticksLeft, "odd", "odd");
                intToPos.put(i+1, oddPos);
                posToInt.put(oddPos, i+1);
            }
        }
    }

    public OddEven(int maxPos) {
        if (maxPos % 2 == 0) {
            System.out.println("maxPos must be odd.");
            System.exit(1);
        }
        this.maxPos = maxPos * 2;
        this.highestMove = 3;
        this.intToPos = new HashMap<>(this.maxPos, 1);
        this.posToInt = new HashMap<>(this.maxPos, 1);

        for (int i = this.maxPos; i >= 0; i-=2) {
            int sticksLeft = i/2;

            if (i == this.maxPos) {
                Position evenPos = new Position(sticksLeft, "even", "even");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);
            } else if (sticksLeft % 2 == 0 ) {
                Position evenPos = new Position(sticksLeft, "even", "odd");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);

                Position oddPos = new Position(sticksLeft, "odd", "even");
                intToPos.put(i+1, oddPos);
                posToInt.put(oddPos, i+1);
            } else {
                Position evenPos = new Position(sticksLeft, "even", "even");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);

                Position oddPos = new Position(sticksLeft, "odd", "odd");
                intToPos.put(i+1, oddPos);
                posToInt.put(oddPos, i+1);
            }
        }
    }

    public OddEven(int maxPos, int highestMove) {
        if (maxPos % 2 == 0) {
            System.out.println("maxPos must be odd.");
            System.exit(1);
        }
        this.maxPos = maxPos * 2;
        this.highestMove = highestMove;
        this.intToPos = new HashMap<>(this.maxPos, 1);
        this.posToInt = new HashMap<>(this.maxPos, 1);

        for (int i = this.maxPos; i >= 0; i-=2) {
            int sticksLeft = i/2;

            if (i == this.maxPos) {
                Position evenPos = new Position(sticksLeft, "even", "even");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);
            } else if (sticksLeft % 2 == 0 ) {
                Position evenPos = new Position(sticksLeft, "even", "odd");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);

                Position oddPos = new Position(sticksLeft, "odd", "even");
                intToPos.put(i+1, oddPos);
                posToInt.put(oddPos, i+1);
            } else {
                Position evenPos = new Position(sticksLeft, "even", "even");
                intToPos.put(i, evenPos);
                posToInt.put(evenPos, i);

                Position oddPos = new Position(sticksLeft, "odd", "odd");
                intToPos.put(i+1, oddPos);
                posToInt.put(oddPos, i+1);
            }
        }
    }

    // Getters
    public int getMaxPos() { return this.maxPos; }
    public int getHighestMove() { return this.highestMove; }

    // Helper psition class
    private class Position {
        private int sticksLeft;
        private String myParity;
        private String theirParity;

        public Position(int sticks, String mine, String theirs) {
            this.sticksLeft = sticks;
            this.myParity = mine;
            this.theirParity = theirs;
        }

        public int getSticks() { return this.sticksLeft; }
        public String getMine() { return this.myParity; }
        public String getTheirs() { return this.theirParity; }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Position)) {
                return false;
            }
            Position pos = (Position) o;
            return sticksLeft == pos.getSticks() && myParity == pos.getMine() && theirParity == pos.getTheirs();
        }

        public int hashCode() {
            return Objects.hash(sticksLeft, myParity, theirParity);
        }
    }

    // Solver Functions
    public PosValue primitive(int p) {
        Position pos = this.intToPos.get(p);
        String res;
        if (pos.getSticks() == 0) {
            res = ((pos.getMine().equals("even")) ? "winning" : "losing");
        } else {
            res = "undecided";
        }
        return new PosValue(res, 0);
    }

    public ArrayList<Integer> generateMoves(int p) {
        Position pos = this.intToPos.get(p);
        ArrayList<Integer> moves = new ArrayList<>(highestMove);
        for (int i = 1; i <= this.highestMove; i++) {
            if (pos.getSticks() >= i) moves.add(i);
        }
        moves.trimToSize();
        return moves;
    }

    public int doMove(int p, int move) {
        Position oldPos = this.intToPos.get(p);

        int newPosSticks = oldPos.getSticks() - move;
        String newTheirs;
        if (move % 2 == 0) {
            newTheirs = oldPos.getMine();
        } else {
            newTheirs = (oldPos.getMine() == "even") ? "odd" : "even";
        }
        Position newPos = new Position(newPosSticks, oldPos.getTheirs(), newTheirs);
        return this.posToInt.get(newPos);
    }

    public String posToString(int p) {
        Position pos = this.intToPos.get(p);
        return String.format("%d sticks left with an %s parity and %s opponent parity", pos.getSticks(), pos.getMine(), pos.getTheirs());
    }

    public String instructions() {
        return String.format("OddEven is a game where you and your opponent take turns\ntaking between 1 and 3 sticks from a pile of %d. When the\nsticks run out, the player with an even number of sticks\nin their hand wins!\n", this.maxPos);
    }

}
