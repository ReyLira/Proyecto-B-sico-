package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Proveedor;

public class ProveedorImpl extends Conexion implements ICRUD<Proveedor> {

    @Override
    public void guardar(Proveedor proveedor) throws Exception {
        try {
            String sql = "insert into PROVEEDOR" + " (NOMPROV, APEPROV, ESTPROV)"
                    + " values (?,?,?) ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getNOMPROV());
            ps.setString(2, proveedor.getAPEPROV());
            ps.setString(3, "A");
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en ProveedorImpl/registrar: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Proveedor proveedor) throws Exception {
        try {
            String sql = "update PROVEEDOR set NOMPROV=?,APEPROV=?,ESTPROV=? where IDPROV=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getNOMPROV());
            ps.setString(2, proveedor.getAPEPROV());
            ps.setString(3, proveedor.getESTPROV());
            ps.setString(4, "A");
            ps.setInt(5, proveedor.getIDPROV());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en ProveedorImpl/modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Proveedor proveedor) throws Exception {
        try {
            String sql = "update PROVEEDOR set ESTPROV='I' where IDPROV=? ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, proveedor.getIDPROV());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en ProveedorImpl/Cambiar Estado: " + e.getMessage());
        }
    }
    
    @Override
    public void restaurar(Proveedor proveedor) throws Exception {
        try {
            String sql = "update PROVEEDOR set ESTPROV='A' where IDPROV=? ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, proveedor.getIDPROV());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en ProveedorImpl/Cambiar Estado: " + e.getMessage());
        }
    }

    @Override
    public List listarTodos(int tipo) throws Exception {
        List<Proveedor> lista = null ;
        Proveedor proveedor;
        String sql = "";  
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM PROVEEDOR WHERE ESTPROV = 'A'";
                break;
            case 2:
                sql = "SELECT * FROM PROVEEDOR WHERE ESTPROV = 'I'";
                break;
            case 3:
                sql = "SELECT * FROM PROVEEDOR";
                break;
        }
        try {
            lista = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Proveedor prov = new Proveedor();
                prov.setIDPROV(rs.getInt("IDPROV"));
                prov.setNOMPROV(rs.getString("NOMPROV"));
                prov.setAPEPROV(rs.getString("APEPROV"));
                lista.add(prov);
            }
        } catch (Exception e) {
            System.out.println("Error al listar todos" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }


}
