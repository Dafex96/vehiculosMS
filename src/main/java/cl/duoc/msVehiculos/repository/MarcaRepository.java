package cl.duoc.msVehiculos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.msVehiculos.model.MarcaVehiculo;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaVehiculo, Integer>{

    Optional<MarcaVehiculo> findByNombre(String nombre);
}
