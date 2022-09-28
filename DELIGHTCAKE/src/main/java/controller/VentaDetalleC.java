/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.VentaDetalleImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.Venta;
import lombok.Data;
import model.Producto;
import model.VentaDetalle;

@Data

@Named(value = "VentaDetalleC")
@SessionScoped
public class VentaDetalleC implements Serializable {

    private Venta venta;
    private VentaDetalle ventadetalle;
    private Producto producto;
    private VentaDetalleImpl dao;
    private List<Venta> listaVenta;
    private List<VentaDetalle> listadoVentaDetalle;
    private List<Producto> listadoProducto;
    

    public VentaDetalleC() {
        venta = new Venta();
        producto = new Producto();
        ventadetalle = new VentaDetalle();
        dao = new VentaDetalleImpl();
        listaVenta = new ArrayList<>();
        listadoVentaDetalle = new ArrayList<>();
        listadoProducto = new ArrayList<>();
    }

    public void agregarFila() {
        try {
            VentaDetalle ventadet = dao.agregarFila(ventadetalle.getProducto().getIDPRO());
            ventadet.setIDPRO(this.ventadetalle.getProducto().getIDPRO());
            ventadet.setCANVENDET(this.ventadetalle.getCANVENDET());
            ventadet.setPREPRO(ventadet.getProducto().getPREPRO());  //extra
            ventadet.setSUBVENDET(ventadet.getProducto().getPREPRO() * this.ventadetalle.getCANVENDET());
            ventadet.setNOMPRO(ventadet.getProducto().getNOMPRO());
            ventadet.setDESPRO(ventadet.getProducto().getDESPRO());
            ventadet.setINGPRO(ventadet.getProducto().getINGPRO());
            ventadet.setSTOPRO(ventadet.getProducto().getSTOPRO());
            this.listadoVentaDetalle.add(ventadet);
            ventadetalle = new VentaDetalle();
            for (VentaDetalle ventaDetalle : listadoVentaDetalle) {
                System.out.println(ventaDetalle);
            }
            sumar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "Producto Agregado"));
        } catch (Exception e) {
            System.out.println("Error en agregarFilaDAO " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarFila(VentaDetalle ventadetalle) {
        try {
            listadoVentaDetalle.remove(ventadetalle);
            sumar();
        } catch (Exception e) {
            System.out.println("Error en eliminarFilaDAO " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void registrarVenta() {
        try {
            dao.registrarVenta(venta);
            int idven = dao.ultimoIdVenta();
            dao.registrarDetalle(listadoVentaDetalle, idven);
            listadoVentaDetalle.clear();
            listarVenta();
            venta = new Venta();
        } catch (Exception e) {
            System.out.println("Error en registrarVentaC " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void limpiar() {
        this.ventadetalle = new VentaDetalle();
        this.venta = new Venta();
    }

    public void anular() throws Exception {
        limpiar();
        listadoVentaDetalle.clear();
    }

    //LISTA DE VENTAS
    public void listarVenta() {
        try {
            listaVenta = dao.listarVenta();
        } catch (Exception e) {
            System.out.println("Error en ListarC " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sumar() {
        double precioTotal = 0.0;
        for (VentaDetalle ventaDetalle : listadoVentaDetalle) {
            precioTotal += ventaDetalle.getSUBVENDET();
        }
        System.out.println(precioTotal);
        venta.setTOTVEN(precioTotal);
    }

    @PostConstruct
    public void construir() {
        listarVenta();
    }

}
