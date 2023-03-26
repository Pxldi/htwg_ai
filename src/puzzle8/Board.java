package puzzle8;

import java.util.*;

/**
 * Klasse Board für 8-Puzzle-Problem
 * @author Pxld1
 */
public class Board {

	/**
	 * Problemgröße
	 */
	public static final int N = 8;

	/**
	 * Board als Feld. 
	 * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 * 0 bedeutet leeres Feld.
	 */
	protected int[] board = new int[N+1];

	/**
	 * Generiert ein zufälliges Board.
	 */
	public Board() {
		this.board = new int[]{0,1,2,3,4,5,6,7,8};
		do {
			shuffle(this.board);
		} while (!parity());
	}

	private void shuffle(int[] array) {
		Random random = new Random();
		for (int i = 0; i < array.length; i++) {
			int randomIdx = random.nextInt(array.length);
			int temp = array[randomIdx];
			array[randomIdx] = array[i];
			array[i] = temp;
		}
	}
	
	/**
	 * Generiert ein Board und initialisiert es mit board.
	 * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 */
	public Board(int[] board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}
	
	/**
	 * Paritätsprüfung.
	 * @return Parität.
	 */
	public boolean parity() {
		int pairs_count = 0;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] == 0)
				continue;
			for (int j = 0; j < i; j++) {
				if (this.board[j] > this.board[i])
					pairs_count++;
			}
		}
		return pairs_count % 2 == 0;
	}
	
	/**
	 * Heuristik h1. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h1() {
		int cost = 0;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] == 0)
				continue;
			if (this.board[i] != i)
				cost++;
		}
		return cost;
	}
	
	/**
	 * Heuristik h2. (siehe Aufgabenstellung)
	 * @return Heuristikwert.
	 */
	public int h2() {
		int cost = 0;
		int width = 3;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] == 0)
				continue;
			int aX = i % width;
			int aY = i / width;
			int bX = this.board[i] % width;
			int bY = this.board[i] / width;
			cost += Math.abs(aX - bX) + Math.abs(aY - bY);
		}
		return cost;
	}
	
	/**
	 * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
	 * @return Folge-Boards.
	 */
	public List<Board> possibleActions() {
		List<Board> boardList = new LinkedList<>();
		int blank = -1;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] == 0) {
				blank = i;
			}
		}

		int width = (N + 1) / 3;
		int blankY = blank / width;
		int blankX = blank % width;

		if (blankY != 0) {
			boardList.add(swap(blank - 3, blank));
		}
		if (blankY != 2) {
			boardList.add(swap(blank + 3, blank));
		}
		if (blankX != 0) {
			boardList.add(swap(blank - 1, blank));
		}
		if (blankX != 2) {
			boardList.add(swap(blank + 1, blank));
		}
		return boardList;
	}
	public Board swap(int newPos, int blankPos) {
		int[] temp = this.board.clone();
		temp[blankPos] = temp[newPos];
		temp[newPos] = 0;
		return new Board(temp);
	}
	
	/**
	 * Prüft, ob das Board ein Zielzustand ist.
	 * @return true, falls Board Zielzustand (d.h. 0,1,2,3,4,5,6,7,8)
	 */
	public boolean isSolved() {
		return Arrays.equals(this.board, new int[]{0,1,2,3,4,5,6,7,8});
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});		// abc aus Aufgabenblatt
		Board goal = new Board(new int[]{0,1,2,3,4,5,6,7,8});

		System.out.println(b);
		System.out.println(b.parity());
		System.out.println(b.h1());
		System.out.println(b.h2());
		
		for (Board child : b.possibleActions())
			System.out.println(child);
		
		System.out.println(goal.isSolved());
	}
}