import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class OddEven implements Game {
    private int maxPos;
    private int highestMove;
    private HashMap<Integer, Position> intToPos;
    private HashMap<Position, Integer> posToInt;

    // Constructors
    public OddEven() {
        this.maxPos = 31;
        this.highestMove = 3;
        this.intToPos = new HashMap<>(this.maxPos, 1);
        this.posToInt = new HashMap<>(this.maxPos, 1);

        for (int i = maxPos; i > 0; i-=2) {
            int sticksLeft = i/2;

            Position evenPos = new Position(sticksLeft, "even");
            intToPos.put(i, evenPos);
            posToInt.put(evenPos, i);

            Position oddPos = new Position(sticksLeft, "odd");
            intToPos.put(i-1, oddPos);
            posToInt.put(oddPos, i-1);
        }
    }

    public OddEven(int maxPos) {
        this.maxPos = maxPos;
        this.highestMove = 3;
        this.intToPos = new HashMap<>(this.maxPos, 1);
        this.posToInt = new HashMap<>(this.maxPos, 1);

        for (int i = maxPos; i > 0; i-=2) {
            int sticksLeft = i/2;

            Position evenPos = new Position(sticksLeft, "even");
            intToPos.put(i, evenPos);
            posToInt.put(evenPos, i);

            Position oddPos = new Position(sticksLeft, "odd");
            intToPos.put(i-1, oddPos);
            posToInt.put(oddPos, i-1);
        }
    }

    public OddEven(int maxPos, int highestMove) {
        this.maxPos = maxPos;
        this.highestMove = highestMove;
        this.intToPos = new HashMap<>(this.maxPos, 1);
        this.posToInt = new HashMap<>(this.maxPos, 1);

        for (int i = maxPos; i > 0; i-=2) {
            int sticksLeft = i/2;

            Position evenPos = new Position(sticksLeft, "even");
            intToPos.put(i, evenPos);
            posToInt.put(evenPos, i);

            Position oddPos = new Position(sticksLeft, "odd");
            intToPos.put(i-1, oddPos);
            posToInt.put(oddPos, i-1);
        }
    }

    // Getters
    public int getMaxPos() { return this.maxPos; }
    public int getHighestMove() { return this.highestMove; }

    // Helper psition class
    private class Position {
        private int sticksLeft;
        private String parity;

        public Position(int sticks, String mine) {
            this.sticksLeft = sticks;
            this.parity = mine;
        }

        public int getSticks() { return sticksLeft; }
        public String getParity() { return parity; }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Position)) {
                return false;
            }
            Position pos = (Position) o;
            return sticksLeft == pos.getSticks() && parity == pos.getParity();
        }

        public int hashCode() {
            return Objects.hash(sticksLeft, parity);
        }
    }

    // Solver Functions
    public PosValue primitive(int p) {
        Position pos = this.intToPos.get(p);
        String res;
        if (pos.getSticks() == 0) {
            res = ((pos.getParity().equals("even")) ? "winning" : "losing");
        } else {
            res = "undecided";
        }
        return new PosValue(res, 0);
    }

    public ArrayList<Integer> generateMoves(int p) {
        Position pos = this.intToPos.get(p);
        ArrayList<Integer> moves = new ArrayList<>(highestMove);
        moves.add(1);
        if (pos.getSticks() >= 2) moves.add(2);
        if (pos.getSticks() >= 3) moves.add(3);
        return moves;
    }

    public int doMove(int p, int move) {
        Position oldPos = this.intToPos.get(p);

        int newPosSticks = oldPos.getSticks() - move;
        String newPosParity;
        if (move % 2 == 0) {
            newPosParity = oldPos.getParity();
        } else {
            newPosParity = (oldPos.getParity() == "even") ? "odd" : "even";
        }
        Position newPos = new Position(newPosSticks, newPosParity);
        return this.posToInt.get(newPos);
    }

    public String posToString(int p) {
        Position pos = this.intToPos.get(p);
        return String.format("%d sticks left with an %s parity", pos.getSticks(), pos.getParity());
    }

}
