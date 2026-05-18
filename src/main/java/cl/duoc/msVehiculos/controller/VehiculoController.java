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

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    @GetMapping("/listar_vehiculo")
    public ResponseEntity<List<Vehiculo>> listarVehiculos(){
        
        List<Vehiculo> listaVehiculos = service.listarVehiculos();

        if (listaVehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaVehiculos);
        }
    }

    @GetMapping("/listar_tipos")
    public ResponseEntity<List<TipoVehiculo>> listarTipos(){
        
        List<TipoVehiculo> listaTipos = service.listarTipos();

        if (listaTipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaTipos);
        }
    }

    @GetMapping("/listar_marcas")
    public ResponseEntity<List<MarcaVehiculo>> listarMarcas(){
        
        List<MarcaVehiculo> listaMarcas = service.listarMarcas();

        if (listaMarcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaMarcas);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<VehiculoDTO> obtenerVehiculoDTOPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(service.buscarDTOPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> obtenerPorPatente(@PathVariable String patente){
        try {
            return ResponseEntity.ok(service.buscarPorPatente(patente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Vehiculo> guardarVehiculo(@RequestBody Vehiculo vehiculo){
        Vehiculo nuevoVehiculo = service.agregarVehiculo(vehiculo);
        return ResponseEntity.ok(nuevoVehiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(@PathVariable Integer id, @RequestBody Vehiculo vehiculo){
        try {
            Vehiculo vehiculoActualizado = service.actualizarVehiculo(id, vehiculo);
            return ResponseEntity.ok(vehiculoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Integer id){
        try {
            service.eliminarVehiculo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{nombre}")
    public ResponseEntity<TipoVehiculo> obtenerTipoPorNombre(@PathVariable String nombre){
        try {
            return ResponseEntity.ok(service.buscarTipoPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/marca/{nombre}")
    public ResponseEntity<MarcaVehiculo> obtenerMarcaPorNombre(@PathVariable String nombre){
        try {
            return ResponseEntity.ok(service.buscarMarcaPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
