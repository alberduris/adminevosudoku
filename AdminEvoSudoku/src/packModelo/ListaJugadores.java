package packModelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Observable;
import java.util.Scanner;

import packExcepciones.ExcepcionListaLlena;

public class ListaJugadores extends Observable{
    private static ListaJugadores miListaJugadores;
    private ListaOrdenadaGenerica<Jugador> listaJugadores;


    private ListaJugadores() {
        listaJugadores = new ListaOrdenadaGenerica<Jugador>(new ComparadorJugadores());
    }

    public static ListaJugadores obtListaJugadores()
    {
        if (miListaJugadores == null)
        {
            miListaJugadores = new ListaJugadores();
        }
        return miListaJugadores;
    }

    /**
     * cargar
     *
     * @param pFichero String
     */
    public void cargar(String pFichero) throws ExcepcionListaLlena{
        try {
            Scanner entrada = new Scanner(new File(pFichero));
            System.out.println(new File(pFichero).getAbsolutePath());
            int puntos;
            String nombre;
            Jugador jugador;
            while (entrada.hasNext())
            {
                puntos = entrada.nextInt();
                nombre = entrada.next();
                jugador = new Jugador(nombre);
                jugador.actualizarPuntos(puntos);
                anadirJugador(jugador);
            }
            entrada.close();
        } catch (FileNotFoundException ex) {
        }
    }

    /**
     * guardar
     *
     * @param pFichero String
   */
    public void guardar(String pFichero) {
        try {
            PrintWriter salida = new PrintWriter(new FileWriter(pFichero));
            Jugador jugador;
            Iterator<Jugador> iterador = listaJugadores.obtIterador();
            while (iterador.hasNext())
            {
                jugador = iterador.next();
                salida.println(jugador.obtPuntos() + " " + jugador.obtNombre());
            }
            salida.close();
           // listaJugadores.clear();
        } catch (IOException ex) {
        }
    }

    /**
     * anadirJugador
     *
     * @param pJugador Jugador
      */
    public void anadirJugador(Jugador pJugador) throws ExcepcionListaLlena{
        try {
           if (!listaJugadores.existeElementoSatisfaceCondicion(new ComparadorJugadorIgual(pJugador)))
           {
                listaJugadores.anadirElemento(pJugador);
                setChanged();
                notifyObservers();
            }
        } catch (OutOfMemoryError ex) {
            throw new ExcepcionListaLlena();
        }
    }

    /**
     * mostrar
     *
     */
    public void mostrar() {
     
	Jugador jugador;
        Iterator<Jugador> it = listaJugadores.obtIterador();   
        while (it.hasNext());
        
           {
               jugador = it.next();
               System.out.println(jugador.obtPuntos() + " " + jugador.obtNombre());
           }

    }
    
    public Iterator<Jugador> obtIterador()
    {
	return listaJugadores.obtIterador();
    }
}
