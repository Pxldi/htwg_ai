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
		
		openList.add(startBoard, startBoard.h2());
		cost.put(startBoard, 0);
		IndexMinPQ<Board, Integer> closedList = new IndexMinPQ<>();

		while (!openList.isEmpty()) {
			Board curBoard = openList.removeMin();
			if (curBoard.isSolved())
				return solutionPath(curBoard);
			closedList.add(curBoard, cost.get(curBoard));

			for (Board child : curBoard.possibleActions()) {
				if (openList.get(child) == null && closedList.get(child) == null) {
					pred.put(child, curBoard);
					cost.put(child, closedList.get(curBoard) + 1);
					openList.add(child, cost.get(curBoard) + child.h2());
				}
				else if (openList.get(child) != null) {
					if (openList.get(child) > cost.get(curBoard) + child.h2()) {
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
		path.addFirst(initialBoard);
		return path;
	}

}
