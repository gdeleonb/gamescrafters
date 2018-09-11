import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
    public static HashMap<Integer, String> solved_pos;

    public static String solve(int pos) {
        String pos_val = TenToZero.primitive(pos);
        if (!pos_val.equals("undecided")) return pos_val;

        ArrayList<Integer> moves = TenToZero.generateMoves(pos);
        int numWinningChildren = 0;
        String outcome;
        int new_pos;
        for (int move : moves) {
            new_pos = TenToZero.doMove(pos, move);
            outcome = (solved_pos.containsKey(new_pos)) ? solved_pos.get(new_pos) : solve(new_pos);
            if (outcome.equals("losing")) return "winning";
            if (outcome.equals("winning")) numWinningChildren++;
        }

        return (numWinningChildren == moves.size()) ? "losing" : "tie";
    }

    public static void main(String[] args) {
        solved_pos = new HashMap<>(TenToZero.POSITIONS, 1);
        for (int pos = 0; pos <= TenToZero.POSITIONS; pos++) {
            solved_pos.put(pos, solve(pos));
            System.out.printf("Position %d is a %s position.\n", pos, solved_pos.get(pos));
        }
    }
}
