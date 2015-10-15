package packAdminSudoku;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import packVistaAdminSudoku.VentanaLoginV2;
import packVistaAdminSudoku.VentanaStart;


public class NoHaySudokuCargadoException extends Exception {
	
	/**
	 * 
	 */
	private static JPanel contentPane;
	private static JDialog dialogError;
	private static Clip sonido;
	private static final long serialVersionUID = 1L;

	public NoHaySudokuCargadoException(){
		super();
	}

	public boolean lanzarExcepcion(){
		VentanaLoginV2 vnt;
		
		try {
			
			vnt = new VentanaLoginV2(sonido);
			vnt.setVisible(true);
			getDialogErrorNombre();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	private void getDialogErrorNombre(){
		
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto = new JLabel("No hay sudokus disponibles de ese nivel");
		JButton boton = new JButton("Cerrar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialogError.dispose();
			}

		
			
		});
		
		dialogError = new JDialog();
		dialogError.setSize(300,125);
		dialogError.setModal(false);
		dialogError.setVisible(true);
		dialogError.setLocationRelativeTo(contentPane);
		dialogError.setTitle("Sudoku no disponible");
		
		
		dialogError.setLayout(new GridBagLayout());
		dialogError.getContentPane().setBackground(new Color(0xFFFFFF));
	

		dialogError.add(texto,csTexto);
		dialogError.add(boton,csBoton);
		
		
	}

}
