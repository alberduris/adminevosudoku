package packPrincipal;

import java.io.FileNotFoundException;

import packExcepciones.ExcepcionListaLlena;
import packInterfazGrafica.VentanaInicial;
import packInterfazGrafica.VentanaPuntuaciones;
import packInterfazGrafica.VentanaSiNo;
import packInterfazGrafica.VentanaTablero;
import packModelo.ListaJugadores;
import packModelo.ListaSudokus;
import packModelo.Sesion;

public class ProgPrincipal {

	    /**
	     * @param args
	     */
	    public static void main(String[] args)
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
		try
		{
		    ListaSudokus.obtListaSudokus().cargar("sudokuak.txt");
		} catch (FileNotFoundException e)
		{
		    e.printStackTrace();
		}
		VentanaInicial.obtVentanaInicial().mostrar();
		boolean seguir = true;
		while (seguir) {
			sesion.iniciarJuego();
			VentanaTablero.obtVentanaTablero().mostrar();
			// Interfaz: Desea Seguir
			seguir =VentanaSiNo.obtVentanaSiNo().preguntar("Desea Seguir?");
		}
		sesion.actualizarPuntuaciones();
		VentanaPuntuaciones.obtVentanaPuntuaciones().mostrar();
		ListaJugadores.obtListaJugadores().guardar("jugadores.txt");
		System.out.println("Final");

	    }

}
