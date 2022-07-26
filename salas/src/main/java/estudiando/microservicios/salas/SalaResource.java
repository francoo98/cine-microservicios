package estudiando.microservicios.salas;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.tomcat.util.http.ResponseUtil;
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

import estudiando.microservicios.salas.exceptions.BadRequestAlertException;
import estudiando.microservicios.salas.exceptions.SalaNotFoundException;

@RestController
@RequestMapping("/api")
// @Transactional
public class SalaResource {
	
	private final Logger log = LoggerFactory.getLogger(SalaResource.class);

	private static final String ENTITY_NAME = "sala";

	private final SalaRepository salaRepository;

	public SalaResource(SalaRepository salaRepository) {
		this.salaRepository = salaRepository;
	}

	
	/**
	 * {@code GET  /salas} : get all the salas.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of salas in body.
	 */
	@GetMapping("/salas")
	public List<Sala> getAllSalas() {
		log.debug("REST request to get all Salas");
		return salaRepository.findAll();
	}

	/**
	 * {@code GET  /salas/:id} : get the "id" sala.
	 *
	 * @param id the id of the sala to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the sala, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/salas/{id}")
	public Sala getSala(@PathVariable Long id) {
		log.debug("REST request to get Sala : {}", id);
		return salaRepository.findById(id).orElseThrow(() -> new SalaNotFoundException(id));
	}
	
	/**
	 * {@code POST  /salas} : Create a new sala.
	 *
	 * @param sala the sala to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new sala, or with status {@code 400 (Bad Request)} if the
	 *         sala has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/salas")
	public ResponseEntity<Sala> createSala(@RequestBody @Valid Sala sala) throws URISyntaxException {
		log.debug("REST request to save Sala : {}", sala);
		if (sala.getId() != null) {
			throw new BadRequestAlertException("A new sala cannot already have an ID");
		}
		Sala result = salaRepository.save(sala);
		return ResponseEntity
				.created(new URI("/api/salas/" + result.getId())).body(result);
	}
	
	/**
	 * {@code PUT  /salas} : Updates an existing sala.
	 *
	 * @param sala the sala to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated sala, or with status {@code 400 (Bad Request)} if the
	 *         sala is not valid, or with status {@code 500 (Internal Server Error)}
	 *         if the sala couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/salas")
	public ResponseEntity<Sala> updateSala(@RequestBody @Valid Sala sala) throws URISyntaxException {
		log.debug("REST request to update Sala : {}", sala);
		if (sala.getId() == null) {
			throw new BadRequestAlertException("Invalid id");
		}
		Sala result = salaRepository.save(sala);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * {@code DELETE  /salas/:id} : delete the "id" sala.
	 *
	 * @param id the id of the sala to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/salas/{id}")
	public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
		log.debug("REST request to delete Sala : {}", id);
		Optional<Sala> sala = this.salaRepository.findById(id);
		if(sala.isPresent()) {
			if(sala.get().getEstado() != EstadosSala.Eliminada) {
				sala.get().setEstado(EstadosSala.Eliminada);
			}
		}
		return ResponseEntity.noContent().build();
	}
}
