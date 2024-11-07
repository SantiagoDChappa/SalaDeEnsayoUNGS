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
import java.util.ArrayList;
import java.util.Date;

//Add this line at the beginning of your file

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import org.w3c.dom.events.EventException;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import Logica.GestorOferta;

public class RegistrarOfertaFrame {
	
	private JFrame RegistrarOfertaFrame;
	private GestorOferta GestorOfertas;
	private JButton btnEnviarOferta, btnBorrar, btnVolver;
    private JPanel panel;
    private JLabel lblTitulo, lblHorarioDeInicio, lblHorarioDeSalida, lblPrecio, lblNombreDelOfertante, lblFecha, lblEquipamientoAUtilizar, lblHs, lblHs_1;
    private JTextField txtMontoOfrecido, txtFecha, txtNombreOfertante;
    private JCalendar calendario;
    private JComboBox cbxHorarioInicio, cbxHorarioSalida;
    private JCheckBox chbxCoros, chbxVientos, chbxBajo, chbxTeclado, chbxGuitarra, chbxBateria;
    private LocalDate localDate;
    private ArrayList<String> equipamientos;

	
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
       	panel.setBounds(50, 92, 1204, 538);
    	panel.setLayout(null);
    	RegistrarOfertaFrame.getContentPane().add(panel);

        txtMontoOfrecido = new JTextField();
        txtMontoOfrecido.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        txtMontoOfrecido.setColumns(10);
        txtMontoOfrecido.setBounds(176, 119, 169, 32);
        
        txtNombreOfertante = new JTextField();
        txtNombreOfertante.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        txtNombreOfertante.setColumns(10);
        txtNombreOfertante.setBounds(327, 74, 169, 34);
        panel.add(txtNombreOfertante);
        
        txtFecha = new JTextField();
        txtFecha.setBounds(689, 355, 216, 34);
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
    	lblHorarioDeInicio.setBounds(101, 174, 180, 34);
    	panel.add(lblHorarioDeInicio);
    	lblHorarioDeInicio.setForeground(Color.WHITE);
    	lblHorarioDeInicio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	
    	lblHorarioDeSalida = new JLabel("Horario de salida");
    	lblHorarioDeSalida.setForeground(Color.WHITE);
    	lblHorarioDeSalida.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblHorarioDeSalida.setBounds(101, 240, 180, 34);
    	panel.add(lblHorarioDeSalida);
    	
    	lblPrecio = new JLabel("Precio");
    	lblPrecio.setForeground(Color.WHITE);
    	lblPrecio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
    	lblPrecio.setBounds(101, 119, 180, 32);
    	panel.add(lblPrecio);
    	
        lblNombreDelOfertante = new JLabel("Nombre del ofertante");
        lblNombreDelOfertante.setForeground(Color.WHITE);
        lblNombreDelOfertante.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblNombreDelOfertante.setBounds(101, 74, 216, 34);
        panel.add(lblNombreDelOfertante);
        
        lblFecha = new JLabel("Fecha");
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblFecha.setBounds(600, 355, 90, 34);
        panel.add(lblFecha);
    	
    	lblTitulo = new JLabel("Registrar oferta");
    	lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    	lblTitulo.setForeground(new Color(255, 255, 255));
    	lblTitulo.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 30));
    	lblTitulo.setBounds(498, 27, 235, 54);
    	RegistrarOfertaFrame.getContentPane().add(lblTitulo);
    	

    	// -------------------------- CALENDARIO ---------------------------------
        calendario = new JCalendar();
        calendario.setBounds(600, 74, 547, 270);
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
        			agregarEquipamientos();

        			GestorOfertas.registrar(txtNombreOfertante.getText(), cbxHorarioInicio.getSelectedItem().toString(), cbxHorarioSalida.getSelectedItem().toString(), txtMontoOfrecido.getText(), calendario.getDate(), equipamientos);
        			
        			JOptionPane.showMessageDialog(null, "Se registro la oferta correctamente!");
        			reiniciarValores();
        			
        		} catch (NumberFormatException exception) {
        			JOptionPane.showMessageDialog(null, "¡Error al registrar oferta! " + exception.getMessage());
        		}catch (Exception exception) {
     				JOptionPane.showMessageDialog(null, "¡Error al registrar oferta! " + exception.getMessage());
        		}
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
        cbxHorarioInicio.setBounds(280, 174, 77, 32);
        panel.add(cbxHorarioInicio);
        
        cbxHorarioSalida = new JComboBox<>(horarios);
        cbxHorarioSalida.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
        cbxHorarioSalida.setBounds(280, 240, 77, 32);
        panel.add(cbxHorarioSalida);
        
        lblHs = new JLabel("hs");
        lblHs.setForeground(Color.WHITE);
        lblHs.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblHs.setBounds(366, 174, 56, 34);
        panel.add(lblHs);
        
        lblHs_1 = new JLabel("hs");
        lblHs_1.setForeground(Color.WHITE);
        lblHs_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblHs_1.setBounds(367, 240, 56, 34);
        panel.add(lblHs_1);
        
        lblEquipamientoAUtilizar = new JLabel("Equipamiento a utilizar");
        lblEquipamientoAUtilizar.setForeground(Color.WHITE);
        lblEquipamientoAUtilizar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        lblEquipamientoAUtilizar.setBounds(101, 300, 228, 32);
        panel.add(lblEquipamientoAUtilizar);
        
        chbxGuitarra = new JCheckBox("Guitarra");
        chbxGuitarra.setBounds(111, 339, 97, 23);
        panel.add(chbxGuitarra);
        
        chbxBateria = new JCheckBox("Bateria");
        chbxBateria.setBounds(220, 339, 97, 23);
        panel.add(chbxBateria);
        
        chbxTeclado = new JCheckBox("Teclado");
        chbxTeclado.setBounds(111, 365, 97, 23);
        panel.add(chbxTeclado);
        
        chbxBajo = new JCheckBox("Bajo");
        chbxBajo.setBounds(220, 365, 97, 23);
        panel.add(chbxBajo);
        
        chbxVientos = new JCheckBox("Vientos");
        chbxVientos.setBounds(111, 391, 97, 23);
        panel.add(chbxVientos);
        
        chbxCoros = new JCheckBox("Coros");
        chbxCoros.setBounds(220, 391, 97, 23);
        panel.add(chbxCoros);
        
	}
	
	private void reiniciarValores() {
		cbxHorarioInicio.setSelectedIndex(0);
		cbxHorarioSalida.setSelectedIndex(0);
		txtMontoOfrecido.setText("0");
		txtNombreOfertante.setText("");
    }
		
	private void agregarEquipamientos() {
		equipamientos = new ArrayList<>();
		
		if(chbxGuitarra.isSelected()) {
			equipamientos.add("Guitarra");
		}
		if(chbxBateria.isSelected()) {
			equipamientos.add("Bateria");
		}
		if(chbxTeclado.isSelected()) {
			equipamientos.add("Teclado");
		}
		if(chbxBajo.isSelected()) {
			equipamientos.add("Bajo");
		}
		if(chbxVientos.isSelected()) {
			equipamientos.add("Vientos");
		}
		if(chbxCoros.isSelected()) {
			equipamientos.add("Coros");
		}
    }
	
	public void setVisible(boolean b) {
		RegistrarOfertaFrame.setVisible(b);
	}
}
