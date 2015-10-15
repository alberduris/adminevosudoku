package packVistaAdminSudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	private JButton btnAtras, btnReplay, btnCambiarLvl;
	
	private static Jugador jug;
	private static int lvl;
	
	private JDialog dialog;

	/**
	 * Create the frame.
	 */
	public VentanaRanking(Jugador pJug, int pLvl) {
		super();
		
		lvl = pLvl;
		jug = pJug;
		
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
		getBtnCambiarLvl();
		getBtnReplay();	
		getBtnAtras();
							
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
			
			String textLblRanking = CatalogoJugadores.getCatalogoJugadores().rankingHTML();

			
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
					VentanaLoginV2 frame;
					try {
						frame = new VentanaLoginV2(null);
						frame.setVisible(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				}				
			});			
		}
		return btnAtras;
	}
	private JButton getBtnReplay(){
		
		if(btnReplay == null){
			btnReplay = new JButton();
			btnReplay.setText("Jugar de Nuevo");
			contentPane3.add(btnReplay);
			
			btnReplay.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					jug.lanzarSudoku(lvl, null);
					VentanaSudoku vent;
					try {
						vent = new VentanaSudoku();
						vent.setVisible(true);
					} catch (LineUnavailableException | IOException
							| UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				}				
			});			
		}
		return btnReplay;
	}
	private JButton getBtnCambiarLvl(){
		
		if(btnCambiarLvl == null){
			btnCambiarLvl = new JButton();
			btnCambiarLvl.setText("Cambiar Nivel");
			contentPane3.add(btnCambiarLvl);
			
			btnCambiarLvl.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					getDialogCambiarLvl();
					dispose();
				}				
			});			
		}
		return btnCambiarLvl;
	}
	
	private void getDialogCambiarLvl(){

		GridBagConstraints csText = new GridBagConstraints();
		GridBagConstraints cs = new GridBagConstraints();
		JButton[] btnLvl = new JButton[5];

		csText.gridwidth = 4;
		csText.gridx = 0;
		csText.gridy = 0;
		
		JLabel lvlMensage = new JLabel("Seleccione Nivel");
		JLabel EspacioEnBlanco = new JLabel(" ");
		JLabel EspacioEnBlanco2 = new JLabel(" ");
		dialog = new JDialog();

		dialog.setSize(250,175);
		dialog.setModal(false);
		dialog.setVisible(true);
		dialog.setLocationRelativeTo(contentPane2);
		dialog.setTitle("Seleccion de Nivel");
		dialog.setLayout(new GridBagLayout());
		dialog.getContentPane().setBackground(new Color(0xFFFFFF));
		dialog.add(lvlMensage,csText);

		csText.gridx = 0;
		csText.gridy = 1;
		dialog.add(EspacioEnBlanco,csText);
		csText.gridx = 0;
		csText.gridy = 5;
		dialog.add(EspacioEnBlanco2,csText);
		
		for(int i=0; i<5; i++){
			if(i==0){
				cs.gridx = 0;
				cs.gridy = 2;
			}else if(i==1){
				cs.gridx = 3;
				cs.gridy = 2;
			}else if(i==2){
				cs.gridx = 2;
				cs.gridy = 3;
			}else if(i==3){
				cs.gridx = 0;
				cs.gridy = 4;
			}else if(i==4){
				cs.gridx = 3;
				cs.gridy = 4;
			}
			final int num = i+1;
			btnLvl[i] = new JButton();
			btnLvl[i].setText(Integer.toString(num));
			btnLvl[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					jug.lanzarSudoku(num, null);
					VentanaSudoku vent;
					try {
						vent = new VentanaSudoku();
						vent.setVisible(true);
					} catch (LineUnavailableException | IOException
							| UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dialog.dispose();
				}				
			});	
			dialog.add(btnLvl[i], cs);
		}				
	}	
}
