package packPrincipal;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import packInterfazGrafica.VentanaStart;

public class PrincipalVistaSudoku {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		
		new VentanaStart();

	}

}
