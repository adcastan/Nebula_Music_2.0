/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.nubulamusicwebaplication.model.Usuario;
import java.util.List;

/**
 *
 * @author martinbl
 */
public interface IUsuarioDAO {

    void guardar(Usuario usuario);

    Usuario buscarPorId(Long id);

    Usuario buscarPorCorreo(String correo);

    Usuario buscarPorCorreoYContrasenia(String correo, String contrasenia);

    Usuario buscarPorPseudonimo(String pseudonimo);

    List<Usuario> listarTodos();

    void actualizar(Usuario usuario);

    void eliminar(Long id);
    List<Usuario> listarTop(int limite);
    List<Usuario> listarPaginado(int pagina, int tamanioPagina);
    long contarUsuarios();
}
