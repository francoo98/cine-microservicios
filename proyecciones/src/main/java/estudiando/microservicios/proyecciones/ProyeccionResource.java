package estudiando.microservicios.proyecciones;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import estudiando.microservicios.proyecciones.exceptions.BadRequestAlertException;
import estudiando.microservicios.proyecciones.exceptions.ProyeccionNotFoundException;

@RestController
@RequestMapping("/api")
public class ProyeccionResource {

	private final Logger log = LoggerFactory.getLogger(ProyeccionResource.class);

	private static final String ENTITY_NAME = "proyeccion";

	private final ProyeccionRepository proyeccionRepository;
	
	private final HttpClient httpClient;

	public ProyeccionResource(ProyeccionRepository proyeccionRepository) {
		this.proyeccionRepository = proyeccionRepository;
		this.httpClient = HttpClient.newBuilder().build();
	}
	
	/**
	 * {@code GET  /proyeccions} : get all the proyeccions.
	 *
	 * @return the list proyeccions.
	 */
	@GetMapping("/proyeccions")
	public List<Proyeccion> getAllProyeccions() {
		log.debug("REST request to get all Proyeccions");
		return proyeccionRepository.findAll();
	}
	
	/**
	 * {@code GET  /proyeccions/:id} : get the "id" proyeccion.
	 *
	 * @param id the id of the proyeccion to retrieve.
	 * @return the proyeccion, or status {@code 404 (Not Found)}.
	 */
	@GetMapping("/proyeccions/{id}")
	public Proyeccion getProyeccion(@PathVariable Long id) {
		log.debug("REST request to get Proyeccion : {}", id);
		return proyeccionRepository.findById(id).orElseThrow(() -> new ProyeccionNotFoundException(id));
	}
	
	/**
	 * {@code GET  /proyeccions/hoy} : get all the proyeccions that are active today.
	 *
	 * @return a list of proyeccions.
	 */
	@GetMapping("/proyeccions/hoy") // 1
	public List<Proyeccion> getAllProyeccionsActivasHoy() {
		log.debug("REST request to get all Proyeccions activas");
		return proyeccionRepository.findProyeccionsActiveToday();
	}
	
	/**
	 * {@code POST  /proyeccions} : Create a new proyeccion.
	 *
	 * @param proyeccion the proyeccion to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new proyeccion, or with status {@code 400 (Bad Request)} if
	 *         the proyeccion has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/proyeccions")
	public ResponseEntity<Proyeccion> createProyeccion(@RequestBody @Valid Proyeccion proyeccion) throws URISyntaxException {
		log.debug("REST request to save Proyeccion : {}", proyeccion);
		JsonNode pelicula = null;
		LocalDate fechaInicio;
		LocalDate fechaFin;
		if (proyeccion.getId() != null) {
			throw new BadRequestAlertException("Error in request. A new proyeccion cannot have an id.");
		}
		
		try {
			if(!checkIfPeliculaExists(proyeccion.getPelicula_id())) {
				throw new BadRequestAlertException("Invalid pelicula");
			}
			
			if(!checkIfSalaExists(proyeccion.getSala_id())) {
				throw new BadRequestAlertException("Invalid sala");
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			pelicula = getPelicula(proyeccion.getPelicula_id());
			System.out.println(pelicula);
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		fechaInicio = LocalDate.parse(pelicula.get("fechaInicio").asText());
		fechaFin = LocalDate.parse(pelicula.get("fechaFin").asText());
		
		if(proyeccion.getFechaInicio().isBefore(fechaInicio) || 
			proyeccion.getFechaFin().isAfter(fechaFin)) {
			throw new BadRequestAlertException("Error in request");
		}
		Proyeccion result = proyeccionRepository.save(proyeccion);
		return ResponseEntity
				.created(new URI("/api/proyeccions/" + result.getId())).body(result);
	}
	
	/**
	 * {@code PUT  /proyeccions} : Updates an existing proyeccion.
	 *
	 * @param proyeccion the proyeccion to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated proyeccion, or with status {@code 400 (Bad Request)} if
	 *         the proyeccion is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the proyeccion couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/proyeccions")
	public ResponseEntity<Proyeccion> updateProyeccion(@RequestBody @Valid Proyeccion proyeccion) throws URISyntaxException {
		log.debug("REST request to update Proyeccion : {}", proyeccion);

		if (proyeccion.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}

		try {
			if(!checkIfPeliculaExists(proyeccion.getPelicula_id())) {
				throw new BadRequestAlertException("Invalid pelicula");
			}
			
			if(!checkIfSalaExists(proyeccion.getSala_id())) {
				throw new BadRequestAlertException("Invalid sala");
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		Proyeccion result = proyeccionRepository.save(proyeccion);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * {@code DELETE  /proyeccions/:id} : delete the "id" proyeccion.
	 *
	 * @param id the id of the proyeccion to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/proyeccions/{id}")
	public ResponseEntity<String> deleteProyeccion(@PathVariable Long id) {
		log.debug("REST request to delete Proyeccion : {}", id);
		Optional<Proyeccion> proyeccion = this.proyeccionRepository.findById(id);
		try {
			Proyeccion p = proyeccion.get();
			if(p.getEstado()) {
				p.setEstado(false);
				this.proyeccionRepository.save(p);
			}
		}
		catch (Exception e) {
			log.debug("ERROR Request to delete proyeccion: ID {} doesn't exist", id);
		}
		return ResponseEntity.noContent().build();
	}
	
	private boolean checkIfSalaExists(Long id) throws URISyntaxException, IOException, InterruptedException {
		HttpRequest requestSalas = HttpRequest.newBuilder()
				  .uri(new URI("http://salas:8080/api/salas/" + id))
				  .GET()
				  .build();
		
		return this.httpClient.send(requestSalas, BodyHandlers.ofString()).statusCode() == 200;
	}
	
	private boolean checkIfPeliculaExists(Long id) throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://peliculas:8080/api/peliculas/" + id))
				  .GET()
				  .build();
		
		return this.httpClient.send(request, BodyHandlers.ofString()).statusCode() == 200;
	}
	
	private JsonNode getPelicula(long id) throws URISyntaxException, IOException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode pelicula;
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://peliculas:8080/api/peliculas/" + id))
				  .GET()
				  .build();
		HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
		pelicula = mapper.readTree(response.body());
		return pelicula;
	}
}
