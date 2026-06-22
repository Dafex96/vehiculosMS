package cl.duoc.msVehiculos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marca")
@Schema(description = "Representa la marca del vehículo")
public class MarcaVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la marca del vehículo")
    private Integer idMarca;

    @Column(nullable = false)
    @Schema(description = "Nombre de la marca")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Modelo de la marca")
    private String modelo;

}
