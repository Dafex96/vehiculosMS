package cl.duoc.msVehiculos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.msVehiculos.model.TipoVehiculo;

@Repository
public interface TipoVehiRepository extends JpaRepository<TipoVehiculo, Integer>{

    Optional<TipoVehiculo> findByNombre(String nombre);
}
