package Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Oferta implements Serializable {
	private String id;
	private String nombreOfertante;
	private Integer horarioInicio;
	private Integer horarioSalida;
	private Double montoOfrecido;
	private ArrayList<String> equipamientos;
	private static final long serialVersionUID = 1L; // Es buena práctica incluir un serialVersionUID

	public Oferta() {
		this.id = generarID();
		this.nombreOfertante = "";
		this.horarioInicio = 0;
		this.horarioSalida = 0;
		this.montoOfrecido = 0.0;
		this.equipamientos = new ArrayList<String>();
	}

	public Oferta(String nombreOfertante, Integer horarioInicio, Integer horarioSalida, Double montoOfrecido,
			ArrayList<String> equipamientos) {
		this.nombreOfertante = nombreOfertante;
		this.horarioInicio = horarioInicio;
		this.horarioSalida = horarioSalida;
		this.montoOfrecido = montoOfrecido;
		this.equipamientos = equipamientos;
	}

	public String generarID() {
		return UUID.randomUUID().toString();
	}

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

	public ArrayList<String> obtenerEquipamientos() {
		return equipamientos;
	}

	@Override
	public String toString() {
		return "Oferta Recibida {{ id= " + id + " | Monto= $" + montoOfrecido + " | horaInicio= " + horarioInicio
				+ " | horaFin= " + horarioSalida + " }}";
	}

}
