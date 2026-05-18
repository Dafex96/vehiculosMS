package cl.duoc.msVehiculos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cl.duoc.msVehiculos.model.MarcaVehiculo;
import cl.duoc.msVehiculos.model.TipoVehiculo;
import cl.duoc.msVehiculos.model.Vehiculo;
import cl.duoc.msVehiculos.repository.MarcaRepository;
import cl.duoc.msVehiculos.repository.TipoVehiRepository;
import cl.duoc.msVehiculos.repository.VehiculoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(VehiculoRepository vehiRepo, MarcaRepository marcaRepo, TipoVehiRepository tipoRepo) {
        return args -> {

            if (marcaRepo.count() > 0 || marcaRepo.count() > 0 || tipoRepo.count() > 0) {
                System.out.println("Datos ya existen, no se cargan nuevamente...");
            }else{

                TipoVehiculo tipo1 = new TipoVehiculo(null, "Auto");
                TipoVehiculo tipo2 = new TipoVehiculo(null, "Moto");
                TipoVehiculo tipo3 = new TipoVehiculo(null, "Camioneta");
                TipoVehiculo tipo4 = new TipoVehiculo(null, "Camion");

                tipoRepo.save(tipo1);
                tipoRepo.save(tipo2);
                tipoRepo.save(tipo3);
                tipoRepo.save(tipo4);

                MarcaVehiculo marca1 = new MarcaVehiculo(null, "Toyota");
                MarcaVehiculo marca2 = new MarcaVehiculo(null, "Mazda");
                MarcaVehiculo marca3 = new MarcaVehiculo(null, "Chevrolet");
                MarcaVehiculo marca4 = new MarcaVehiculo(null, "Hyundai");

                marcaRepo.save(marca1);
                marcaRepo.save(marca2);
                marcaRepo.save(marca3);
                marcaRepo.save(marca4);

                Vehiculo vehiculo1 = new Vehiculo(null, "HF3456", 2019, "Rojo", 12354, marca1, tipo1);
                Vehiculo vehiculo2 = new Vehiculo(null, "GH7890", 2020, "Azul", 56789, marca2, tipo2);
                Vehiculo vehiculo3 = new Vehiculo(null, "IJ1234", 2018, "Blanco", 23456, marca3, tipo3);
                Vehiculo vehiculo4 = new Vehiculo(null, "KL5678", 2021, "Negro", 78901, marca4, tipo4);

                vehiRepo.save(vehiculo1);
                vehiRepo.save(vehiculo2);
                vehiRepo.save(vehiculo3);
                vehiRepo.save(vehiculo4);

                System.out.println("Datos cargados con exito...");

            }
        };
    }
}
