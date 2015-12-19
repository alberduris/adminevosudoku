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
	
	public int buscarDificultadPorId(int pIdSudoku){
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
			return 0;
		}
		return sd.obtDificultad();
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
		
	public String[] obtListaEstado(int pNivel, boolean pEstado){
		ListaSudokus listAux = new ListaSudokus();
		Iterator<Sudoku> itr = obtIterador();
		Sudoku sud = null;
		while(itr.hasNext()){
			sud = itr.next();
			if(sud.obtDificultad() == pNivel){
				listAux.anadirSudoku(sud);
			}
		}
		return listAux.obtListaEstado(pEstado);
	}
	
	public String[] obtListaEstado(boolean pEstado){
		ArrayList<String> list = new ArrayList<String>();
		Iterator<Sudoku> itr = obtIterador();
		Sudoku sud;
		while(itr.hasNext()){
			sud = itr.next();
			if(sud.obtActivado() == pEstado){
				list.add(String.valueOf(sud.obtIdentificador()));
			}
		}
		String[] lis = new String[list.size()];
		for(int i = 0; i<list.size(); i++){
			lis[i] = list.get(i);
		}
		
		return lis;
	}
	
	public void modificarEstadoSudoku(int pIdSudoku, boolean pEstado){
		boolean modificado = false;
		Iterator<Sudoku> itr = obtIterador();
		Sudoku sud;
		while(itr.hasNext() && !modificado){
			sud = itr.next();
			if(sud.obtIdentificador() == pIdSudoku){
				modificado = true;
				sud.setActivado(pEstado);
			}
		}
	}
	
	public void eliminarSudoku(int pId){
		boolean eliminado = false;
		int i = 0;
		while(i < lista.size() && !eliminado){
			if(lista.get(i).obtIdentificador() == pId){
				eliminado = true;
				lista.remove(i);
			}
			i++;
		}
	}
	
	public void imprimir(){
		Iterator<Sudoku> itr = obtIterador();
		Sudoku sud;
		while(itr.hasNext()){
			sud = itr.next();
			if(sud.obtIdentificador() == 999 | sud.obtIdentificador() == 1999 | sud.obtIdentificador() == 2999 | sud.obtIdentificador() == 3999 | sud.obtIdentificador() == 4999){
				System.out.println("");
			}
		}
	}
	
}
