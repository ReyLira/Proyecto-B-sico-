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
import model.Cliente;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

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
    private PDFOptions pdf;
    private ExcelOptions xls;

    public EmpleadoC() {
        empleado = new Empleado();
        dao = new EmpleadoImpl();
        lstEmpleado = new ArrayList<>();
        opcionesPersonalizacion();
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminado", "Eliminado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en eliminar ProductoC/eliminar: " + e.getMessage());
        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(empleado);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurado", "Restaurado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en eliminar EmpleadoC/restaurar: " + e.getMessage());
        }
    }

    public void listar() {
        try {
            lstEmpleado = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en restaurar EmpleadoC/modificar: " + e.getMessage());
        }
    }

    public void limpiar() {
        empleado = new Empleado();
    }
    
    public void opcionesPersonalizacion() {
        xls = new ExcelOptions();
        xls.setFacetBgColor("#19C7FF");
        xls.setFacetFontSize("10");
        xls.setFacetFontColor("#FFFFFF");
        xls.setFacetFontStyle("BOLD");
        xls.setCellFontColor("#000000");
        xls.setCellFontSize("8");
        xls.setFontName("Verdana");

        pdf = new PDFOptions();
        pdf.setFacetBgColor("#19C7FF");
        pdf.setFacetFontColor("#000000");
        pdf.setFacetFontStyle("BOLD");
        pdf.setCellFontSize("12");
        pdf.setFontName("Courier");
        pdf.setOrientation(PDFOrientationType.LANDSCAPE);
    }

    @PostConstruct
    public void construir() {
        listar();
    }
}
