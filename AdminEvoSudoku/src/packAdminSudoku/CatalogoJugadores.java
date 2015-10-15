package packAdminSudoku;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class CatalogoJugadores {//MAE
//todoslos metodos de listas van a estar en el TAD
	private ListaJugadores lista;
	private static CatalogoJugadores miListaJugadores;
	
	private CatalogoJugadores(){
		this.lista = new ListaJugadores();
	}
	
	public static CatalogoJugadores getCatalogoJugadores(){
		if(miListaJugadores == null){
			miListaJugadores = new CatalogoJugadores();
		}
		
		
		return miListaJugadores;
	}
	
	public void leerFichero(){
		try {
			File archivo = new File (System.getenv("APPDATA") + "\\Sudoku\\bd.save");
			
			if(archivo.exists()){
				Scanner entrada = new Scanner(new FileReader(archivo));
				String linea, id, punt;
				boolean dosPuntos = false;
				Puntuacion puntuacion;
				Jugador jug = null;
				while(entrada.hasNext()){
					linea = ""; id = ""; punt = "";
					dosPuntos = false;
					linea = entrada.nextLine();
					if(!Character.isDigit(linea.charAt(0))){
						if(jug != null){ lista.anadirJugador(jug);}
						jug = new Jugador(linea);
					}else{
						for(int i=0; i<linea.length(); i++){
							if(linea.charAt(i) == ':'){
								dosPuntos = true;
							}else if(!dosPuntos){
								id += linea.charAt(i);
							}else{
								punt += linea.charAt(i);
							}
						}
						puntuacion = new Puntuacion(Integer.parseInt(id), Integer.parseInt(punt));
						jug.anadirPuntuacion(puntuacion);
					}
				}
				lista.anadirJugador(jug);
				entrada.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private ListaJugadores getRanking(){
		//Hay que devolver un fichero xml para que la vista no interfiera.
		
		ListaJugadores listaRanking = new ListaJugadores();
		lista.ordenarLista();
		for(int i=0; i<lista.size(); i++){
			
			listaRanking.anadirJugador(lista.getPorIndice(i));
			

		}
		return listaRanking;
	}
	
	public String rankingHTML(){
	
		ListaJugadores listRanking = getRanking();		
		
		String sInicio = "<html><body>";
		String sMedio = "";
		String sFinal = "</body></html>";
		String aux;
		
		Jugador jugador;
		int puntuacion = 0;
		
		for(int i = 0; i < 10 && i<listRanking.size(); i++){
			jugador = listRanking.getPorIndice(i);
			puntuacion = jugador.getPuntuacionMejor();
			aux = "<br>"+(i+1)+"."+jugador.getNombre()+" : "+puntuacion+" puntos.";
			sMedio = sMedio + aux;
		}
		String ranking = sInicio + sMedio + sFinal;
	
		return ranking;
	}
	
	
	
	public void escribirEnFichero(){
		File directorio = new File(System.getenv("APPDATA") + "\\Sudoku");
		directorio.mkdir();
		String sFichero = System.getenv("APPDATA") + "\\Sudoku\\bd.save";
		ListaPuntuaciones listaPuntuacion;
		String linea;
		try{
		  BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));
		  for(int i=0; i<size(); i++){
			  bw.write(lista.getPorIndice(i).getNombre() + '\n');
			  listaPuntuacion = lista.getPorIndice(i).getListaPuntos();
			  for(int j=0; j<listaPuntuacion.size(); j++){
				  linea = String.valueOf(listaPuntuacion.obtenerPuntuacion(j).getIdSudoku());
				  linea+=':';
				  linea+=String.valueOf(listaPuntuacion.obtenerPuntuacion(j).getPuntos());
				  bw.write(linea + '\n');
			  }
		  }
		  
		  // Hay que cerrar el fichero
		  bw.close();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}	
	}	
	
	public Jugador anadirJugador(String pNombre){
		return lista.anadirJugadorPorNombre(pNombre);
	}
	
	//Método creado para las JUnits
	public int size(){
		return lista.size();
	}
	
}
