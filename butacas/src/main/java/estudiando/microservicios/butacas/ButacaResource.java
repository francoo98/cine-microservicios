package estudiando.microservicios.butacas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import estudiando.microservicios.butacas.exceptions.BadRequestAlertException;
import estudiando.microservicios.butacas.exceptions.ButacaNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ButacaResource {

	private final Logger log = LoggerFactory.getLogger(ButacaResource.class);
	private static final String ENTITY_NAME = "butaca";
	
	private final ButacaRepository repository;
	private final HttpClient httpClient;
	//private final ButacaService butacaService;

	public ButacaResource(ButacaRepository repository) {
		this.repository = repository;
		this.httpClient = HttpClient.newBuilder().build();
		//this.butacaService = butacaService;
	}
	
	/**
	 * {@code GET  /butacas} : get all the butacas.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of butacas in body.
	 */
	@GetMapping("/butacas")
	public List<Butaca> getAllButacas() {
		log.debug("REST request to get all Butacas");
		return repository.findAll();
	}

	/**
	 * {@code GET  /butacas/:id} : get the "id" butaca.
	 *
	 * @param id the id of the butaca to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the butaca, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/butacas/{id}")
	public Butaca getButaca(@PathVariable Long id) {
		log.debug("REST request to get Butaca : {}", id);
		return repository.findById(id).orElseThrow(() -> new ButacaNotFoundException(id));
	}
	
	/**
	 * {@code POST  /butacas} : Create a new butaca.
	 *
	 * @param butaca the butaca to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new butaca, or with status {@code 400 (Bad Request)} if the
	 *         butaca has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/butacas")
	public ResponseEntity<Butaca> createButaca(@RequestBody @Valid Butaca butaca) throws URISyntaxException {
		log.debug("REST request to save Butaca : {}", butaca);
		JsonNode proyeccion = null;
		JsonNode pelicula;
		Instant fechaProyeccion;
		if (butaca.getId() != null) {
			throw new BadRequestAlertException("A new butaca cannot already have an ID");
		}
		try {
			if(!checkIfProyeccionExists(butaca.getProyeccionId())) {
				throw new BadRequestAlertException("Invalid proyeccion");
			}
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			proyeccion = getProyeccion(butaca.getProyeccionId());
			pelicula = getPelicula(proyeccion.get("pelicula_id").asLong());
		} catch (URISyntaxException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fechaProyeccion = Instant.parse(proyeccion.get("hora").asText());
		//fechaProyeccion = LocalDateTime.parse(proyeccion.get("hora").asText());
		/*if(!(pelicula.getFechaInicio().isBefore(fechaProyeccion) && pelicula.getFechaFin().isAfter(fechaProyeccion)
				|| pelicula.getFechaInicio().isEqual(fechaProyeccion) || pelicula.getFechaFin().isEqual(fechaProyeccion))) {
			throw new BadRequestAlertException("Pelicula is not active", ENTITY_NAME, "non-activepelicula");
		}*/
		//Butaca result = butacaService.save(butaca);
		Butaca result = repository.save(butaca);
		return ResponseEntity
				.created(new URI("/api/butacas/" + result.getId())).body(result);
	}
	
	/**
	 * {@code PUT  /butacas} : Updates an existing butaca.
	 *
	 * @param butaca the butaca to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated butaca, or with status {@code 400 (Bad Request)} if the
	 *         butaca is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the butaca couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/butacas")
	public ResponseEntity<Butaca> updateButaca(@RequestBody @Valid Butaca butaca) throws URISyntaxException {
		log.debug("REST request to update Butaca : {}", butaca);
		if (butaca.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}
		Butaca result = repository.save(butaca);
		return ResponseEntity.ok().body(result);
	}
	
	private boolean checkIfProyeccionExists(Long id) throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://proyecciones:8080/api/proyeccions/" + id))
				  .GET()
				  .build();
		
		return this.httpClient.send(request, BodyHandlers.ofString()).statusCode() == 200;
	}
	
	private JsonNode getProyeccion(long id) throws URISyntaxException, IOException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode proyeccion;
	    HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://proyecciones:8080/api/proyeccions/" + id))
				  .GET()
				  .build();
	    HttpResponse<String> response = this.httpClient.send(request, BodyHandlers.ofString());
	    proyeccion = mapper.readTree(response.body());
		return proyeccion;
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
