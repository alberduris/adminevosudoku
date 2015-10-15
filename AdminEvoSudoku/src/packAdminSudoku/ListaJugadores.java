package packAdminSudoku;

import java.util.ArrayList;
import java.util.Iterator;

public class ListaJugadores {//TAD
	private ArrayList<Jugador> lista;
	
	public ListaJugadores(){
		this.lista = new ArrayList<Jugador>();
	}	
	private Iterator<Jugador> getIterador(){
		return lista.iterator();
	}
	
	public Jugador anadirJugadorPorNombre(String pNombre){
		Jugador jugador = this.existe(pNombre);
		if(jugador == null){
			jugador = new Jugador (pNombre);
			lista.add(jugador);
		}
		return jugador;
	}
	
	public void anadirJugador(Jugador pJug){
		if(this.existe(pJug.getNombre()) == null){
			lista.add(pJug);
		}
	}
	
	private Jugador existe(String pNombre){
		Iterator<Jugador> itr = this.getIterador();
		Jugador jug = null;
		while(jug == null && itr.hasNext()){
			jug = itr.next();
			if(!jug.getNombre().equals(pNombre)){
				jug = null;
			}
		}
		return jug;
	}
	
	public Jugador getPorIndice(int pIndice){
		if(pIndice >= lista.size()){ return null;} 
		return lista.get(pIndice);
	}
	
	public void ordenarLista(){
		boolean terminado = false;
		Jugador jugAux = null;
		while(!terminado){
			terminado = true;
			for(int i=0; i<lista.size()-1;i++){
				if(lista.get(i).getListaPuntos().getMejorPuntuacion()>lista.get(i+1).getListaPuntos().getMejorPuntuacion()){
					terminado = false;
					jugAux = lista.get(i);
					lista.set(i,lista.get(i+1));
					lista.set(i+1,jugAux);
				}
			}
		}
	}
	
	public int size(){
		return lista.size();
	}
}
