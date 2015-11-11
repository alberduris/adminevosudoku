package packExcepciones;

public class ExcepcionListaLlena extends Exception {
    public ExcepcionListaLlena() {
        super();
    }

    public ExcepcionListaLlena(String mes)
    {
        super(mes);
    }
}
