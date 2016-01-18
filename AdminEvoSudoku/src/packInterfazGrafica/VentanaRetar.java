package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import packModelo.Sesion;
import packModelo.Tablero;

public class VentanaRetar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JComboBox<String> comboBoxJugadores;
	private JButton btnRetar;
	
	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(250, 175);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaRetar();
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
		
		setVisible(true);

	}


	

	private void getLblTitulo() {
		
		lblTitulo = new JLabel("Retar");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	
	private void getComboBoxJugadores() {
		comboBoxJugadores = new JComboBox<String>(Sesion.obtSesion().obtenerJugadores());
				
		comboBoxJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboBoxJugadores.setMinimumSize(dimBtn);
		comboBoxJugadores.setPreferredSize(dimBtn);
		comboBoxJugadores.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(comboBoxJugadores);
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
				Tablero.obtTablero().retar((String) comboBoxJugadores.getSelectedItem());
			}
		});		

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnRetar);
	}

	

	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	

}