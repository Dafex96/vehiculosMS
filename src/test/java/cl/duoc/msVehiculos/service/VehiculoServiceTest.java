package cl.duoc.msVehiculos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.duoc.msVehiculos.model.MarcaVehiculo;
import cl.duoc.msVehiculos.model.TipoVehiculo;
import cl.duoc.msVehiculos.model.Vehiculo;
import cl.duoc.msVehiculos.repository.VehiculoRepository;

@ExtendWith(MockitoExtension.class)
public class VehiculoServiceTest {

       /*
              creamos un @Mock para el repositorio de Vehiculo, que es una dependencia de la clase VehiculoService. 
              Esto nos permitirá simular el comportamiento del repositorio sin necesidad de acceder a una base de datos real.
       */

       @Mock
       private VehiculoRepository vehiculoRepo;

       /*
              Luego, utilizamos @InjectMocks para crear una instancia de VehiculoService e inyectar el mock del repositorio en ella. 
              Esto nos permitirá probar los métodos de VehiculoService sin depender de la implementación real del repositorio.
       */

       @InjectMocks
       private VehiculoService vehiculoService;

       private Vehiculo vehiculoEjemplo;

       @BeforeEach
       void setup() {
              vehiculoEjemplo = new Vehiculo();
              vehiculoEjemplo.setIdVehiculo(1);
              vehiculoEjemplo.setPatente("ABC123");
              vehiculoEjemplo.setAnio(2015);
              vehiculoEjemplo.setColor("Rojo");
              vehiculoEjemplo.setMarcaVehiculo(new MarcaVehiculo(1, "Toyota", "GR Yaris"));
              vehiculoEjemplo.setTipoVehiculo(new TipoVehiculo(1, "Auto"));
              // Configura otros atributos del vehículo según sea necesario
       }

       @Test
       void listar(){
              // Aquí puedes configurar el comportamiento del mock y llamar al método listarVehiculos() para verificar su funcionamiento.

              //ARRANGE - Creamos una lista de vehículos de ejemplo y configuramos el mock para que devuelva esa lista cuando se llame al método findAll() del repositorio.
              List<Vehiculo> listaFalsa = new ArrayList<>();
              listaFalsa.add(vehiculoEjemplo);
              when(vehiculoRepo.findAll()).thenReturn(listaFalsa);
              
              // ACT - Llamamos al método listarVehiculos() del servicio, que debería devolver la lista de vehículos que configuramos en el mock.
              List<Vehiculo> listaVehiculos = vehiculoService.listarVehiculos();

              // ASSERT - Verificamos que la lista devuelta por el servicio es la misma que configuramos en el mock.
              assertEquals(1, listaVehiculos.size());
              assertEquals("ABC123", listaVehiculos.get(0).getPatente());
              assertEquals(2015, listaVehiculos.get(0).getAnio());
              assertEquals("Rojo", listaVehiculos.get(0).getColor());
              assertEquals(1, listaVehiculos.get(0).getMarcaVehiculo().getIdMarca());
              assertEquals("Toyota", listaVehiculos.get(0).getMarcaVehiculo().getNombre());
              assertEquals("GR Yaris", listaVehiculos.get(0).getMarcaVehiculo().getModelo());
              assertEquals(1, listaVehiculos.get(0).getTipoVehiculo().getIdTipoVehiculo());
              assertEquals("Auto", listaVehiculos.get(0).getTipoVehiculo().getNombre());



       }

       @Test
       void buscarPorId_encontrado() {
              // Aquí puedes configurar el comportamiento del mock para simular la búsqueda de un vehículo por su ID. 
              // Luego llamar al método buscarPorId() para verificar su funcionamiento.

              // ARRANGE - Configuramos el mock para que devuelva un vehículo de ejemplo cuando se llame al método findById() del repositorio con el ID 1.
              Optional<Vehiculo> vehiculo = Optional.of(vehiculoEjemplo);
              when(vehiculoRepo.findById(1)).thenReturn(vehiculo);

              // ACT - Llamamos al método buscarPorId() del servicio con el ID 1, que debería devolver el vehículo de ejemplo que configuramos en el mock.
              Vehiculo resultado = vehiculoService.buscarPorId(1);

              // ASSERT - Verificamos que el vehículo devuelto por el servicio es el mismo que configuramos en el mock.
              assertEquals(1, resultado.getIdVehiculo());
              assertEquals("ABC123", resultado.getPatente());
       }

       @Test
       void buscarPorId_noEncontrado() {
              // Aquí puedes configurar el comportamiento del mock para simular la búsqueda de un vehículo por un ID que no existe. 
              // Luego llamar al método buscarPorId() para verificar que se lance la excepción correspondiente.

              // ARRANGE - Configuramos el mock para que devuelva un Optional vacío cuando se llame al método findById() del repositorio con el ID 2.
              Optional<Vehiculo> vehiculoVacio = Optional.empty();
              when(vehiculoRepo.findById(99)).thenReturn(vehiculoVacio);

              // ACT & ASSERT - Llamamos al método buscarPorId() del servicio con el ID 2,
              // y verificamos que se lance una RuntimeException con el mensaje "Vehiculo no encontrado...".
              RuntimeException error = assertThrows(RuntimeException.class, () -> {
                     vehiculoService.buscarPorId(99);
              });

              assertEquals("Vehiculo no encontrado...", error.getMessage());
       }







}
