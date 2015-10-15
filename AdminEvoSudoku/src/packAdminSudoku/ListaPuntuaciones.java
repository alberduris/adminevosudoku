package packAdminSudoku;

import java.util.ArrayList;

public class ListaPuntuaciones {
	private ArrayList<Puntuacion> lista;
	
	public ListaPuntuaciones(){
		this.lista = new ArrayList<Puntuacion>();
	}
	
	public int getMejorPuntuacion(){
		int punt = lista.get(0).getPuntos();
		for(int i=1; i<lista.size();i++){
			if(punt > lista.get(i).getPuntos()){
				punt = lista.get(i).getPuntos();
			}
		}
		return punt;
	}
	
	public boolean esta(int pId){
		boolean esta = false;
		for(int i=0; i<lista.size();i++){
			if(pId == lista.get(i).getIdSudoku()){
				esta = true;
			}
		}
		return esta;
	}
	
	public void anadirPuntuacion(Puntuacion pPuntuacion){
		if(!this.esta(pPuntuacion.getIdSudoku())){
			lista.add(pPuntuacion);
		}
	}
	
	public int size(){
		return lista.size();
	}
	
	public Puntuacion obtenerPuntuacion(int pI){
		return lista.get(pI);
	}
}
