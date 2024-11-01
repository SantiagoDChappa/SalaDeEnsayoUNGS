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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Entidades.Oferta;
import Logica.CalendarioLogica;
import Logica.GestorOferta;

public class CalendarioFrame {
	
	private String[] nombreColumnas = {"Nombre del ofertante", "Horario inicio", "Horario salida", "Monto Ofrecido"};
	private DefaultTableModel model;
	private GestorOferta GestorOfertas;
	private ArrayList<Oferta> ofertas;
	private String ultimaFechaBuscada;
	private JFrame CalendarioFrame;
	private JCalendar calendario;
	private JButton btnVolver;
	private JTable tblOferta;
	private JScrollPane spTablero;

    
	public CalendarioFrame() {
    	initialize();
    }
    
	private void initialize() {
        GestorOfertas = new GestorOferta();
        CalendarioFrame = new JFrame();
        ofertas = new ArrayList<Oferta>();
	    CalendarioFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Usuario\\Desktop\\Nueva carpeta\\newCarpet\\Facultad\\Programacion3\\Proyectos\\TP3\\src\\imagenes\\logo_ungs.png"));
	    CalendarioFrame.setTitle("Buscar ofertas por fechas - Sala de ensayo UNGS");
	    CalendarioFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    CalendarioFrame.setBounds(100, 100, 592, 401);
	    CalendarioFrame.setSize(1280, 720);
    	CalendarioFrame.setLocationRelativeTo(null);
    	CalendarioFrame.getContentPane().setLayout(null);
    	
    	// Le pongo un background de color #0E1012
    	CalendarioFrame.getContentPane().setBackground(new Color(14, 16, 18));
    	
    	JLabel lblMejoresOfertasFecha = new JLabel("Las ofertas del dia: ");
    	lblMejoresOfertasFecha.setForeground(new Color(255, 255, 255));
    	lblMejoresOfertasFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblMejoresOfertasFecha.setBounds(42, 59, 620, 27);
    	CalendarioFrame.getContentPane().add(lblMejoresOfertasFecha);
    	
    	JButton btnBuscarOfertas = new JButton("Buscar Ofertas");
    	btnBuscarOfertas.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	btnBuscarOfertas.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
				lblMejoresOfertasFecha.setText("Las ofertas del dia: " + mostrarFecha(calendario.getDate()));
				
				LocalDate localDate = calendario.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String fechaAComprobar = localDate.getDayOfMonth() + "-" + localDate.getMonth() + "-" + localDate.getYear();
				
				if(ultimaFechaBuscada == null || !ultimaFechaBuscada.equals(fechaAComprobar)) {
					ultimaFechaBuscada = fechaAComprobar;
					
					if(!ofertas.isEmpty()) {ofertas.clear();}
	    			ofertas = GestorOfertas.obtenerOfertas(calendario.getDate());
	    			model = new DefaultTableModel(null, nombreColumnas);
	    				    			
	    			for (Oferta oferta : ofertas) {
	    				model.addRow(new Object[] {
    						oferta.obtenerNombreOfertante(), 
							formatearHora(oferta.obtenerHorarioInicio()),
							formatearHora(oferta.obtenerHorarioSalida()), 
							formatearMonto(oferta.obtenerMontoOfrecido()) 
    		            });
	    			}
	    			tblOferta.setModel(model);
				}
    		}
    	});
    	btnBuscarOfertas.setBounds(443, 610, 347, 38);
    	CalendarioFrame.getContentPane().add(btnBuscarOfertas);
    	
    	btnVolver = new JButton("Volver");
    	btnVolver.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	btnVolver.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			MenuFrame menuFrame = new MenuFrame();
    			menuFrame.setVisible(true);
    			CalendarioFrame.setVisible(false);
    		}
    	});
    	btnVolver.setBounds(28, 11, 113, 38);
    	CalendarioFrame.getContentPane().add(btnVolver);
    	
    	calendario = new JCalendar();
    	calendario.setBounds(635, 97, 574, 486);
    	calendario.setVisible(true);
    	CalendarioFrame.getContentPane().add(calendario);
    	    	
    	tblOferta = new JTable();
    	tblOferta.setModel(new DefaultTableModel(null, nombreColumnas));
    	spTablero = new JScrollPane(tblOferta);
    	spTablero.setBounds(28, 97, 574, 486);
    	CalendarioFrame.getContentPane().add(spTablero);
    	
    	
    }
	
	private String formatearHora(Integer horario) {
        return String.format("%02d:00hs", horario);
	}
	
	private String formatearMonto(Double monto) {
	    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-AR"));

        return formatoMoneda.format(monto);
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
