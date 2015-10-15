package packAdminSudoku;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import packVistaAdminSudoku.VentanaLoginV2;


public class CogerSudokus {
	
	public CogerSudokus(){
		
	}
	
	public static void descargar(String pUrl) throws IOException{
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
	
	public static boolean cogerSudoku(VentanaLoginV2 pVnt) throws IOException {
		boolean completadoConExito = false;
		File directorio = new File(System.getenv("APPDATA") + "\\Sudoku");
		directorio.mkdir();
		String ruta = null;
		
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
				pVnt.actualizarBarra();
				completadoConExito = true;
				
			}
		}
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
}
