package packPrincipal;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import packExcepciones.ExcepcionListaLlena;
import packExcepciones.NoHaySudokuCargadoException;
import packInterfazGrafica.VentanaInicial;
import packInterfazGrafica.VentanaPuntuaciones;
import packInterfazGrafica.VentanaSiNo;
import packInterfazGrafica.VentanaTablero;
import packModelo.CatalogoSudoku;
import packModelo.ListaJugadores;
import packModelo.ListaSudokus;
import packModelo.Sesion;

public class ProgPrincipal {

	    /**
	     * @param args
	     * @throws UnsupportedAudioFileException 
	     * @throws IOException 
	     * @throws LineUnavailableException 
	     * @throws NoHaySudokuCargadoException 
	     */
	    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException, NoHaySudokuCargadoException
	    {
		System.out.println("Iniciando Sesión");
		
		// sesion.iniciarJuego();
		//TODO: Bukaeran osatu begiratzeko ea ondo dabilen
		Sesion sesion = Sesion.obtSesion();
		try
		{
		    ListaJugadores.obtListaJugadores().cargar("jugadores.txt");
		} catch (ExcepcionListaLlena e)
		{
		
		}
		CatalogoSudoku.getCatalogoSudoku().leerBD();
		VentanaInicial.obtVentanaInicial().mostrar();
		boolean seguir = true;
	//	while (seguir) {
			sesion.iniciarJuego();
			VentanaTablero vnt = new VentanaTablero();
			vnt.setVisible(true);
			
			// Interfaz: Desea Seguir
			//seguir =VentanaSiNo.obtVentanaSiNo().preguntar("Desea Seguir?");
	//	}
		//sesion.actualizarPuntuaciones();
		//VentanaPuntuaciones.obtVentanaPuntuaciones().mostrar();
		//ListaJugadores.obtListaJugadores().guardar("jugadores.txt");
		//System.out.println("Final");

	    }

}