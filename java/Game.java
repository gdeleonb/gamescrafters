import java.util.ArrayList;

public interface Game {
    public PosValue primitive(int pos);
    public ArrayList<Integer> generateMoves(int pos);
    public int doMove(int pos, int move);
    public String posToString(int p);

    public int getMaxPos();
    public int getHighestMove();
}
