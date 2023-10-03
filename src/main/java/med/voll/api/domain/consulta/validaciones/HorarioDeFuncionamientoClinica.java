
package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import java.time.DayOfWeek;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

/**
 *
 * @author sofia
 */
@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas {
    
    public void validar(DatosAgendarConsulta datos){
        
        var domingo= DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        var antesDeApertura = datos.fecha().getHour()<7;
        var despuesDeCierre=datos.fecha().getHour()>19;
        if (domingo || antesDeApertura|| despuesDeCierre){
            throw new ValidationException("El horario de atencion de la clinica es de Lunes a Sabado de 7 a 19 horas");
        }
    }
}
