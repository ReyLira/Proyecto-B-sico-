/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data

public class Venta {

    private int IDVEN;
    private Date FECVEN = GregorianCalendar.getInstance().getTime();
    private int IDEMP;
    private int IDCLI;
    private Double TOTVEN;
    private Date FECENTVEN;
    private Cliente cliente = new Cliente();
    private Empleado empleado = new Empleado();
    

}
