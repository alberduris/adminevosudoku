package packModelo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import packAdminSudoku.ListaPuntuaciones;

public class CatalogoSudoku {
	static CatalogoSudoku miListaSudoku;
	ListaSudokus lista;
	
	private CatalogoSudoku(){
		this.lista = new ListaSudokus();
	}
	
	public static CatalogoSudoku getCatalogoSudoku(){
		if(miListaSudoku == null){
			miListaSudoku = new CatalogoSudoku();
		}
		return miListaSudoku;
	}
	
	public Iterator<Sudoku> obtIteradorSudokus(int pNivel){
		return lista.seleccionarSudoku(pNivel);
	}
	
	public void anadirSudoku(Sudoku sud){
		lista.anadirSudoku(sud);
	}
	
	public boolean leerFichero(String fich){
		boolean descargadoConExito = true;
		boolean lanzar = true;
		if(lista.tamano() ==0){
			char[] numero, solucion;
			Sudoku sudo;
			
			int idSudoku = 1;
			int dificultad = 1;
			int j;		
			int contador = 9;
			try{
			File fichero = new File(fich);
			if(!fichero.exists()){
				try{
					//descargadoConExito = CogerSudokus.cogerSudoku(pVnt);
					
					/*if(!descargadoConExito){
						NoHayFicheroSudokusException ex = new NoHayFicheroSudokusException();
						throw ex;
					}*/
				}
				catch(Exception /*NoHayFicheroSudokusException*/ ex){
					//lanzar = ex.lanzarExcepcion();
				}
				
			}
			
			if(lanzar){
				/*if(pVnt != null){
					pVnt.setBarra(211);
				}*/
				Scanner entradaNumero = new Scanner(new FileReader(fich));
				Scanner entradaSolucion = new Scanner(new FileReader(fich));
				
				for(int i=0; i<10; i++){
					entradaSolucion.nextLine();
				}
			//	pVnt.actualizarBarra();
				 while (entradaSolucion.hasNext() && contador >=0) {
					 sudo = new Sudoku(idSudoku, dificultad);		
					 j = 0;
					 while(j< 9){
						 numero = getCharDeLinea(entradaNumero, contador);
						 solucion = getCharDeLinea(entradaSolucion, 20);
						 contador --;
						 while(!Character.isDigit(numero[0])){
							 numero = getCharDeLinea(entradaNumero, contador);					 
						 }
						 while(!Character.isDigit(solucion[0])){
							 solucion = getCharDeLinea(entradaSolucion, 20);
							 contador--;
						 }
						 if(comprobarLineaEnBlanco(numero, solucion)){
							/* for(int i=0; i<9; i++){
								sudo.asgValorInicial(i, pColumna, pValor);(j, i,' ' ,solucion[i]);
							 }*/
							 j++;
							 solucion = getCharDeLinea(entradaSolucion, 20);
							 while(!Character.isDigit(solucion[0])){
								 solucion = getCharDeLinea(entradaSolucion, 20);
								 contador --;
							 }
						 }
						 for(int i=0; i<9; i++){
							 if(estaEnNumero(solucion[i], numero) != -1){
								 sudo.asgValorInicial(j, i, (int)(solucion[i]-'0')); 
							 }else{
							    sudo.asgValor(j, i,(int)(solucion[i]-'0'));
							 }
						 }
						 CatalogoSudoku.getCatalogoSudoku().anadirSudoku(sudo);
						 j++;
					 }
					dificultad = configurarDificultad(idSudoku);
					contador = saltar(entradaNumero);
					contador = saltar(entradaSolucion);
					idSudoku ++;
				/*	if(pVnt != null){
						pVnt.actualizarBarra();
					}*/
				}
				 entradaNumero.close();
				 entradaSolucion.close();		
				}
			}
				 catch(IOException e) {e.printStackTrace();}
			}
		return lanzar;
	}
	
	
	private int configurarDificultad(int idSudoku){
		int dificultad = 1;
		if(idSudoku >= 1000 && idSudoku < 2000){
			 dificultad = 2;
		 }else if(idSudoku >= 2000 && idSudoku < 3000){
			 dificultad = 3;
		 }else if(idSudoku >= 3000 && idSudoku < 4000){
			 dificultad = 4;
		 }else if(idSudoku >= 4000 && idSudoku < 5000){
			 dificultad = 5;
		 }
		return dificultad;
	}
	private boolean comprobarLineaEnBlanco(char[] pNumero, char[] pSolucion){
		boolean lineaBlanco = false;
		if(tamanoReal(pNumero)>1){
			for(int i= 0; i<tamanoReal(pNumero)-1 &&!lineaBlanco; i++){
				if(posicionNumero(pNumero[i], pSolucion) > posicionNumero(pNumero[i+1], pSolucion)){
					lineaBlanco = true;
				}
			}
		}
		return lineaBlanco;
	}
	
	private int posicionNumero(char numero, char[] pLista){
		boolean encontrado = false;
		int i = 0;
		while(i< tamanoReal(pLista) && !encontrado){
			if(numero == pLista[i]){
				encontrado = true;
			}else{
				i++;
			}
		}
		return i;
	}
	
	private int saltar(Scanner pArchivo){
		String linea;
		int contador = 0;
		boolean fin = false;
		if(pArchivo.hasNext()){
			linea = pArchivo.nextLine();
			if(!Character.isDigit(linea.charAt(0))){
				if(pArchivo.hasNext()){
					linea = pArchivo.nextLine();
					if(!Character.isDigit(linea.charAt(0))){
						if(pArchivo.hasNext()){
							linea = pArchivo.nextLine();
						}else{
							fin = true;
							contador = -100;
						}
					}else{
						contador++;
					}
				}else{
					fin = true;
					contador = -100;
				}
			}else{
				contador++;
			}
			while(Character.isDigit(linea.charAt(0))&& !fin){
				if(pArchivo.hasNext()){
					linea = pArchivo.nextLine();
					contador++;
				}else{
					fin = true;
					contador = -100;
				}
			}
		}
		return contador;
	}
	
	private char[] getCharDeLinea(Scanner pScanner, int pContador){
		String linea;
		char[] result = new char[9];
		int i = 0, j=0;
		if(pContador != 0){
			if(pScanner.hasNext()){
				linea = pScanner.nextLine();
				while( i < linea.length()){
					try{
						result[j] = linea.charAt(i);
					}catch(Exception e){
						return result;
					}
					j++;
					i +=2;
				}
			}
		}else{
			result[0] = '0';
		}
		return result;		
	}
	
	private int tamanoReal(char[] pList){
		int tamano = 0;
		while(pList[tamano]!= '\0' && tamano<pList.length-1){
			tamano++;
		}
		return tamano;
	}
	
	private int estaEnNumero(char pSol, char[] listNum){
		boolean esta = false;
		int posicion = -1;
		for(int i = 0; i< tamanoReal(listNum) && !esta; i++){
			if(pSol == listNum[i]){
				esta=true;
				posicion = i;
			}
		}
		return posicion;
	}	
	
	//Método para las JUnits
	public void resetear(){
		lista.resetear();
	}
	
	public Sudoku buscarSudokuPorId(int pIdSudoku){
		return lista.buscarSudokuPorId(pIdSudoku);
	}
}
