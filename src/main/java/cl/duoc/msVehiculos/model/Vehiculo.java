package cl.duoc.msVehiculos.model;

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
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVehiculo;

    @Column(nullable = false)
    private String patente;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer kilometraje;

    @ManyToOne
    @JoinColumn(name = "idMarca")
    private MarcaVehiculo marcaVehiculo;

    @ManyToOne
    @JoinColumn(name = "idTipoVehiculo")
    private TipoVehiculo tipoVehiculo;
}
