package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Cliente;
import model.Venta;
import model.Producto;
import model.VentaDetalle;

public class VentaDetalleImpl extends Conexion {

    // Formateo para el campo fecha 
    DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");

    // Metodo para para agregar en la tabla temporal venta
    public VentaDetalle agregarFila(int idpro) throws SQLException, Exception {
        VentaDetalle ventadetalle = null;
        String sql = "SELECT * FROM PRODUCTO WHERE IDPRO = " + idpro;
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ventadetalle = new VentaDetalle();
                Producto producto = new Producto();
                ventadetalle.setIDPRO(rs.getInt("IDPRO"));
                producto.setNOMPRO(rs.getString("NOMPRO"));
                producto.setPREPRO(rs.getDouble("PREPRO"));
                producto.setDESPRO(rs.getString("DESPRO"));
                producto.setINGPRO(rs.getString("INGPRO"));
                producto.setSTOPRO(rs.getInt("STOPRO"));
                ventadetalle.setProducto(producto);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en agregarFila_D " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
            return ventadetalle;
        }
    }

    // Metodo para registrar el la venta detalle 
    public void registrarDetalle(List<VentaDetalle> listaVentaDetalle, int idVenta) throws Exception {
        String sql = "INSERT INTO VENTA_DETALLE (CANVENDET,IDVEN,IDPRO,SUBVENDET) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            for (VentaDetalle ventadetalle : listaVentaDetalle) {
                ps.setInt(1, ventadetalle.getCANVENDET());
                ps.setInt(2, idVenta);
                ps.setInt(3, ventadetalle.getIDPRO());
                ps.setDouble(4, ventadetalle.getSUBVENDET());
                ps.executeUpdate();
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registroMultipleDAO " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    // Metodo para registar la venta 
    public void registrarVenta(Venta venta) throws Exception {
        String sql = "INSERT INTO VENTA (FECVEN,IDCLI,IDEMP,TOTVEN) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, fecha.format(venta.getFECVEN()));
            ps.setInt(2, venta.getCliente().getIDCLI());
            ps.setInt(3, venta.getEmpleado().getIDEMP());
            ps.setDouble(4, venta.getTOTVEN());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registrar_D VENTA " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.cerrar();
        }
    }

    // Metodo para obtener el ultimo id de la venta
    public int ultimoIdVenta() {
        try {
            PreparedStatement ps1 = this.conectar().prepareStatement("SELECT MAX(V.IDVEN) AS IDVEN FROM VENTA V");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDVEN");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en obtenerUltimoIdDAO" + e.getMessage());
        }
        return -1;
    }

    // Metodo listar para la tabla venta usando una vista
    public List<Venta> listarVenta() throws Exception {
        List<Venta> listado = new ArrayList<>();
        Cliente cliente;
        Venta venta;
        String sql = "SELECT * FROM vVENTA ORDER BY IDVEN DESC";
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                venta = new Venta();
                venta.setIDVEN(rs.getInt("IDVEN"));
                venta.setFECVEN(rs.getDate("FECVEN"));
                cliente = new Cliente();
                cliente.setIDCLI(rs.getInt("IDCLI"));
                cliente.setNOMCLI(rs.getString("NOMCLI"));
                cliente.setDNICLI(rs.getString("DNICLI"));
                venta.setCliente(cliente);
                listado.add(venta);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en el listadoDaoVenta " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

}
