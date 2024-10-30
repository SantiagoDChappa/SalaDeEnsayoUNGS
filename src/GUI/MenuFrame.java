package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


public class MenuFrame {
	
	private JFrame MenuFrame;
	private CalendarioFrame CalendarioFrame;
	
	public static void main(String[] args) {
 		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame window = new MenuFrame();
					window.MenuFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MenuFrame() {
		if (MenuFrame != null && MenuFrame.isVisible() == false) {
			MenuFrame.setVisible(true);
		}
		MenuFrame = new JFrame();
		MenuFrame.setSize(1280, 720);
		MenuFrame.setTitle("Sala de ensayo UNGS");
		MenuFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Usuario\\Desktop\\Nueva carpeta\\newCarpet\\Facultad\\Programacion3\\Proyectos\\TP3\\src\\imagenes\\logo_ungs.png"));
		MenuFrame.setForeground(UIManager.getColor("InternalFrame.inactiveTitleForeground"));
		MenuFrame.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MenuFrame.getContentPane().setLayout(null);
		MenuFrame.setLocationRelativeTo(null);
        
		JLabel lblTitulo = new JLabel("Sala de ensayo UNGS");
		lblTitulo.setForeground(new Color(255, 255, 255));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 30));
		lblTitulo.setBounds(0, 57, 1264, 61);
		
		MenuFrame.getContentPane().add(lblTitulo);
		MenuFrame.getContentPane().setLayout(null);
		
		JButton botonRegistrarOferta = new JButton("Registrar oferta");		
		botonRegistrarOferta.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		botonRegistrarOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarOfertaFrame registrarOfertaFrame = new RegistrarOfertaFrame();
				registrarOfertaFrame.setVisible(true);
    			MenuFrame.setVisible(false);
			}
		});
		botonRegistrarOferta.setBounds(480, 159, 321, 81);
		
		MenuFrame.getContentPane().add(botonRegistrarOferta);
		
		JButton botonVerOfertas = new JButton("Ver oferta");
		botonVerOfertas.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		botonVerOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		botonVerOfertas.setBounds(480, 276, 321, 81);
		MenuFrame.getContentPane().add(botonVerOfertas);
		
		JButton botonCalendario = new JButton("Ver calendario");
		botonCalendario.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		botonCalendario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarioFrame = new CalendarioFrame();
				CalendarioFrame.setVisible(true);
				MenuFrame.setVisible(false);
			}
		});
		botonCalendario.setBounds(480, 391, 321, 81);
		MenuFrame.getContentPane().add(botonCalendario);
		
		JButton botonVerMejoresOfertas = new JButton("Ver las mejores ofertas");
		botonVerMejoresOfertas.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		botonVerMejoresOfertas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		botonVerMejoresOfertas.setBounds(480, 509, 321, 81);
		MenuFrame.getContentPane().add(botonVerMejoresOfertas);
		
		MenuFrame.getContentPane().setBackground(new Color(14, 16, 18));

	};
	
	// SetVisible
	public void setVisible(boolean b) {
		MenuFrame.setVisible(b);
	}
	
	public void cerrar() {
		try {
			MenuFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			MenuFrame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			MenuFrame.setVisible(true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
