
package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sofia
 */
@Component
public class PacienteActivo implements ValidadorDeConsultas {
    @Autowired
    private PacienteRepository pacienteRepository;
    public void validar (DatosAgendarConsulta datos){
        if (datos.idPaciente() == null){
            return;
        }
        var pacienteAtivo = pacienteRepository.findActivoById(datos.idPaciente());
        
        if (!pacienteAtivo){
            throw new ValidationException("No se pueden agendar citas con pacientes inactivos en el sistema");
        }
    }
}
