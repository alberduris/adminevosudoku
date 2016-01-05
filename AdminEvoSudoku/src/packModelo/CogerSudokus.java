package packModelo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;


public class CogerSudokus {
	
	public CogerSudokus(){
		
	}
	
	public static void main(String[] arg){
		try {
			cogerSudoku();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void descargar(String pUrl) throws IOException{
		URL url = new URL(pUrl);
		InputStream in = url.openStream();
		FileOutputStream fos = new FileOutputStream(new File(System.getenv("APPDATA") + "\\Sudoku\\sudoku.pdf"));
		int length = -1;
		byte[] buffer = new byte[1024];// buffer for portion of data from
		                                // connection
		while ((length = in.read(buffer)) > -1) {
		    fos.write(buffer, 0, length);
		}
		fos.close();
		in.close();
	}
	
	public static boolean cogerSudoku() throws IOException {
		boolean completadoConExito = false;
		File directorio = new File(System.getenv("APPDATA") + "\\Sudoku");
		File f = new File(System.getenv("APPDATA") + "\\Sudoku/sudokus.save");
		directorio.mkdir();
		String ruta = null;
		if(!f.exists()){
			for(int j = 1; j<6; j++){
				if(j == 1){
					System.out.println("opening connection nivel_muyfacil");
					ruta = "http://www.sudoku-online.org/sudokus/muyfacil/sudokus_muyfacil_";
				
				}
				else if(j == 2){
					System.out.println("opening connection nivel_facil");
					ruta = "http://www.sudoku-online.org/sudokus/facil/sudokus_facil_";
				}else if(j == 3){
					System.out.println("opening connection nivel_normal");
					ruta = "http://www.sudoku-online.org/sudokus/normal/sudokus_normal_";
				}else if(j == 4){
					System.out.println("opening connection nivel_dificil");
					ruta = "http://www.sudoku-online.org/sudokus/dificil/sudokus_dificil_";
				}else{
					System.out.println("opening connection nivel_muydificil");
					ruta = "http://www.sudoku-online.org/sudokus/muydificil/sudokus_muydificil_";
				}
				for(int i=1; i<43; i++){
					descargar(ruta + i + ".pdf");
					leerYEscribirFichero(System.getenv("APPDATA") + "\\Sudoku\\sudoku.pdf", System.getenv("APPDATA") + "\\Sudoku\\sudokus.save");
					eliminarPDF(System.getenv("APPDATA") + "\\Sudoku\\sudoku.pdf");
					completadoConExito = true;
					
				}
				
			}
		}
		System.out.println("EMPIEZA");
		escribirEnBD(System.getenv("APPDATA") + "\\Sudoku/sudokus.save");
		System.out.println("FIN");
		File fi = new File(System.getenv("APPDATA") + "\\Sudoku/sudokus.save");
		fi.delete();
		return completadoConExito;
	}
	
	private static void eliminarPDF(String pRuta){
		File fichero = new File(pRuta);
		fichero.delete();
	}
	
	private static void leerYEscribirFichero(String pRutaPDF, String pRutaTxt){
		try {
			PdfReader reader = new PdfReader(pRutaPDF);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			PrintWriter out = new PrintWriter(new FileOutputStream(pRutaTxt, true));
			TextExtractionStrategy strategy;
			for(int i = 1; i<= reader.getNumberOfPages(); i++){
				strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
				out.println(strategy.getResultantText());
			}
			out.flush();
			out.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void escribirEnBD(String fich){
		GestorBD gBD = GestorBD.getGestorBD();
		ByteArrayOutputStream byteArray;
    	ObjectOutputStream oos;
    	char[] numero, solucion;
		Sudoku sudo;
		int idSudoku = 1;
		int dificultad = 1;
		int j;		
		int contador = 9;
		try{
			Scanner entradaNumero = new Scanner(new FileReader(fich));
			Scanner entradaSolucion = new Scanner(new FileReader(fich));
			
			for(int i=0; i<10; i++){
				entradaSolucion.nextLine();
			}
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
					j++;
				 }
				byteArray = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(byteArray);
				oos.writeObject(sudo);
				gBD.Update("INSERT INTO Sudokus values ("+idSudoku+","+dificultad+",1,?)",byteArray);
				dificultad = configurarDificultad(idSudoku);
				contador = saltar(entradaNumero);
				contador = saltar(entradaSolucion);
				idSudoku ++;
				}
				 entradaNumero.close();
				 entradaSolucion.close();
			}
			 catch(IOException e) {e.printStackTrace();}
			}
	
	private static int configurarDificultad(int idSudoku){
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
	
	private static boolean comprobarLineaEnBlanco(char[] pNumero, char[] pSolucion){
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
	
	private static int posicionNumero(char numero, char[] pLista){
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
	
	private static int saltar(Scanner pArchivo){
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
	
	private static char[] getCharDeLinea(Scanner pScanner, int pContador){
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
	
	private static int tamanoReal(char[] pList){
		int tamano = 0;
		while(pList[tamano]!= '\0' && tamano<pList.length-1){
			tamano++;
		}
		return tamano;
	}
	
	private static int estaEnNumero(char pSol, char[] listNum){
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
}
