package packAdminSudoku;

import java.util.Timer;
import java.util.TimerTask;

public class Puntuacion {
	private int idSudoku;
	private int puntos;
	
	public Puntuacion(int pIdSudoku, int pPuntos){
		this.idSudoku = pIdSudoku;
		this.puntos = pPuntos;
	}
	
	public int getIdSudoku(){
		return this.idSudoku;
	}
	
	public int getPuntos(){
		return puntos;
	}
}
