package packModelo;

public class NivelIgual implements ICondicion<Sudoku> {
	
    private int nivel;
    public NivelIgual(int pDif)
    {
	  nivel = pDif;
    }

    /* (non-Javadoc)
     * @see packSudoku.ICondicion#satisfaceCondicion(java.lang.Object)
     */
    @Override
    public boolean satisfaceCondicion(Sudoku pElem)
    {
	  return pElem.obtDificultad() == nivel;
    }

}
