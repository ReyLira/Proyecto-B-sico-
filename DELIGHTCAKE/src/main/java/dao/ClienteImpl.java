package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

/**
 *
 * @author manol
 */
public class ClienteImpl extends Conexion implements ICRUD<Cliente> {

    @Override
    public void guardar(Cliente cliente) throws Exception {
        try {
            String sql = "insert into CLIENTE" + " (NOMCLI,APECLI,DNICLI,TELCLI, EMACLI, ESTCLI,CODUBI,DOMCLI)"
                    + " values (?,?,?,?,?,?,?,?) ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cliente.getNOMCLI());
            ps.setString(2, cliente.getAPECLI());
            ps.setString(3, cliente.getDNICLI());
            ps.setString(4, cliente.getTELCLI());
            ps.setString(5, cliente.getEMACLI());
            ps.setString(6, "A");
            ps.setString(7, cliente.getCODUBI());
            ps.setString(8, cliente.getDOMCLI());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en ClienteImpl/registrar: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Cliente cliente) throws Exception {
        try {
            String sql = "update CLIENTE set NOMCLI=?,APECLI=?,DNICLI=?,TELCLI=?, EMACLI=?,ESTCLI=?,CODUBI=?,DOMCLI=? where IDCLI=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, cliente.getNOMCLI());
            ps.setString(2, cliente.getAPECLI());
            ps.setString(3, cliente.getDNICLI());
            ps.setString(4, cliente.getTELCLI());
            ps.setString(5, cliente.getEMACLI());
            ps.setString(6, "A");
            ps.setString(7, cliente.getCODUBI());
            ps.setString(8, cliente.getDOMCLI());
            ps.setInt(9, cliente.getIDCLI());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en ClienteImpl/modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Cliente cliente) throws Exception {
        try {
            String sql = "update CLIENTE set ESTCLI='I' where IDCLI=? ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, cliente.getIDCLI());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en ClienteImpl/eliminar: " + e.getMessage());
        }
    }
    
    @Override
    public void restaurar(Cliente cliente) throws Exception {
        try {
            String sql = "update CLIENTE set ESTCLI='A' where IDCLI=? ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, cliente.getIDCLI());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en ClienteImpl/restaurar: " + e.getMessage());
        }
    }

    @Override
    public List listarTodos(int tipo) throws Exception {
        List<Cliente> lista = null ;
        Cliente cliente;
        String sql = "";  
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vCLIENTE WHERE ESTCLI = 'A'";
                break;
            case 2:
                sql = "SELECT * FROM vCLIENTE WHERE ESTCLI = 'I'";
                break;
            case 3:
                sql = "SELECT * FROM vCLIENTE";
                break;
        }
        try {
            lista = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setIDCLI(rs.getInt("IDCLI"));
                cli.setNOMCLI(rs.getString("NOMCLI"));
                cli.setAPECLI(rs.getString("APECLI"));
                cli.setDNICLI(rs.getString("DNICLI"));
                cli.setTELCLI(rs.getString("TELCLI"));
                cli.setEMACLI(rs.getString("EMACLI"));
                cli.setDOMCLI(rs.getString("DOMCLI"));
                cli.setCODUBI(rs.getString("CODUBI"));
                cli.setDISUBI(rs.getString("DISUBI"));
                cli.setPROUBI(rs.getString("PROUBI"));
                cli.setDEPUBI(rs.getString("DEPUBI"));
                lista.add(cli);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error al listar todos" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }

}
