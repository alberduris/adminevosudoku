package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import packBD.GestorBD;
import packModelo.Sesion;
import packModelo.Sudoku;
import packModelo.Tablero;

public class VentanaConfigurarSudoku extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JComboBox<String> nivel;
	private JRadioButton conTiempo, sinTiempo;
	private JPanel tiempo;
	private JComboBox<String> seg, min;
	private JButton btnSalirYGuardar, btnAtras;
	
	
	

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(250, 400);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new VentanaConfigurarSudoku();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaConfigurarSudoku() {
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

		getTituloDialogoConfig();
		getNivel();
		getTiempoSelec();
		getbtnJugar();
		getBtnAtras();
		
		setVisible(true);

	}

	private void getTituloDialogoConfig() {
	
		lblTitulo = new JLabel("<html><center>Configuración<br>Sudoku</center></html>");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	private void getNivel() {
		String[] niveles = {"Muy Fácil", "Fácil", "Normal", "Difícil", "Muy Difícil"};
		nivel = new JComboBox<String>(niveles);
		nivel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nivel.setMinimumSize(dimBtn);
		nivel.setPreferredSize(dimBtn);
		nivel.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(nivel);
	}

	private void getTiempoSelec() {
		ButtonGroup grupo = new ButtonGroup();
		sinTiempo = new JRadioButton("Sin Fijar Tiempo");
		sinTiempo.setAlignmentX(Component.CENTER_ALIGNMENT);
		sinTiempo.setMinimumSize(dimBtn);
		sinTiempo.setPreferredSize(dimBtn);
		sinTiempo.setSelected(true);
		sinTiempo.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		sinTiempo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				min.setEnabled(false);
				seg.setEnabled(false);
			}
			
		});

		conTiempo = new JRadioButton("Fijar Tiempo");
		conTiempo.setAlignmentX(Component.CENTER_ALIGNMENT);
		conTiempo.setMinimumSize(dimBtn);
		conTiempo.setPreferredSize(dimBtn);
		conTiempo.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		conTiempo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				min.setEnabled(true);
				seg.setEnabled(true);
			}
			
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		grupo.add(sinTiempo);
		grupo.add(conTiempo);
		panelConBoxLayout.add(sinTiempo);
		panelConBoxLayout.add(conTiempo);
		getTiempo();
	}
	
	private void getTiempo(){
		tiempo = new JPanel();
		String[] posibilidades = new String[60];
		for(int i = 0; i< 60; i++){
			posibilidades[i] = String.valueOf(i);
		}
		seg = new JComboBox(posibilidades);
		seg.setAlignmentX(Component.RIGHT_ALIGNMENT);
		min = new JComboBox(posibilidades);
		min.setAlignmentX(Component.LEFT_ALIGNMENT);
		min.setEnabled(false);
		seg.setEnabled(false);
		tiempo.add(min);
		tiempo.add(seg);		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(tiempo);
	}

	
	private void getbtnJugar() {
		btnSalirYGuardar = new JButton("Jugar");
		btnSalirYGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSalirYGuardar.setMinimumSize(dimBtn);
		btnSalirYGuardar.setPreferredSize(dimBtn);
		btnSalirYGuardar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnSalirYGuardar.setEnabled(true);
		
		btnSalirYGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Tablero tab = Tablero.obtTablero();
				String nombreUs = Sesion.obtSesion().obtNombreUsuario();
				int niv = nivel.getSelectedIndex()+1;
				try{
					ResultSet res = GestorBD.getGestorBD().Select("SELECT Sudoku FROM Sudokus WHERE Nivel="+niv+" AND Identificador NOT IN(SELECT IdSudoku FROM Ranking WHERE NombreUsuario='"+nombreUs+"')ORDER BY RAND() LIMIT 1, 1");
					if(res.next()){
						byte[] b = res.getBytes("Sudoku");
						ByteArrayInputStream byteArray = new ByteArrayInputStream(b);
						ObjectInputStream oos = new ObjectInputStream(byteArray);
						Sudoku sud = (Sudoku) oos.readObject();
						tab.inicializar(sud, null);
						if(conTiempo.isSelected()){
							int segu = seg.getSelectedIndex();
							int minu = (min.getSelectedIndex())*60;
							System.out.println(minu);
							tab.configTiempo(segu+minu);
						}else{
							tab.reiniciar();
						}
						Sesion.obtSesion().borrarTablero();
						tab.pausado(false);
						new VentanaTablero();
						dispose();
					}
				}catch (ClassNotFoundException | SQLException | IOException | LineUnavailableException | UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnSalirYGuardar);
	}
	
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Sesion.obtSesion().obtNombreUsuario().trim().equalsIgnoreCase("Admin")){
						new VentanaMenuPpalAdministrador();
					}else{
						new VentanaMenuPpalUsuario();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				dispose();
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(leftJustify(btnAtras));
		
	}
	
	private Component leftJustify(JButton btn)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(btn);
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
}