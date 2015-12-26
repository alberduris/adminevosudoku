//Forzar commit 2

package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import packBD.GestorBD;
import packModelo.Sesion;
import packModelo.Tablero;

public class VentanaMenuPpalUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JButton btnJugar;
	private JButton btnPremios;
	private JButton btnEstadisticas;
	private JButton btnRanking;
	private JButton btnOpciones;
	private JButton btnCerrarSesion;

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(350, 380);

	private Tablero tab = Tablero.obtTablero();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaMenuPpalUsuario();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaMenuPpalUsuario() {
		tab = Tablero.obtTablero();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(dimVentana);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		setContentPane(contentPane);
		panelConBorderLayout = new JPanel();
		panelConBoxLayout = new JPanel();

		BorderLayout border = new BorderLayout();
		panelConBorderLayout.setLayout(border);
		BoxLayout box = new BoxLayout(panelConBoxLayout, BoxLayout.Y_AXIS);
		panelConBoxLayout.setLayout(box);

		contentPane.add(panelConBorderLayout);
		panelConBorderLayout.add(panelConBoxLayout, BorderLayout.CENTER);

		getTituloMenuPpalUsuario();
		

		getbtnJugar();
		getbtnPremios();
		getbtnEstadisticas();
		getbtnRanking();
		getbtnOpciones();
		getbtnCerrarSesion();
		setVisible(true);

	}

	private void getTituloMenuPpalUsuario() {
	
		lblTitulo = new JLabel("Men� principal");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	
	

	

	private void getbtnJugar() {
		btnJugar = new JButton("Jugar");
		btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnJugar.setMinimumSize(dimBtn);
		btnJugar.setPreferredSize(dimBtn);
		btnJugar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnJugar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nombreUs = Sesion.obtSesion().obtNombreUsuario();
				try{
					ResultSet res = GestorBD.getGestorBD().Select("SELECT Tablero FROM Jugadores WHERE NombreUsuario='"+nombreUs+"'");
					if(res.next()){
						byte[] b = res.getBytes("Tablero");
						if(b==null){
							new VentanaConfigurarSudoku();
							dispose();
						}else{
							ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
							ObjectInputStream oos = new ObjectInputStream(byteArray);
							tab = (Tablero) oos.readObject();
							getDialogContinuar();
						}
					}
				}catch (ClassNotFoundException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnJugar);
	}

	private void getbtnPremios() {
		btnPremios = new JButton("Premios");
		btnPremios.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPremios.setMinimumSize(dimBtn);
		btnPremios.setPreferredSize(dimBtn);
		btnPremios.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnPremios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Premios");
				
			}
			
			
				
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnPremios);
	}

	
	private void getbtnEstadisticas() {
		btnEstadisticas = new JButton("Estadisticas");
		btnEstadisticas.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEstadisticas.setMinimumSize(dimBtn);
		btnEstadisticas.setPreferredSize(dimBtn);
		btnEstadisticas.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnEstadisticas.setEnabled(true);
		
		btnEstadisticas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Estadisticas");
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnEstadisticas);
	}
	
	private void getbtnRanking() {
		btnRanking = new JButton("Ranking");
		btnRanking.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRanking.setMinimumSize(dimBtn);
		btnRanking.setPreferredSize(dimBtn);
		btnRanking.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnRanking.setEnabled(true);
		
		btnRanking.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Ranking");
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnRanking);
	}
	
	private void getbtnOpciones() {
		btnOpciones = new JButton("Opciones");
		btnOpciones.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOpciones.setMinimumSize(dimBtn);
		btnOpciones.setPreferredSize(dimBtn);
		btnOpciones.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnOpciones.setEnabled(true);
		
		btnOpciones.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Opciones");
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnOpciones);
	}
	
	private void getbtnCerrarSesion() {
		btnCerrarSesion = new JButton("Cerrar Sesion");
		btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCerrarSesion.setMinimumSize(dimBtn);
		btnCerrarSesion.setPreferredSize(dimBtn);
		btnCerrarSesion.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnCerrarSesion.setEnabled(true);
		
		btnCerrarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Sesion.obtSesion().finSesion(false);
					new VentanaStart();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnCerrarSesion);
	}
	
	private void getDialogContinuar(){
		final JDialog dialogContinuar = new JDialog();
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel texto = new JLabel("�Quiere continuar el Sudoku guardado?");
		JButton botonSi = new JButton("S�");
		
		botonSi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				Tablero.obtTablero().establecerTablero(tab);
				new VentanaTablero();
				dialogContinuar.dispose();
				dispose();
			}
			
			
		});
		
		JButton botonNo = new JButton("No");
		
		botonNo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new VentanaConfigurarSudoku();
				dialogContinuar.dispose();
				dispose();
			}
			
			
		});
		
		dialogContinuar.setSize(300,125);
		dialogContinuar.setModal(false);
		dialogContinuar.setVisible(true);
		dialogContinuar.setTitle("Continuar");
		dialogContinuar.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialogContinuar.setLocationRelativeTo(this);		
		dialogContinuar.setLayout(new GridBagLayout());
		dialogContinuar.getContentPane().setBackground(new Color(0xFFFFFF));
		
		botonSi.setAlignmentX(Component.LEFT_ALIGNMENT);
		botonNo.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JPanel respuesta = new JPanel();
		respuesta.add(botonSi,csBoton);
		respuesta.add(botonNo,csBoton);
		
		dialogContinuar.add(texto,csTexto);
		dialogContinuar.add(respuesta, csBoton);	

	}
	

}