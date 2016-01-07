package packInterfazGrafica;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	public VentanaStart(){
	
		
		//Opciones JFrame base, su ContentPane y su layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		crearContentPane();
		setContentPane(contentPane);
		
		//Anadir resto elementos
		getBtnLogin();
		getBtnRegistro();
		
		setVisible(true);
	}
	

	
	private void crearContentPane(){
		try {
			contentPane = new JPanel(){
				
			private static final long serialVersionUID = 1L;
				BufferedImage img = ImageIO.read(this.getClass().getResource("ad2.jpg"));
				public void paintComponent(Graphics g){
			        super.paintComponent(g);
			        g.drawImage(img, 0, 0, 500, 500, this);
			    }
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
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
					
					new VentanaLogin();
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
					
					new VentanaRegistro();
					dispose();	
				}
			});			
		}
	
	}
}

