package packModelo;

public class Jugador {

    // Nombre del Jugador
    private String nombre;
    // Puntuaci�n del Jugador
    private int puntuacion;


    /**
     * Jugador
     * <p> POST: crea una instancia de Jugador con el nombre indicado y 0 puntos.<p>
     * @param pNombre String
     */
    public Jugador(String pNombre) {
        nombre = pNombre;
        puntuacion = 0;
    }

    /**
     * actualizarPuntos
     *
     * <p> PRE: pPuntos contiene una puntuaci�n v�lida.
     *
     * <p>
     *
     * <p> POST: actualiza la puntuaci�n del Jugador con el valor
     * suministrado.
     *
     * <p>
     *
     * @param pPuntos int
     */
    public void actualizarPuntos(int pPuntos) {
        puntuacion = pPuntos;
    }

    /**
     * obtNombre
     *
     * <p> POST: devuelve el nombre del Jugador.
     *
     * <p>
     *
     * @return String
     */
    public String obtNombre() {
        return nombre;
    }

    /**
     * obtPuntos
     *
     * <p> POST: devuelve la puntuaci�n del Jugador.
     *
     * <p>
     *
     * @return int
     */
    public int obtPuntos() {
        return puntuacion;
    }

    /**
     * masPuntos
     *
     * <p> POST: devuelve true si esta instancia tiene mayor puntuaci�n que
     * pJugador.
     *
     * <p>
     *
     * @param pJugador Jugador
     * @return boolean
     */
    public boolean masPuntos(Jugador pJugador) {
        return this.obtPuntos() >= pJugador.obtPuntos();
    }

    /**
     * esIgual
     *
     * <p> POST: devuelve true si esta instancia de Jugador y pJugador tienen
     * el mismo nombre y la misma puntuaci�n y false en caso contrario.
     *
     * <p>
     *
     * @param pJugador Jugador
     * @return boolean
     */
    public boolean esIgual(Jugador pJugador) {
        return (this.obtNombre().equalsIgnoreCase(pJugador.obtNombre()) &&
                this.obtPuntos() == pJugador.obtPuntos());
    }
}
