package packInterfazGrafica;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import packExcepciones.NoHaySudokuCargadoException;
import packModelo.Tablero;

public class VentanaTablero extends JDialog implements Observer {

	    private boolean packframe = false;
	    // Atributos
	    private final int NUMFILAS = 9;
	    private final int NUMCOLUMNAS = 9;
	    private final int NUMZONAS = 9;


	    private Vector listaPaneles = new Vector();
	    private Vector listaCasillas = new Vector();

	    // private String caminoIconos = "Img//";
	    private JPanel panelCasillas = new JPanel();
	    private JPanel panelTerminar = new JPanel();
	    private JButton botonTerminar = new JButton();
	    private JButton botonAyuda = new JButton();

	    private BorderLayout borderLayout1 = new BorderLayout();

	    private JPopupMenu miMenu = new JPopupMenu();


	    private GridLayout gridLayoutCasillas = new GridLayout();
	    private Border border1 = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	    private JPanel panelInfo = new JPanel();
	    private JLabel lblErroresEtiq = new JLabel();
	    private JLabel lblIDSudoku = new JLabel();
	    private GridLayout gridLayoutInfo = new GridLayout();
	    private JLabel lblSudoku = new JLabel();
	    private JLabel lblEtiqNivel = new JLabel();
	    private JLabel lblNivel = new JLabel();
	    private JLabel lblErrores = new JLabel();

	    private final Color COLORRESALTADO = Color.YELLOW;

	    private static VentanaTablero miVentana;
	    private Border border2 = BorderFactory.createEmptyBorder(20, 10, 20, 10);
	    private Border border3 = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	    private Controlador controlador = new Controlador();
	    private Tablero tablero = Tablero.obtTablero();
	    
	    //Construcci�n del tablero
	    private VentanaTablero() {

		tablero.obtTablero().addObserver(this);
	        // Crear Menu
	        crearMenu();

	        // CrearPanelesZonas
	        crearPanelesZonas();

	        // Crear CasillasGraficas
	        crearCasillas();

	        //Inicializaci�n de la recepci�n de eventos en la ventana
	        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	        try {
	            jbInit();
	            pack();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static VentanaTablero obtVentanaTablero() {
	        if (miVentana == null) {
	            miVentana = new VentanaTablero();
	        }
	        return miVentana;
	    }

	    /**
	     * crearPanelesZonas
	     */
	    private void crearPanelesZonas() {
	        // Generaci�n de Paneles
	        for (int i = 0; i < NUMZONAS; i++) {
	            listaPaneles.addElement(crearPanel());
	        }
	    }

	    /**
	     * crearPanel
	     *
	     * @return JPanel
	     */
	    private JPanel crearPanel() {
	        JPanel panelZona = new JPanel();
	        // panelZona.setBorder(BorderFactory.createLineBorder(Color.black));
	        GridLayout gridLayoutZona = new GridLayout();
	        gridLayoutZona.setColumns(3);
	        gridLayoutZona.setRows(3);
	        //  gridLayoutZona.setHgap(2);
	        // gridLayoutZona.setVgap(2);
	        panelZona.setLayout(gridLayoutZona);
	        return panelZona;
	    }

	    private void crearCasillas() {
	        //Generaci�n de las casillas
	        int columna = 0;
	        int fila = 0;
	        for (fila = 0; fila < NUMFILAS; fila++) {
	            for (columna = 0; columna < NUMCOLUMNAS; columna++) {
	                listaCasillas.addElement(crearCasilla());
	            }
	        }

	    }

	    //M�todo privado para crear una casilla vac�a del tablero
	    private JTextField crearCasilla() {
	        JTextField cuadroTexto = new JTextField();
	        cuadroTexto.setMaximumSize(new Dimension(50, 50));
	        cuadroTexto.setMinimumSize(new Dimension(50, 50));
	        cuadroTexto.setSize(new Dimension(50, 50));
	        cuadroTexto.setHorizontalAlignment(JTextField.CENTER);
	        cuadroTexto.setFont(new Font("Helvetica", Font.PLAIN, 16));
	        cuadroTexto.setComponentPopupMenu(miMenu);
	        cuadroTexto.setFocusable(false);
	        return cuadroTexto;
	    }


	    //M�todo privado para obtener la casilla
	    private JTextField obtCasilla(int fila, int columna) {
	        return (JTextField) listaCasillas.elementAt((fila * NUMCOLUMNAS) +
	                columna);
	    }

	    private JPanel obtPanelFilaCol(int pFila, int pCol) {
	        JPanel panel = (JPanel) listaPaneles.elementAt((pFila / 3 * 3) +
	                pCol / 3);
	        return panel;
	    }

	    //Inicializaci�n de componentes
	    private void jbInit() throws Exception {
	        this.setModal(true);
	        this.setTitle("Sudoku: ");
	        this.getContentPane().setLayout(borderLayout1);

	        botonTerminar.setBorder(BorderFactory.createRaisedBevelBorder());
	        botonTerminar.setPreferredSize(new Dimension(60, 30));
	        botonTerminar.setMargin(new Insets(5, 25, 5, 25));
	        botonTerminar.setText("Finalizar");
	        botonTerminar.addActionListener((ActionListener) controlador);
	        botonTerminar.setActionCommand("botonTerminar");
	  
	        botonAyuda.setBorder(BorderFactory.createRaisedBevelBorder());
	        botonAyuda.setPreferredSize(new Dimension(60, 30));
	        botonAyuda.setMargin(new Insets(5, 25, 5, 25));

	        botonAyuda.setText("Ayuda");
	        botonAyuda.addActionListener(controlador);
	        botonAyuda.setActionCommand("botonAyuda");

	        // panelTerminar.setPreferredSize(new Dimension(70, 50));
	        borderLayout1.setVgap(10);

	        panelCasillas.setLayout(gridLayoutCasillas);
	        gridLayoutCasillas.setColumns(3);
	        gridLayoutCasillas.setHgap(5);
	        gridLayoutCasillas.setRows(3);
	        gridLayoutCasillas.setVgap(5);

	        // A�adir Paneles
	        anadirPanelesZona();
	        // A�adir Casillas a los paneles
	        anadirCasillasAPaneles();
	        // A�adir Men�
	        // crearMenu();

	        panelCasillas.setBorder(border1);
	        panelCasillas.setMaximumSize(new Dimension(250, 300));
	        panelCasillas.setMinimumSize(new Dimension(250, 300));
	        panelCasillas.setPreferredSize(new Dimension(250, 300));
	        panelInfo.setLayout(gridLayoutInfo);
	        lblIDSudoku.setText("Sudoku:");
	        gridLayoutInfo.setColumns(2);
	        gridLayoutInfo.setRows(3);
	        lblSudoku.setText("");
	        lblErroresEtiq.setText("Errores:");
	        lblEtiqNivel.setText("Nivel:");
	        lblNivel.setText("");
	        lblErrores.setText("");
	        panelInfo.setBorder(border3);

	        this.getContentPane().add(panelTerminar, BorderLayout.SOUTH);
	        panelTerminar.add(botonAyuda);
	        panelTerminar.add(botonAyuda);
	        panelTerminar.add(botonTerminar);
	        this.getContentPane().add(panelCasillas, BorderLayout.CENTER);
	        this.getContentPane().add(panelInfo, java.awt.BorderLayout.NORTH);
	        panelInfo.add(lblIDSudoku);
	        panelInfo.add(lblSudoku);
	        panelInfo.add(lblEtiqNivel);
	        panelInfo.add(lblNivel);
	        panelInfo.add(lblErroresEtiq);
	        panelInfo.add(lblErrores);
	        lblErrores.setVisible(false);
	        lblErroresEtiq.setVisible(false);
	        this.setSize(new Dimension(450, 300));

	        this.setResizable(false);

	    }

	    private void crearMenu() {
	        JMenuItem menuItem;
	        for (int i = 1; i <= 9; i++) {
	            menuItem = new JMenuItem("" + i + "");
	            menuItem.setActionCommand("asignarValor");
	            menuItem.addMouseListener((MouseListener) controlador);
	            miMenu.add(menuItem);
	        }
	        miMenu.add(new JPopupMenu.Separator());
	        menuItem = new JMenuItem("Quitar Valor");
	        menuItem.setActionCommand("quitarValor");
	        menuItem.addMouseListener(controlador);
	        miMenu.add(menuItem);
	        miMenu.setFocusable(true);
	        miMenu.addPopupMenuListener((PopupMenuListener) controlador);

	    }

	    private void anadirPanelesZona() {
	        for (int i = 0; i < listaPaneles.size(); i++) {
	            JPanel panel = (JPanel) listaPaneles.elementAt(i);
	            panelCasillas.add(panel);
	        }
	    }

	    private void anadirCasillasAPaneles() {

	        int columna = 0;
	        int fila = 0;
	        for (fila = 0; fila < NUMFILAS; fila++) {
	            for (columna = 0; columna < NUMCOLUMNAS; columna++) {

	                JPanel panel = obtPanelFilaCol(fila, columna);
	                panel.add(obtCasilla(fila, columna),
	                          new GridBagConstraints(columna, fila, 1, 1, 0.0, 0.0,
	                                                 GridBagConstraints.CENTER,
	                                                 GridBagConstraints.NONE,
	                                                 new Insets(2, 2, 2, 2), 0, 0));
	            }
	        }

	    }

	    //Para salir de la aplicaci�n cuando se cierra la ventana
	    protected void processWindowEvent(WindowEvent e) {
	        super.processWindowEvent(e);
	        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
	            this.setVisible(false);
	        }
	    }

	    private void botonTerminar_actionPerformed(ActionEvent e) {
	        this.setVisible(false);
	    }

	    public void bloquear() {
	        for (int i = 0; i < NUMFILAS * NUMCOLUMNAS; i++) {
	            JTextField etiq = (JTextField) listaCasillas.elementAt(i);
	            etiq.setComponentPopupMenu(null);
	        }
	    }



	    private void quitarInfoAyuda() {
	        lblErroresEtiq.setVisible(false);
	        lblErrores.setVisible(false);
	        JTextField cuadroTexto;
	        for (int fila = 0; fila < NUMFILAS; fila++) {
	            for (int columna = 0; columna < NUMCOLUMNAS; columna++) {
	                cuadroTexto = obtCasilla(fila, columna);
	                cuadroTexto.setBackground(Color.WHITE);
	            }
	        }
	    }

	    public void inicializarTablero() {
	        JTextField casilla;
	        for (int fila = 1; fila <= NUMFILAS; fila++) {
	            for (int columna = 1; columna <= NUMCOLUMNAS; columna++) {
	                quitarValor(fila, columna);
	                casilla = obtCasilla(fila - 1, columna - 1);
	                casilla.setForeground(Color.BLACK);
	                casilla.setFont(new Font("Helvetica", Font.PLAIN, 14));
	                casilla.setComponentPopupMenu(miMenu);
	            }
	        }
	    }

	    private void resaltarArea(char pTipo, int pId) {
	        switch (pTipo) {
	        case 'F':
	        case 'f':
	            resaltarFila(pId);
	            break;
	        case 'C':
	        case 'c':
	            resaltarColumna(pId);
	            break;
	        case 'Z':
	        case 'z':
	            resaltarZona(pId);
	            break;
	        }
	    }

	    private void resaltarColumna(int pIdCol) {
	        int columna = pIdCol - 1;
	        JTextField cuadroTexto;
	        for (int fila = 0; fila < NUMFILAS; fila++) {
	            cuadroTexto = obtCasilla(fila, columna);
	            cuadroTexto.setBackground(COLORRESALTADO);
	        }
	    }


	    private void resaltarFila(int pIdFila) {
	        int fila = pIdFila - 1;
	        JTextField cuadroTexto;
	        for (int columna = 0; columna < NUMCOLUMNAS; columna++) {
	            cuadroTexto = obtCasilla(fila, columna);
	            cuadroTexto.setBackground(COLORRESALTADO);
	        }
	    }

	    private void resaltarZona(int pIdZona) {
	        pIdZona = pIdZona - 1; //S�lo si las zonas se numeran de 1-9
	        int auxZona = pIdZona / 3;
	        int filaIni = auxZona * 3;
	        int filaFin = filaIni + 3;
	        int colIni = (pIdZona % 3) * 3;
	        int colFin = colIni + 3;
	        JTextField cuadroTexto;
	        for (int fila = filaIni; fila < filaFin; fila++) {
	            for (int columna = colIni; columna < colFin; columna++) {
	                cuadroTexto = obtCasilla(fila, columna);
	                cuadroTexto.setBackground(COLORRESALTADO);
	            }
	        }
	    }

	    private void asgValor(int pFila, int pCol, int pValor) {
	        quitarInfoAyuda();

	        JTextField cuadroTexto = obtCasilla(pFila - 1, pCol - 1);
	        cuadroTexto.setText("" + pValor + "");
	    }

	    private void asgValorInicial(int pFila, int pCol, int pValor) {
	        quitarInfoAyuda();
	        JTextField cuadroTexto = obtCasilla(pFila - 1, pCol - 1);
	        cuadroTexto.setForeground(Color.RED);
	        cuadroTexto.setFont(new Font("Helvetica", Font.BOLD, 16));
	        cuadroTexto.setText("" + pValor + "");
	        cuadroTexto.setComponentPopupMenu(null);
	    }

	    private void quitarValor(int pFila, int pCol) {
	        quitarInfoAyuda();
	        JTextField cuadroTexto = obtCasilla(pFila - 1, pCol - 1);
	        cuadroTexto.setText("");
	    }

	    private void ponerIdSudoku(String pID) {
	        lblSudoku.setText(pID);
	    }

	    public void ponerNivelSudoku(int pNivel) {
	        lblNivel.setText("" + pNivel + "");
	    }

	    public void mostrarErrores(int pErrores) {
	        lblErrores.setText("" + pErrores + "");
	        if (pErrores > 0) {
	            lblErrores.setForeground(Color.RED);
	            lblErroresEtiq.setForeground(Color.RED);
	        } else {
	            lblErrores.setForeground(Color.BLACK);
	            lblErroresEtiq.setForeground(Color.BLACK);
	        }
	        lblErroresEtiq.setVisible(true);
	        lblErrores.setVisible(true);

	    }

	    private void asignarValorCasilla(MouseEvent e) {

	        JTextField casilla = (JTextField) miMenu.getInvoker();
	        int posicion = listaCasillas.indexOf(casilla);
	        System.out.println("Posicion en la que pincha con el derecho: " +
	                           posicion);
	        int filaInicial = (int) (posicion / NUMCOLUMNAS);
	        int columnaInicial = posicion % NUMCOLUMNAS;
	        System.out.println("Posicion de la casilla: " + filaInicial + ", " +
	                           columnaInicial);

	        JMenuItem menuItem = (JMenuItem) e.getComponent();
	        int val = Integer.parseInt(menuItem.getText());
	        // Aldatu behar da
	        try {
	            Tablero.obtTablero().asgValor(filaInicial + 1, columnaInicial + 1,
	                                          val);
	        } catch (Exception ex) {
	            // En principio aqu� habr�a que tratar cada una de las excepciones
	            // por separado. Como no se vana a producir y algunos hab�is definido
	            // las excepciones en distintos paquetes, lo hemos simplificado
	            VentanaError.obtVentanaError().mostrar(ex.getMessage());

	        }
	        miMenu.setVisible(false);
	    }

	    private void quitarValorCasilla(MouseEvent e) {
	        JTextField casilla = (JTextField) miMenu.getInvoker();
	        int posicion = listaCasillas.indexOf(casilla);
	        System.out.println("Posicion en la que pincha con el derecho: " +
	                           posicion);
	        int filaInicial = (int) (posicion / NUMCOLUMNAS);
	        int columnaInicial = posicion % NUMCOLUMNAS;
	        System.out.println("Posicion de la casilla: " + filaInicial + ", " +
	                           columnaInicial);

	        JMenuItem menuItem = (JMenuItem) e.getComponent();
	        // Aldatu behar da
	        try {
	            Tablero.obtTablero().quitarValor(filaInicial + 1,
	                                             columnaInicial + 1);
	        } catch (Exception ex) {
	            // En principio aqu� habr�a que tratar cada una de las excepciones
	            // por separado. Como no se vana a producir y algunos hab�is definido
	            // las excepciones en distintos paquetes, lo hemos simplificado
	            VentanaError.obtVentanaError().mostrar(ex.getMessage());
	        }
	        miMenu.setVisible(false);
	    }

	    

	    public void mostrar() {
	        if (packframe) {
	            this.pack();
	        } else {
	            this.validate();
	        }
	        this.setModal(true);
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        Dimension ventanaTableroSize = this.getSize();
	        if (ventanaTableroSize.height > screenSize.height) {
	            ventanaTableroSize.height = screenSize.height;
	        }
	        if (ventanaTableroSize.width > screenSize.width) {
	            ventanaTableroSize.width = screenSize.width;
	        }
	        this.setLocation((screenSize.width - ventanaTableroSize.width) / 2,
	                         (screenSize.height - ventanaTableroSize.height) / 2);
	        update(null,null);

	        this.setVisible(true);
	    
	    }

	    /* (non-Javadoc)
	     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	     */
	    @Override
	    public void update(Observable pO, Object pArg)
	    {
		inicializarTablero();
		try
		{
		    this.lblSudoku.setText(tablero.obtIdSudoku());
		    this.lblNivel.setText("" + tablero.obtNivel());
		} catch (NoHaySudokuCargadoException e)
		{
		}
		
		this.lblErrores.setText("");
		if (!tablero.finalDeJuego())
		{
		    mostrarCasillas();
		}
		else
		{
		    descubrirCasillas();
		}
		
	    }
	    
	    /**
	     * 
	     */
	    private void descubrirCasillas()
	    {
		for (int i = 1; i <= NUMFILAS; i++)
		{
		    for (int j = 1; j <= NUMCOLUMNAS; j++)
		    {
			if (!tablero.esValorInicial(i, j) )
			{
			    asgValor(i, j,tablero.obtValorCasilla(i,j));
			}
			else if (tablero.esValorInicial(i, j))
			{
			    asgValorInicial(i,j,tablero.obtValorCasilla(i,j));
			}
		    }
		}
		bloquear();
	    }

	    /**
	     * 
	     */
	    private void mostrarCasillas()
	    {
		for (int i = 1; i <= NUMFILAS; i++)
		{
		    for (int j = 1; j <= NUMCOLUMNAS; j++)
		    {
			if (!tablero.esValorInicial(i, j) && !tablero.estaLibre(i, j))
			{
			    asgValor(i, j,tablero.obtValorCasilla(i,j));
			}
			else if (tablero.esValorInicial(i, j))
			{
			    asgValorInicial(i,j,tablero.obtValorCasilla(i,j));
			}
		    }
		}
		
	    }
	    
	    private void mostrarErroresInconsistencias()
	    {
		mostrarErrores(tablero.obtNumErrores());
		resaltarInconsistencias();
	    }

	    /**
	     * 
	     */
	    private void resaltarInconsistencias()
	    {
		for (int i = 1; i <= 9; i++)
		{
		    if (tablero.hayInconsistencias('F', i))
			resaltarArea('F', i);
		    if (tablero.hayInconsistencias('C', i))
		    	resaltarArea('C', i);
		    if (tablero.hayInconsistencias('Z', i))
		    	resaltarArea('Z', i);
		    
			
		}
		
	    }

	    private class Controlador implements ActionListener, PopupMenuListener, MouseListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent pE)
		{
		    if (pE.getActionCommand().equalsIgnoreCase("botonTerminar"))
			setVisible(false);
		    else
			mostrarErroresInconsistencias();
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.PopupMenuListener#popupMenuCanceled(javax.swing.event.PopupMenuEvent)
		 */
		@Override
		public void popupMenuCanceled(PopupMenuEvent pE)
		{
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent)
		 */
		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent pE)
		{
	    
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent)
		 */
		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent pE)
		{
		    JTextField casilla = (JTextField) miMenu.getInvoker();
	            int posicion = listaCasillas.indexOf(casilla);
	            System.out.println("Posicion en la que pincha con el derecho: " +
	                               posicion);
	            int filaInicial = (int) (posicion / NUMCOLUMNAS);
	            int columnaInicial = posicion % NUMCOLUMNAS;
	            Component comp[] = miMenu.getComponents();
	            JMenuItem menuItem = (JMenuItem) comp[comp.length - 1];

	            if (Tablero.obtTablero().estaLibre(filaInicial + 1,
	                    columnaInicial + 1)) {

	                menuItem.setEnabled(false);

	            } else {
	                menuItem.setEnabled(true);
	            }
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent pE)
		{
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(MouseEvent pE)
		{
		    
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent pE)
		{
			}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent pE)
		{
		    JMenuItem menuItem = (JMenuItem) pE.getSource();
		    if (menuItem.getActionCommand().equalsIgnoreCase("asignarValor"))
		    {
			asignarValorCasilla(pE);
		    }
		    else
		    {
			quitarValorCasilla(pE);
		    }
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent pE)
		{
		     
		}
		
	    }

}