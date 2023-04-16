package kalah;

/**
 * Hauptprogramm für KalahMuster.
 *
 * @author oliverbittel
 * @since 29.3.2021
 */
public class Kalah {

    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        //testExample();
        //testHHGame();

        testMinMaxAlphaBetaWithGivenBoard();

        //testMinMaxWithGivenBoard();
        //testAlphaBetaWithGivenBoard();

        //testHumanMinMax();
        //testHumanAlphaBeta();
    }

    /**
     * Beispiel von https://de.wikipedia.org/wiki/Kalaha
     */
    public static void testExample() {
        KalahBoard kalahBd = new KalahBoard(new int[]{5, 3, 2, 1, 2, 0, 0, 4, 3, 0, 1, 2, 2, 0}, 'B');
        kalahBd.print();

        System.out.println("B spielt Mulde 11");
        kalahBd.move(11);
        kalahBd.print();

        System.out.println("B darf nochmals ziehen und spielt Mulde 7");
        kalahBd.move(7);
        kalahBd.print();
    }

    /**
     * Mensch gegen Mensch
     */
    public static void testHHGame() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action = kalahBd.readAction();
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    private static void testMinMaxAlphaBetaWithGivenBoard() {
        KalahBoard kalahBd = new KalahBoard(new int[]{2, 0, 4, 3, 2, 0, 0, 1, 0, 1, 3, 2, 1, 0}, 'A');
        // A ist am Zug und kann aufgrund von Bonuszügen 8-aml hintereinander ziehen!
        // A muss deutlich gewinnen!
        kalahBd.print();

        KalahBoard kalahBdMinMax = new KalahBoard(kalahBd);
        KalahBoard kalahBdAlphaBeta = new KalahBoard(kalahBd);

        for (int i = 0; i < 8; i++) {
            if (kalahBdMinMax.getCurPlayer() == 'A') {
                kalahBdMinMax = MinMax.maxAction(kalahBdMinMax);
                System.out.println(ANSI_BLUE + "A (MinMax) spielt Mulde: " + kalahBdMinMax.getLastPlay());
                System.out.println(ANSI_BLUE + MinMax.getTurns() + " Züge in " + MinMax.getTime() + " ms");

                kalahBdAlphaBeta = AlphaBeta.alphaBetaPruning(kalahBdAlphaBeta);
                System.out.println("\n" + ANSI_BLUE + "A (AlphaBeta) spielt Mulde: " + kalahBdAlphaBeta.getLastPlay());
                System.out.println(ANSI_BLUE + AlphaBeta.getTurns() + " Züge in " + AlphaBeta.getTime() + " ms");
            }

            kalahBdAlphaBeta.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testMinMaxWithGivenBoard() {
        KalahBoard kalahBd = new KalahBoard(new int[]{2, 0, 4, 3, 2, 0, 0, 1, 0, 1, 3, 2, 1, 0}, 'A');
        // A ist am Zug und kann aufgrund von Bonuszügen 8-aml hintereinander ziehen!
        // A muss deutlich gewinnen!
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            if (kalahBd.getCurPlayer() == 'A') {
                kalahBd = MinMax.maxAction(kalahBd);
                System.out.println(ANSI_BLUE + "A (MinMax) spielt Mulde: " + kalahBd.getLastPlay());
                kalahBd.print();
            } else {
                int action = kalahBd.readAction();
                kalahBd.move(action);
                kalahBd.print();
            }
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testAlphaBetaWithGivenBoard() {
        KalahBoard kalahBd = new KalahBoard(new int[]{2, 0, 4, 3, 2, 0, 0, 1, 0, 1, 3, 2, 1, 0}, 'A');
        // A ist am Zug und kann aufgrund von Bonuszügen 8-aml hintereinander ziehen!
        // A muss deutlich gewinnen!
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            if (kalahBd.getCurPlayer() == 'A') {
                kalahBd = AlphaBeta.alphaBetaPruning(kalahBd);
                System.out.println(ANSI_BLUE + "A (AlphaBetaPruning) spielt Mulde: " + kalahBd.getLastPlay());
                kalahBd.print();
            } else {
                int action = kalahBd.readAction();
                kalahBd.move(action);
                kalahBd.print();
            }
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testHumanMinMax() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            if (kalahBd.getCurPlayer() == 'A') {
                int action = kalahBd.readAction();
                kalahBd.move(action);
                kalahBd.print();
            } else {
                kalahBd = MinMax.maxAction(kalahBd);
                System.out.println(ANSI_BLUE + "B (MinMax) spielt Mulde: " + kalahBd.getLastPlay());
                kalahBd.print();
            }
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testHumanAlphaBeta() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            if (kalahBd.getCurPlayer() == 'A') {
                int action = kalahBd.readAction();
                kalahBd.move(action);
                kalahBd.print();
            } else {
                kalahBd = AlphaBeta.alphaBetaPruning(kalahBd);
                System.out.println(ANSI_BLUE + "B (AlphaBetaPruning) spielt Mulde: " + kalahBd.getLastPlay());
                kalahBd.print();
            }
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }
}
