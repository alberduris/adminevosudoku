package packAdminSudoku;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import packVistaAdminSudoku.VentanaRanking;
import packVistaAdminSudoku.VentanaSudoku;

public class GestionSudoku extends Observable{
	private boolean terminado = false;
	private Sudoku sud;
	private int tiempo;
	private Jugador jug;
	private Timer time = new Timer();
	private static GestionSudoku miGestionSudoku = new GestionSudoku();
	
	private GestionSudoku(){
		terminado = false;
		this.tiempo=-1;
		jug = null;
		

		time = new Timer();
		TimerTask  timerTask = new TimerTask() {
			@Override
			public void run() {
				if(!terminado){
					aumentarTiempo();
				}
			}
		};
		time.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	
	private void aumentarTiempo(){
		tiempo ++;	
		notifyObservers(tiempo);	
		setChanged();
	}
	
	public int obtenerTiempo(){
		return tiempo;
	}
	
	protected void anadirSudoku(Sudoku pSud){
		terminado = false;
		sud = pSud;
	}

	public void borrarNumero(int pI, int pJ){
		sud.borrarNumero(pI,pJ);
		setChanged();
		notifyObservers();
	}
	
	public static GestionSudoku getGestionSudoku(){
		if(miGestionSudoku == null){
			miGestionSudoku = new GestionSudoku();
		}
		return miGestionSudoku;
	}
	
	public void anadirNumero(int pI, int pJ, char pNumero){
		sud.anadirNumero(pI, pJ, pNumero);
		setChanged();
		notifyObservers();
	}
		
	public char obtenerNum(int pI, int pJ){
		return sud.obtenerNum(pI, pJ);
	}
	
	public char[] obtenerListaNotas(int pI, int pJ){
		return sud.obtenerListaNotas(pI, pJ);
	}
	
	public boolean obtenerFija(int pI, int pJ){
		return sud.obtenerFija(pI, pJ);
	}
	
	public boolean[][] comprobarCorrecto(){
		return sud.comprobarCorrecto();
	}
	
	public boolean[][] todosLosNumeros(char pNum){
		return sud.todosLosNumeros(pNum);
	}
	
	public void anadirJugador(Jugador pJug){
		jug = pJug;
	}
	
	public void terminar(){
		terminado = true;
		Puntuacion punt = new Puntuacion(sud.getId(), puntuacionConPenalizacion());
		jug.anadirPuntuacion(punt);
		CatalogoJugadores.getCatalogoJugadores().escribirEnFichero();
		
		
	}
	
	public int puntuacionConPenalizacion(){
		int penalizacion = 1;
		if(sud.getDificultad()==5){
			penalizacion = 1;
		}else if(sud.getDificultad()==4){
			penalizacion = 2;
		}else{
			penalizacion = (int) (Math.pow(2,(5-sud.getDificultad()))-1);
		}
		
		return tiempo*penalizacion;
	}
	
	public void lanzarRanking(){
		VentanaRanking vnt = new VentanaRanking(jug, sud.getDificultad());
		vnt.setVisible(true);
	}
	
	public void eliminateValues(){
		if(sud.eliminateValues()){
			tiempo +=300;
			setChanged();
			notifyObservers();			
		}
	}

	public void reiniciar(){
		tiempo = 0;
	}

}
	

