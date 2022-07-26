package estudiando.microservicios.proyecciones;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "proyeccion")
@Data
public class Proyeccion implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull
    @Column(name = "hora", nullable = false)
    private Instant hora;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    //@ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "proyeccions", allowSetters = true)
    private Long pelicula_id;

    //@ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "proyeccions", allowSetters = true)
    private Long sala_id;

	public Proyeccion() {
		super();
	}

	public Proyeccion(@NotNull LocalDate fechaInicio, @NotNull LocalDate fechaFin, @NotNull Instant hora,
			@NotNull Boolean estado, @NotNull Long pelicula_id, @NotNull Long sala_id) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.hora = hora;
		this.estado = estado;
		this.pelicula_id = pelicula_id;
		this.sala_id = sala_id;
	}
    
}
