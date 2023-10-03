/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;


/**
 *
 * @author sofia
 */
public record DatosActualizarPaciente(@NotNull Long id,String nombre,String documento,DatosDireccion direccion) {

}
