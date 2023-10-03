
package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import med.voll.api.domain.medico.Especialidad;


/**
 *
 * @author sofia
 */
public record DatosAgendarConsulta(Long id,@NotNull Long idPaciente,Long idMedico,@NotNull @Future LocalDateTime fecha,Especialidad especialidad) {

 /** 
    * si se anota @NotNull no puedo enviar el mensaje al cliente desde AgendaDeConsultasService
    *  public record DatosCompra(@JsonAlias("producto_id") Long idProducto,
    * @JsonAlias({"fecha_compra", "fecha"}) LocalDate fechaCompra){}
 */
}
