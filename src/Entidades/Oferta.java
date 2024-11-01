package Entidades;

import java.io.Serializable;

public class Oferta implements Serializable {
	private static final long serialVersionUID = 1L; // Es buena práctica incluir un serialVersionUID
	private String nombreOfertante;
	private Integer horarioInicio;
	private Integer horarioSalida;
	private Double montoOfrecido;

	
	public Oferta() {
		this.nombreOfertante = "";
		this.horarioInicio = 0;
		this.horarioSalida = 0;
		this.montoOfrecido = 0.0;
	}
	
	public Oferta(String nombreOfertante, Integer horarioInicio, Integer horarioSalida, Double montoOfrecido) {
		if (horarioInicio < 0 || horarioInicio > 24) throw new NumberFormatException("El horario de inicio no es valido, debe ser entre las 0 y las 23 horas");
		if (horarioSalida < 0 || horarioSalida > 24) throw new NumberFormatException("El horario de salida no es valido, debe ser entre las 0 y las 23 horas"); 
		if (montoOfrecido < 0) throw new NumberFormatException("El monto ofrecido no es valido, debe ser mayor a cero"); 

		this.nombreOfertante = nombreOfertante;
		this.horarioInicio = horarioInicio;
		this.horarioSalida = horarioSalida;
		this.montoOfrecido = montoOfrecido;	
	}
	
    /*@Override
    public String toString() {
        return "Oferta { Nombre Horario de inicio = '" + horarioInicio + "', horario de salida = " + horarioSalida + ", monto ofrecido = '" + montoOfrecido + "'}";
    }*/
	
	public Integer obtenerHorarioInicio() {
		return horarioInicio;
	}

	public Integer obtenerHorarioSalida() {
		return horarioSalida;
	}

	public String obtenerNombreOfertante() {
		return nombreOfertante;
	}

	public Double obtenerMontoOfrecido() {
		return montoOfrecido;
	}
	
}
