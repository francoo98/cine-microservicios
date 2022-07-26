package estudiando.microservicios.butacas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long>{

}
