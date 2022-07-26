package estudiando.microservicios.peliculas;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "pelicula")
@Data
public class Pelicula implements Serializable {

	private static final long serialVersionUID = 1L;

	Pelicula() {}
	
	Pelicula(String nombre, String descripcion, String detalle, Integer duracion, String genero, String clasificacion,
			Boolean estado, LocalDate fechaInicio, LocalDate fechaFin) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.detalle = detalle;
		this.duracion = duracion;
		this.estado = estado;
		this.fechaFin = fechaFin;
		this.fechaInicio = fechaInicio;
		this.clasificacion = clasificacion;
		this.genero = genero;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Size(max = 400)
	@Column(name = "descripcion", length = 400)
	private String descripcion;

	@Size(max = 400)
	@Column(name = "detalle", length = 400)
	private String detalle;

	@Min(value = 0)
	@Max(value = 400)
	@Column(name = "duracion")
	private Integer duracion;

	@Column(name = "genero")
	private String genero;

	@Column(name = "clasificacion")
	private String clasificacion;

	@NotNull
	@Column(name = "estado", nullable = false)
	private Boolean estado;

	@NotNull
	@Column(name = "fecha_inicio", nullable = false)
	private LocalDate fechaInicio;

	@NotNull
	@Column(name = "fecha_fin", nullable = false)
	private LocalDate fechaFin;
}
