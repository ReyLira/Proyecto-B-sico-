package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import modelo.Personero;

public class PersoneroImpl extends Conexion implements ICRUD<Personero> {

    @Override
    public void registrar(Personero per) throws Exception {
        // dni_per | nom_per | nac_per | tel_per | asig_mes | mes_per | obs_per (9 campos)
        String sql = "insert into personero (dni_per,nom_per,nac_per,tel_per,asig_mes,mes_per,obs_per,cod_ubi,sex_per) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getDni());
            ps.setString(2, per.getNombre());
//            SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(3, forma.format(per.getNacimiento()));
            ps.setString(4, per.getTelefono());
            ps.setString(5, per.getAsignacion());
            ps.setString(6, per.getMesa());
            ps.setString(7, per.getObservacion());
            ps.setString(8, per.getUbigeo());
            ps.setString(9, per.getSexo());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Ingresar PersoneroD: " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Personero per) throws Exception {
//        String sql = "update personero set nom_per=?,tel_per=?,asig_mes=?,mes_per=?,obs_per=?,cod_ubi=?,sex_per=?  where dni_per=? ";        
        String sql = "update personero set nom_per=?,tel_per=?,asig_mes=?,mes_per=?,obs_per=?,cod_ubi=?,sex_per=?, nac_per=? where dni_per=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getNombre());
            ps.setString(2, per.getTelefono());
            ps.setString(3, per.getAsignacion());
            ps.setString(4, per.getMesa());
            ps.setString(5, per.getObservacion());
            ps.setString(6, per.getUbigeo());
            ps.setString(7, per.getSexo());
            System.out.println("Nacimiento " + per.getNacimiento());
            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(8, forma.format(per.getNacimiento()));
            ps.setString(9, per.getDni());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Modificar PersoneroD: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Personero per) throws Exception {
        String sql = "delete from personero where dni_per=?";     // query dml simple, vista, procedures, trigger         
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getDni());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminarD" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public List listarTodos() throws Exception {
        List<Personero> listado = null;
        Personero pers;
        String sql = "select * from personero";
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pers = new Personero();
                pers.setDni(rs.getString("dni_per"));
                pers.setNombre(rs.getString("nom_per"));
                pers.setNacimiento(rs.getDate("nac_per"));
                pers.setTelefono(rs.getString("tel_per"));
                pers.setAsignacion(rs.getString("asig_mes"));
                pers.setMesa(rs.getString("mes_per"));
                pers.setObservacion(rs.getString("obs_per"));
                pers.setUbigeo(rs.getString("cod_ubi"));
                pers.setSexo(rs.getString("sex_per"));
                listado.add(pers);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error en listarTodosD: " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    public List<String> autocompleteUbigeo(String consulta) throws SQLException, Exception {
        List<String> lista = new ArrayList<>();
        String sql = "select top 10 concat(dist_ubi, ', ', prov_ubi, ', ',dpto_ubi) AS UBIGEODESC from UBIGEO WHERE dist_ubi LIKE ?";
        try {
            PreparedStatement ps = this.conectar().prepareCall(sql);
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("UBIGEODESC"));
            }
        } catch (Exception e) {
            System.out.println("Error en autocompleteUbigeoDao" + e.getMessage());
        }
        return lista;
    }

    public String obtenerCodigoUbigeo(String cadenaUbi) throws SQLException, Exception {
        String sql = "select cod_ubi FROM UBIGEO WHERE concat(dist_ubi, ', ', prov_ubi, ', ',dpto_ubi) = ?";
        try {
            PreparedStatement ps = this.conectar().prepareCall(sql);
            ps.setString(1, cadenaUbi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("cod_ubi");
            }
            return rs.getString("cod_ubi");
        } catch (Exception e) {
            System.out.println("Error en obtenerCodigoUbigeo " + e.getMessage());
            throw e;
        }
    }
}
