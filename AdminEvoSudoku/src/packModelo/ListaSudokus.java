package packModelo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import packAdminSudoku.ListaPuntuaciones;

public class ListaSudokus {	
	ArrayList<Sudoku> lista;
	
	public ListaSudokus(){
		this.lista = new ArrayList<Sudoku>();
	}
	
	private Iterator<Sudoku> obtIterador(){
		return lista.iterator();
	}
	
	private ListaSudokus obtenerLista(int pNivel){
		ListaSudokus list = new ListaSudokus();
		Iterator<Sudoku> itr = this.obtIterador();
		Sudoku sud = null;
		while(itr.hasNext()){
			sud = itr.next();
			if(sud.obtDificultad() == pNivel){
				list.anadirSudoku(sud);
			}
		}		
		return list;
	}
	public int tamano(){
		return lista.size();
	}
	
	
	public void anadirSudoku(Sudoku sud){
		lista.add(sud);
	}
		
	public Iterator<Sudoku> seleccionarSudoku(int pNivel){
		return this.obtenerLista(pNivel).obtIterador();
	}
		
	public void resetear(){
		lista.clear();
	}
	
	public Sudoku buscarSudokuPorId(int pIdSudoku){
		boolean enc = false;
		Iterator<Sudoku> itr = obtIterador();
		Sudoku sd = new Sudoku(0, 0);
		while(itr.hasNext() && !enc){
			sd = itr.next();
			if(sd.obtIdentificador() == pIdSudoku){
				enc = true;
			}
		}
		if(!enc){
			sd = null;
		}
		return sd;
	}
}
