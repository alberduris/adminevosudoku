package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import packModelo.GestorEstadisticas;
import packModelo.Sesion;

public class VentanaHistorial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel norte, centro, sur;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	

	private JPanel lista;
	private JPanel[] combinaciones;
	private JComboBox<String> tipos, usuarios;
	
	private JButton btnAtras;

	GestorEstadisticas gE = GestorEstadisticas.obtGestorEstadisticas();
	
	private Dimension dimVentana = new Dimension(490, 500);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaHistorial();
	}

	/**
	 * Create the frame.
	 */

	public VentanaHistorial() {
		gE = GestorEstadisticas.obtGestorEstadisticas();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(dimVentana);
		setLocationRelativeTo(null);

		norte = new JPanel();
		add(norte, BorderLayout.NORTH);
		centro = new JPanel();
		centro.setLayout(new BorderLayout());
		add(centro, BorderLayout.CENTER);
		sur = new JPanel();
		add(sur, BorderLayout.SOUTH);
		
		panelConBoxLayout = new JPanel();

		BoxLayout box = new BoxLayout(panelConBoxLayout, BoxLayout.Y_AXIS);
		panelConBoxLayout.setLayout(box);

		getTituloVentanaTitulo();
		
		crearNorte(Sesion.obtSesion().obtNombreUsuario(), 0);
		
		getSudokus();
			
		getBtnAtras();
		
		setVisible(true);
	}

	private void getTituloVentanaTitulo() {
	
		lblTitulo = new JLabel("Estadísticas");
		lblTitulo.setHorizontalAlignment(0);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 23));
		lblTitulo.setOpaque(true);
		lblTitulo.setForeground(Color.black);
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border border = BorderFactory.createBevelBorder(0);
		lblTitulo.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

		norte.add(lblTitulo);

	}
	
	private void crearNorte(String pUs, int pTipo){	
		tipos = null;
		String[] valTipos = {"Sudokus", "Retos", "Premios"};
		tipos = new JComboBox<String>(valTipos);
		tipos.setSelectedIndex(pTipo);
		tipos.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centro.removeAll();
				if(tipos.getSelectedItem() == "Sudokus"){
					getSudokus();
				}else if(tipos.getSelectedItem() == "Retos"){
					getRetos();
				}else{
					getPremios();
				}
				centro.updateUI();
				crearNorte((String)usuarios.getSelectedItem(), tipos.getSelectedIndex());
			}
			
		});
		usuarios = null;
		String[] valUsus = gE.obtJugadores();
		usuarios = new JComboBox<String>(valUsus);
		usuarios.setSelectedItem(pUs);
		usuarios.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centro.removeAll();
				if(tipos.getSelectedItem() == "Sudokus"){
					getSudokus();
				}else if(tipos.getSelectedItem() == "Retos"){
					getRetos();
				}else{
					getPremios();
				}
				centro.updateUI();
				crearNorte((String)usuarios.getSelectedItem(), tipos.getSelectedIndex());

			}
			
		});
		
		JPanel centroNorte = new JPanel(new GridLayout(1,4));
		JLabel est = new JLabel("Mostrar: ");
		est.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		JLabel est1 = new JLabel("Usuario: ");
		est1.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		centroNorte.add(est1);
		centroNorte.add(usuarios);
		centroNorte.add(est);
		centroNorte.add(tipos);
		centro.add(centroNorte, BorderLayout.NORTH);
	}
	
	private void getSudokus(){
		String[][] com = new String[0][0];
		com = gE.obtPuntSudokusJugados((String)usuarios.getSelectedItem());
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		if(com.length == 0){		
			JLabel txt = new JLabel("<html><big>No Existe Sudokus jugados por "+(String)usuarios.getSelectedItem()+"</big></html>");
			txt.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
			centro.add(txt);
			}else{
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 3));
				combinaciones[i].setBackground(Color.WHITE);
				JLabel l1, l2;
				if(i==0){
					l1 = new JLabel("<html><font SIZE=4><u>IdSudoku</u></font></html>");
					l2 = new JLabel("<html><font SIZE=4><u>Puntuación</u></font></html>");
				}else{
					l1 = new JLabel(com[i-1][0]);
					l2 = new JLabel(com[i-1][1]);
				}
				l1.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
				l2.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
				combinaciones[i].add(l1);
				combinaciones[i].add(l2);
				lista.add(combinaciones[i]);
				JScrollPane scroll = new JScrollPane(lista);
				centro.add(scroll, BorderLayout.CENTER);
				
				centro.add(Box.createRigidArea(new Dimension(0, 15)));
				centro.add(scroll);
			}			
		}
	}
	
	
	private void getRetos(){
		String[][] com = gE.obtenerRetosFinalizados();
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		if(com.length == 0){				
			JLabel txt = new JLabel("<html><big>No Existe Retos finalizados</big></html>");
			txt.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
			centro.add(txt);
		}else{
			System.out.println(com.length);
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 5));
				if(i==0){
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Usuario</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>IdSudoku</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Puntuación</u></font></html>"));
					combinaciones[i].add(new JLabel("<html><font SIZE=4><u>Estado</u></font></html>"));
				}else{
					combinaciones[i].add(new JLabel(com[i-1][0]));
					combinaciones[i].add(new JLabel(com[i-1][1]));
					combinaciones[i].add(new JLabel(com[i-1][2]));
					if(com[i-1][3] == "true"){
						combinaciones[i].add(new JLabel("SUPERADO"));
					}else{
						combinaciones[i].add(new JLabel("NO SUPERADO"));
					}
				}
				lista.add(combinaciones[i]);
			}
			JScrollPane scroll = new JScrollPane(lista);
			centro.setLayout(new BorderLayout());
			centro.add(scroll, BorderLayout.CENTER);
			
			centro.add(Box.createRigidArea(new Dimension(0, 15)));
			centro.add(scroll);
		}
	}
	
	private void getPremios(){
		String[][] com = gE.obtInfoPremios((String)usuarios.getSelectedItem());
		lista = new JPanel(new GridLayout(com.length+1, 1));
		lista.setAutoscrolls(false);
		if(com.length == 0){				
			JLabel txt = new JLabel("<html><big>Este usuario no ha obtenido ningún premio</big></html>");
			txt.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
			centro.add(txt);
		}else{
			combinaciones = new JPanel[com.length+1];
			for(int i = 0; i<com.length+1; i++){
				combinaciones[i] = new JPanel(new GridLayout(1, 3));
				combinaciones[i].setBackground(Color.WHITE);
				JLabel nom, obj, fech;
				if(i==0){
					nom = new JLabel("<html><font SIZE=4><u>Premio</u></font></html>");
					obj = new JLabel("<html><font SIZE=4><u>Objetivo</u></font></html>");
					fech = new JLabel("<html><font SIZE=4><u>Fecha</u></font></html>");
				}else{					
					nom = new JLabel("<html>"+com[i-1][0]+"</html>");
					if(com[i-1][4].equals("Tiempo")){
						obj = new JLabel("<html>Se ha completado el<br>sudoku "+com[i-1][3]+" en un<br>tiempo menor a "+com[i-1][2]+"</html>");
					}else{
						obj = new JLabel("<html>Se ha completado el<br> sudoku "+com[i-1][3]+" con una<br>puntuación mayor a "+com[i-1][2]+"</html>");
					}
					fech = new JLabel("<html>"+com[i-1][1]+"</html>");
				}				
				nom.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
				obj.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
				fech.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
				combinaciones[i].add(nom);
				combinaciones[i].add(obj);
				combinaciones[i].add(fech);
				lista.add(combinaciones[i]);
			}
			JScrollPane scroll = new JScrollPane(lista);
			centro.setLayout(new BorderLayout());
			centro.add(scroll, BorderLayout.CENTER);
			
			centro.add(Box.createRigidArea(new Dimension(0, 15)));
			centro.add(scroll);
		}
	}	

	private void getBtnAtras() {
		btnAtras = new JButton("Atrás");
		btnAtras.setEnabled(true);
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaMenuPpalAdministrador();
				dispose();
				
			}
		});
		JPanel panelBox = new JPanel();
		
		panelBox.setLayout(new BoxLayout(panelBox, BoxLayout.Y_AXIS));
		panelBox.add(Box.createRigidArea(new Dimension(5, 5)));
		panelBox.add(leftJustify(btnAtras));
		sur.add(panelBox);
		
	}
	
	
	private Component leftJustify(JButton btn)  {
	    Box  b = Box.createHorizontalBox();
	    b.add(btn);
	    b.add( Box.createHorizontalGlue() );
	  
	    return b;
	}
}
