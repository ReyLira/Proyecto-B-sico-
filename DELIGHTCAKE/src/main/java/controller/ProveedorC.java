package controller;

import dao.ProveedorImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import model.Proveedor;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

@Data

@Named(value = "ProveedorC")
@SessionScoped
public class ProveedorC implements Serializable {

    private Proveedor proveedor;
    private ProveedorImpl dao;
    private List<Proveedor> lstProveedor;
    private int tipo = 1;
    private PDFOptions pdf;
    private ExcelOptions xls;

    public ProveedorC() {
        proveedor = new Proveedor();
        dao = new ProveedorImpl();
        lstProveedor = new ArrayList<>();
        opcionesPersonalizacion();
    }

    public void registrar() throws Exception {
        try {
            dao.guardar(proveedor);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Registrado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en registar ProveedorC/registar: " + e.getMessage());
        }

    }

    public void modificar() throws Exception {
        try {
            dao.modificar(proveedor);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Registrado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en modificar ProveedorC/modificar: " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(proveedor);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminado", "Eliminado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en eliminar ProveedorC/eliminar: " + e.getMessage());
        }
    }

    public void restaurar() throws Exception {
        try {
            dao.restaurar(proveedor);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurado", "Restaurado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en eliminar ProveedorC/restaurar: " + e.getMessage());
        }
    }

    public void listar() {
        try {
            lstProveedor= dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en restaurar ProveedorC/modificar: " + e.getMessage());
        }
    }

    public void limpiar() {
        proveedor = new Proveedor();
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
