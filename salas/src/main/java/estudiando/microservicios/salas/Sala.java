package estudiando.microservicios.salas;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Sala.
 */
@Entity
@Table(name = "sala")
@Data
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadosSala estado;

    @NotNull
    @Min(value = 7)
    @Max(value = 10)
    @Column(name = "filas", nullable = false)
    private Integer filas;

    @NotNull
    @Min(value = 8)
    @Max(value = 10)
    @Column(name = "asientos", nullable = false)
    private Integer asientos;
    
    Sala() {}

	public Sala(@NotNull String nombre, @NotNull EstadosSala estado, @NotNull @Min(7) @Max(10) Integer filas,
			@NotNull @Min(8) @Max(10) Integer asientos) {
		super();
		this.nombre = nombre;
		this.estado = estado;
		this.filas = filas;
		this.asientos = asientos;
	}
}
