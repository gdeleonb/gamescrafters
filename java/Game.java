import java.util.ArrayList;

public interface Game {
    final static int NUM_PLAYERS = 2;
    public PosValue primitive(int pos);
    public ArrayList<Integer> generateMoves(int pos);
    public int doMove(int pos, int move);

    public String posToString(int p);
    public String instructions();

    public int getInitialPos();
    public int getHighestMove();
}
