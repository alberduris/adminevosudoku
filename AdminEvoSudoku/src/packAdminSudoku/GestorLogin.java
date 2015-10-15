package packAdminSudoku;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;

import packVistaAdminSudoku.VentanaLoginV2;
import packVistaAdminSudoku.VentanaSudoku;

public class GestorLogin extends Thread{
	
	private String nombre;
	private int nivel;
	private VentanaLoginV2 vnt;
	private JDialog dialogCargando;
	
	public GestorLogin(String pNombre, int pNivel, VentanaLoginV2 pVnt,JDialog pDialog){
		nombre = pNombre;
		nivel = pNivel;
		vnt = pVnt;
		dialogCargando = pDialog;
	}
	
	public void run(){
		boolean lanzar = true;
		CatalogoJugadores.getCatalogoJugadores().leerFichero();
		
		Jugador jug = CatalogoJugadores.getCatalogoJugadores().anadirJugador(nombre);
		lanzar = jug.lanzarSudoku(nivel, vnt);
		VentanaSudoku vent = null;
		try {
			if(lanzar){
				vent = new VentanaSudoku();
				vent.setVisible(true);
				dialogCargando.dispose();
			}
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
