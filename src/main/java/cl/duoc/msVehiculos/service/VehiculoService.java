package cl.duoc.msVehiculos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.msVehiculos.model.MarcaVehiculo;
import cl.duoc.msVehiculos.model.TipoVehiculo;
import cl.duoc.msVehiculos.model.Vehiculo;
import cl.duoc.msVehiculos.repository.MarcaRepository;
import cl.duoc.msVehiculos.repository.TipoVehiRepository;
import cl.duoc.msVehiculos.repository.VehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepo;
    private TipoVehiRepository tipoRepo;
    private MarcaRepository marcaRepo;

    public List<Vehiculo> listarVehiculos(){
        return vehiculoRepo.findAll();
    }

    public List<TipoVehiculo> listarTipos(){
        return tipoRepo.findAll();
    }

    public List<MarcaVehiculo> listarMarcas(){
        return marcaRepo.findAll();
    }

    public Vehiculo buscarPorId(Integer id){
        return vehiculoRepo.findById(id).orElseThrow(() -> new RuntimeException("Vehiculo no encontrado..."));
    }

    public Vehiculo buscarPorPatente(String patente){
        return vehiculoRepo.findByPatente(patente).orElseThrow(() -> new RuntimeException("Vehiculo no encontrado..."));
    }

    public Vehiculo agregarVehiculo(Vehiculo vehiculo){
        return vehiculoRepo.save(vehiculo);
    }

    public void eliminarVehiculo(Integer id){
        if (vehiculoRepo.existsById(id)) {
            vehiculoRepo.deleteById(id);
        }else{
            throw new RuntimeException("Vehiculo no encontrado");
        }
    }

    public Vehiculo actualizarVehiculo(Integer id, Vehiculo vehiculoActualizado){

        Vehiculo vehiculoAnt = vehiculoRepo.findById(id).orElseThrow(() -> new RuntimeException("Vehiculo no encontrado..."));

        vehiculoAnt.setPatente(vehiculoActualizado.getPatente());
        vehiculoAnt.setAnio(vehiculoActualizado.getAnio());
        vehiculoAnt.setColor(vehiculoActualizado.getColor());
        vehiculoAnt.setKilometraje(vehiculoActualizado.getKilometraje());
        vehiculoAnt.setMarcaVehiculo(vehiculoActualizado.getMarcaVehiculo());
        vehiculoAnt.setTipoVehiculo(vehiculoActualizado.getTipoVehiculo());

        return vehiculoRepo.save(vehiculoAnt); 
    }




    public TipoVehiculo buscarTipoPorNombre(String nombre){
        return tipoRepo.findByNombre(nombre).orElseThrow(() -> new RuntimeException("Tipo de vehiculo no encontrado..."));
    }

    public MarcaVehiculo buscarMarcaPorNombre(String nombre){
        return marcaRepo.findByNombre(nombre).orElseThrow(() -> new RuntimeException("Marca no encontrada..."));
    }
}
