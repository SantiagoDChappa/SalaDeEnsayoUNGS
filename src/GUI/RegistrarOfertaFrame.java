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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Logica.CalendarioLogica;
import Logica.RegistrarOfertaLogica;

public class RegistrarOfertaFrame {
	
	private JFrame RegistrarOfertaFrame;
	private JDateChooser buscadorFecha;
	private RegistrarOfertaLogica RegistarOferta;
    
	public RegistrarOfertaFrame() {
    	initialize();
    }
    
	private void initialize() {
		RegistarOferta = new RegistrarOfertaLogica();
		
		RegistrarOfertaFrame = new JFrame();
		RegistrarOfertaFrame.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		RegistrarOfertaFrame.getContentPane().setBackground(new Color(14, 16, 18));
		RegistrarOfertaFrame.setForeground(UIManager.getColor("InternalFrame.inactiveTitleForeground"));
	    RegistrarOfertaFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Usuario\\Desktop\\Nueva carpeta\\newCarpet\\Facultad\\Programacion3\\Proyectos\\TP3\\src\\imagenes\\logo_ungs.png"));
	    RegistrarOfertaFrame.setTitle("Registrar oferta - Sala de ensayo UNGS");
	    RegistrarOfertaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    RegistrarOfertaFrame.setBounds(100, 100, 592, 401);
	    RegistrarOfertaFrame.setSize(1280, 720);
    	RegistrarOfertaFrame.setLocationRelativeTo(null);
    	RegistrarOfertaFrame.getContentPane().setLayout(null); 
    	
    	JButton btnVolver = new JButton("Volver");
    	btnVolver.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
    	btnVolver.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			MenuFrame menuFrame = new MenuFrame();
    			menuFrame.setVisible(true);
    			RegistrarOfertaFrame.setVisible(false);
    		}
    	});
    	btnVolver.setBounds(10, 11, 113, 38);
    	RegistrarOfertaFrame.getContentPane().add(btnVolver);
    	
    	JLabel lblNewLabel = new JLabel("Registrar oferta");
    	lblNewLabel.setForeground(new Color(255, 255, 255));
    	lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 26));
    	lblNewLabel.setBounds(522, 22, 235, 54);
    	RegistrarOfertaFrame.getContentPane().add(lblNewLabel);
    	
    	JPanel panel = new JPanel();
    	panel.setBackground(new Color(44, 62, 80));
       	panel.setBounds(26, 107, 1204, 538);
    	RegistrarOfertaFrame.getContentPane().add(panel);
    	panel.setLayout(null);
    	
    	JLabel lblHorarioDeInicio = new JLabel("Horario de inicio");
    	lblHorarioDeInicio.setBounds(102, 109, 180, 34);
    	panel.add(lblHorarioDeInicio);
    	lblHorarioDeInicio.setForeground(Color.WHITE);
    	lblHorarioDeInicio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	
    	JTextField txtHorarioInicio = new JTextField();
    	txtHorarioInicio.setBounds(281, 109, 169, 34);
    	txtHorarioInicio.setColumns(10);
    	panel.add(txtHorarioInicio);
    	
    	JLabel lblHorarioDeSalida = new JLabel("Horario de salida");
    	lblHorarioDeSalida.setForeground(Color.WHITE);
    	lblHorarioDeSalida.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblHorarioDeSalida.setBounds(102, 175, 180, 34);
    	panel.add(lblHorarioDeSalida);
    	
    	JTextField txtHorarioSalida = new JTextField();
    	txtHorarioSalida.setColumns(10);
    	txtHorarioSalida.setBounds(281, 175, 169, 34);
    	panel.add(txtHorarioSalida);
    	
        JCalendar calendario = new JCalendar();
        calendario.setBounds(571, 109, 547, 270);
        calendario.setVisible(true);
        panel.add(calendario);
        
        JLabel lblPrecio = new JLabel("Precio");
        lblPrecio.setForeground(Color.WHITE);
        lblPrecio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblPrecio.setBounds(206, 243, 180, 32);
        panel.add(lblPrecio);
        
        JTextField txtPrecio = new JTextField();
        txtPrecio.setColumns(10);
        txtPrecio.setBounds(281, 243, 169, 32);
        panel.add(txtPrecio);
        
        JLabel lblNombreDelOfertante = new JLabel("Nombre del ofertante");
        lblNombreDelOfertante.setForeground(Color.WHITE);
        lblNombreDelOfertante.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblNombreDelOfertante.setBounds(55, 311, 216, 34);
        panel.add(lblNombreDelOfertante);
        
        JTextField txtNombreOfertante = new JTextField();
        txtNombreOfertante.setColumns(10);
        txtNombreOfertante.setBounds(281, 311, 169, 34);
        panel.add(txtNombreOfertante);
        
        JTextField txtFecha = new JTextField();
        txtFecha.setBounds(660, 390, 216, 34);
        panel.add(txtFecha);
        txtFecha.setColumns(10);
        
        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblFecha.setBounds(571, 390, 90, 34);
        panel.add(lblFecha);
        
        JButton btnEnviarOferta = new JButton("Enviar Oferta");
        btnEnviarOferta.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        btnEnviarOferta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnEnviarOferta.setBounds(509, 493, 137, 34);
        panel.add(btnEnviarOferta);
        
    }
	
	public void setVisible(boolean b) {
		RegistrarOfertaFrame.setVisible(b);
	}
}
