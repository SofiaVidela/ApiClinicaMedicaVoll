package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.consulta.DatosRespuestaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sofia
 */
//@Controller y @ResponseBody conforman el @RestController y el @Controller a su vez compuesto por el @Component
//
@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;
    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos) {

        var response = service.agendar(datos);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listadoConsultas(@PageableDefault(size = 2) Pageable paginacion) {
        var response = consultaRepository.findAll(paginacion).map(DatosDetalleConsulta::new);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaConsulta> Consulta(@PathVariable Long id) {
        Consulta consulta = consultaRepository.getReferenceById(id);
        var datosConsulta = new DatosRespuestaConsulta(consulta.getId(), consulta.getPaciente().getNombre(),
                consulta.getMedico().getNombre(), consulta.getMedico().getEspecialidad().toString(), consulta.getData().toString(), consulta.getActivo(), consulta.getMotivoCancelamiento().toString());
        return ResponseEntity.ok(datosConsulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DatosCancelamientoConsulta datos) {
        service.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

}
