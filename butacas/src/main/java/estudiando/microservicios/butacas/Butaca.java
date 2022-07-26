package estudiando.microservicios.butacas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import estudiando.microservicios.butacas.dto.ButacaEstadoDTO;
import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "butaca")
@Data
public class Butaca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_de_venta", nullable = false)
    private LocalDate fechaDeVenta;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "fila", nullable = false)
    private Integer fila;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "asiento", nullable = false)
    private Integer asiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosButaca estado;

    @NotNull
    @JsonIgnoreProperties(value = "butacas", allowSetters = true)
    private Long proyeccionId;
    
    public ButacaEstadoDTO toButacaEstadoDTO() {
    	return new ButacaEstadoDTO(this.estado, this.fila, this.asiento);
    }
    
    

}