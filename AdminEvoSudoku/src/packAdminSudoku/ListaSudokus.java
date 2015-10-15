package packAdminSudoku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ListaSudokus {	
	ArrayList<Sudoku> lista;
	
	public ListaSudokus(){
		this.lista = new ArrayList<Sudoku>();
	}
	
	private Iterator<Sudoku> getIterador(){
		return lista.iterator();
	}
	
	private ListaSudokus obtenerLista(int pNivel){
		ListaSudokus list = new ListaSudokus();
		Iterator<Sudoku> itr = this.getIterador();
		Sudoku sud = null;
		while(itr.hasNext()){
			sud = itr.next();
			if(sud.getDificultad() == pNivel){
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
		
	public Sudoku seleccionarSudoku(int pNivel, ListaPuntuaciones pLista){
		return this.obtenerLista(pNivel).seleccionarSudokuDeNivel(pLista);
	}
	
	private Sudoku seleccionarSudokuDeNivel(ListaPuntuaciones pLista){
		int tamano = seleccionarNumRandom(lista.size());
		while(lista.get(tamano).estaEn(pLista)){
			System.out.println(tamano);
			tamano = seleccionarNumRandom(lista.size());
		}
		return lista.get(tamano);		
	}
	
	private int seleccionarNumRandom(int pTamano){
		Random rm = new Random ();
		return rm.nextInt(pTamano);
	}
	
	public void resetear(){
		lista.clear();
	}
}
