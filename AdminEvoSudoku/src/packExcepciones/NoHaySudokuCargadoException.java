package packExcepciones;

public class NoHaySudokuCargadoException extends Exception {
	
    public NoHaySudokuCargadoException()
    {
    }

    /**
     * @param pArg0
     */
    public NoHaySudokuCargadoException(String pArg0)
    {
	super(pArg0);
    }

    /**
     * @param pArg0
     */
    public NoHaySudokuCargadoException(Throwable pArg0)
    {
	super(pArg0);
    }

    /**
     * @param pArg0
     * @param pArg1
     */
    public NoHaySudokuCargadoException(String pArg0, Throwable pArg1)
    {
	super(pArg0, pArg1);
    }

}
