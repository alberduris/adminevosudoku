package packInterfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import packModelo.Sesion;

public class VentanaInicial extends JDialog implements Observer {

	  private JPanel panel = new JPanel();
	  private BorderLayout borderLayout1 = new BorderLayout();
	  private JPanel panelDatos = new JPanel();
	  private JPanel panelOk = new JPanel();
	  private JLabel labelNombre = new JLabel();
	  private JTextField textNivel = new JTextField();
	  private JTextField textNombre = new JTextField();
	  private JLabel labelNivel = new JLabel();
	  private GridBagLayout gridBagLayout1 = new GridBagLayout();
	  private Border border3;
	  private JButton botonOk = new JButton();
	  private Border border4;
	  private Controlador controlador = new Controlador();
	  private Sesion sesion = Sesion.obtSesion();

	  private static VentanaInicial miVentana;

	  public static VentanaInicial obtVentanaInicial()
	  {
	      if (miVentana==null)
	      {
	          miVentana = new VentanaInicial();
	      }
	      return miVentana;
	  }

	  //Constructoras
	  private VentanaInicial () {
	    this(null, "Identificacion del usuario", true);
	  }


	  private VentanaInicial(Frame frame, String title, boolean modal) {
	    super(frame, title, modal);
	   sesion.addObserver(this);
	    try {
	      jbInit();
	      pack();
	    }
	    catch(Exception ex) {
	      ex.printStackTrace();
	    }
	    update(sesion,null);
	  }


	  void jbInit() throws Exception {
	    border3 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 156, 158)),BorderFactory.createEmptyBorder(10,10,10,10));
	    border4 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.white,Color.white,new Color(109, 109, 110),new Color(156, 156, 158)),BorderFactory.createEmptyBorder(2,15,2,15));
	    panel.setLayout(borderLayout1);
	    labelNombre.setFont(new java.awt.Font("Dialog", 1, 14));
	    labelNombre.setAlignmentX((float) 20.0);
	    labelNombre.setText("Nombre:");
	    textNivel.setFont(new java.awt.Font("Dialog", 0, 12));
	    panelDatos.setLayout(gridBagLayout1);
	    textNombre.setFont(new java.awt.Font("Dialog", 0, 12));
	    labelNivel.setFont(new java.awt.Font("Dialog", 1, 14));
	    labelNivel.setAlignmentX((float) 1.0);
	    labelNivel.setText("Nivel:");
	    panelDatos.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 156, 158)),BorderFactory.createEmptyBorder(10,10,10,10)));
	    botonOk.setBorder(border4);
	    botonOk.setText("OK");
	    botonOk.addActionListener((ActionListener) controlador);
	    getContentPane().add(panel);
	    panel.add(panelDatos,  BorderLayout.NORTH);
	    panelDatos.add(labelNombre,                   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 10, 0), 10, 1));
	    panelDatos.add(textNombre,       new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 137, 0));
	    panelDatos.add(labelNivel,           new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 10, 0), 10, 1));
	    panelDatos.add(textNivel,     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 137, 0));
	    panel.add(panelOk, BorderLayout.CENTER);
	    panelOk.add(botonOk, null);
	  }

	  void botonOk_actionPerformed(ActionEvent e) {
	     this.setVisible(false);
	  }

	  public void mostrar() {

	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension frameSize = this.getSize();
	    if (frameSize.height > screenSize.height) {
	      frameSize.height = screenSize.height;
	    }
	    if (frameSize.width > screenSize.width) {
	      frameSize.width = screenSize.width;
	    }
	    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	    this.setVisible(true);
	  }


	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable pO, Object pArg)
	{
	  if (sesion.obtNombreUsuario()!= null)
	  {
	      textNombre.setText(sesion.obtNombreUsuario());
	  }
	  else
	  {
	      textNombre.setText("");
	  }
	  textNivel.setText("" + sesion.obtNivel());
	}

	private class Controlador implements ActionListener
	{

	    /* (non-Javadoc)
	     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	     */
	    @Override
	    public void actionPerformed(ActionEvent pE)
	    {
		int nivel = 1;
		String s = textNombre.getText();
		if (s.length() == 0)
		{
		    s = "desconocido";
		}
		sesion.ponNombreUsuario(s);
		s = textNivel.getText();
		if (s.length()!=0)
		{
		    try
		    {
			nivel = Integer.parseInt(s.trim());
		    } catch (NumberFormatException e)
		    {
		    }
		}
		sesion.ponNivel(nivel);
		setVisible(false);	
	    }    
	}
}
