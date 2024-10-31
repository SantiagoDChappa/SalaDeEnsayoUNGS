package Logica;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    public void registrar(Integer horarioInicio, Integer horarioSalida, Double montoOfrecido, Date fecha) {
    	try {
    		LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    		RUTA_ARCHIVO = "data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear() + ".txt";
    		
    		// Serializo el archivo
    		fos = new FileOutputStream(RUTA_ARCHIVO);
    		out = new ObjectOutputStream(fos);
    				
    		Oferta oferta = new Oferta(horarioInicio, horarioSalida, montoOfrecido);
    		
    		out.writeObject(oferta);

    		out.close();
    		fos.close();
    		
    	} catch (NumberFormatException e) {
    	} catch (Exception e) {
    	}
    }
    
    public ArrayList<Oferta> obtenerOfertas(Date fecha) {
    	LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		RUTA_ARCHIVO = "data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear() + ".txt";
		try {
	    	fis = new FileInputStream(RUTA_ARCHIVO);
	    	in = new ObjectInputStream(fis);
	    	
	    	while (true) {
	            try {
	                Oferta oferta = (Oferta) in.readObject();
	                ofertas.add(oferta);
	            } catch (EOFException e) {
	                break; // End of file reached
	            }
	        }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ofertas;
    }
}

