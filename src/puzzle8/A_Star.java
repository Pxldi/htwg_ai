package puzzle8;

import java.util.*;

/**
 *
 * @author Pxldi
 */
public class A_Star {
	// cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
	// pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25). 
	// In cost und pred sind genau alle Knoten der closedList und openList enthalten!
	// Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
	private static HashMap<Board,Integer> cost = new HashMap<>();
	private static HashMap<Board, Board> pred = new HashMap<>();

	private static Board initialBoard;
	
	// openList als Prioritätsliste.
	// Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
	private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();
	
	public static Deque<Board> aStar(Board startBoard) {
		initialBoard = startBoard;

		if (startBoard.isSolved())
			return new LinkedList<>();
		
		openList.add(startBoard, 0);
		List<Board> closedList = new LinkedList<>();

		while (!openList.isEmpty()) {
			Board curBoard = openList.removeMin();
			if (curBoard.isSolved())
				return solutionPath(curBoard);
			cost.put(curBoard, 0);
			closedList.add(curBoard);

			for (Board child : curBoard.possibleActions()) {
				if (openList.get(child) == null && !closedList.contains(child)) {
					pred.put(child, curBoard);
					cost.put(child, cost.get(curBoard) + 1);
					openList.add(child, cost.get(curBoard) + child.h2());
				}
				else if (openList.get(child) != null) {
					if (cost.get(curBoard) + 1 < cost.get(child)) {
						pred.put(child, curBoard);
						cost.put(child, cost.get(curBoard) + 1);
						openList.change(child, cost.get(curBoard) + child.h2());
					}
				}
			}
		}
		return null; // Keine Lösung
	}

	private static Deque<Board> solutionPath(Board finalBoard) {
		Deque<Board> path = new LinkedList<>();
		Board curBoard = finalBoard;
		while (curBoard != initialBoard) {
			path.addFirst(curBoard);
			curBoard = pred.get(curBoard);
		}
		return path;
	}

}
