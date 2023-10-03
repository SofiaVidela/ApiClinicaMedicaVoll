
package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
import med.voll.api.domain.medico.Especialidad;

/**
 *
 * @author sofia
 */
public record DatosRespuestaConsulta( Long id, String Paciente,String Medico,String fecha,String especialidad,Boolean activo,String motivoCancelamiento) {

}
