package Logic;

import java.util.List;

public class Main {
	 public static void main(String[] args) {
	        GestorOfertas gestor = new GestorOfertas();

	        // Registrar algunas ofertas de prueba
	       /* gestor.registrarOferta(new Oferta(1, 16000, 8, 12));
	        gestor.registrarOferta(new Oferta(2, 14000, 12, 15));
	        gestor.registrarOferta(new Oferta(3, 10000, 11, 16));
	        gestor.registrarOferta(new Oferta(44, 14000, 17, 21));
	        gestor.registrarOferta(new Oferta(5, 14000, 7, 11));
	        gestor.registrarOferta(new Oferta(44, 10000, 21, 23));
	        */
	        gestor.registrarOferta(new Oferta(16000, 8, 12));
	        gestor.registrarOferta(new Oferta(14000, 12, 15));
	        gestor.registrarOferta(new Oferta(10000, 11, 16));
	        gestor.registrarOferta(new Oferta(14000, 17, 21));
	        gestor.registrarOferta(new Oferta(14000, 7, 11));
	        gestor.registrarOferta(new Oferta(10000, 21, 23));

	        // Mostrar todas las ofertas registradas
	        System.out.println("Ofertas Registradas:");
	        List<Oferta> ofertasRegistradas = gestor.mostrarOfertas();
	        for (Oferta oferta : ofertasRegistradas) {
	            System.out.println(oferta);
	            
	        }

	        // Resolver el problema de adjudicación
	        System.out.println("\nOfertas Seleccionadas para Maximizar la Ganancia:");
	        List<Oferta> adjudicadas = gestor.resolverAdjudicacion(ofertasRegistradas);
	        double gananciaTotal = 0;

	        for (Oferta oferta : adjudicadas) {
	            System.out.println(oferta);
	            gananciaTotal += oferta.getMonto();
	            System.out.println("Cantidad de horas de la oferta { "
	            + oferta.getId() +" } => "+ gestor.calcularDuracionEnHoras(oferta));
	        }

	        System.out.println("\nGanancia Total: $" + gananciaTotal);
	    }
}
