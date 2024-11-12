package Logica;

import Entidades.Oferta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorOfertaTest {

    private GestorOferta gestor;
    private Date fecha;
    private ArrayList<String> equipamientos;

    @BeforeEach
    void setUp() {
        gestor = new GestorOferta();
        fecha = new Date();
        equipamientos = new ArrayList<>();
        equipamientos.add("Amplificador");
    }

    @AfterEach
    void limpiar() {
        String carpetaDestino = System.getProperty("user.dir") + "/dataTest/";
        File carpeta = new File(carpetaDestino);
        if (carpeta.isDirectory()) {
            for (File subCarpeta : carpeta.listFiles()) {
                if (subCarpeta.isDirectory()) {
                    for (File archivo : subCarpeta.listFiles()) {
                    	archivo.delete();
                    }
                    subCarpeta.delete();
                }
            }
        }
    }

    @Test
    void testRegistrarEnDataTest() {
        gestor.registrarEnDataTest("Pedro", 9, 11, 150.0, fecha, equipamientos);
        ArrayList<Oferta> ofertas = gestor.obtenerOfertasDataTest(fecha);

        assertEquals(1, ofertas.size());
        Oferta oferta = ofertas.get(0);
        assertEquals("Pedro", oferta.obtenerNombreOfertante());
        assertEquals(9, oferta.obtenerHorarioInicio());
        assertEquals(11, oferta.obtenerHorarioSalida());
        assertEquals(150.0, oferta.obtenerMontoOfrecido());
    }
    
    @Test
    void testResolverAdjudicacion() {
        List<Oferta> ofertas = new ArrayList<>();
        ofertas.add(new Oferta("Juan", 9, 11, 200.0, equipamientos));
        ofertas.add(new Oferta("Maria", 12, 14, 150.0, equipamientos));
        ofertas.add(new Oferta("Carlos", 10, 13, 300.0, equipamientos));
        ofertas.add(new Oferta("Ana", 14, 16, 100.0, equipamientos));

        List<Oferta> adjudicadas = gestor.resolverAdjudicacion(ofertas);

        assertEquals(3, adjudicadas.size());
        assertEquals("Juan", adjudicadas.get(0).obtenerNombreOfertante());
        assertEquals("Maria", adjudicadas.get(1).obtenerNombreOfertante());
        assertEquals("Ana", adjudicadas.get(2).obtenerNombreOfertante());
    }
    
    
    @Test
    void testSeleccionarOfertasMaxGananciaDP() {
        List<Oferta> ofertas = new ArrayList<>();
        ofertas.add(new Oferta("Juan", 9, 11, 200.0, equipamientos));
        ofertas.add(new Oferta("Maria", 12, 14, 150.0, equipamientos));
        ofertas.add(new Oferta("Carlos", 10, 13, 300.0, equipamientos));
        ofertas.add(new Oferta("Ana", 14, 16, 100.0, equipamientos));

        List<Oferta> seleccionadas = gestor.seleccionarOfertasMaxGanancia(ofertas);

        // Imprimir el resultado para verificar el contenido
        seleccionadas.forEach(oferta -> System.out.println(oferta.obtenerNombreOfertante() + " - " + oferta.obtenerMontoOfrecido()));

        assertEquals(3, seleccionadas.size());
        assertEquals("Juan", seleccionadas.get(0).obtenerNombreOfertante());
        assertEquals("Maria", seleccionadas.get(1).obtenerNombreOfertante());
        assertEquals("Ana", seleccionadas.get(2).obtenerNombreOfertante());
    }

    @Test
    void testObtenerOfertasSinOfertasRegistradas() {
        // obtener ofertas para una fecha sin registros
        ArrayList<Oferta> ofertas = gestor.obtenerOfertasDataTest(fecha);

        // testear que no hay ofertas registradas
        assertTrue(ofertas.isEmpty(), "No debe haber ofertas registradas");
    }

    @Test
    void testRegistrarVariasOfertas() {
        // registrar ofertas
        gestor.registrarEnDataTest("Ofertante 1", 9, 12, 1000.0, fecha, equipamientos);
        gestor.registrarEnDataTest("Ofertante 2", 13, 16, 1500.0, fecha, equipamientos);

        // obtener las ofertas para la fecha actual
        ArrayList<Oferta> ofertas = gestor.obtenerOfertasDataTest(fecha);

        // testear que se han registrado dos ofertas
        assertEquals(2, ofertas.size(), "Debe haber dos ofertas registradas");

        // testear los datos de la primera oferta
        Oferta oferta1 = ofertas.get(0);
        assertEquals("Ofertante 1", oferta1.obtenerNombreOfertante());
        assertEquals(9, oferta1.obtenerHorarioInicio());
        assertEquals(12, oferta1.obtenerHorarioSalida());
        assertEquals(1000.0, oferta1.obtenerMontoOfrecido());

        // testear los datos de la segunda oferta
        Oferta oferta2 = ofertas.get(1);
        assertEquals("Ofertante 2", oferta2.obtenerNombreOfertante());
        assertEquals(13, oferta2.obtenerHorarioInicio());
        assertEquals(16, oferta2.obtenerHorarioSalida());
        assertEquals(1500.0, oferta2.obtenerMontoOfrecido());
    }
}
