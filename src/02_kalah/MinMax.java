package kalah;

public class MinMax {

    private static char curPlayer;
    private static int turns;
    private static long startTime;
    private static long endTime;

    public static KalahBoard maxAction(KalahBoard kalahBd) {
        if (kalahBd.isFinished()) {
            return kalahBd;
        }

        curPlayer = kalahBd.getCurPlayer();
        startTime = System.nanoTime();
        int v = Integer.MIN_VALUE;
        KalahBoard action = kalahBd;

        for (KalahBoard currBoard : kalahBd.possibleActions()) {
            if (currBoard.isBonus()) {
                int v1 = maxValue(currBoard, 20);
                turns++;
                if (v1 > v)
                    v = v1;
                action = currBoard;
            } else {
                int v1 = minValue(currBoard, 20);
                turns++;
                if (v1 > v) {
                    v = v1;
                    action = currBoard;
                }
            }
        }
        endTime = System.nanoTime();
        return action;
    }

    private static int minValue(KalahBoard kalahBd, int depth) {
        //System.out.println(depth);
        if (kalahBd.isFinished() || depth <= 0) {
            return evaluate(kalahBd);
        }

        int v = Integer.MAX_VALUE;

        for (KalahBoard currBoard : kalahBd.possibleActions()) {
            if (currBoard.isBonus()) {
                v = Math.min(v, minValue(currBoard, depth));
                turns++;
            } else {
                v = Math.min(v, maxValue(currBoard, --depth));
                turns++;
            }
        }
        return v;
    }

    private static int maxValue(KalahBoard kalahBd, int depth) {
        //System.out.println(depth);
        if (kalahBd.isFinished() || depth <= 0) {
            return evaluate(kalahBd);
        }

        int v = Integer.MIN_VALUE;

        for (KalahBoard currBoard : kalahBd.possibleActions()) {
            if (currBoard.isBonus()) {
                v = Math.max(v, maxValue(currBoard, depth));
                turns++;
            } else {
                v = Math.max(v, minValue(currBoard, --depth));
                turns++;
            }
        }
        return v;
    }

    private static int evaluate(KalahBoard kalahBd) {
        if (curPlayer == 'A')
            return kalahBd.getAKalah() - kalahBd.getBKalah();
        else
            return kalahBd.getBKalah() - kalahBd.getAKalah();
    }

    public static int getTurns() {
        return turns;
    }

    public static long getTime() {
        return (endTime - startTime) / 1000000;
    }
}
