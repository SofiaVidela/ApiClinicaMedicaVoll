
package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author sofia
 */
public record DatosListadoPaciente( Long id,String nombre, String email, String documento) {
    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(),paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }
}
