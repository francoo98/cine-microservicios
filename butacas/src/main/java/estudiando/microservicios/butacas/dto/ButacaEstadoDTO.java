package estudiando.microservicios.butacas.dto;

import estudiando.microservicios.butacas.EstadosButaca;
import lombok.Data;

@Data
public class ButacaEstadoDTO {
	EstadosButaca estado;
	int fila;
	int asiento;
	
	public ButacaEstadoDTO(EstadosButaca estado, int fila, int asiento) {
		super();
		this.estado = estado;
		this.fila = fila;
		this.asiento = asiento;
	}
}
