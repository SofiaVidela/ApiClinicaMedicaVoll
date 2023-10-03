package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.net.URI;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosActualizarMedico;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author sofia
 */
@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired //no se deberia colocar aca por posibles fallas de testing deberia estar en el getter? esto es como poner abajo = new blabla
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {

        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);

        //tiene que retornar 201 que dice que se ha creado correcctamente
        //como standard debe debolver URL de donde encontrar al medico regustrado
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
        // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new); RETORNA TODOS LOS MEDICOS
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));//SOLO RETORNA LOSACTIVOS
    }

    /**
     * Pageable paginacion tiene parametros por defecto pero se pueden modificar
     * desde la url que hace la solicitud por ejemplo:
     * http://localhost:8080/medicos?size=2&page2 en el ejemplo devuelve 2
     * resultados por pagina y muestra la 2da pagina de la busqueda si le
     * agregamos al ejemplo &sort=nombre en este caso tamnien esta ordenando la
     * respuesta por nombre-> este parametro tiene que estar definido igual que
     * en la entidad(Medico.java)
     *
     * @PageableDefault(size=2) esta anotacion sobreescribe los parametros por
     * defecto, pero los que envie el cliente van a sobre escribir estos Estos
     * parametros pueden re nombrarse en el archivo properties
     * spring.data.web.pageable.page-parameter=pagina
     * spring.data.web.pageable.size-parameter=tamano
     * spring.data.web.sort.sort-parameter=orden
     *
     */
    @PutMapping
    @Transactional //necesario si No llama al repository genera un comit y un cierre de la coneccion.Si falla Rollback
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

    //DELETE LOGICO QUE RESPONDE OK(204) EN LA VERIFICAION
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }
//  DELETE LOGICO QUE RESPONDE OK(200) EN LA VERIFICAION 
//    @DeleteMapping("/{id}")
//   @Transactional
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medico.desactivarMedico();
//    }
// DELETE EN BASE DE DATOS
//    @DeleteMapping("/{id}")//entre llaves es un parametro variable @PathVariable(variable de la barra de direcciones)
//    @Transactional//para hacer efectivo el cambio en la base de datos
//    public void eliminarMedico(@PathVariable Long id){
//                Medico medico =  medicoRepository.getReferenceById(id);
//                medicoRepository.delete(medico);
//    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }
}
