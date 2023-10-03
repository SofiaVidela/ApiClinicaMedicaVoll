
package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author sofia
 */
public record DatosCancelamientoConsulta(@NotNull Long idConsulta,@NotNull MotivoCancelamiento motivo) {

}
