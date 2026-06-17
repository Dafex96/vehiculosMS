package cl.duoc.msVehiculos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import cl.duoc.msVehiculos.dto.VehiculoDTO;
import cl.duoc.msVehiculos.model.MarcaVehiculo;
import cl.duoc.msVehiculos.model.TipoVehiculo;
import cl.duoc.msVehiculos.model.Vehiculo;
import cl.duoc.msVehiculos.repository.MarcaRepository;
import cl.duoc.msVehiculos.repository.TipoVehiRepository;
import cl.duoc.msVehiculos.repository.VehiculoRepository;

@ExtendWith(MockitoExtension.class)
public class VehiculoServiceTest {

       /*
              creamos un @Mock para el repositorio de Vehiculo, que es una dependencia de la clase VehiculoService. 
              Esto nos permitirá simular el comportamiento del repositorio sin necesidad de acceder a una base de datos real.
       */

       @Mock
       private VehiculoRepository vehiculoRepo;

       @Mock
       private TipoVehiRepository tipoRepo;

       @Mock
       private MarcaRepository marcaRepo;

       /*
              Luego, utilizamos @InjectMocks para crear una instancia de VehiculoService e inyectar el mock del repositorio en ella. 
              Esto nos permitirá probar los métodos de VehiculoService sin depender de la implementación real del repositorio.
       */

       @InjectMocks
       private VehiculoService vehiculoService;

       private Vehiculo vehiculoEjemplo;
       private MarcaVehiculo marcaEjemplo;
       private TipoVehiculo tipoEjemplo;

       @BeforeEach
       void setup() {
              marcaEjemplo = new MarcaVehiculo(1, "Toyota", "GR Yaris");
              tipoEjemplo = new TipoVehiculo(1, "Auto");

              vehiculoEjemplo = new Vehiculo();
              vehiculoEjemplo.setIdVehiculo(1);
              vehiculoEjemplo.setPatente("ABC123");
              vehiculoEjemplo.setAnio(2015);
              vehiculoEjemplo.setColor("Rojo");
              vehiculoEjemplo.setKilometraje(100000);
              vehiculoEjemplo.setMarcaVehiculo(marcaEjemplo);
              vehiculoEjemplo.setTipoVehiculo(tipoEjemplo);
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

       @Test
       void listarTipos_retornaLista() {
              List<TipoVehiculo> listaTipos = new ArrayList<>();
              listaTipos.add(tipoEjemplo);
              when(tipoRepo.findAll()).thenReturn(listaTipos);

              List<TipoVehiculo> resultado = vehiculoService.listarTipos();

              assertEquals(1, resultado.size());
              assertEquals("Auto", resultado.get(0).getNombre());
       }

       @Test
       void listarMarcas_retornaLista() {
              List<MarcaVehiculo> listaMarcas = new ArrayList<>();
              listaMarcas.add(marcaEjemplo);
              when(marcaRepo.findAll()).thenReturn(listaMarcas);

              List<MarcaVehiculo> resultado = vehiculoService.listarMarcas();

              assertEquals(1, resultado.size());
              assertEquals("Toyota", resultado.get(0).getNombre());
       }

       @Test
       void buscarPorPatente_encontrado() {
              when(vehiculoRepo.findByPatente("ABC123")).thenReturn(Optional.of(vehiculoEjemplo));
       
              Vehiculo resultado = vehiculoService.buscarPorPatente("ABC123");
       
              assertNotNull(resultado);
              assertEquals("ABC123", resultado.getPatente());
       }

       @Test
       void agregarVehiculo_exitoso() {
       // Configuramos que al guardar cualquier entidad de tipo Vehiculo, nos retorne nuestro ejemplo
              when(vehiculoRepo.save(any(Vehiculo.class))).thenReturn(vehiculoEjemplo);

              Vehiculo resultado = vehiculoService.agregarVehiculo(new Vehiculo());

              assertNotNull(resultado);
              assertEquals("ABC123", resultado.getPatente());
       }

       @Test
       void eliminarVehiculo_exitoso() {
       // ARRANGE: Simulamos que el vehículo existe
              when(vehiculoRepo.existsById(1)).thenReturn(true);

       // ACT: Llamamos al método
              vehiculoService.eliminarVehiculo(1);

       // ASSERT: Verificamos que el repositorio efectivamente llamó al método deleteById exactamente 1 vez
              verify(vehiculoRepo, times(1)).deleteById(1);
       }

       @Test
       void eliminarVehiculo_noEncontrado() {
       // ARRANGE: Simulamos que NO existe
              when(vehiculoRepo.existsById(99)).thenReturn(false);

       // ACT & ASSERT: Esperamos la excepción
              RuntimeException error = assertThrows(RuntimeException.class, () -> {
              vehiculoService.eliminarVehiculo(99);
              });
       assertEquals("Vehiculo no encontrado", error.getMessage());
       }

       @Test
       void actualizarVehiculo_exitoso() {
       // ARRANGE
              Vehiculo vehiculoActualizado = new Vehiculo();
              vehiculoActualizado.setPatente("XYZ987");
              vehiculoActualizado.setAnio(2022);

              when(vehiculoRepo.findById(1)).thenReturn(Optional.of(vehiculoEjemplo));
              // Simulamos el guardado
              when(vehiculoRepo.save(any(Vehiculo.class))).thenReturn(vehiculoEjemplo);

       // ACT
              Vehiculo resultado = vehiculoService.actualizarVehiculo(1, vehiculoActualizado);

       // ASSERT
       // Al actualizar, el vehiculoEjemplo original en memoria toma los nuevos valores antes de hacer el save
              assertEquals("XYZ987", resultado.getPatente());
              assertEquals(2022, resultado.getAnio());
       }

       @Test
       void buscarTipoPorNombre_encontrado() {
              when(tipoRepo.findByNombre("Auto")).thenReturn(Optional.of(tipoEjemplo));
              TipoVehiculo resultado = vehiculoService.buscarTipoPorNombre("Auto");
              assertEquals("Auto", resultado.getNombre());
       }

       @Test
       void buscarMarcaPorNombre_encontrado() {
              when(marcaRepo.findByNombre("Toyota")).thenReturn(Optional.of(marcaEjemplo));
              MarcaVehiculo resultado = vehiculoService.buscarMarcaPorNombre("Toyota");
              assertEquals("Toyota", resultado.getNombre());
       }

       @Test
       void buscarDTOPorId_exitoso() {
       // ARRANGE: buscarDTOPorId usa buscarPorId internamente, así que mockeamos findById del repo
       when(vehiculoRepo.findById(1)).thenReturn(Optional.of(vehiculoEjemplo));

       // ACT
       VehiculoDTO dtoResultado = vehiculoService.buscarDTOPorId(1);

       // ASSERT: Comprobamos que el mapeo Manual en el Service funcionó correctamente
              assertNotNull(dtoResultado);
              assertEquals(1, dtoResultado.getId());
              assertEquals("ABC123", dtoResultado.getPatente());
              assertEquals("Toyota", dtoResultado.getMarca());
              assertEquals("GR Yaris", dtoResultado.getModelo());
       }




}
