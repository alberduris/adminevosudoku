//Forzar commit 2

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

public class VentanaAdministrarSudokus extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JButton btnAnadir;
	private JButton btnBorrar;
	private JButton btnModificar;
	private JButton btnActivar;
	private JButton btnDesactivar;
	private JButton btnAtras;

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(350, 380);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdministrarSudokus frame = new VentanaAdministrarSudokus();
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
	public VentanaAdministrarSudokus() {
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
		

		getbtnAnadir();
		getbtnBorrar();
		getbtnModificar();
		getbtnActivar();
		getbtnDesactivar();
		getBtnAtras();
		

	}

	private void getTituloMenuPpalUsuario() {
	
		lblTitulo = new JLabel("Administrar Sudokus");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}

	
	

	

	private void getbtnAnadir() {
		btnAnadir = new JButton("Añadir");
		btnAnadir.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAnadir.setMinimumSize(dimBtn);
		btnAnadir.setPreferredSize(dimBtn);
		btnAnadir.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnAnadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Jugar");
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnAnadir);
	}

	private void getbtnBorrar() {
		btnBorrar = new JButton("Borrar");
		btnBorrar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBorrar.setMinimumSize(dimBtn);
		btnBorrar.setPreferredSize(dimBtn);
		btnBorrar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		btnBorrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Premios");
				
			}
			
			
				
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnBorrar);
	}

	
	private void getbtnModificar() {
		btnModificar = new JButton("Modificar");
		btnModificar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnModificar.setMinimumSize(dimBtn);
		btnModificar.setPreferredSize(dimBtn);
		btnModificar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnModificar.setEnabled(true);
		
		btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Estadisticas");
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnModificar);
	}
	
	private void getbtnActivar() {
		btnActivar = new JButton("Activar");
		btnActivar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnActivar.setMinimumSize(dimBtn);
		btnActivar.setPreferredSize(dimBtn);
		btnActivar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnActivar.setEnabled(true);
		
		btnActivar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Ranking");
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnActivar);
	}
	
	private void getbtnDesactivar() {
		btnDesactivar = new JButton("Desactivar");
		btnDesactivar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDesactivar.setMinimumSize(dimBtn);
		btnDesactivar.setPreferredSize(dimBtn);
		btnDesactivar.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnDesactivar.setEnabled(true);
		
		btnDesactivar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Has pulsado Opciones");
				
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnDesactivar);
	}
	
	private void getBtnAtras() {
		btnAtras = new JButton("<");
		btnAtras.setEnabled(true);
		btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					VentanaStart frame = new VentanaStart();
					frame.setVisible(true);
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