
package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sofia
 */
@Component("ValidadorHorarioAntecedenciaCancelamiento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsulta{
    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Override
    public void validar(DatosCancelamientoConsulta datos){
        var consulta =consultaRepository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora,consulta.getData()).toHours();
        
        if(diferenciaEnHoras<24){
            throw new ValidationException("La consulta solo puede cancelarce con 24horas de anticipacion");
        }
    } 
}
