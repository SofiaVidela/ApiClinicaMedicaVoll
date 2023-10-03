package med.voll.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author sofia
 */
@RestControllerAdvice//buscar info de programacion orientada a aspectos
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatosErrorValidation::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity errorHandlerValidacionesDeIntegridad(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DatosErrorValidation(String campo, String error) {

        public DatosErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
    /**
     * Se pueden modificar los mensajes de error de las validaciones de esta
     * forma
     *
     * @NotBlank(message = "Email es obligatorio")
     * @Email(message = "Formato de email es inválido") o en un archivo
     * ValidationMessages.properties en el directorio src/main/resources:
     * nombre.obligatorio=El nombre es obligatorio email.obligatorio=Correo
     * electrónico requerido email.invalido=El formato del correo electrónico no
     * es válido phone.obligatorio=Teléfono requerido crm.obligatorio=CRM es
     * obligatorio crm.invalido=El formato CRM no es válido
     * especialidad.obligatorio=La especialidad es obligatoria
     * address.obligatorio=Los datos de dirección son obligatorios y despues en
     * los mensajes
     * @NotBlank(message = "{nombre.obligatorio}")
     */
}
