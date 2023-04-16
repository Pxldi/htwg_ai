package kalah;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlphaBeta {

    private static char currPlayer;
    private static int turns;
    private static long startTime;
    private static long endTime;

    public static KalahBoard alphaBetaPruning(KalahBoard kalahBd) {
        if (kalahBd.isFinished()) {
            return kalahBd;
        }

        currPlayer = kalahBd.getCurPlayer();
        turns = 0;
        startTime = System.nanoTime();
        int v = Integer.MIN_VALUE;
        KalahBoard action = kalahBd;

        for (KalahBoard currBoard : sortTurns(kalahBd.possibleActions())) {
            int v1;
            if (currBoard.isBonus()) {
                v1 = maxValue(currBoard, 20, Integer.MIN_VALUE, Integer.MAX_VALUE);
            } else {
                v1 = minValue(currBoard, 20, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            turns++;
            if (v1 > v) {
                v = v1;
                action = currBoard;
            }
        }
        endTime = System.nanoTime();
        return action;
    }

    private static int minValue(KalahBoard kalahBd, int depth, int alpha, int beta) {
        if (kalahBd.isFinished() || depth <= 0) {
            return evaluate(kalahBd);
        }

        int v = Integer.MAX_VALUE;

        for (KalahBoard currBoard : sortTurns(kalahBd.possibleActions())) {
            if (currBoard.isBonus()) {
                v = Math.min(v, minValue(currBoard, depth, alpha, beta));
            } else {
                v = Math.min(v, maxValue(currBoard, --depth, alpha, beta));
            }
            turns++;
            if (v <= alpha)
                return v;
            beta = Math.min(beta, v);
        }

        return v;
    }

    private static int maxValue(KalahBoard kalahBd, int depth, int alpha, int beta) {
        if (kalahBd.isFinished() || depth <= 0) {
            return evaluate(kalahBd);
        }

        int v = Integer.MIN_VALUE;

        for (KalahBoard currBoard : sortTurns(kalahBd.possibleActions())) {
            if (currBoard.isBonus()) {
                v = Math.max(v, maxValue(currBoard, depth, alpha, beta));
            } else {
                v = Math.max(v, minValue(currBoard, --depth, alpha, beta));
            }
            turns++;
            if (v >= beta)
                return v;
            alpha = Math.max(alpha, v);
        }

        return v;
    }

    private static int evaluate(KalahBoard kalahBd) {
        if (currPlayer == 'A')
            return kalahBd.getAKalah() - kalahBd.getBKalah();
        else
            return kalahBd.getBKalah() - kalahBd.getAKalah();
    }

    private static List<KalahBoard> sortTurns(List<KalahBoard> possibleActions) {
        return possibleActions.stream()
                .sorted((a, b) -> evaluate(b) - evaluate(a))
                .collect(Collectors.toList());
    }

    public static int getTurns() {
        return turns;
    }

    public static long getTime() {
        return (endTime - startTime) / 1000000;
    }
}
