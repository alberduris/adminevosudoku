package packModelo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;

import packBD.GestorBD;

public class GestorAdministrador extends Observable{

	private static GestorAdministrador miGestor;
	private Sudoku sud;
	
	private GestorAdministrador(){
		
	}
	
	public static GestorAdministrador getGestorAdministrador(){
		if(miGestor == null){
			miGestor = new GestorAdministrador();
		}
		return miGestor;
	}
	
	public boolean[][] crearSudoku(int pDif, int[][] pSud, boolean[][] pFijas){
		int id = CatalogoSudoku.getCatalogoSudoku().buscarPrimerIdDisp();
		sud = new Sudoku(id, pDif);
		boolean[][] result;
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(pFijas[i][j]){
					sud.asgValorInicial(i, j, pSud[i][j]);
				}else{
					sud.asgValor(i, j, pSud[i][j]);
				}
			}
		}
		result = sud.comprobarCorrecto(sud.obtMatriz());
		if(result.length == 0){
			CatalogoSudoku.getCatalogoSudoku().anadirSudoku(sud);
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(byteArray);
				oos.writeObject(sud);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GestorBD.getGestorBD().updateTablero("INSERT INTO Sudokus values ("+id+","+pDif+",?)",byteArray);
			
		}
		return result;
	}
	
}
