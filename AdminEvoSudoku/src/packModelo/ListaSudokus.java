package packModelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class ListaSudokus {

	    private static ListaSudokus miListaSudokus;
	    private ListaOrdenadaGenerica<Sudoku> listaSudokus;

	    private ListaSudokus() {
	        listaSudokus = new ListaOrdenadaGenerica<Sudoku>(new ComparadorSudokus());;
	    }

	    public static ListaSudokus obtListaSudokus() {
	        if (miListaSudokus == null) {
	            miListaSudokus = new ListaSudokus();
	        }
	        return miListaSudokus;
	    }

	    /**
	     * cargar
	     *
	     * <p> PRE: pFichero contiene el nombre del fichero que contiene la lista
	     * de sudokus
	     *
	     * <p>
	     *
	     * <p> POST: Se ha cargado la lista de sudokus
	     *
	     * <p>
	     *
	     * @param pFichero String
	     * @todo Implement this packSudoku.IListaSudokus method
	     */
	    public void cargar(String pFichero) throws FileNotFoundException {
	        Scanner entradaSudokus = null;
	        entradaSudokus = new Scanner(new File(pFichero));

	        while (entradaSudokus.hasNext()) {
	            listaSudokus.anadirElemento(cargarSudoku(entradaSudokus));
	        }
	        entradaSudokus.close();
	     }

	    private Sudoku cargarSudoku(Scanner pEntrada) {
	        String id = pEntrada.next();
	        int dificultad = pEntrada.nextInt();
	        Sudoku sudoku = new Sudoku(id, dificultad);
	        // Cargar Matriz sudoku
	        cargarMatrizSudoku(sudoku, pEntrada, true);
	        cargarMatrizSudoku(sudoku, pEntrada, false);
	        return sudoku;
	    }

	    private void cargarMatrizSudoku(Sudoku pSudoku, Scanner pEntrada,
	                                    boolean pValoresIniciales) {
	        String linea;
	        for (int i = 1; i <= 9; i++) {
	            linea = pEntrada.next();
	            cargarFilaSudoku(pSudoku, i, linea, pValoresIniciales);
	        }
	    }

	    private void cargarFilaSudoku(Sudoku pSudoku, int pFila,
	                                  String pNumerosFila,
	                                  boolean pValoresIniciales) {
	        int valor;
	        for (int columna = 1; columna <= 9; columna++) {
	            valor = Integer.parseInt(pNumerosFila.substring(columna - 1,
	                    columna));
	            if (pValoresIniciales) {
	                if (valor != 0) {

	                    pSudoku.asgValorInicial(pFila, columna, valor);
	                }
	            } else {
	                if (!pSudoku.obtMatriz().esInicial(pFila, columna)) {
	                    pSudoku.asgValor(pFila, columna, valor);
	                }
	            }
	        }
	    }


	    /**
	     * obtIterador
	     *
	     * <p> PRE: pDific contiene el nivel de dificultad del sudoku deseado<p>
	     *
	     * <p> POST: Devuelve un sudoku que apunta al primer sudoku con el nivel
	     * de dificultad especificado<p>
	     *
	     * @return Iterator
	     * @param pDific int
	     * @todo Implement this packSudoku.IListaSudokus method
	     */
	    public Iterator<Sudoku> obtIterador(int pDific) {
		return listaSudokus.obtIterador(new NivelIgual(pDific));

	    }


	}
