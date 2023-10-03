
package med.voll.api.domain.consulta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

/**
 *
 * @author sofia
 */
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Consulta")
@Table(name = "consultas")
public class Consulta {
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    //@JsonFormat(pattern = "dd/MM/yyyy HH:mm") en caso de necesitar que la consulta sea realizada con otro formato
        
    private LocalDateTime data;
    private Boolean activo;
    
    @Column(name= "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;
    
    public Consulta (Medico medico,Paciente paciente,LocalDateTime fecha){
        this.medico=medico;
        this.paciente=paciente;
        this.data=fecha;
    }
    public void cancelar (MotivoCancelamiento motivo){
        this.motivoCancelamiento= motivo;
        this.activo = false;
    }
}
