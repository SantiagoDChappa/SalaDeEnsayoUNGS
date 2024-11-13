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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import Entidades.Oferta;

public class GestorOferta implements Serializable {
	private ArrayList<Oferta> ofertas, ofertasAdjudicadas;
	private FileOutputStream fos;
	private ObjectOutputStream out;
	private FileInputStream fis;
	private ObjectInputStream in;
	private String RUTA_ARCHIVO;
	private Integer horaInicioInt, horaSalidaInt;
	private Double valorMontoOfrecido, tiempoEjecutado;
	private static final long serialVersionUID = 1L;

	public GestorOferta() {
		this.ofertas = new ArrayList<Oferta>();
	}
    
	// -------------------------------------------------------------------------
	// ------------------------- Crear Ofertas --------------------------------
	// -------------------------------------------------------------------------
	public void registrar(String nombreOfertante, String horarioInicio, String horarioSalida, String montoOfrecido,
			Date fecha, ArrayList<String> equipamientos) {
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
    		// Ordenar las ofertas por horarioSalida de forma creciente
            Collections.sort(ofertas, new Comparator<Oferta>() {
                @Override
                public int compare(Oferta o1, Oferta o2) {
                    return Integer.compare(o1.obtenerHorarioSalida(), o2.obtenerHorarioSalida());
                }
            });
		}
		return ofertas;
	}

	// -------------------------------------------------------------------------
	// ------------------------- Manejo de archivos ----------------------------
	// -------------------------------------------------------------------------
	private void escribirArchivo(Oferta oferta, String direccionDestino) {
		try {
			fos = new FileOutputStream(direccionDestino);
			out = new ObjectOutputStream(fos);

			out.writeObject(oferta);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Oferta leerArchivo(String rutaArchivo) {
		try {
			fis = new FileInputStream(rutaArchivo);
			in = new ObjectInputStream(fis);

			return (Oferta) in.readObject();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// -------------------------------------------------------------------------
	// ------------------------- Manejo de carpetas ----------------------------
	// -------------------------------------------------------------------------
	private String crearCarpeta(Date fecha) {
		LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String rutaActual = System.getProperty("user.dir");
		String ruta = rutaActual + "/data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-"
				+ localFecha.getYear();

		File carpeta = new File(ruta);

		if (!(carpeta.exists() && carpeta.isDirectory())) {
			carpeta.mkdirs();
		}

		return carpeta.getPath();
	}

	private String obtenerCarpeta(Date fecha) {
		LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String rutaActual = System.getProperty("user.dir");
		String ruta = rutaActual + "/data/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-"
				+ localFecha.getYear();

		File carpeta = new File(ruta);

		if (carpeta.exists() && carpeta.isDirectory()) {
			return carpeta.getPath();
		}
		return "";
	}

	// -------------------------------------------------------------------------
	// ------------------------- Algoritmo Polinomial --------------------------
	// -------------------------------------------------------------------------
	public static int encontrarUltimaNoSuperpuesta(ArrayList<Oferta> ofertas, int i) {
		for (int j = i - 1; j >= 0; j--) {
			if (ofertas.get(i).obtenerHorarioInicio() >= ofertas.get(j).obtenerHorarioSalida()) {
				return j;
			}
		}
		return -1;
	}

	private boolean seSuperponeConSeleccionadas(ArrayList<Oferta> adjudicadas, Oferta nuevaOferta) {
		for (Oferta oferta : adjudicadas) {
			if (!(nuevaOferta.obtenerHorarioInicio() >= oferta.obtenerHorarioSalida()
					|| nuevaOferta.obtenerHorarioSalida() <= oferta.obtenerHorarioInicio())) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Oferta> seleccionarOfertasMaxGananciaDP(ArrayList<Oferta> ofertas) {
		long inicio = System.nanoTime();

		ofertas.sort((o1, o2) -> {
			int comparacionMonto = Double.compare(o2.obtenerMontoOfrecido(), o1.obtenerMontoOfrecido());
			if (comparacionMonto != 0) {
				return comparacionMonto;
			}

			return Integer.compare(o1.obtenerHorarioInicio(), o2.obtenerHorarioInicio());
		});

		int n = ofertas.size();
		Double[] maxGanancia = new Double[n];
		maxGanancia[0] = ofertas.get(0).obtenerMontoOfrecido();
		ofertasAdjudicadas = new ArrayList<Oferta>();

		ofertasAdjudicadas.add(ofertas.get(0));

		for (int i = 1; i < n; i++) {
			Double incluirMonto = ofertas.get(i).obtenerMontoOfrecido();
			int j = encontrarUltimaNoSuperpuesta(ofertas, i);
			if (j != -1) {
				incluirMonto += maxGanancia[j];
			}

			maxGanancia[i] = Math.max(incluirMonto, maxGanancia[i - 1]);

			if (incluirMonto > maxGanancia[i - 1] && !seSuperponeConSeleccionadas(ofertasAdjudicadas, ofertas.get(i))) {
				ofertasAdjudicadas.add(ofertas.get(i));
			}
		}

		long fin = System.nanoTime();
		long tiempoEjecucion = fin - inicio;
		tiempoEjecutado = tiempoEjecucion / 100_000.0;

		return ofertasAdjudicadas;
	}

	// -------------------------------------------------------------------------
	// ------------------------- Algoritmo Goloso ------------------------------
	// -------------------------------------------------------------------------
	public ArrayList<Oferta> resolverAdjudicacion(ArrayList<Oferta> ofertas) {
		long inicio = System.nanoTime();

		ArrayList<Oferta> adjudicadas = new ArrayList<>();
		int horaActual = 0;

		ofertas.sort((o1, o2) -> {
			int comparacionMonto = Double.compare(o2.obtenerMontoOfrecido(), o1.obtenerMontoOfrecido());
			if (comparacionMonto != 0) {
				return comparacionMonto;
			}

			return Integer.compare(o1.obtenerHorarioInicio(), o2.obtenerHorarioInicio());
		});

		for (Oferta oferta : ofertas) {
			if (oferta.obtenerHorarioInicio() >= horaActual) {
				adjudicadas.add(oferta);
				horaActual = oferta.obtenerHorarioSalida();
			}
		}

		long fin = System.nanoTime();
		long tiempoEjecucion = fin - inicio;
		tiempoEjecutado = tiempoEjecucion / 100_000.0;

		return adjudicadas;
	}
	
	// -------------------------------------------------------------
	// --------------------- TEST ----------------------------------
	// -------------------------------------------------------------

    public void registrarEnDataTest(String nombreOfertante, Integer horarioInicio, Integer horarioSalida, Double montoOfrecido, Date fecha, ArrayList<String> equipamientos) {
    	 try {
 		 	String carpetaDestino = crearCarpetaDataTest(fecha);   
 	        RUTA_ARCHIVO = carpetaDestino + "/oferta" + nombreOfertante + ".txt";
 	        
 	        // Serializo el archivo
 			fos = new FileOutputStream(RUTA_ARCHIVO);
 			out = new ObjectOutputStream(fos);        			

	  		Oferta oferta = new Oferta(nombreOfertante, horarioInicio, horarioSalida, montoOfrecido, equipamientos);
	  		
	  		out.writeObject(oferta);
	  		out.close();
	 	    } catch (Exception e) {
	 	        e.printStackTrace(); // Manejar errores
	 	    }
	  }
  
	  public ArrayList<Oferta> obtenerOfertasDataTest(Date fecha) {
	  	String carpetaDestino = obtenerCarpetaDataTest(fecha);
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
	  
	  
	  
  	private String obtenerCarpetaDataTest(Date fecha) {
		LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String rutaActual = System.getProperty("user.dir");
		String ruta = rutaActual + "/dataTest/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear();
		
		File carpeta = new File(ruta);
		
		if(carpeta.exists() && carpeta.isDirectory()) {
			return carpeta.getPath();
		}
		return "";
  	}
   
	private String crearCarpetaDataTest(Date fecha) {
		LocalDate localFecha = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String rutaActual = System.getProperty("user.dir");
		String ruta = rutaActual + "/dataTest/" + localFecha.getDayOfMonth() + "-" + localFecha.getMonthValue() + "-" + localFecha.getYear();
		
		File carpeta = new File(ruta);
		
		if(!(carpeta.exists() && carpeta.isDirectory())) {
			carpeta.mkdirs();
		}
		
		return carpeta.getPath();
	}
	
	// -------------------------------------------------------------
	// --------------------- Funciones Auxiliares ------------------
	// -------------------------------------------------------------

	public ArrayList<Oferta> obtenerOfertasAdjudicadas() {
		return ofertasAdjudicadas;
	}

	public Double obtenerTiempoEjecutado() {
		return tiempoEjecutado;
	}

	public int calcularDuracionEnHoras(Oferta o) {
		return o.obtenerHorarioInicio() - o.obtenerHorarioSalida();
	}

	public Double obtenerGananciaTotales(ArrayList<Oferta> ofertas) {
		Double gananciaTotal = 0.0;
		if (ofertas.size() > 0) {
			for (Oferta oferta : ofertas) {
				gananciaTotal += oferta.obtenerMontoOfrecido();
			}
		}
		return gananciaTotal;
	}

	private int convertirHora(String hora) {
		String horaSolo = hora.split(":")[0];
		return Integer.parseInt(horaSolo);
	}

	private void verificarDatos(String nombreOfertante, ArrayList<String> equipamientos) {
		if (nombreOfertante.equals(""))
			throw new NumberFormatException("El nombre del ofertante no puede estar vacío");

		if (valorMontoOfrecido <= 0)
			throw new NumberFormatException("El monto ofrecido debe ser mayor a 0");

		if (horaInicioInt == horaSalidaInt)
			throw new NumberFormatException("El horario de inicio y salida no pueden ser iguales");

		if (horaInicioInt >= horaSalidaInt)
			throw new NumberFormatException("El horario de inicio debe ser menor al de salida");

		if (equipamientos.isEmpty())
			throw new NumberFormatException("Debe seleccionar al menos un equipamiento");
	}

}
