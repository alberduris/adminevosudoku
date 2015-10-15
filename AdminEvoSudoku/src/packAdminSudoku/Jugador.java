package packAdminSudoku;

import java.io.File;

import packVistaAdminSudoku.VentanaLoginV2;

public class Jugador {
	private String nombre;
	private ListaPuntuaciones listaPuntos;
	
	public Jugador(String pNombre){
		this.nombre= pNombre;
		listaPuntos = new ListaPuntuaciones();
	}

	public String getNombre(){
		return nombre;
	}
	
	public void anadirPuntuacion(Puntuacion pPuntuacion){
		listaPuntos.anadirPuntuacion(pPuntuacion);
	}
	
	public ListaPuntuaciones getListaPuntos(){
		return this.listaPuntos;
	}
	
	public int getPuntuacionMejor(){
		return listaPuntos.getMejorPuntuacion();
	}
	
	public boolean lanzarSudoku(int pNivel, VentanaLoginV2 pVnt){
		boolean lanzar = true;
		Sudoku sud = null;
	
		
		File ficheroSudokus = new File (System.getenv("APPDATA") + "\\Sudoku\\sudokus.save");
		lanzar = CatalogoSudoku.getCatalogoSudoku().leerFichero(ficheroSudokus.getAbsolutePath(), pVnt);
		
		
		if(lanzar){
			try{
				sud = CatalogoSudoku.getCatalogoSudoku().seleccionarSudoku(pNivel, listaPuntos);
				//sud = null;//SI SE DESCOMENTA ESTA LINEA SALTA NoHaySudokuCargadoException!!
				if(sud == null){
					NoHaySudokuCargadoException ex = new NoHaySudokuCargadoException();
					throw ex;
				}
			}
			catch(NoHaySudokuCargadoException ex){
				lanzar = ex.lanzarExcepcion();
				
			}
			
			GestionSudoku gs = GestionSudoku.getGestionSudoku();
			gs.anadirSudoku(sud);
			gs.anadirJugador(this);
		}	
		
		return lanzar;
	}
}
