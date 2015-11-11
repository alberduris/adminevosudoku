package packModelo;

import java.util.Comparator;

public class ComparadorSudokus implements Comparator<Sudoku> {

	@Override
	public int compare(Sudoku pO1, Sudoku pO2) {
		// TODO Auto-generated method stub
		return pO2.obtDificultad() - pO1.obtDificultad();
	}

}
