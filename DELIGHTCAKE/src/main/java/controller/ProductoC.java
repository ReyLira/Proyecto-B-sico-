/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProductoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Producto;
import lombok.Data;

@Data

@Named(value = "ProductoC")
@SessionScoped
public class ProductoC implements Serializable {

    private Producto producto;
    private ProductoImpl dao;
    private List<Producto> lstProducto;
    private int tipo = 1;

    public ProductoC() {
        producto = new Producto();
        dao = new ProductoImpl();
        lstProducto = new ArrayList<>();
    }

    public void registrar() throws Exception {
        try {
            dao.guardar(producto);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Registrado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en registar ProductoC/registar: " + e.getMessage());
        }

    }

    public void modificar() throws Exception {
        try {
            dao.modificar(producto);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Registrado con éxito"));
        } catch (Exception e) {
            System.out.println("Error en modificar ProductoC/modificar: " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(producto);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Correcto", "Producto eliminado"));
        } catch (Exception e) {
            System.out.println("Error en cambiar estado ProductoC/estado: " + e.getMessage());
        }
    }

    public void listar() {
        try {
            lstProducto = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en eliminar ProductoC/modificar: " + e.getMessage());
        }
    }

    @PostConstruct
    public void construir() {
        listar();
    }
}
