package dao;

import java.util.List;

public interface ICRUD<Generic> {

    void registrar(Generic obj) throws Exception;

    void modificar(Generic obj) throws Exception;

    void eliminar(Generic obj) throws Exception;

    List<Generic> listarTodos() throws Exception;

}
