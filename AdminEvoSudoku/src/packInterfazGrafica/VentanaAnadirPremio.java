package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import packModelo.CatalogoSudoku;
import packModelo.GestorAdministrador;

public class VentanaAnadirPremio extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelConBorderLayout;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JComboBox<String> ident;
	private JRadioButton tiempo, puntuacion;
	private JTextField limite, nombre;
	private JButton btnAnadir, btnAtras;
	
	private JDialog dialogFinal;
	

	private Dimension dimBtn = new Dimension(200, 30);
	private Dimension dimVentana = new Dimension(250, 420);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaAnadirPremio();
	}

	/**
	 * Create the frame.
	 */
	public VentanaAnadirPremio() {
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
		getNombre();
		getIdent();
		getTipoSelec();
		getLimite();
		getbtnAnadir();
		getBtnAtras();
		
		setVisible(true);

	}

	private void getTituloDialogoConfig() {
	
		lblTitulo = new JLabel("<html><center>Añadir<br>Premios</center></html>");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		panelConBorderLayout.add(lblTitulo, BorderLayout.NORTH);

	}
	
	private void getNombre(){

		JPanel nm = new JPanel(new GridLayout(1,2));
		
		nombre = new JTextField();
		nombre.setEditable(true);
		nombre.setHorizontalAlignment(JTextField.CENTER);

		nm.add(new JLabel("Nombre : " ));
		nm.add(nombre);
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(nm);
	}

	private void getIdent() {
		JPanel id = new JPanel(new GridLayout(1,2));
		String[] identes = CatalogoSudoku.getCatalogoSudoku().obtListaIdent();
		ident = new JComboBox<String>(identes);
		ident.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		id.add(new JLabel("IdSudoku: " ));
		id.add(ident);
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(id);
	}

	private void getTipoSelec() {
		ButtonGroup grupo = new ButtonGroup();
		puntuacion = new JRadioButton("Puntuación");
		puntuacion.setAlignmentX(Component.CENTER_ALIGNMENT);
		puntuacion.setMinimumSize(dimBtn);
		puntuacion.setPreferredSize(dimBtn);
		puntuacion.setSelected(true);
		puntuacion.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		tiempo = new JRadioButton("Tiempo");
		tiempo.setAlignmentX(Component.CENTER_ALIGNMENT);
		tiempo.setMinimumSize(dimBtn);
		tiempo.setPreferredSize(dimBtn);
		tiempo.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		grupo.add(puntuacion);
		grupo.add(tiempo);
		panelConBoxLayout.add(puntuacion);
		panelConBoxLayout.add(tiempo);
	}
	
	private void getLimite(){
		JPanel lm = new JPanel(new GridLayout(1,2));
		
		limite = new JTextField();
		limite.setEditable(true);
		limite.setHorizontalAlignment(JTextField.CENTER);

		lm.add(new JLabel("Límite: " ));
		lm.add(limite);
		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(lm);
	}

	
	private void getbtnAnadir() {
		btnAnadir = new JButton("Añadir");
		btnAnadir.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAnadir.setMinimumSize(dimBtn);
		btnAnadir.setPreferredSize(dimBtn);
		btnAnadir.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		btnAnadir.setEnabled(true);
		
		btnAnadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int iden = ident.getSelectedIndex()+1;
				boolean error = false;
				int limit = 0;
				boolean excepcion = false;
				if(nombre.getText().trim().isEmpty()){
					getDialogo("Tienes que poner un nombre al premio.");
				}else{
					if(limite.getText().trim().isEmpty()){
						getDialogo("Tienes que poner un valor numérico al límite.");
					}else{
						try{
							limit = Integer.parseInt(limite.getText());
						}
						catch(Exception NumberFormatException){
							getDialogo("Tienes que poner un valor numérico al límite.");
							excepcion = true;
						}
						if(!excepcion){
							if(tiempo.isSelected()){
								error = GestorAdministrador.getGestorAdministrador().anadirPremio(nombre.getText().trim(), iden, limit, "Tiempo");
							}else{
								error = GestorAdministrador.getGestorAdministrador().anadirPremio(nombre.getText(), iden, limit, "Puntuación");
							}
							if(error){
								getDialogo("<html>El nombre del premio ya está utilizado<br>por otro premio</html>");
							}else{
								getDialogo("Premio añadido correctamente");
							}
						}
					}
				}			
			}
		});

		panelConBoxLayout.add(Box.createRigidArea(new Dimension(0, 15)));
		panelConBoxLayout.add(btnAnadir);
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
	
	private void getDialogo(String pTexto){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel txt = new JLabel(pTexto);
		JButton boton = new JButton("Aceptar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialogFinal.dispose();
			}
			
		});
		
		dialogFinal = new JDialog();
		dialogFinal.setSize(300,125);
		dialogFinal.setModal(false);
		dialogFinal.setVisible(true);
		dialogFinal.setLocationRelativeTo(this);
		dialogFinal.setTitle("AVISO");
		
		dialogFinal.setLayout(new GridBagLayout());
	
		dialogFinal.add(txt,csTexto);
		csTexto.gridy = 1;
		dialogFinal.add(boton,csBoton);
	}
	
	private Component leftJustify(JButton btn)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(btn);
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
}
