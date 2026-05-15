package cl.duoc.msVehiculos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {

    private Integer id;
    private String patente;
    private String marca;
    private String modelo;

}
