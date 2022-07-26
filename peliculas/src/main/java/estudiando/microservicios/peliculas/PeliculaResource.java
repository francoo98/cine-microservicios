package estudiando.microservicios.peliculas;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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

import estudiando.microservicios.peliculas.exceptions.BadRequestAlertException;
import estudiando.microservicios.peliculas.exceptions.PeliculaNotFoundException;

@RestController
@RequestMapping("/api")
@Transactional
public class PeliculaResource {

	private final Logger log = LoggerFactory.getLogger(PeliculaResource.class);

	private final PeliculaRepository peliculaRepository;
	// private final PeliculaService peliculaService;

	public PeliculaResource(PeliculaRepository peliculaRepository, PeliculaService peliculaService) {
		this.peliculaRepository = peliculaRepository;
		// this.peliculaService = peliculaService;
	}

	/**
	 * {@code GET  /peliculas} : get all the peliculas.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of peliculas in body.
	 */
	@GetMapping("/peliculas")
	public ResponseEntity<List<Pelicula>> getAllPeliculas() {
		log.debug("REST request to get all Peliculas");
		return ResponseEntity.ok(peliculaRepository.findAll());
	}

	@GetMapping("/peliculas/{id}")
	public Pelicula getPelicula(@PathVariable Long id) {
		log.debug("REST request to get Pelicula : {}", id);
		return peliculaRepository.findById(id).orElseThrow(() -> new PeliculaNotFoundException(id));
	}

	@PostMapping("/peliculas")
	public ResponseEntity<Pelicula> createPelicula(@Valid @RequestBody Pelicula pelicula) throws URISyntaxException {
		log.debug("REST request to save Pelicula : {}", pelicula);
		if (pelicula.getId() != null) {
			throw new BadRequestAlertException("A new pelicula cannot have an id.");
		}
		Pelicula result = peliculaRepository.save(pelicula);
		return ResponseEntity.created(new URI("/api/peliculas/" + result.getId())).body(result);
	}

	@PutMapping("/peliculas")
	public ResponseEntity<Pelicula> updatePelicula(@Valid @RequestBody Pelicula pelicula) throws URISyntaxException {
		log.debug("REST request to update Pelicula : {}", pelicula);
		if (pelicula.getId() == null) {
			throw new BadRequestAlertException("Invalid id.");
		}
		Pelicula result = peliculaRepository.save(pelicula);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/peliculas/{id}")
	public ResponseEntity<Void> deletePelicula(@PathVariable Long id) {
		log.debug("REST request to delete Pelicula : {}", id);
		Optional<Pelicula> pelicula = this.peliculaRepository.findById(id);
		if (pelicula.isPresent()) {
			if (pelicula.get().getEstado()) {
				pelicula.get().setEstado(false);
				this.peliculaRepository.save(pelicula.get());
			}
		}
		return ResponseEntity.noContent().build();
	}

}
