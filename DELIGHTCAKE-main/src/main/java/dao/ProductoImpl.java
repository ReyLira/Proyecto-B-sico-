package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Producto;

public class ProductoImpl extends Conexion implements ICRUD<Producto> {

    @Override
    public void guardar(Producto producto) throws Exception {
        try {
            String sql = "insert into producto" + " (NOMPRO,PREPRO,DESPRO)" + " values (?,?,?) ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setDouble(2, producto.getPREPRO());
            ps.setString(3, producto.getDESPRO());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/registrar: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Producto producto) throws Exception {
        try {
            String sql = "update product set NOMPRO=?,PREPRO=?,DESPRO=? whrere IDPRO=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNOMPRO());
            ps.setDouble(2, producto.getPREPRO());
            ps.setString(3, producto.getDESPRO());
            ps.setInt(4, producto.getIDPRO());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Producto producto) throws Exception {
        try {
            String sql = "delete paciente where IDPRO=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getIDPRO());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/eliminar: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> listarTodos() throws Exception {
        List<Producto> lista = new ArrayList<>();
        ResultSet rs;
        String sql = "Select * from producto";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setIDPRO(rs.getInt("IDPRO"));
                pro.setNOMPRO(rs.getString("NOMPRO"));
                pro.setPREPRO(rs.getDouble("PREPRO"));
                pro.setDESPRO(rs.getString("DESPRO"));
                lista.add(pro);

            }
            

        } catch (Exception e) {

        } finally {
            this.cerrar();
        }
        return lista;
    }

}
