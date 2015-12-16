package packModelo;


import java.util.ArrayList;
import java.util.Iterator;

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
		Sudoku sd = null;
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
	
	public int buscarPrimerIdDisp(){
		int id = 1;
		boolean enc = false;
		Iterator<Sudoku> itr = obtIterador();
		boolean siguiente = itr.hasNext();
		while(siguiente && !enc){
			if(itr.next().obtIdentificador() == id){
				itr = obtIterador();
				id++;
			}					
			siguiente = itr.hasNext();
		}
		if(!enc){
			id++;
		}
		return id;			
	}
	
	public int getTamano(){
		return lista.size();
	}
	
	public String[] obtListaIdent() {
		String[] list = new String[lista.size()];
		Iterator<Sudoku> itr = obtIterador();
		int i = 0;
		while(itr.hasNext()){
			list[i] = String.valueOf(itr.next().obtIdentificador());
			i++;
		}
		return list;
	}

	public String[] obtListaIdent(int pNivel) {
		ListaSudokus listAux = new ListaSudokus();
		Iterator<Sudoku> itr = obtIterador();
		Sudoku sud = null;
		while(itr.hasNext()){
			sud = itr.next();
			if(sud.obtDificultad() == pNivel){
				listAux.anadirSudoku(sud);
			}
		}
		return listAux.obtListaIdent();
	}
		
	public void eliminarSudoku(int pId){
		boolean eliminado = false;
		Iterator<Sudoku> itr = obtIterador();
		Sudoku sud;
		int i = 0;
		while(i < lista.size() && !eliminado){
		//	sud = itr.next();
			if(lista.get(i).obtIdentificador() == pId){
				eliminado = true;
				System.out.println(pId);
				System.out.println(lista.get(i).obtIdentificador());
				lista.remove(i);
			}
			i++;
		}
	}
}
