import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class OddEven {
    static int MAX_POSITION = 31;
    static int MAX_MOVES = 3;
    static HashMap<Integer, Position> INT_TO_POS = new HashMap<>(MAX_POSITION, 1);
    static HashMap<Position, Integer> POS_TO_INT = new HashMap<>(MAX_POSITION, 1);

    static {
        for (int i = MAX_POSITION; i > 0; i-=2) {
            int sticksLeft = i/2;

            Position evenPos = new Position(sticksLeft, "even");
            INT_TO_POS.put(i, evenPos);
            POS_TO_INT.put(evenPos, i);

            Position oddPos = new Position(sticksLeft, "odd");
            INT_TO_POS.put(i-1, oddPos);
            POS_TO_INT.put(oddPos, i-1);
        }
    }

    private static class Position {
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

    public static PosValue primitive(int p) {
        Position pos = INT_TO_POS.get(p);
        String res;
        if (pos.getSticks() == 0) {
            res = ((pos.getParity().equals("even")) ? "winning" : "losing");
        } else {
            res = "undecided";
        }
        return new PosValue(res, 0);
    }

    public static ArrayList<Integer> generateMoves(int p) {
        Position pos = INT_TO_POS.get(p);
        ArrayList<Integer> moves = new ArrayList<>(MAX_MOVES);
        moves.add(1);
        if (pos.getSticks() >= 2) moves.add(2);
        if (pos.getSticks() >= 3) moves.add(3);
        return moves;
    }

    public static int doMove(int p, int move) {
        Position oldPos = INT_TO_POS.get(p);

        int newPosSticks = oldPos.getSticks() - move;
        String newPosParity;
        if (move % 2 == 0) {
            newPosParity = oldPos.getParity();
        } else {
            newPosParity = (oldPos.getParity() == "even") ? "odd" : "even";
        }
        Position newPos = new Position(newPosSticks, newPosParity);
        return POS_TO_INT.get(newPos);
    }

    public static String posToString(int p) {
        Position pos = INT_TO_POS.get(p);
        return String.format("%d sticks left with an %s parity", pos.getSticks(), pos.getParity());
    }

}
