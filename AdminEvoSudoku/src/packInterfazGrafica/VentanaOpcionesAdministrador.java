package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class VentanaOpcionesAdministrador extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JButton btnAdministrarSudokus;
	private JButton btnAdministrarPremios;
	
	private JButton btnAtras;

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(420, 250);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaOpcionesAdministrador();
	}

	/**
	 * Create the frame.
	 */
	public VentanaOpcionesAdministrador() {
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

		getTituloVentanaOpcionesAdmin();
		

		getbtnAdministrarSudokus();
		getbtnAdministrarPremios();
		
		getBtnAtras();
		
		setVisible(true);

	}

	private void getTituloVentanaOpcionesAdmin() {
	
		lblTitulo = new JLabel("Opciones de administrador");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}
	
	private void getbtnAdministrarSudokus() {
		btnAdministrarSudokus = new JButton("Administrar Sudokus");
		btnAdministrarSudokus.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAdministrarSudokus.setMinimumSize(dimBtn);
		btnAdministrarSudokus.setPreferredSize(dimBtn);
		btnAdministrarSudokus.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnAdministrarSudokus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAdministrarSudokus();
				dispose();
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnAdministrarSudokus);
	}

	private void getbtnAdministrarPremios() {
		btnAdministrarPremios = new JButton("Administrar premios");
		btnAdministrarPremios.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAdministrarPremios.setMinimumSize(dimBtn);
		btnAdministrarPremios.setPreferredSize(dimBtn);
		btnAdministrarPremios.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnAdministrarPremios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new VentanaAdministrarPremios();
			}				
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnAdministrarPremios);
	}
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaMenuPpalAdministrador();
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