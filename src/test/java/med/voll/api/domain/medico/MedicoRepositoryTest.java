package med.voll.api.domain.medico;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author sofia * Para lo cual se necesita configurar una Base de Datos de
 * Prueba para trabajar Una opcion es agregar la dependencia para una base de
 * datos en memoria como H2 dentro de el pom.xml:
 * <dependency>
 * <groupId>com.h2database</groupId>
 * <artifactId>h2</artifactId>
 * <scope>runetime</optional>
 * </dependency>
 * y la configuracion pertinente de la misma en el archivo
 * application.properties o application.yml segun corresponda
 * spring.datasourse.url=jdbc:h2:mem:testdb
 * spring.datasourse.driverClassName=org.h2.Driver spring.datasourse.username=sa
 * spring.datasourse.password=password
 * spring.jpa.database-plataform=org.hibernate.dialect.H2Dialect y eliminar la
 * notacion @AutoConfigureTestDatabase
 *
 * Para usar una base de datos diferente a la usada en produccion se necesita
 * configurar application-test.properties o application-test.yml
 */
@DataJpaTest//permite utilizar algunos metodos relacionados con la utilizacion de capas de percistencia
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//aqui se aclara que se va a usar una base de datos externa pero que no remplazara la utilizada previamente
@ActiveProfiles("test")//indica a que busque la informacion necesaria en application-test.propertie

class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepocitorio;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEcenario1() {

        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("jose", "j@gmail.com", "123456", Especialidad.PEDIATRIA);
        var paciente = registrarPaciente("sofia", "s@email.com", "321654");
        registarConsulta(medico, paciente, proximoLunes10H);

        var medicoLibre = medicoRepocitorio.seleccionarMedicoConEspecialidadEnFecha(Especialidad.PEDIATRIA, proximoLunes10H);
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("deberia retornar un medico se encuentre en consulta en la Base de Datos")
    void seleccionarMedicoConEspecialidadEnFechaEcenario2() {

        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("jose", "j@gmail.com", "123456", Especialidad.PEDIATRIA);

        var medicoLibre = medicoRepocitorio.seleccionarMedicoConEspecialidadEnFecha(Especialidad.PEDIATRIA, proximoLunes10H);
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad PEDIATRIA) {
        var medico = new Medico(datosMedico(nombre, email, documento, Especialidad.PEDIATRIA));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(nombre, email, "123245654", documento, especialidad, datosDireccion());
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(nombre, email, "123245654", documento, datosDireccion());
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion("Maipu", "123", "Mendoza", "321", "12");
    }

}
