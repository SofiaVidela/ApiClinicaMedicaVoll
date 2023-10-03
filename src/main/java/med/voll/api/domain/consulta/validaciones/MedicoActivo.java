
package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sofia
 */
@Component
public class MedicoActivo implements ValidadorDeConsultas {
    @Autowired
    private MedicoRepository medicoRepository;
    public void validar (DatosAgendarConsulta datos){
        if (datos.idMedico() == null){
            return;
        }
        var medicoAtivo = medicoRepository.findActivoById(datos.idMedico());
        
        if (!medicoAtivo){
            throw new ValidationException("No se pueden agendar citas con medicos inactivos en el sistema");
        }
    }
}
