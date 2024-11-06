package Logica;

import Entidades.Oferta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class GestorOfertaTest {

    private GestorOferta gestor;
    private Date fecha;

    @BeforeEach
    void setUp() {
        gestor = new GestorOferta();
        fecha = new Date();
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
    void testRegistrarYObtenerOfertas() {
        // registrar una oferta
        gestor.registrarEnDataTest("Ofertante 1", 9, 12, 1000.0, fecha);

        // obtener las ofertas para la fecha actual
        ArrayList<Oferta> ofertas = gestor.obtenerOfertasDataTest(fecha);

        // testear que se ha registrado una oferta
        assertEquals(1, ofertas.size(), "Debe haber una oferta registrada");

        // testear los datos de la oferta registrada
        Oferta oferta = ofertas.get(0);
        assertEquals("Ofertante 1", oferta.obtenerNombreOfertante());
        assertEquals(9, oferta.obtenerHorarioInicio());
        assertEquals(12, oferta.obtenerHorarioSalida());
        assertEquals(1000.0, oferta.obtenerMontoOfrecido());
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
        gestor.registrarEnDataTest("Ofertante 1", 9, 12, 1000.0, fecha);
        gestor.registrarEnDataTest("Ofertante 2", 13, 16, 1500.0, fecha);

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
