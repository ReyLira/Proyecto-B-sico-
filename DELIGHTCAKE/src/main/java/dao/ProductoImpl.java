package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Producto;


public class ProductoImpl extends Conexion implements ICRUD<Producto> {
//private Conexion conexionTransaccional;

    @Override
    public void guardar(Producto producto) throws Exception {
        try {
            String sql = "insert into producto" + " (NOMPRO,PREPRO,DESPRO,ESTPRO,INGPRO,STOPRO)"
                    + " values (?,?,?,?,?,?) ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setDouble(2, producto.getPREPRO());
            ps.setString(3, producto.getDESPRO());
            ps.setString(4, "A");
            ps.setString(5, producto.getINGPRO());
            ps.setInt(6, producto.getSTOPRO());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/registrar: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Producto producto) throws Exception {
        try {
            String sql = "update producto set NOMPRO=?,PREPRO=?,DESPRO=?,ESTPRO=?,INGPRO=?,STOPRO=? where IDPRO=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setDouble(2, producto.getPREPRO());
            ps.setString(3, producto.getDESPRO());
            ps.setString(4, "A");
            ps.setString(5, producto.getINGPRO());
            ps.setInt(6, producto.getSTOPRO());
            ps.setInt(7, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/modificar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Producto producto) throws Exception {
        try {
            String sql = "update Producto set ESTPRO='I' where IDPRO=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/eliminar: " + e.getMessage());
        }
    }
    
    @Override
    public void restaurar(Producto producto) throws Exception {
        try {
            String sql = "update Producto set ESTPRO='A' where IDPRO=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/eliminar: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> listarTodos(int tipo) throws Exception {
        List<Producto> lista = null ;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "select * from PRODUCTO where ESTPRO='A' order by IDPRO desc";
                break;
            case 2:
                sql = "select * from PRODUCTO where ESTPRO='I' order by IDPRO desc";
                break;
            case 3:
                sql = "select * from PRODUCTO where order by IDPRO desc";
                break;
        }
        try {
            lista = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setIDPRO(rs.getInt("IDPRO"));
                pro.setNOMPRO(rs.getString("NOMPRO"));
                pro.setPREPRO(rs.getDouble("PREPRO"));
                pro.setDESPRO(rs.getString("DESPRO"));
                pro.setINGPRO(rs.getString("INGPRO"));
                pro.setSTOPRO(rs.getInt("STOPRO"));
                lista.add(pro);
            }
        } catch (Exception e) {
            System.out.println("Error al listar todos" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
}


