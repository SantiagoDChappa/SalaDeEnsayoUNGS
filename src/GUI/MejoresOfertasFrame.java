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
import java.util.List;
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

public class MejoresOfertasFrame {

	private String[] nombreColumnas = { "Nombre del ofertante", "Horario inicio", "Horario salida", "Monto Ofrecido",
			"Equipamientos" };
	private JFrame MejoresOfertasFrame;
	private JCalendar calendario;
	private JButton btnVolver, btnAlgoritmoGoloso, btnAlgoritmoPolinomial;
	private JTable tblOferta;
	private JScrollPane spTablero;
	private GestorOferta GestorOfertas;
	private JLabel lblResultado, lblMaximaGanancia, lblCantidadDeOfertas, lblMejoresOfertasFecha, lblAlgoritmoUtilizado,
			lblTiempoDeEjecucion;
	private JPanel panel_1;

	public MejoresOfertasFrame() {
		initialize();
	}

	private void initialize() {
		GestorOfertas = new GestorOferta();
		MejoresOfertasFrame = new JFrame();
		MejoresOfertasFrame.setTitle("Buscar ofertas por fechas - Sala de ensayo UNGS");
		MejoresOfertasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MejoresOfertasFrame.setBounds(100, 100, 592, 401);
		MejoresOfertasFrame.setSize(1280, 720);
		MejoresOfertasFrame.setLocationRelativeTo(null);
		MejoresOfertasFrame.getContentPane().setLayout(null);
		MejoresOfertasFrame.getContentPane().setBackground(new Color(14, 16, 18));

		// -------------------------------------------------------------------------
		// ------------------------- Labels ----------------------------------------
		// -------------------------------------------------------------------------

		lblResultado = new JLabel("RESULTADO");
		lblResultado.setForeground(Color.BLACK);
		lblResultado.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
		lblResultado.setBounds(848, 11, 202, 25);
		panel_1.add(lblResultado);

		lblMaximaGanancia = new JLabel("Ganancia total:");
		lblMaximaGanancia.setForeground(Color.BLACK);
		lblMaximaGanancia.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
		lblMaximaGanancia.setBounds(611, 402, 512, 39);
		panel_1.add(lblMaximaGanancia);

		lblCantidadDeOfertas = new JLabel("Cantidad de ofertas:");
		lblCantidadDeOfertas.setForeground(Color.BLACK);
		lblCantidadDeOfertas.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
		lblCantidadDeOfertas.setBounds(611, 447, 318, 39);
		panel_1.add(lblCantidadDeOfertas);

		lblAlgoritmoUtilizado = new JLabel("Algoritmo utilizado: ");
		lblAlgoritmoUtilizado.setForeground(Color.BLACK);
		lblAlgoritmoUtilizado.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
		lblAlgoritmoUtilizado.setBounds(23, 409, 620, 27);
		panel_1.add(lblAlgoritmoUtilizado);

		lblTiempoDeEjecucion = new JLabel("Tiempo de ejecucion: ");
		lblTiempoDeEjecucion.setForeground(Color.BLACK);
		lblTiempoDeEjecucion.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 23));
		lblTiempoDeEjecucion.setBounds(23, 452, 620, 27);
		panel_1.add(lblTiempoDeEjecucion);
		calendario.setVisible(true);

		lblMejoresOfertasFecha = new JLabel("Las mejores ofertas del dia: ");
		lblMejoresOfertasFecha.setForeground(new Color(255, 255, 255));
		lblMejoresOfertasFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
		lblMejoresOfertasFecha.setBounds(42, 59, 620, 27);
		MejoresOfertasFrame.getContentPane().add(lblMejoresOfertasFecha);

		// -------------------------------------------------------------------------
		// ------------------------- Panel -----------------------------------------
		// -------------------------------------------------------------------------
		panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(28, 96, 1212, 503);
		MejoresOfertasFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		// -------------------------------------------------------------------------
		// ------------------------- Botones ---------------------------------------
		// -------------------------------------------------------------------------

		btnAlgoritmoGoloso = new JButton("Algoritmo Goloso");
		btnAlgoritmoGoloso.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
		btnAlgoritmoGoloso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date fechaSeleccionada = calendario.getDate();
				double gananciaTotal = 0.0;

				lblMejoresOfertasFecha.setText("Las mejores ofertas del dia: " + mostrarFecha(fechaSeleccionada));
				ArrayList<Oferta> todasLasOfertas = GestorOfertas.obtenerOfertas(fechaSeleccionada);
				ArrayList<Oferta> mejoresOfertas = GestorOfertas.resolverAdjudicacion(todasLasOfertas);

				mostrarOfertas(mejoresOfertas);
				gananciaTotal = GestorOfertas.obtenerGananciaTotales(mejoresOfertas);

				lblAlgoritmoUtilizado.setText("Algoritmo utilizado: Goloso");
				lblTiempoDeEjecucion.setText("Tiempo de ejecucion: " + GestorOfertas.obtenerTiempoEjecutado() + " ms");
				lblCantidadDeOfertas.setText("Cantidad de ofertas: " + mejoresOfertas.size());
				lblMaximaGanancia.setText("Ganancia total: " + formatearMonto(gananciaTotal));
			}
		});
		btnAlgoritmoGoloso.setBounds(428, 610, 183, 38);
		MejoresOfertasFrame.getContentPane().add(btnAlgoritmoGoloso);

		btnAlgoritmoPolinomial = new JButton("Algoritmo Polinomial");
		btnAlgoritmoPolinomial.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
		btnAlgoritmoPolinomial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date fechaSeleccionada = calendario.getDate();
				double gananciaTotal = 0.0;

				lblMejoresOfertasFecha.setText("Las mejores ofertas del dia: " + mostrarFecha(fechaSeleccionada));
				ArrayList<Oferta> todasLasOfertas = GestorOfertas.obtenerOfertas(fechaSeleccionada);
				ArrayList<Oferta> mejoresOfertas = GestorOfertas.seleccionarOfertasMaxGananciaDP(todasLasOfertas);

				mostrarOfertas(mejoresOfertas);
				gananciaTotal = GestorOfertas.obtenerGananciaTotales(mejoresOfertas);

				lblAlgoritmoUtilizado.setText("Algoritmo utilizado: Polinomial");
				lblTiempoDeEjecucion.setText("Tiempo de ejecucion: " + GestorOfertas.obtenerTiempoEjecutado() + " ms");
				lblCantidadDeOfertas.setText("Cantidad de ofertas: " + mejoresOfertas.size());
				lblMaximaGanancia.setText("Ganancia total: " + formatearMonto(gananciaTotal));
			}
		});
		btnAlgoritmoPolinomial.setBounds(628, 610, 183, 38);
		MejoresOfertasFrame.getContentPane().add(btnAlgoritmoPolinomial);

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

		// -------------------------------------------------------------------------
		// ------------------------- Tablero ---------------------------------------
		// -------------------------------------------------------------------------

		tblOferta = new JTable();
		tblOferta.setModel(new DefaultTableModel(null, nombreColumnas));
		spTablero = new JScrollPane(tblOferta);
		spTablero.setBounds(611, 47, 591, 325);
		panel_1.add(spTablero);

		// -------------------------------------------------------------------------
		// ------------------------- Calendario ------------------------------------
		// -------------------------------------------------------------------------

		calendario = new JCalendar();
		calendario.setBounds(23, 47, 546, 325);
		panel_1.add(calendario);

	}

	// -------------------------------------------------------------------------
	// ------------------------- Funciones auxiliares --------------------------
	// -------------------------------------------------------------------------

	private void mostrarOfertas(ArrayList<Oferta> mejoresOfertas) {
		DefaultTableModel model = (DefaultTableModel) tblOferta.getModel();
		model.setRowCount(0); // Limpiar la tabla antes de agregar nuevas filas

		for (Oferta oferta : mejoresOfertas) {
			model.addRow(new Object[] { oferta.obtenerNombreOfertante(), formatearHora(oferta.obtenerHorarioInicio()),
					formatearHora(oferta.obtenerHorarioSalida()), formatearMonto(oferta.obtenerMontoOfrecido()),
					formatearEquipamientos(oferta.obtenerEquipamientos()) });
		}
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
		if (equipamientos != null && !equipamientos.isEmpty()) {
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
