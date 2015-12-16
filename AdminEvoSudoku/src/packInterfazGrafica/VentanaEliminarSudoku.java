package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.synth.SynthScrollBarUI;

import packExcepciones.NoHaySudokuCargadoException;
import packModelo.CatalogoSudoku;
import packModelo.GestorAdministrador;

public class VentanaEliminarSudoku extends JDialog implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static VentanaTablero ventana;
	JLabel[][] cajas;
	JPanel[][] paneles;
	JLabel dificultad, identificador;
	JPanel centro, sur;
	JPanel norte;
	JButton btn1,btn2;
	JComboBox<String> ident, dif;
	JButton[] listBotones;
	
	int filaColumna = 0;
	
	//boolean flag;
	static final int MAX = 9;
	int[] activado;
	GestorAdministrador gA = GestorAdministrador.getGestorAdministrador();

	/**
	 * Create the frame.
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	public VentanaEliminarSudoku() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		gA = GestorAdministrador.getGestorAdministrador();
		gA.addObserver(this);
		setSize(500,500);
		setLocationRelativeTo(null);
		setVisible(true);
		norte = new JPanel();
		
		centro = new JPanel();
		centro.setLayout(new GridLayout(3,3,5,5));
		cajas = new JLabel[MAX][MAX];
		paneles = new JPanel[3][3];
		centro.setBackground(Color.black);
		setLayout(new BorderLayout());

		add(norte,"North");
		add(centro);
		
		crearNorte("0", 0);
		gA.introducirSudoku(Integer.parseInt((String) ident.getSelectedItem()));
		
		
		crearPaneles();
		crearCajas();
				
		sur = new JPanel();
		sur.setLayout(new GridLayout(1,2,0,2));
		add(sur,"South");
		
		getBotones();

		norte.setLayout(new GridLayout(1, 4));
		
		try{
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					try {
						getSeguro();
					} catch (NoHaySudokuCargadoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void crearNorte(String pIdent, final int pDif){	
		ident = null;
		dif = null;
		String[] valNivel = { "Muy Fácil", "Fácil", "Normal", "Difícil", "Muy Difícil"};
		dif = new JComboBox<String>(valNivel);
		dif.setSelectedIndex(pDif);
		dif.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ident = new JComboBox<String>(CatalogoSudoku.getCatalogoSudoku().obtListaIdent(pDif+1));
				int d = dif.getSelectedIndex();
				norte.removeAll();
				gA.introducirSudoku(Integer.parseInt((String) ident.getSelectedItem()));
				centro.removeAll();
				crearPaneles();
				crearCajas();
				centro.updateUI();
				crearNorte(CatalogoSudoku.getCatalogoSudoku().obtListaIdent(d+1)[0], d);
				norte.updateUI();
			}
			
		});
		ident = new JComboBox<String>(CatalogoSudoku.getCatalogoSudoku().obtListaIdent(pDif+1));
		ident.setSelectedIndex((Integer.parseInt(pIdent)/(pDif+1)));
		ident.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gA.introducirSudoku(Integer.parseInt((String) ident.getSelectedItem()));
				centro.removeAll();
				crearPaneles();
				crearCajas();
				centro.updateUI();
			}
			
		});
		
		norte.add(new JLabel());
		norte.add(new JLabel("Sudoku Nº: "));
		norte.add(ident);
		norte.add(new JLabel());
		norte.add(new JLabel("Dificultad: "));
		norte.add(dif);
		norte.add(new JLabel());
	}
	
	
	private void getSeguro() throws NoHaySudokuCargadoException{
		int valor = JOptionPane.showConfirmDialog(this, "Â¿Estas seguro de que quieres cerrar?", "CERRAR", JOptionPane.YES_NO_OPTION);
		if(valor==JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(null, "Gracias por jugar", "Gracias", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
		
	private void crearPaneles(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3;j++){
				paneles[i][j] = new JPanel();
				paneles[i][j].setLayout(new GridLayout(3,3,0,0));
				centro.add(paneles[i][j]);
				setColorPanel(i,j);
			}
		}
	}
	
	private void setColorPanel(int pI,int pJ){
		Color gris = Color.decode("0xE0E0E0");
		Color blanco = Color.decode("0xFAFAFA");
		if(((pI == 0 || pI == 2) && (pJ == 0 || pJ ==2)) || ((pI == 1 || pI == 3) && (pJ == 1 || pJ ==3))){
			paneles[pI][pJ].setBackground(blanco);
		}
		else{
			paneles[pI][pJ].setBackground(gris);

		}
		
	}
		
	private void crearCajas(){
		int f,c;
		String num;
		for(int i = 0; i < MAX; i++){
			for (int j = 0; j < MAX; j++){
				if(gA.obtValorCasilla(i, j) == 0){
					num = " ";
				}else{
					num = String.valueOf(gA.obtValorCasilla(i, j)-'0');
				}
				cajas[i][j] = crearJLabel(i, j, num);
				if(gA.obtFijas(i, j)){
					cajas[i][j].setBackground(Color.BLACK);
					cajas[i][j].setForeground(Color.WHITE);
					cajas[i][j].updateUI();
					cajas[i][j].setOpaque(true);
				}		
				f = (i+1)/3;
				if((i+1)%3 > 0) f++;
				c = (j+1)/3;
				if((j+1) % 3 > 0) c++;

				paneles[f-1][c-1].add(cajas[i][j]);
				
			}
		}
	}
	private JLabel crearJLabel(final int pI,final int pJ, String pNum){
		Font fuente;
		
		fuente = new Font("Monospaced",Font.BOLD,20);
		
		final JLabel j = new JLabel();
		j.setText(pNum+"");
		j.setFont(fuente);
		j.setBackground(Color.green);
		j.setHorizontalAlignment(WIDTH/2);
		j.setBorder(LineBorder.createBlackLineBorder());
		return j;

	}	
	
	private void getBotones(){
		getBtn1();
		getBtn2();
	}
	private JButton getBtn1() {
		if(btn1 == null){
			btn1 = new JButton("Eliminar");
			sur.add(btn1);
			btn1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					norte.removeAll();
					int num = 0;
					int aux1 = Integer.parseInt((String)ident.getSelectedItem());
					int aux2 = Integer.parseInt(CatalogoSudoku.getCatalogoSudoku().obtListaIdent(dif.getSelectedIndex()+1)[0]);
					if(aux1 == aux2){
						num = Integer.parseInt((String) ident.getSelectedItem()) + 1;
					}else{
						num = Integer.parseInt((String) ident.getSelectedItem()) - 1;
					}
					gA.eliminarSudoku();
					gA.introducirSudoku(num);
					centro.removeAll();
					crearPaneles();
					crearCajas();
					centro.updateUI();
					crearNorte(String.valueOf(gA.obtSud().obtIdentificador()), dif.getSelectedIndex());
					norte.updateUI();
					btn1.setEnabled(false);
					crearDialogoFinal();

				}
			});
		}
		return btn1;
	}
		
	private JButton getBtn2() {
		if(btn2 == null){
			btn2 = new JButton("Atrás");
			sur.add(btn2);
		}
		btn2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				VentanaAdministrarSudokus vnt = new VentanaAdministrarSudokus();
				vnt.setVisible(true);
				dispose();
			}
		});
		return btn2;
		
	}
	
	private void crearDialogoFinal(){
		GridBagConstraints csTexto = new GridBagConstraints();
		GridBagConstraints csBoton = new GridBagConstraints();
		final JDialog dialogFinal = new JDialog();
		
		csTexto.weighty = 1;
		csTexto.gridx = 0;
		csTexto.gridy = 0;
		
		csBoton.weighty = 1;
		csBoton.gridx = 0;
		csBoton.gridy = 1;
		
		JLabel txt = new JLabel("El Sudoku se ha eliminado correctamente");
		JButton boton = new JButton("Continuar");
		
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btn1.setEnabled(true);
				dialogFinal.dispose();
				
			}
			
		});
		
		dialogFinal.setSize(300,125);
		dialogFinal.setAlwaysOnTop(true);
		dialogFinal.setModal(false);
		dialogFinal.setVisible(true);
		dialogFinal.setLocationRelativeTo(this);
		dialogFinal.setTitle("CORRECTO");
		
		dialogFinal.setLayout(new GridBagLayout());
	
		dialogFinal.add(txt,csTexto);
		csTexto.gridy = 1;
		dialogFinal.add(boton,csBoton);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		for(int j = 0; j<MAX; j++){
			for(int i=0; i<MAX; i++){
			}
		}
	}
		
	public static void main(String arg[]) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		VentanaEliminarSudoku vnt = new VentanaEliminarSudoku();
		vnt.setVisible(true);
	}
}