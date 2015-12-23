package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import packAdminSudoku.CatalogoJugadores;
import packAdminSudoku.Jugador;

public class VentanaRanking extends JFrame {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JPanel contentPane1;
	private JPanel contentPane2;
	private JPanel contentPane3;
	
	
	private JLabel lblTituloRanking;
	private JLabel lblRanking;
	private JButton btnAtras;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaRanking();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaRanking() {
		super();
		
		
		
		//Opciones JFrame base, su ContentPane y su layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,350);
		setLocationRelativeTo(null);
		
		BorderLayout bl1 = new BorderLayout();
		getContentPane().setLayout(bl1);
		
		//Añadir JPanels principales
		
		FlowLayout fl1 = new FlowLayout(1);
		contentPane1 = new JPanel(fl1);
		fl1.setVgap(10);
	
		contentPane2 = new JPanel(new FlowLayout());
		
		
		FlowLayout fl3 = new FlowLayout(1);
		fl3.setVgap(12);
		fl3.setHgap(15);
		contentPane3 = new JPanel(fl3);
		
	
		getContentPane().add(contentPane1, BorderLayout.NORTH);
		contentPane1.setBackground(Color.GRAY);
		contentPane1.setPreferredSize(new Dimension(400,50));

		
		getContentPane().add(contentPane2, BorderLayout.CENTER);
		contentPane2.setBackground(Color.WHITE);
		
		getContentPane().add(contentPane3, BorderLayout.SOUTH);
		contentPane3.setBackground(Color.BLACK);
		contentPane3.setPreferredSize(new Dimension(400,50));
		
		
		//Añadir resto elementos
		
		getLblTituloRanking();
		getLblRanking();
		
		getBtnAtras();
		
		setVisible(true);
							
	}
	
	private JLabel getLblTituloRanking(){
		Font fuente1 = new Font("Arial",Font.BOLD,24);
		
		if(lblTituloRanking == null){
			lblTituloRanking = new JLabel();
			lblTituloRanking.setText("Ranking");
			lblTituloRanking.setFont(fuente1);
			lblTituloRanking.setForeground(Color.WHITE);
			
			contentPane1.add(lblTituloRanking);
			
		}
		return lblTituloRanking;
	}
	
	private JLabel getLblRanking(){
		
		if(lblRanking == null){
			lblRanking = new JLabel();
			lblRanking.setHorizontalAlignment(SwingConstants.CENTER);
			
			String textLblRanking = "Ranking"; //TODO pasar el ranking

			
			lblRanking.setText(textLblRanking);
			lblRanking.setBackground(Color.WHITE);
			
			contentPane2.add(lblRanking);
			
		}
		return lblRanking;
	}
	
	private JButton getBtnAtras(){
				
		if(btnAtras == null){
			btnAtras = new JButton();
			btnAtras.setText("Atras");
			contentPane3.add(btnAtras);
			
			btnAtras.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					VentanaMenuPpalUsuario frame;
					frame = new VentanaMenuPpalUsuario();
					frame.setVisible(true);
					dispose();
				}				
			});			
		}
		return btnAtras;
	}
	
	
	
		
}
