package packModelo;

import java.util.Comparator;

public class ComparadorJugadores implements Comparator<Jugador> {
	    /* (non-Javadoc)
	     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	     */
	    @Override
	    public int compare(Jugador pO1, Jugador pO2)
	    {
		return pO1.obtPuntos() - pO2.obtPuntos();
	    }
}
