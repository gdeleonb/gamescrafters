import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
    public static HashMap<Integer, PosValue> solved_pos;

    public static PosValue solve(int pos) {
        PosValue pos_val = TenToZero.primitive(pos);
        if (!pos_val.equals("undecided")) return pos_val;

        ArrayList<Integer> moves = TenToZero.generateMoves(pos);
        int numWinningChildren = 0;
        PosValue outcome;
        int newPos;
        int minRemoteness = Integer.MAX_VALUE; // Will change unless something went really wrong
        for (int move : moves) {
            newPos = TenToZero.doMove(pos, move);
            outcome = (solved_pos.containsKey(newPos)) ? solved_pos.get(newPos) : solve(newPos);

            if (outcome.equals("losing")) {
                return new PosValue("winning", outcome.getRemoteness() + 1, move);
            } else if (outcome.equals("winning")) {
                numWinningChildren++;
            }
            minRemoteness = Math.min(minRemoteness, outcome.getRemoteness());
        }

        String res = (numWinningChildren == moves.size()) ? "losing" : "tie";
        return new PosValue(res, minRemoteness + 1);
    }

    public static void main(String[] args) {
        solved_pos = new HashMap<>(TenToZero.POSITIONS, 1);
        for (int pos = 0; pos <= TenToZero.POSITIONS; pos++) {
            PosValue pVal = solve(pos);
            solved_pos.put(pos, pVal);

            String output;
            if (pVal.equals("winning")) {
                output = String.format("Position %d is a %s position with a remoteness of %d by playing '%d'.\n", pos, pVal.getValue(), pVal.getRemoteness(), pVal.getWinningMove());
            } else {
                output = String.format("Position %d is a %s position with a remoteness of %d.\n", pos, pVal.getValue(), pVal.getRemoteness());
            }

            System.out.print(output);
        }
    }

}
