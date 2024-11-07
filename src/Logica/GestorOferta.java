package Logica;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Entidades.Oferta;

public class GestorOferta implements Serializable{
	private ArrayList<Oferta> ofertas;
    private FileOutputStream fos;
    private ObjectOutputStream out;
    private FileInputStream fis;
    private ObjectInputStream in;
    private String RUTA_ARCHIVO;
    private Integer horaInicioInt, horaSalidaInt;
    private Double valorMontoOfrecido;
	private static final long serialVersionUID = 1L;


    public GestorOferta() {
    	this.ofertas = new ArrayList<Oferta>();
    }

    
    // Creo la oferta
    public void registrar(String nombreOfertante, String horarioInicio, String horarioSalida, String montoOfrecido, Date fecha, ArrayList<String> equipamientos) {
		horaInicioInt = convertirHora(horarioInicio);
		horaSalidaInt = convertirHora(horarioSalida);
		valorMontoOfrecido = (!montoOfrecido.equals("")) ? Double.parseDouble(montoOfrecido.replace(".", "")) : 0;
		
		verificarDatos(nombreOfertante, equipamientos);

		Oferta oferta = new Oferta(nombreOfertante, horaInicioInt, horaSalidaInt, valorMontoOfrecido, equipamientos);
	 
	 	String carpetaDestino = crearCarpeta(fecha);   
        RUTA_ARCHIVO = carpetaDestino + "/oferta" + nombreOfertante + ".txt";
        
        escribirArchivo(oferta, RUTA_ARCHIVO);
    }
    
    public ArrayList<Oferta> obtenerOfertas(Date fecha) {
    	String carpetaDestino = obtenerCarpeta(fecha);
    	File folder = new File(carpetaDestino);
    	
        if (folder.isDirectory() && folder.list().length > 0) {
        	
        	String[] files = folder.list();
			ofertas = new ArrayList<Oferta>();
    		for (String fileName : files) {
		        RUTA_ARCHIVO = carpetaDestino + "/" + fileName;
		      
				Oferta oferta = leerArchivo(RUTA_ARCHIVO);
                ofertas.add(oferta);
	    	}
        }
		return ofertas;
    }
    
	private int convertirHora(String hora) {
	    String horaSolo = hora.split(":")[0];
	    return Integer.parseInt(horaSolo);
	}
    
    // ----------------------------------- ARCHIVO ---------------------------------------
    
    private void escribirArchivo(Oferta oferta, String direccionDestino) {
    	try {
    		fos = new FileOutputStream(direccionDestino);
    		out = new ObjectOutputStream(fos);        			
    		
    		out.writeObject(oferta);
    		out.close();			
		} catch (Exception e) {
            e.printStackTrace(); // Puedes manejar los errores con más detalle si es necesario
		}
    }
    
    private Oferta leerArchivo(String rutaArchivo) {
        try {
        	fis = new FileInputStream(rutaArchivo);
            in = new ObjectInputStream(fis);
            
            return (Oferta) in.readObject();
        } catch (EOFException e) {
            // Archivo vacío, manejar si es necesario
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return null;
    }
    
    // ----------------------------------- CARPETA ---------------------------------------
    
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
    
    // --------------------------------- Adjudicacion -------------------------------------- 
    public List<Oferta> resolverAdjudicacion(List<Oferta> ofertas) {
		List <Oferta> adjudicadas = new ArrayList<>();
		int horaActual = 0;
		
		for(Oferta oferta : ofertas) {
			if(oferta.obtenerHorarioInicio() >= horaActual) {
				adjudicadas.add(oferta);
				horaActual = oferta.obtenerHorarioSalida();
			}
		}
		return adjudicadas;
	}
    
	public int calcularDuracionEnHoras(Oferta o) {
		return o.obtenerHorarioInicio() - o.obtenerHorarioSalida();
	}
	
	
	private void verificarDatos(String nombreOfertante, ArrayList<String> equipamientos) {
			if (nombreOfertante.equals("")) throw new NumberFormatException("El nombre del ofertante no puede estar vacío");
			
			if (valorMontoOfrecido <= 0) throw new NumberFormatException("El monto ofrecido debe ser mayor a 0");
			
			if (horaInicioInt == horaSalidaInt) throw new NumberFormatException("El horario de inicio y salida no pueden ser iguales");
			
			if (horaInicioInt >= horaSalidaInt) throw new NumberFormatException("El horario de inicio debe ser menor al de salida");	
			
			if(equipamientos.isEmpty()) throw new NumberFormatException("Debe seleccionar al menos un equipamiento");
	}
	
}

