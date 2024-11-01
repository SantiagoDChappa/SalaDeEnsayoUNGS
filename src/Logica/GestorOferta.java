package Logica;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


import Entidades.Oferta;

public class GestorOferta {
	private ArrayList<Oferta> ofertas;
    private FileOutputStream fos;
    private ObjectOutputStream out;
    private FileInputStream fis;
    private ObjectInputStream in;
    private String RUTA_ARCHIVO;

    public GestorOferta() {
    	this.ofertas = new ArrayList<Oferta>();
    }

    
    // Creo la oferta
    public void registrar(String nombreOfertante, Integer horarioInicio, Integer horarioSalida, Double montoOfrecido, Date fecha) {
    	 try {
    	        LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	        RUTA_ARCHIVO = "data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear() + ".txt";
    	        
    	        // Serializo el archivo
        		fos = new FileOutputStream(RUTA_ARCHIVO);
        		out = new ObjectOutputStream(fos);
        				
        		Oferta oferta = new Oferta(nombreOfertante, horarioInicio, horarioSalida, montoOfrecido);
        		
        		out.writeObject(oferta);
        		out.close();
    	    } catch (Exception e) {
    	        e.printStackTrace(); // Manejar errores
    	    }
    }
    
    public ArrayList<Oferta> obtenerOfertas(Date fecha) {
    	LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		RUTA_ARCHIVO = "data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear() + ".txt";
		 
		try {		
			ofertas = new ArrayList<Oferta>();
	    	fis = new FileInputStream(RUTA_ARCHIVO);
	    	in = new ObjectInputStream(fis);
	    	
	    	while (true) {
	            try {
	                Oferta oferta = (Oferta) in.readObject();
	                ofertas.add(oferta);
	            } catch (EOFException e) {
	                break;
	            }
	        }
	    	in.close();
		} catch (Exception e) {

		}
		return ofertas;
    }
}

