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
import java.util.List;
import model.Producto;

/**
 *
 * @author cupej
 */
@Named(value = "ProductoC")
@SessionScoped
public class ProductoC implements Serializable {

    private Producto producto;
    ProductoImpl dao;
    private List<Producto> lstProducto;

    public ProductoC() {
        producto = new Producto();
        dao = new ProductoImpl();
    }

    public void registrar() throws Exception {
        try {
            dao.guardar(producto);
        } catch (Exception e) {
            System.out.println("Error en registar ProductoC/registar: " + e.getMessage());
        }

    }

    public void modificar() throws Exception {
        try {
            dao.modificar(producto);
        } catch (Exception e) {
            System.out.println("Error en modificar ProductoC/modificar: " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(producto);
        } catch (Exception e) {
            System.out.println("Error en eliminar ProductoC/eliminar: " + e.getMessage());
        }
    }

    public void listar() throws Exception {
        try {
            lstProducto = dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error en eliminar ProductoC/eliminar: " + e.getMessage());
        }
    }

    // Getter y Setter
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(List<Producto> lstProducto) {
        this.lstProducto = lstProducto;
    }

}
