
package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DatosDireccion;

/**
 *
 * @author sofia
 */
public record DatosRespuestaMedico(Long id, String nombre, String email, String telefono, String documento, DatosDireccion direccion) {

}
