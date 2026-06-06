package cl.duoc.msVehiculos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.msVehiculos.dto.VehiculoDTO;
import cl.duoc.msVehiculos.model.MarcaVehiculo;
import cl.duoc.msVehiculos.model.TipoVehiculo;
import cl.duoc.msVehiculos.model.Vehiculo;
import cl.duoc.msVehiculos.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/vehiculos")
@Tag(name = "Vehiculos", description = "Operaciones relacionadas con los vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    @GetMapping("/listar_vehiculo")
    @Operation(
        summary = "Listar todos los vehiculos",
        description = "Retorna una lista de todos los vehiculos registrados en la base de datos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hay vehiculos registrados"),
        @ApiResponse(responseCode = "204", description = "No hay vehiculos registrados")
    })
    public ResponseEntity<List<Vehiculo>> listarVehiculos(){
        
        List<Vehiculo> listaVehiculos = service.listarVehiculos();

        if (listaVehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaVehiculos);
        }
    }

    @GetMapping("/listar_tipos")
    @Operation(
        summary = "Listar todos los tipos de vehiculos",
        description = "Retorna una lista de todos los tipos de vehiculos registrados en la base de datos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hay tipos de vehiculos registrados"),
        @ApiResponse(responseCode = "204", description = "No hay tipos de vehiculos registrados")
    })
    public ResponseEntity<List<TipoVehiculo>> listarTipos(){
        
        List<TipoVehiculo> listaTipos = service.listarTipos();

        if (listaTipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaTipos);
        }
    }

    @GetMapping("/listar_marcas")
    @Operation(
        summary = "Listar todos los marcas de vehiculos",
        description = "Retorna una lista de todos los marcas de vehiculos registrados en la base de datos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hay marcas registradas"),
        @ApiResponse(responseCode = "204", description = "No hay marcas registrados")
    })
    public ResponseEntity<List<MarcaVehiculo>> listarMarcas(){
        
        List<MarcaVehiculo> listaMarcas = service.listarMarcas();

        if (listaMarcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaMarcas);
        }
    }

    @GetMapping("/id/{id}")
    @Operation(
        summary = "Buscar un vehiculo por su ID",
        description = "Retorna un vehiculo segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    public ResponseEntity<Vehiculo> obtenerPorId(
            @Parameter(description = "ID del vehiculo a buscar", example = "1")
            @PathVariable Integer id){
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(
        summary = "Buscar un vehiculo por su ID y retornar como DTO",
        description = "Retorna un vehiculo en formato DTO segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    public ResponseEntity<VehiculoDTO> obtenerVehiculoDTOPorId(
        @Parameter(description = "ID del vehiculo a buscar", example = "1")
        @PathVariable Integer id){
        try {
            return ResponseEntity.ok(service.buscarDTOPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patente/{patente}")
    @Operation(
        summary = "Buscar un vehiculo por su patente",
        description = "Retorna un vehiculo segun la patente proporcionada"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    public ResponseEntity<Vehiculo> obtenerPorPatente(
        @Parameter(description = "Patente del vehiculo a buscar", example = "AB123")
        @PathVariable String patente){
        try {
            return ResponseEntity.ok(service.buscarPorPatente(patente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Agregar un nuevo vehiculo",
        description = "Crea un nuevo vehiculo con los datos proporcionados en el cuerpo de la solicitud"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehiculo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de vehiculo invalidos")
    })
    public ResponseEntity<Vehiculo> guardarVehiculo(
        @Parameter(description = "Datos del vehiculo a crear")
        @RequestBody Vehiculo vehiculo){
        Vehiculo nuevoVehiculo = service.agregarVehiculo(vehiculo);
        return ResponseEntity.ok(nuevoVehiculo);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un vehiculo existente",
        description = "Actualiza los datos de un vehiculo existente segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehiculo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    public ResponseEntity<Vehiculo> actualizarVehiculo(
        @Parameter(description = "ID del vehiculo a actualizar", example = "1")
        @PathVariable Integer id,
        @Parameter(description = "Datos del vehiculo a actualizar")
        @RequestBody Vehiculo vehiculo){
        try {
            Vehiculo vehiculoActualizado = service.actualizarVehiculo(id, vehiculo);
            return ResponseEntity.ok(vehiculoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un vehiculo",
        description = "Elimina un vehiculo segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vehiculo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    public ResponseEntity<Void> eliminarVehiculo(
        @Parameter(description = "ID del vehiculo a eliminar", example = "1")
        @PathVariable Integer id){
        try {
            service.eliminarVehiculo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{nombre}")
    @Operation(
        summary = "Buscar un tipo de vehiculo por su nombre",
        description = "Retorna un tipo de vehiculo segun el nombre proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de vehiculo encontrado"),
        @ApiResponse(responseCode = "404", description = "Tipo de vehiculo no encontrado")
    })
    public ResponseEntity<TipoVehiculo> obtenerTipoPorNombre(
        @Parameter(description = "Nombre del tipo de vehiculo a buscar", example = "Moto")
        @PathVariable String nombre){
        try {
            return ResponseEntity.ok(service.buscarTipoPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/marca/{nombre}")
    @Operation(
        summary = "Buscar una marca de vehiculo por su nombre",
        description = "Retorna una marca de vehiculo segun el nombre proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Marca de vehiculo encontrada"),
        @ApiResponse(responseCode = "404", description = "Marca de vehiculo no encontrada")
    })
    public ResponseEntity<MarcaVehiculo> obtenerMarcaPorNombre(
        @Parameter(description = "Nombre de la marca a buscar", example = "Toyota")
        @PathVariable String nombre){
        try {
            return ResponseEntity.ok(service.buscarMarcaPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
