package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import packBD.GestorBD;

public class VentanaRetar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JLabel lblRetar;
	private JComboBox<String> comboBoxJugadores;
	private JButton btnRetar;
	
	
	
	

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(250, 175);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRetar frame = new VentanaRetar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaRetar() {
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

		getLblTitulo();
		getComboBoxJugadores();
		getBtnRetar();
		
		

	}


	

	private void getLblTitulo() {
		
		lblTitulo = new JLabel("Retar");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		/*
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
		 */
		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	
	private void getComboBoxJugadores() {
		comboBoxJugadores = new JComboBox(getJugadores());
		
		
		
		comboBoxJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboBoxJugadores.setMinimumSize(dimBtn);
		comboBoxJugadores.setPreferredSize(dimBtn);
		comboBoxJugadores.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(comboBoxJugadores);
	}
	
	private String[] getJugadores(){
		ResultSet count = GestorBD.getGestorBD().Select("SELECT COUNT(NombreUsuario) FROM Jugadores");
		
		
		String[] aux = null;
		
		//Conseguir el num de jugadores
		try {
			
			if(count.next()){
				System.out.println((int) count.getLong(1));
				aux = new String[(int) count.getLong(1)];
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet resultado = GestorBD.getGestorBD().Select("SELECT NombreUsuario FROM Jugadores");
		int i = 0;
		
		//Conseguir los jugadores
		
		try {
			while(resultado.next()){
				aux[i] = resultado.getString("NombreUsuario");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return aux;
	}
	
	private void getBtnRetar() {
		btnRetar = new JButton("Retar");
		btnRetar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRetar.setMinimumSize(dimBtn);
		btnRetar.setPreferredSize(dimBtn);
		btnRetar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnRetar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(contentPane, "Has lanzado un reto!");
				
			}
		});
		

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnRetar);
	}

	

	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	

}