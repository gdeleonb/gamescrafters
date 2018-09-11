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
        if (!this.value.equals("winning")) return -1;
        return winningMove;
    }

    public Boolean equals(String str) {
        return this.value.equals(str);
    }
}
