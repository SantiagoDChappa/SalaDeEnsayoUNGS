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

public class MejoresOferasFrame {
	
	private String[] nombreColumnas = {"Nombre del ofertante", "Horario inicio", "Horario salida", "Monto Ofrecido", "Equipamientos"};
	private JFrame MejoresOfertasFrame;
	private JCalendar calendario;
	private JButton btnVolver;
	private JTable tblOferta;
	private JScrollPane spTablero;

    
	public MejoresOferasFrame() {
    	initialize();
    }
    
	private void initialize() {
		MejoresOfertasFrame = new JFrame();
	    MejoresOfertasFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Usuario\\Desktop\\Nueva carpeta\\newCarpet\\Facultad\\Programacion3\\Proyectos\\TP3\\src\\imagenes\\logo_ungs.png"));
	    MejoresOfertasFrame.setTitle("Buscar ofertas por fechas - Sala de ensayo UNGS");
	    MejoresOfertasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    MejoresOfertasFrame.setBounds(100, 100, 592, 401);
	    MejoresOfertasFrame.setSize(1280, 720);
    	MejoresOfertasFrame.setLocationRelativeTo(null);
    	MejoresOfertasFrame.getContentPane().setLayout(null);
    	
    	// Le pongo un background de color #0E1012
    	MejoresOfertasFrame.getContentPane().setBackground(new Color(14, 16, 18));
    	
    	JLabel lblMejoresOfertasFecha = new JLabel("Las mejores ofertas del dia: ");
    	lblMejoresOfertasFecha.setForeground(new Color(255, 255, 255));
    	lblMejoresOfertasFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblMejoresOfertasFecha.setBounds(42, 59, 620, 27);
    	MejoresOfertasFrame.getContentPane().add(lblMejoresOfertasFecha);
    	
    	JButton btnBuscarOfertas = new JButton("Maximizar Ganancia");
    	btnBuscarOfertas.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	btnBuscarOfertas.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
				
    		}
    	});
    	btnBuscarOfertas.setBounds(528, 610, 183, 38);
    	MejoresOfertasFrame.getContentPane().add(btnBuscarOfertas);
    	
    	btnVolver = new JButton("Volver");
    	btnVolver.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	btnVolver.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			MenuFrame menuFrame = new MenuFrame();
    			menuFrame.setVisible(true);
    			MejoresOfertasFrame.setVisible(false);
    		}
    	});
    	btnVolver.setBounds(28, 11, 113, 38);
    	MejoresOfertasFrame.getContentPane().add(btnVolver);
    	
    	JPanel panel_1 = new JPanel();
    	panel_1.setBackground(Color.GRAY);
    	panel_1.setBounds(28, 96, 1212, 503);
    	MejoresOfertasFrame.getContentPane().add(panel_1);
    	panel_1.setLayout(null);
    	
    	tblOferta = new JTable();
    	tblOferta.setModel(new DefaultTableModel(null, nombreColumnas));
    	spTablero = new JScrollPane(tblOferta);
    	spTablero.setBounds(611, 47, 591, 325);
    	panel_1.add(spTablero);
    	
    	calendario = new JCalendar();
    	calendario.setBounds(23, 47, 546, 256);
    	panel_1.add(calendario);
    	
    	JLabel lblNewLabel = new JLabel("RESULTADO");
    	lblNewLabel.setForeground(Color.BLACK);
    	lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
    	lblNewLabel.setBounds(848, 11, 202, 25);
    	panel_1.add(lblNewLabel);
    	
    	JLabel lblMaximaGanancia = new JLabel("Ganancia total:");
    	lblMaximaGanancia.setForeground(Color.BLACK);
    	lblMaximaGanancia.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
    	lblMaximaGanancia.setBounds(611, 405, 318, 39);
    	panel_1.add(lblMaximaGanancia);
    	calendario.setVisible(true);
    	
    }
	
	private String formatearHora(Integer horario) {
        return String.format("%02d:00hs", horario);
	}
	
	private String formatearMonto(Double monto) {
	    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-AR"));

        return formatoMoneda.format(monto);
	}
	
	private String formatearEquipamientos(ArrayList<String> equipamientos) {
	    String equipamientosStr = "";
	    if(equipamientos != null && !equipamientos.isEmpty()) {
			for (String equipamiento : equipamientos) {
				equipamientosStr += equipamiento + ", ";
			}
		
			return equipamientosStr.substring(0, equipamientosStr.length() - 2);
	    }
	    return "";
	}
	
	private String mostrarFecha(Date fecha) {
		LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.of("es", "ES"));
	    String mesEnEspanol = localDate.format(formatter);
	    return localDate.getDayOfMonth() + " de " + mesEnEspanol + " de " + localDate.getYear();
	}
	
	public void setVisible(boolean b) {
		MejoresOfertasFrame.setVisible(b);
	}
}
