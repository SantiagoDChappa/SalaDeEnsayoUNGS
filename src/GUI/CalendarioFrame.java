package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.awt.event.MouseEvent;
//Add this line at the beginning of your file
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import Logica.CalendarioLogica;

public class CalendarioFrame {
	
	private JFrame CalendarioFrame;
	private JDateChooser buscadorFecha;
	private CalendarioLogica Calendario;
    
	public CalendarioFrame() {
    	initialize();
    }
    
	private void initialize() {
	    Calendario = new CalendarioLogica();  // Inicialización de TableroLogica
	    CalendarioFrame = new JFrame();
	    CalendarioFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Usuario\\Desktop\\Nueva carpeta\\newCarpet\\Facultad\\Programacion3\\Proyectos\\TP3\\src\\imagenes\\logo_ungs.png"));
	    CalendarioFrame.setTitle("Buscar ofertas por fechas - Sala de ensayo UNGS");
	    CalendarioFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    CalendarioFrame.setBounds(100, 100, 592, 401);
	    CalendarioFrame.setSize(1280, 720);
    	CalendarioFrame.setLocationRelativeTo(null);
    	CalendarioFrame.getContentPane().setLayout(null); 
	
    	buscadorFecha = new JDateChooser();
    	buscadorFecha.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
    	buscadorFecha.setDateFormatString("dd/MM/yyyy");
    	buscadorFecha.setBounds(386, 11, 371, 38);
    	CalendarioFrame.getContentPane().add(buscadorFecha);
    	
    	// Le pongo un background de color #0E1012
    	CalendarioFrame.getContentPane().setBackground(new Color(14, 16, 18));

    	JList listadoResultado = new JList();
    	listadoResultado.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	listadoResultado.setBounds(44, 135, 1154, 515);
    	CalendarioFrame.getContentPane().add(listadoResultado);
    	
    	JLabel lblMejoresOfertasFecha = new JLabel("Las mejores ofertas del dia: ");
    	lblMejoresOfertasFecha.setForeground(new Color(255, 255, 255));
    	lblMejoresOfertasFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblMejoresOfertasFecha.setBounds(44, 97, 620, 27);
    	CalendarioFrame.getContentPane().add(lblMejoresOfertasFecha);
    	
    	JButton btnBuscarOfertas = new JButton("Buscar Ofertas");
    	btnBuscarOfertas.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	btnBuscarOfertas.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			lblMejoresOfertasFecha.setText("Las mejores ofertas del dia: " + mostrarFecha(buscadorFecha.getDate()));
    		}
    	});
    	btnBuscarOfertas.setBounds(767, 11, 186, 38);
    	CalendarioFrame.getContentPane().add(btnBuscarOfertas);
    	
    	JButton btnVolver = new JButton("Volver");
    	btnVolver.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	btnVolver.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			MenuFrame menuFrame = new MenuFrame();
    			menuFrame.setVisible(true);
    			CalendarioFrame.setVisible(false);
    		}
    	});
    	btnVolver.setBounds(10, 11, 113, 38);
    	CalendarioFrame.getContentPane().add(btnVolver);
    	
    }
	
	private String mostrarFecha(Date fecha) {
		LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.of("es", "ES"));
	    String mesEnEspanol = localDate.format(formatter);
	    return localDate.getDayOfMonth() + " de " + mesEnEspanol + " de " + localDate.getYear();
	}
	
	public void setVisible(boolean b) {
		CalendarioFrame.setVisible(b);
	}
}
