package packModelo;

public class Casilla{

    // Indica si es un valor inicial
    private boolean valorInicial;

    // Valor contenido en la casilla
    private int valor;

    // Estado de ocupaci�n de la casilla
    private boolean ocupada;

    /**
     * Casilla
     * <p> POST: crea una casilla que est� libre y no contiene un valor inicial.<p>
     */
    public Casilla() {
       valorInicial = false;
       ocupada = false;
    }

    /**
     * asgValorInicial
     *
     * <p>POST: inicializa el valor de la casilla con el valor indicado,
     * pasando a estar ocupada.
     *
     * @param pValor int
     */
    public void asgValorInicial(int pValor) {
       valor = pValor;
       ocupada = true;
       valorInicial = true;
    }

    /**
     * asgValor
     *
     * <p>POST: inicializa el valor de la casilla con el valor indicado.
     *
     * @param pValor int
     */
    public void asgValor(int pValor) {
       valor = pValor;
       ocupada = true;
       valorInicial = false;
    }

    /**
     * quitarValor
     * <p>POST: indica que la casilla no est� ocupada.<p>
     */
    public void quitarValor() {
       ocupada = false;
    }

    /**
     * estaLibre
     *
     * <p>POST: devuelve true si la casilla esta libre y false en caso
     * contrario
     *
     * <p>
     *
     * @return boolean
     */
    public boolean estaLibre() {
       return !ocupada;
    }

    /**
     * esInicial
     *
     * <p> POST: devuelve true si la casilla contiene un valor inicial y
     * false en caso contrario.
     *
     * <p>
     *
     * @return boolean
     */
    public boolean esInicial() {
       return valorInicial;
    }

    /**
     * obtValor
     *
     * <p> PRE: La casilla est� ocupada.
     *
     * <p>
     *
     * <p> POST: devuelve el valor contenido en la casilla.
     *
     * <p>
     *
     * @return int
     */
    public int obtValor() {
       return valor;
    }

    /**
     * mismoValor
     * <p> PRE: ambas casillas est�n ocupadas.<p>
     * <p> POST: devuelve true si ambas casillas contienen el mismo valor y
     *         false en caso contrario.<p>
     * @param pCasilla Casilla
     * @return boolean
     */
     public boolean mismoValor(Casilla pCasilla) {
        return this.obtValor() == pCasilla.obtValor();
     }

}

