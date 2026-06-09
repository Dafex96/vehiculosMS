package cl.duoc.msVehiculos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehiculo")
@Schema(description = "Representa un vehículo en el sistema")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del vehículo")
    private Integer idVehiculo;

    @Column(nullable = false)
    @Schema(description = "Patente del vehículo")
    private String patente;

    @Column(nullable = false)
    @Schema(description = "Año del vehículo")
    private Integer anio;

    @Column(nullable = false)
    @Schema(description = "Color del vehículo")
    private String color;

    @Column(nullable = false)
    @Schema(description = "Kilometraje del vehículo")
    private Integer kilometraje;

    @ManyToOne
    @JoinColumn(name = "idMarca")
    @Schema(description = "Marca del vehículo")
    private MarcaVehiculo marcaVehiculo;

    @ManyToOne
    @JoinColumn(name = "idTipoVehiculo")
    @Schema(description = "Tipo de vehículo")
    private TipoVehiculo tipoVehiculo;
}
