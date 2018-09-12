import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
    private HashMap<Integer, PosValue> solvedPos;
    private Game game;

    public Solver(Game game) {
        this.game = game;
        this.solvedPos = new HashMap<>(this.game.getMaxPos(), 1);
    }

    public PosValue solve(int pos) {
        PosValue pos_val = game.primitive(pos);
        if (!pos_val.equals("undecided")) return pos_val;

        ArrayList<Integer> moves = game.generateMoves(pos);
        int numWinningChildren = 0;
        int minRemoteness = Integer.MAX_VALUE;
        for (int move : moves) {
            int newPos = game.doMove(pos, move);
            PosValue outcome = (this.solvedPos.containsKey(newPos)) ? this.solvedPos.get(newPos) : this.solve(newPos);

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
        Game game;
        if (args.length != 1) {
            System.out.println("Must pass in a game class name.");
            System.exit(-1);
        }

        if (args[0].equals("OddEven")) {
            game = new OddEven();
        } else if (args[0].equals("TenToZero")) {
            game = new TenToZero();
        } else {
            game = new TenToZero();
        }

        Solver solver = new Solver(game);
        for (int pos = 0; pos <= game.getMaxPos(); pos++) {
            PosValue pVal = solver.solve(pos);
            solver.solvedPos.put(pos, pVal);

            String output;
            if (pVal.equals("winning") && pVal.getRemoteness() != 0) {
                output = String.format("%s is a %s position with a remoteness of %d by playing '%d'.\n", game.posToString(pos), pVal.getValue(), pVal.getRemoteness(), pVal.getWinningMove());
            } else {
                output = String.format("%s is a %s position with a remoteness of %d.\n", game.posToString(pos), pVal.getValue(), pVal.getRemoteness());
            }

            System.out.print(output);
        }
    }

}
