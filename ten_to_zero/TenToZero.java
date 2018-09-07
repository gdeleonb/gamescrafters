import java.util.ArrayList;
import java.util.HashMap;

public class TenToZero {
    public static HashMap<Integer, String> solved_pos;
    static int positions = 10;

    public static void main(String[] args) {
        solved_pos = new HashMap<>(positions, 1);
        for (int pos = 0; pos <= positions; pos++) {
            solved_pos.put(pos, solve(pos));
            System.out.printf("Position %d is a %s position.\n", pos, solved_pos.get(pos));
        }
    }

    public static ArrayList<Integer> generateMoves(int pos) {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        if (pos >= 2) moves.add(2);
        return moves;
    }

    public static int doMove(int pos, int move) {
        return pos - move;
    }

    public static String primitive(int pos) {
        return (pos == 0) ? "losing" : "undecided";
    }

    public static String solve(int pos) {
        String pos_val = primitive(pos);
        if (!pos_val.equals("undecided")) return pos_val;

        ArrayList<Integer> moves = generateMoves(pos);
        int numWinningChildren = 0;
        for (int move : moves) {
            int new_pos = doMove(pos, move);
            String outcome;
            outcome = (solved_pos.containsKey(new_pos)) ? solved_pos.get(new_pos) : solve(new_pos);
            if (outcome.equals("losing")) return "winning";
            if (outcome.equals("winning")) numWinningChildren++;
        }

        return (numWinningChildren == moves.size()) ? "losing" : "tie";
    }
}
