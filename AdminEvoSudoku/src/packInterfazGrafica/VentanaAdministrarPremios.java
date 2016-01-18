package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class VentanaAdministrarPremios extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JButton btnAnadirPremio;
	private JButton btnEliminarPremio;
	
	private JButton btnAtras;

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(420, 250);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			new VentanaAdministrarPremios();
	}

	/**
	 * Create the frame.
	 */
	public VentanaAdministrarPremios() {
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
		

		getbtnAnadirPremio();
		getbtnEliminarPremio();
		
		getBtnAtras();
		
		setVisible(true);

	}

	private void getTituloVentanaOpcionesAdmin() {
	
		lblTitulo = new JLabel("Administrador Premios");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}
	
	private void getbtnAnadirPremio() {
		btnAnadirPremio = new JButton("Añadir Premio");
		btnAnadirPremio.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAnadirPremio.setMinimumSize(dimBtn);
		btnAnadirPremio.setPreferredSize(dimBtn);
		btnAnadirPremio.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnAnadirPremio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAnadirPremio();
				dispose();
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnAnadirPremio);
	}

	private void getbtnEliminarPremio() {
		btnEliminarPremio = new JButton("Eliminar premios");
		btnEliminarPremio.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEliminarPremio.setMinimumSize(dimBtn);
		btnEliminarPremio.setPreferredSize(dimBtn);
		btnEliminarPremio.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnEliminarPremio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new VentanaEliminarPremios();
			}				
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnEliminarPremio);
	}
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaOpcionesAdministrador();
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
