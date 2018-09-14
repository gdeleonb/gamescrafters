import java.util.Objects;

public class PosValue {
    private String value; // Will be 'winning', 'losing', or 'undecided'
    private int remoteness; // Number of moves away from conclusion
    private int winningMove; // -1 for 'losing' and 'undecided'

    public PosValue(String value, int remoteness) {
      this.value = value;
      this.remoteness = remoteness;
    }

    public PosValue(String value, int remoteness, int move) {
      this.value = value;
      this.remoteness = remoteness;
      this.winningMove = move;
    }

    public String getValue() { return value; }
    public int getRemoteness() { return remoteness; }
    public int getWinningMove() {
        if (!value.equals("winning")) return -1;
        return winningMove;
    }

    public boolean winning() {
      return value.equals("winning");
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof PosValue) {
            PosValue pv = (PosValue) o;
            return ((value.equals(pv.getValue())) &&
             (remoteness == pv.getRemoteness()) &&
             (winningMove == pv.getWinningMove()));
        }
        if (o instanceof String) {
            String str = (String) o;
            return value.equals(str);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(value, remoteness, winningMove);
    }
}
