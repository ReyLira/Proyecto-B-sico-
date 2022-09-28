/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EmpleadoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Empleado;
import lombok.Data;

@Data

/**
 *
 * @author cupej
 */
@Named(value = "EmpleadoC")
@SessionScoped
public class EmpleadoC implements Serializable {

    private Empleado empleado;
    private EmpleadoImpl dao;
    private List<Empleado> lstEmpleado;
    private int tipo = 1;

    public EmpleadoC() {
        empleado = new Empleado();
        dao = new EmpleadoImpl();
        lstEmpleado = new ArrayList<>();
    }

    public void registrar() throws Exception {
        try {
            dao.guardar(empleado);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Registrado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en registar EmpleadoC/registar: " + e.getMessage());
        }

    }

    public void modificar() throws Exception {
        try {
            dao.modificar(empleado);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Registrado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en modificar EmpleadoC/modificar: " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(empleado);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminado", "Eliminado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en eliminar ProductoC/eliminar: " + e.getMessage());
        }
    }

    public void listar() {
        try {
            lstEmpleado = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en eliminar EmpleadoC/modificar: " + e.getMessage());
        }
    }
    
    public void limpiar(){
        empleado = new Empleado();
    }

    @PostConstruct
    public void construir() {
        listar();
    }
}
