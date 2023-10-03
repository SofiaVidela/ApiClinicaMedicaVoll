
package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

/**
 *
 * @author sofia
 */
public record DatosDetalleConsulta(Long id,Long idPaciente,Long idMedico,LocalDateTime fecha,Boolean activo) {

    public DatosDetalleConsulta(Consulta consulta) {
    this(consulta.getId(),consulta.getPaciente().getId(),consulta.getMedico().getId(),consulta.getData(),consulta.getActivo()); 
    }

}
