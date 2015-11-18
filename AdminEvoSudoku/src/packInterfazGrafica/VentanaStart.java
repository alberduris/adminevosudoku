package packInterfazGrafica;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaStart extends JFrame {


	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JButton btnLogin;
	private JButton btnRegistro;
	
	private Dimension dimBoton = new Dimension(150,30);
	
	//private static Clip sonido;
	
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 */
	public VentanaStart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
	
		
		//Opciones JFrame base, su ContentPane y su layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		crearContentPane();
		setContentPane(contentPane);
	
		/*
		sonido = AudioSystem.getClip();
		Random rd = new Random();
		if(rd.nextInt(2) == 0){
			sonido.open(AudioSystem.getAudioInputStream(getClass().getResource("epico.wav")));
		}else{
			sonido.open(AudioSystem.getAudioInputStream(getClass().getResource("rancho.wav")));
		}
		sonido.start();
		*/
		
		
		//Anadir resto elementos
		getBtnLogin();
		getBtnRegistro();
		
	}
	

	
	private void crearContentPane() throws IOException {
		contentPane = new JPanel(){
			
		private static final long serialVersionUID = 1L;
			BufferedImage img = ImageIO.read(this.getClass().getResource("ad2.jpg"));
			public void paintComponent(Graphics g){
	            super.paintComponent(g);
	            g.drawImage(img, 0, 0, 500, 500, this);
	        }
		};	
		
		FlowLayout fl1 = new FlowLayout(1);
		contentPane.setLayout(fl1);
		fl1.setVgap(425);
		
	}

	private void getBtnLogin(){
		if(btnLogin == null){
			btnLogin = new JButton("Login");
			btnLogin.setPreferredSize(dimBoton);
			contentPane.add(btnLogin);
			
			btnLogin.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0){
					
					VentanaLogin frame = new VentanaLogin();
					frame.setVisible(true);
					dispose();	
				}
			});			
		}
	
	}
	
	private void getBtnRegistro(){
		if(btnRegistro == null){
			btnRegistro = new JButton("Registro");
			btnRegistro.setPreferredSize(dimBoton);
			contentPane.add(btnRegistro);
			
			btnRegistro.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0){
					
					VentanaRegistro frame = new VentanaRegistro();
					frame.setVisible(true);
					dispose();	
				}
			});			
		}
	
	}
}

