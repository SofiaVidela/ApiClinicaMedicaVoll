
package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

/**
 *
 * @author sofia
 */
@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{
        
        public void validar(DatosAgendarConsulta datos){
        
        var ahora= LocalDateTime.now();
        var horaDeConsulta=datos.fecha();
        
        var diderenciaDe30min= Duration.between(ahora, horaDeConsulta).toMinutes()<30;
        if (diderenciaDe30min){
            throw new ValidationException("Las consultas deben programarse al menos con 30min de anticipacion");
        }
    }
}
