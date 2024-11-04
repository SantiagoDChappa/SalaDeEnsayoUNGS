package Logic;
import java.io.Serializable;
import java.util.UUID;

public class Oferta implements Serializable {
	private String id;
	private double monto;
	private int horaInicio;
	private int horaFin;
	private static final long serialVersionUID = 1L;
	
	public Oferta(double monto, int horaInicio, int horaFin) {
		this.id = generarID();
		this.monto = monto;
		this.horaFin = horaFin;
		this.horaInicio = horaInicio;
	}
	
	public String generarID() {
		return UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	public double getMonto() {
		return monto;
	}
	public int getHoraInicio() {
		return horaInicio;
	}
	public int getHoraFin() {
		return horaFin;
	}
	
	@Override
	public String toString() {
		return "Oferta Recibida {{ id= " + id + 
				" | Monto= $" + monto + 
				" | horaInicio= " + horaInicio + 
				" | horaFin= " + horaFin + " }}";
	}
	
	
}
