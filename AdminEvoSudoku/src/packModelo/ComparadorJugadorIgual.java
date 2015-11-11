package packModelo;

public class ComparadorJugadorIgual implements ICondicion<Jugador> {
    private Jugador jugador1;
    
    public ComparadorJugadorIgual(Jugador pJug1)
    {
	  jugador1 = pJug1;
    }

    /* (non-Javadoc)
     * @see packSudoku.ICondicion#satisfaceCondicion(java.lang.Object)
     */
    @Override
    public boolean satisfaceCondicion(Jugador pElem)
    {
	
	  return jugador1.esIgual(pElem);
    }

}
