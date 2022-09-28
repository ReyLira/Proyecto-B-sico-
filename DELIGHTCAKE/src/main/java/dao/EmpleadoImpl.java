package dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Empleado;

public class EmpleadoImpl extends Conexion implements ICRUD<Empleado> {

    @Override
    public void guardar(Empleado empleado) throws Exception {
        try {
            String sql = "insert into EMPLEADO" + " (NOMEMP,APEEMP,DNIEMP,TELEMP, ESTEMP,EMAEMP, ROLEMP,CODUBI)"
                    + " values (?,?,?,?,?,?,?,?) ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, empleado.getNOMEMP());
            ps.setString(2, empleado.getAPEEMP());
            ps.setString(3, empleado.getDNIEMP());
            ps.setString(4, empleado.getTELEMP());
            ps.setString(5, "A");
            ps.setString(6, empleado.getEMAEMP());
            ps.setString(7, empleado.getROLEMP());
            ps.setString(8, empleado.getCODUBI());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en EmpleadoImpl/registrar: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Empleado empleado) throws Exception {
        try {
            String sql = "update EMPLEADO set NOMEMP=?,APEEMP=?,DNIEMP=?,TELEMP=?, ESTEMP=?,EMAEMP=?,ROLEMP=?,CODUBI=? where IDEMP=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, empleado.getNOMEMP());
            ps.setString(2, empleado.getAPEEMP());
            ps.setString(3, empleado.getDNIEMP());
            ps.setString(4, empleado.getTELEMP());
            ps.setString(5, "A");
            ps.setString(6, empleado.getEMAEMP());
            ps.setString(7, empleado.getROLEMP());
            ps.setString(8, empleado.getCODUBI());
            ps.setInt(9, empleado.getIDEMP());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error en EmpleadoImpl/modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Empleado empleado) throws Exception {
        try {
            String sql = "update EMPLEADO set ESTEMP='I' where IDEMP=? ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, empleado.getIDEMP());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en EmpleadoImpl/Cambiar Estado: " + e.getMessage());
        }
    }
    
    @Override
    public void restaurar(Empleado empleado) throws Exception {
        try {
            String sql = "update EMPLEADO set ESTEMP='A' where IDEMP=? ";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, empleado.getIDEMP());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en EmpleadoImpl/Cambiar Estado: " + e.getMessage());
        }
    }

    @Override
    public List listarTodos(int tipo) throws Exception {
        List<Empleado> lista = null ;
        Empleado empleado;
        String sql = "";  
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vEMPLEADO WHERE ESTEMP = 'A'";
                break;
            case 2:
                sql = "SELECT * FROM vEMPLEADO WHERE ESTEMP = 'I'";
                break;
            case 3:
                sql = "SELECT * FROM vEMPLEADO";
                break;
        }
        try {
            lista = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIDEMP(rs.getInt("IDEMP"));
                emp.setNOMEMP(rs.getString("NOMEMP"));
                emp.setAPEEMP(rs.getString("APEEMP"));
                emp.setDNIEMP(rs.getString("DNIEMP"));
                emp.setTELEMP(rs.getString("TELEMP"));
                emp.setEMAEMP(rs.getString("EMAEMP"));
                emp.setROLEMP(rs.getString("ROLEMP"));
                emp.setCODUBI(rs.getString("CODUBI"));
                emp.setDISUBI(rs.getString("DISUBI"));
                emp.setPROUBI(rs.getString("PROUBI"));
                emp.setDEPUBI(rs.getString("DEPUBI"));
                lista.add(emp);
            }
        } catch (Exception e) {
            System.out.println("Error al listar todos" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }

}
