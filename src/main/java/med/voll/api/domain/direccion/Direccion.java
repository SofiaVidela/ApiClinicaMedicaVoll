package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author sofia
 */
@Embeddable
@Getter//genera getters con lombok
@NoArgsConstructor//genera constructor vacioo por defecto con lombok
@AllArgsConstructor//genera constructor con todos los parametros con lombok
public class Direccion {

    private String calle;
    private String numero;
    private String complemento;
    private String distrito;
    private String ciudad;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.ciudad = direccion.ciudad();
        this.complemento = direccion.complemento();
        this.distrito = direccion.distrito();
        this.numero = direccion.numero();
    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.ciudad = direccion.ciudad();
        this.complemento = direccion.complemento();
        this.distrito = direccion.distrito();
        this.numero = direccion.numero();
        return this;
    }

}
