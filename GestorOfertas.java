package Logic;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestorOfertas implements Serializable {
	private List <Oferta> ofertas;
	private static final long serialVersionUID = 1L;
	
	public GestorOfertas(){
		ofertas = new ArrayList<Oferta>();
		cargarOfertas();
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	
	public void registrarOferta(Oferta oferta) {
		ofertas.add(oferta);
		guardarOfertas();
	}
	
	public List<Oferta> mostrarOfertas() {
		return new ArrayList<>(ofertas);
	}
	
	public List<Oferta> resolverAdjudicacion(List<Oferta> ofertas) {
		List <Oferta> adjudicadas = new ArrayList<>();
		int horaActual = 0;
		
		for(Oferta oferta : ofertas) {
			if(oferta.getHoraInicio() >= horaActual) {
				adjudicadas.add(oferta);
				horaActual = oferta.getHoraFin();
			}
		}
		return adjudicadas;
	}
	
	public int calcularDuracionEnHoras(Oferta o) {
		return o.getHoraFin() - o.getHoraInicio();
	}
	
	/* Para leer desde un archivo serializado ??? */
	public void cargarOfertas() {}
	
	/* Para guardar en un archivo serializado ??? */
	public void guardarOfertas() {}
}
