package cl.duoc.msVehiculos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa un vehículoDTO con sus atributos principales")
public class VehiculoDTO {

    @Schema(description = "ID del vehículo")
    private Integer id;
    
    @Schema(description = "Patente del vehículo")
    private String patente;
    
    @Schema(description = "Marca del vehículo")
    private String marca;

    @Schema(description = "Modelo del vehículo")
    private String modelo;

}
