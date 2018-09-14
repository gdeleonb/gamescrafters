import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.*;

public class Player {
    private HashMap<Integer, PosValue> solvedPos;
    private Game game;
    private int curPos;
    private String[] playerNames;
    private boolean[] human;

    public Player(Game game) {
        this.game = game;
        this.curPos = game.getMaxPos();

        this.playerNames = new String[this.game.NUM_PLAYERS];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < this.game.NUM_PLAYERS; i++) {
            System.out.printf("What is player %d's name? ", i+1);
            this.playerNames[i] = scanner.nextLine();
        }
        System.out.println();

        this.human = new boolean[this.game.NUM_PLAYERS];
        for (int i = 0; i < this.game.NUM_PLAYERS; i++) {
            System.out.printf("Is %s human or a computer? ", this.playerNames[i]);
            String response = scanner.nextLine();
            if (response.toLowerCase().equals("human") || response.toLowerCase().equals("h")) {
                this.human[i] = true;
            } else if ((response.toLowerCase().equals("computer") || response.toLowerCase().equals("c"))) {
                this.human[i] = false;
            } else {
                i-=1;
            }
        }
        System.out.println();

        Solver solver = new Solver(this.game, false);
        for (int pos = 0; pos <= this.game.getMaxPos(); pos++) {
            solver.solve(pos);
        }
        this.solvedPos = solver.getSolvedPos();
    }

    public static void main(String[] args) {
        Game game;
        try {
            if (args.length < 1 || args.length > 2) {
                System.out.println("Must pass in a game class name.");
                System.exit(-1);
            }
            if (args.length == 1) {
                game = (Game) Class.forName(args[0]).newInstance();
            } else if (args.length == 2) {
                Class<?> c = Class.forName(args[0]);
                Constructor<?> con = c.getConstructor(int.class);
                game = (Game) con.newInstance(Integer.parseInt(args[1]));
            } else {
                game = new TenToZero(); // Never executed
            }
        } catch (NumberFormatException nfe) {
            game = new TenToZero();
            System.out.printf("The second argument must be an integer. Error: %s\n", nfe);
            System.exit(1);
        } catch (Exception e) {
            game = new TenToZero(); // Complains if I don't have it...
            System.out.println(e);
            System.exit(1);
        }
        Player player = new Player(game);

        System.out.println(player.game.instructions());

        int curTurn = 0;
        int move;
        Scanner scanner = new Scanner(System.in);
        while (player.solvedPos.get(player.curPos).getRemoteness() != 0) {
            System.out.printf("It is %s's turn.\nCurrent state: %s.\nWhat is your move? ", player.playerNames[curTurn], player.game.posToString(player.curPos));
            int maxMove = player.game.generateMoves(player.curPos).size();
            if (player.human[curTurn]) {
                try {
                    move = scanner.nextInt();
                    scanner.nextLine();
                    if (move > maxMove || move <= 0) {
                        throw new Error();
                    }
                    player.curPos = player.game.doMove(player.curPos, move);
                } catch (Error e) {
                    System.out.println("That is an illegal move, please try again.");
                }
            } else {
                PosValue pVal = player.solvedPos.get(player.curPos);
                if (pVal.winning()) {
                    move = pVal.getWinningMove();
                } else {
                    move = ThreadLocalRandom.current().nextInt(1, maxMove + 1);
                }
                System.out.printf("%d\n", move);
                player.curPos = player.game.doMove(player.curPos, move);
            }
            System.out.println();
            curTurn = (curTurn + 1) % player.game.NUM_PLAYERS;
        }
        PosValue finalPVal = player.solvedPos.get(player.curPos);
        String winner;
        if (finalPVal.winning()) {
            winner = player.playerNames[curTurn];
        } else {
            winner = player.playerNames[(curTurn + 1) % player.game.NUM_PLAYERS];
        }
        System.out.printf("Game over! %s wins!\n", winner);
    }

}
