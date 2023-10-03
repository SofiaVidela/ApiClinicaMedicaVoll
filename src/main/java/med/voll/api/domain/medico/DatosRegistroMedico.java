
package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

/**
 *
 * @author sofia
 */
//ESTO SERIA EL DTO - DATA TRANSFER OBJECT- patron de diseño
/**
su objetivo es únicamente representar datos que serán recibidos o devueltos por la API,
sin ningún tipo de comportamiento.
Para crear una clase DTO inmutable, sin la utilización de Record,
era necesario escribir mucho código. Veamos un ejemplo de una clase DTO que
representa un teléfono:
public final class Telefono {

    private final String ddd;
    private final String numero;

    public Telefono(String ddd, String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ddd, numero);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Telefono)) {
            return false;
        } else {
            Telefono other = (Telefono) obj;
            return Objects.equals(ddd, other.ddd)
              && Objects.equals(numero, other.numero);
        }
    }

    public String getDdd() {
        return this.ddd;
    }

    public String getNumero() {
        return this.numero;
    }
}
*/


public record DatosRegistroMedico(
        @NotBlank                       //se asegura que no sea ni en blanco ni nulo
        String nombre,
        
        @NotBlank
        @Email
        String email,
        
        @NotBlank
        String telefono,
        
        @NotBlank
        @Pattern(regexp = "\\d{5,8}")   //se debe cumplir el patrin de que sean numeros de entre 5 y 8 digitos
        String documento,
        
        @NotNull
        Especialidad especialidad,
        
        @NotNull                       //los objetos solo pueden ser nulos
        @Valid
        DatosDireccion direccion) {
}
