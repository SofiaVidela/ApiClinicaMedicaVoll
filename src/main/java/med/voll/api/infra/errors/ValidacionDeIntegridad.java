
package med.voll.api.infra.errors;

/**
 *
 * @author sofia
 */
public class ValidacionDeIntegridad extends RuntimeException {
    //puede extender de RunTimeException o de Throwable pero de este ultimo en el metodo tengo que crear un throw
    //quedando public void agendar (DatosAgendarConsulta datos)throws ValidacionDeIntegridad{....}
    public ValidacionDeIntegridad(String s){
        
        super(s);
    }
}
