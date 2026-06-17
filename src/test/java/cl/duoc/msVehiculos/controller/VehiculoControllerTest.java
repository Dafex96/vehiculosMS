package cl.duoc.msVehiculos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import cl.duoc.msVehiculos.dto.VehiculoDTO;
import cl.duoc.msVehiculos.model.MarcaVehiculo;
import cl.duoc.msVehiculos.model.TipoVehiculo;
import cl.duoc.msVehiculos.model.Vehiculo;
import cl.duoc.msVehiculos.service.VehiculoService;

@WebMvcTest(VehiculoController.class) // Indica que esta clase es un test para el controlador VehiculoController
public class VehiculoControllerTest {

       @MockitoBean
       private VehiculoService vehiculoService; // Simula el servicio para las pruebas

       private Vehiculo vehiculoEjemplo;
       private VehiculoDTO vehiculoEjemploDTO;
       
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
       
              vehiculoEjemploDTO = new VehiculoDTO();
              vehiculoEjemploDTO.setId(1);
              vehiculoEjemploDTO.setPatente("ABC123");
              vehiculoEjemploDTO.setMarca("Toyota");
              vehiculoEjemploDTO.setModelo("GR Yaris");
       }

       @Test
       void buscarPorId_retorna200() throws Exception {
              // ARRANGE: este servicio debe retornar un vehiculo
              when(vehiculoService.buscarPorId(1)).thenReturn(vehiculoEjemplo);

              // ACT & ASSERT: se hace la llamada al endpoint y se espera un status 200
              llamadaFalsa.perform(get("/api/v1/vehiculos/id/1"))
                     .andExpect(status().isOk());
       }

       @Test
       void buscarPorId_retorna404() throws Exception {
              // ARRANGE: este servicio debe lanzar una excepción
              when(vehiculoService.buscarPorId(99)).thenThrow(new RuntimeException("Vehiculo no encontrado..."));

              // ACT & ASSERT: se hace la llamada al endpoint y se espera un status 404
              llamadaFalsa.perform(get("/api/v1/vehiculos/id/99"))
                     .andExpect(status().isNotFound());
       }

       @Test
       void listarVehiculos_retorna200() throws Exception {
              
              List<Vehiculo> listaFalsa = new ArrayList<>();
              listaFalsa.add(vehiculoEjemplo);

              // ARRANGE: este servicio debe retornar una lista de vehiculos
              when(vehiculoService.listarVehiculos()).thenReturn(listaFalsa);

              llamadaFalsa.perform(get("/api/v1/vehiculos/listar_vehiculo"))
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("$[0].patente").value("ABC123"));
       }

       @Test
       void listarVehiculos_retorna204() throws Exception {
              // ARRANGE: lista vacía
              List<Vehiculo> listaVacia = new ArrayList<>();
              when(vehiculoService.listarVehiculos()).thenReturn(listaVacia);
              
              // ACT + ASSERT: verificamos que retorna 204 sin contenido
              llamadaFalsa.perform(get("/api/v1/vehiculos/listar_vehiculo"))
                            .andExpect(status().isNoContent()); // código HTTP 204
       }

       @Test
       void guardar_retorna200() throws Exception {
              // ARRANGE
              when(vehiculoService.agregarVehiculo(any(Vehiculo.class))).thenReturn(vehiculoEjemplo);

              // ACT + ASSERT
              llamadaFalsa.perform(post("/api/v1/vehiculos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":1,\"patente\":\"ABC123\",\"marcaVehiculo\":{\"id\":1,\"nombre\":\"Toyota\"},\"anio\":2020,\"color\":\"Rojo\",\"kilometraje\":2342342,\"tipoVehiculo\":{\"id\":1,\"nombre\":\"Auto\"}}"))
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("$.patente").value("ABC123"));
       }

       @Test
       void eliminar_retorna204() throws Exception {
        // ARRANGE: el servicio no hace nada (método void)
              doNothing().when(vehiculoService).eliminarVehiculo(1);

        // ACT + ASSERT
              llamadaFalsa.perform(delete("/api/v1/vehiculos/1"))
                     .andExpect(status().isNoContent());
       }

       @Test
       void eliminar_retorna404() throws Exception {
              // ARRANGE: el servicio lanza excepción
              doThrow(new RuntimeException("Vehiculo no existe")).when(vehiculoService).eliminarVehiculo(99);

              // ACT + ASSERT
              llamadaFalsa.perform(delete("/api/v1/vehiculos/99"))
                            .andExpect(status().isNotFound());
       }

       @Test
       void obtenerVehiculoDTO_retorna200() throws Exception {
              // ARRANGE
              when(vehiculoService.buscarDTOPorId(1)).thenReturn(vehiculoEjemploDTO);

              // ACT + ASSERT
              llamadaFalsa.perform(get("/api/v1/vehiculos/dto/1"))
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("$.patente").value("ABC123"))
                     .andExpect(jsonPath("$.marca").value("Toyota"))
                     .andExpect(jsonPath("$.modelo").value("GR Yaris"));
       }


}
