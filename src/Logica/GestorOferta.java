package Logica;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
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
    		 	String carpetaDestino = crearCarpeta(fecha);   
    	        RUTA_ARCHIVO = carpetaDestino + "/oferta" + nombreOfertante + ".txt";
    	        
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
    	String carpetaDestino = obtenerCarpeta(fecha);
    	File folder = new File(carpetaDestino);
    	
        if (folder.isDirectory() && folder.list().length > 0) {
        	
        	String[] files = folder.list();
			ofertas = new ArrayList<Oferta>();
    		for (String fileName : files) {
    			
		        RUTA_ARCHIVO = carpetaDestino + "/" + fileName;
		        
				try {		
			    	fis = new FileInputStream(RUTA_ARCHIVO);
			    	in = new ObjectInputStream(fis);
			    	
	                Oferta oferta = (Oferta) in.readObject();
	                ofertas.add(oferta);

			    	in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	    	}
        }
		return ofertas;
    }
    
    private String crearCarpeta(Date fecha) {
    	LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	String rutaActual = System.getProperty("user.dir");
    	String ruta = rutaActual + "/data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear();
        
	    File carpeta = new File(ruta);
	    
	    if(!(carpeta.exists() && carpeta.isDirectory())) {
	    	carpeta.mkdirs();
	    }
	    
	    return carpeta.getPath();
    }
    
    private String obtenerCarpeta(Date fecha) {
    	LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	String rutaActual = System.getProperty("user.dir");
    	String ruta = rutaActual + "/data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear();
        
	    File carpeta = new File(ruta);
	    
	    if(carpeta.exists() && carpeta.isDirectory()) {
	    	return carpeta.getPath();
	    }
	    return "";
	    
    }
}

