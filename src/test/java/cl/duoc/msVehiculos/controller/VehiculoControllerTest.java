package cl.duoc.msVehiculos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import cl.duoc.msVehiculos.model.MarcaVehiculo;
import cl.duoc.msVehiculos.model.TipoVehiculo;
import cl.duoc.msVehiculos.model.Vehiculo;
import cl.duoc.msVehiculos.service.VehiculoService;

@WebMvcTest(VehiculoController.class) // Indica que esta clase es un test para el controlador VehiculoController
public class VehiculoControllerTest {

       @MockitoBean
       private VehiculoService vehiculoService; // Simula el servicio para las pruebas

       private Vehiculo vehiculoEjemplo;
       
       @Autowired
       private MockMvc llamadaFalsa; // Permite simular llamadas HTTP al controlador

       @BeforeEach
       void setUp() {
              // Configura un vehículo de ejemplo para usar en las pruebas
              vehiculoEjemplo = new Vehiculo();
              vehiculoEjemplo.setIdVehiculo(1);
              vehiculoEjemplo.setPatente("ABC123");
              vehiculoEjemplo.setMarcaVehiculo(new MarcaVehiculo(1, "Toyota", "GR Yaris"));
              vehiculoEjemplo.setAnio(2020);
              vehiculoEjemplo.setColor("Rojo");
              vehiculoEjemplo.setKilometraje(2342342);
              vehiculoEjemplo.setTipoVehiculo(new TipoVehiculo(1, "Auto"));
       }

       @Test
       void buscarPorId_retorna200() throws Exception {
              // ARRANGE: este servicio debe retornar un vehiculo
              when(vehiculoService.buscarPorId(1)).thenReturn(vehiculoEjemplo);

              // ACT & ASSERT: se hace la llamada al endpoint y se espera un status 200
              llamadaFalsa.perform(get("/api/v1/vehiculos/1"))
                     .andExpect(status().isOk());
       }



       @Test
       void buscarPorId_retorna404() throws Exception {
              // ARRANGE: este servicio debe lanzar una excepción
              when(vehiculoService.buscarPorId(99)).thenThrow(new RuntimeException("Vehiculo no encontrado..."));

              // ACT & ASSERT: se hace la llamada al endpoint y se espera un status 404
              llamadaFalsa.perform(get("/api/v1/vehiculos/99"))
                     .andExpect(status().isNotFound());
       }





}
