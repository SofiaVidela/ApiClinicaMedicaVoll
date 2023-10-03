package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sofia
 */
@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepocitory;

    public void validar(DatosAgendarConsulta datos) {
        if (datos.idMedico() == null) {
            return;
        }

        var medicoConConsulta = consultaRepocitory.existsByMedicoIdAndData(datos.idMedico(), datos.fecha());

        if (medicoConConsulta) {
            throw new ValidationException("Este Medico ya tiene una consulta agendada en ese horario");
        }
    }
}
