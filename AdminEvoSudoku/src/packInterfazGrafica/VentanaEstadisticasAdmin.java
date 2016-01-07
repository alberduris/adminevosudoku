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
import javax.swing.border.Border;

import packModelo.GestorEstadisticas;
import packModelo.Sesion;

public class VentanaEstadisticasAdmin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel norte, centro, sur;
	private JPanel panelConBoxLayout;

	private JLabel lblTitulo;
	
	private JPanel centroSuperior, centroInferior;
	private JComboBox<String> sudokus, usuarios;
	
	private JButton btnAtras;

	GestorEstadisticas gE = GestorEstadisticas.obtGestorEstadisticas();
	
	private Dimension dimVentana = new Dimension(350, 500);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new VentanaEstadisticasAdmin();
	}

	/**
	 * Create the frame.
	 */

	public VentanaEstadisticasAdmin() {
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
		
		crearNorte();
		
		getCentro(); 
		centro.add(panelConBoxLayout, BorderLayout.CENTER);
		
			
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
	
	private void crearNorte(){	
		sudokus = null;
		String[] valsudokus = gE.obtSudokusJugados();
		sudokus = new JComboBox<String>(valsudokus);
		sudokus.setSelectedIndex(0);
		sudokus.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centroSuperior.removeAll();
				centroInferior.removeAll();
				getCentro();
				panelConBoxLayout.updateUI();
			}
			
		});
		usuarios = null;
		String[] valUsus = gE.obtJugadores();
		usuarios = new JComboBox<String>(valUsus);
		usuarios.setSelectedItem("Admin");
		usuarios.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				centroSuperior.removeAll();
				centroInferior.removeAll();
				getCentro();
				panelConBoxLayout.updateUI();
			}
			
		});
		
		JPanel centroNorte = new JPanel(new GridLayout(1,4));
		JLabel est = new JLabel("Sudoku: ");
		est.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		JLabel est1 = new JLabel("Usuario: ");
		est1.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		centroNorte.add(est1);
		centroNorte.add(usuarios);
		centroNorte.add(est);
		centroNorte.add(sudokus);
		centro.add(centroNorte, BorderLayout.NORTH);
	}
	
	private void getCentro(){
		getEstadisticasSudoku();
		getEstadisticasUsuario();
		panelConBoxLayout.add(centroSuperior);
		panelConBoxLayout.add(centroInferior);
	}
		
	private void getEstadisticasSudoku(){
		centroSuperior = new JPanel();
		centroSuperior.setLayout(new GridLayout(4,1));
		String[] info = new String[3];
		info = gE.obtInfoSudoku(Integer.valueOf((String) sudokus.getSelectedItem()));
		JLabel titulo, jugados, porcentaje, tiempoMedio;
		titulo = new JLabel("Estadísticas Sudoku");
		titulo.setFont(new Font("Arial", Font.BOLD, 14));
		titulo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0),BorderFactory.createEmptyBorder(5,5,5,5)));
		jugados = new JLabel("<html>Nº Jugadores jugado: "+info[0]+"</html>");
		porcentaje = new JLabel("<html>Porcentaje Partidas acabadas: "+info[1]+"%</html>");
		tiempoMedio = new JLabel("<html>Tiempo Medio de Resolución: "+info[2]+"</html>");
		titulo.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		jugados.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		porcentaje.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		tiempoMedio.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		centroSuperior.add(titulo);
		centroSuperior.add(jugados);
		centroSuperior.add(porcentaje);
		centroSuperior.add(tiempoMedio);
	}
	
	private void getEstadisticasUsuario(){
		centroInferior = new JPanel();
		centroInferior.setLayout(new GridLayout(7,1));
		String[] info = new String[3];
		info = gE.obtInfoUsuario((String) usuarios.getSelectedItem());
		JLabel titulo, total, porcentaje, tiempoMedio;
		titulo = new JLabel("Estadísticas Usuario");
		titulo.setFont(new Font("Arial", Font.BOLD, 14));
		titulo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(0),BorderFactory.createEmptyBorder(5,5,5,5)));
		total = new JLabel("<html>Nº Sudokus completados: "+info[0]+"</html>");
		porcentaje = new JLabel("<html>Porcentaje Partidas acabadas: "+info[1]+"%</html>");
		tiempoMedio = new JLabel("<html>Tiempo Medio de Resolución: </html>");
		titulo.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		total.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		porcentaje.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		tiempoMedio.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		JPanel p1, p2, p3;
		p1 = new JPanel(new GridLayout(1,2));
		p2 = new JPanel();
		p3 = new JPanel(new GridLayout(1,2));
		JLabel muyfacil, facil, medio, dificil, muydificil;
		muyfacil = new JLabel("<html>Muy Fácil: "+info[2]+"</html>");
		facil = new JLabel("<html>Fácil: "+info[3]+"</html>");
		medio = new JLabel("<html>Medio: "+info[4]+"</html>");
		dificil = new JLabel("<html>Difícil: "+info[5]+"</html>");
		muydificil = new JLabel("<html>Muy Difícil: "+info[6]+"</html>");
		muyfacil.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		facil.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		medio.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		dificil.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		muydificil.setHorizontalAlignment((int) Component.CENTER_ALIGNMENT);
		p1.add(muyfacil); p1.add(facil);
		p2.add(medio);
		p3.add(dificil); p3.add(muydificil);
		centroInferior.add(titulo);
		centroInferior.add(total);
		centroInferior.add(porcentaje);
		centroInferior.add(tiempoMedio);
		centroInferior.add(p1);
		centroInferior.add(p2);
		centroInferior.add(p3);
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
