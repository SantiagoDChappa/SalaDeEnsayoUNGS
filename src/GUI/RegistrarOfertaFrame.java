package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//Add this line at the beginning of your file

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Logica.GestorOferta;

public class RegistrarOfertaFrame {
	
	private JFrame RegistrarOfertaFrame;
	private GestorOferta GestorOfertas;
	private JButton btnEnviarOferta, btnBorrar, btnVolver;
    private JPanel panel;
    private JLabel lblTitulo, lblHorarioDeInicio, lblHorarioDeSalida, lblPrecio, lblNombreDelOfertante, lblFecha;
    private JTextField txtHorarioInicio, txtHorarioSalida, txtMontoOfrecido, txtFecha, txtNombreOfertante;
    private JCalendar calendario;
    private JComboBox cbxHorarioInicio, cbxHorarioSalida;
    private LocalDate localDate;
    private NumberFormat currencyFormat;

	
	public RegistrarOfertaFrame() {
    	initialize();
    }
    
	private void initialize() {
        GestorOfertas = new GestorOferta();
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
    	
        panel = new JPanel();
    	panel.setBackground(new Color(44, 62, 80));
       	panel.setBounds(26, 107, 1204, 538);
    	panel.setLayout(null);
    	RegistrarOfertaFrame.getContentPane().add(panel);

        txtMontoOfrecido = new JTextField();
        txtMontoOfrecido.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        txtMontoOfrecido.setColumns(10);
        txtMontoOfrecido.setBounds(177, 164, 169, 32);
        
        txtNombreOfertante = new JTextField();
        txtNombreOfertante.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        txtNombreOfertante.setColumns(10);
        txtNombreOfertante.setBounds(328, 109, 169, 34);
        panel.add(txtNombreOfertante);
        
        txtFecha = new JTextField();
        txtFecha.setBounds(660, 390, 216, 34);
        txtFecha.setColumns(10);
        localDate = LocalDate.now();
        txtFecha.setText(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear());
        txtFecha.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String dateStr = txtFecha.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                try {
                    Date date = sdf.parse(dateStr);
                    calendario.setDate(date);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy");
                }
        	}
        });
        panel.add(txtFecha);
        
        txtMontoOfrecido.getDocument().addDocumentListener(new DocumentListener() {
        	 private boolean isUpdating = false; // Para evitar recursividad infinita

    	    private void formatText() {
    	        if (isUpdating) return; // Evita múltiples llamadas recursivas
    	        isUpdating = true;

    	        SwingUtilities.invokeLater(() -> {
    	            String originalText = txtMontoOfrecido.getText().replaceAll("\\.", ""); // Elimina puntos existentes
    	            if (!originalText.isEmpty()) {
    	                try {
    	                    // Convierte el texto a un número
    	                    long number = Long.parseLong(originalText);
    	                    // Formatea el número con puntos como separadores de miles
    	                    NumberFormat numberFormat = new DecimalFormat("#,###");
    	                    String formattedText = numberFormat.format(number).replace(",", ".");

    	                    // Guarda la posición original del cursor
    	                    int originalCaretPosition = txtMontoOfrecido.getCaretPosition();
    	                    int newCaretPosition = formattedText.length();

    	                    // Actualiza el texto solo si es necesario
    	                    if (!formattedText.equals(txtMontoOfrecido.getText())) {
    	                        txtMontoOfrecido.setText(formattedText);

    	                        // Si el cursor estaba al final antes de formatear, mantenlo al final
    	                        if (originalCaretPosition == originalText.length()) {
    	                            txtMontoOfrecido.setCaretPosition(newCaretPosition);
    	                        } else {
    	                            // Ajusta la posición para que esté al final del texto actualizado
    	                            txtMontoOfrecido.setCaretPosition(newCaretPosition);
    	                        }
    	                    }
    	                } catch (NumberFormatException e) {
    	                    // Maneja la excepción si el texto no es un número válido
    	                }
    	            }
    	            isUpdating = false; // Restablece la bandera
    	        });
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                formatText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                formatText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                formatText();
            }
        });
        panel.add(txtMontoOfrecido);

    	// ---------------------------- LABELS -------------------------------------
    	lblHorarioDeInicio = new JLabel("Horario de inicio");
    	lblHorarioDeInicio.setBounds(102, 225, 180, 34);
    	panel.add(lblHorarioDeInicio);
    	lblHorarioDeInicio.setForeground(Color.WHITE);
    	lblHorarioDeInicio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	
    	lblHorarioDeSalida = new JLabel("Horario de salida");
    	lblHorarioDeSalida.setForeground(Color.WHITE);
    	lblHorarioDeSalida.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblHorarioDeSalida.setBounds(102, 291, 180, 34);
    	panel.add(lblHorarioDeSalida);
    	
    	lblPrecio = new JLabel("Precio");
    	lblPrecio.setForeground(Color.WHITE);
    	lblPrecio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblPrecio.setBounds(102, 164, 180, 32);
    	panel.add(lblPrecio);
    	
        lblNombreDelOfertante = new JLabel("Nombre del ofertante");
        lblNombreDelOfertante.setForeground(Color.WHITE);
        lblNombreDelOfertante.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblNombreDelOfertante.setBounds(102, 109, 216, 34);
        panel.add(lblNombreDelOfertante);
        
        lblFecha = new JLabel("Fecha");
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblFecha.setBounds(571, 390, 90, 34);
        panel.add(lblFecha);
    	
    	lblTitulo = new JLabel("Registrar oferta");
    	lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    	lblTitulo.setForeground(new Color(255, 255, 255));
    	lblTitulo.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 30));
    	lblTitulo.setBounds(498, 27, 235, 54);
    	RegistrarOfertaFrame.getContentPane().add(lblTitulo);
    	

    	// -------------------------- CALENDARIO ---------------------------------
        calendario = new JCalendar();
        calendario.setBounds(571, 109, 547, 270);
        calendario.setVisible(true);
        calendario.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
            	LocalDate localDate = calendario.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                txtFecha.setText(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear());
            }
        });
        panel.add(calendario);
        

        // --------------------------- BOTONES ------------------------------------
    	btnVolver = new JButton("Volver");
    	btnVolver.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
    	btnVolver.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			MenuFrame menuFrame = new MenuFrame();
    			menuFrame.setVisible(true);
    			RegistrarOfertaFrame.setVisible(false);
    		}
    	});
    	btnVolver.setBounds(26, 25, 113, 38);
    	RegistrarOfertaFrame.getContentPane().add(btnVolver);
    	
        btnEnviarOferta = new JButton("Enviar Oferta");
        btnEnviarOferta.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        btnEnviarOferta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			Integer valorHorarioInicio = convertirHora(cbxHorarioInicio.getSelectedItem().toString());
        			Integer valorHorarioSalida = convertirHora(cbxHorarioSalida.getSelectedItem().toString());
        			Double valorMontoOfrecido = Double.parseDouble(txtMontoOfrecido.getText().replace(".", ""));
        			
        			GestorOfertas.registrar(txtNombreOfertante.getText(), valorHorarioInicio, valorHorarioSalida, valorMontoOfrecido, calendario.getDate());					
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null, "¡Error al registrar oferta! " + exception.getMessage());
				}
        		JOptionPane.showMessageDialog(null, "Se registro la oferta correctamente!");
        		reiniciarValores();
        	}
        });
        btnEnviarOferta.setBounds(600, 481, 137, 34);
        panel.add(btnEnviarOferta);
        
        btnBorrar = new JButton("Borrar");
        btnBorrar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        btnBorrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		reiniciarValores();
        	}
        });
        btnBorrar.setBounds(406, 481, 137, 34);
        panel.add(btnBorrar);
        
        // --------------------- Combo Box ------------------------
        String[] horarios = new String[24];
        for (int i = 0; i < 24; i++) {
            horarios[i] = String.format("%02d:00", i);
        }

        cbxHorarioInicio = new JComboBox<>(horarios);
        cbxHorarioInicio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        cbxHorarioInicio.setBounds(281, 225, 77, 32);
        panel.add(cbxHorarioInicio);
        
        cbxHorarioSalida = new JComboBox<>(horarios);
        cbxHorarioSalida.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        cbxHorarioSalida.setBounds(281, 291, 77, 32);
        panel.add(cbxHorarioSalida);
        
        JLabel lblHs = new JLabel("hs");
        lblHs.setForeground(Color.WHITE);
        lblHs.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblHs.setBounds(367, 225, 56, 34);
        panel.add(lblHs);
        
        JLabel lblHs_1 = new JLabel("hs");
        lblHs_1.setForeground(Color.WHITE);
        lblHs_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblHs_1.setBounds(368, 291, 56, 34);
        panel.add(lblHs_1);
        
	}
	
	private void reiniciarValores() {
		cbxHorarioInicio.setSelectedIndex(0);
		cbxHorarioSalida.setSelectedIndex(0);
		txtMontoOfrecido.setText("0");
		txtNombreOfertante.setText("");
    }
	
	private int convertirHora(String hora) {
	    // Eliminar los ceros iniciales y la parte de minutos
	    String horaSolo = hora.split(":")[0]; // Obtiene la parte antes de los dos puntos
	    return Integer.parseInt(horaSolo);    // Convierte a entero
	}
	
	public void setVisible(boolean b) {
		RegistrarOfertaFrame.setVisible(b);
	}
}
