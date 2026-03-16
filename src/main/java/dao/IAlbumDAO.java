/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import com.mycompany.nubulamusicwebaplication.model.Album;
import java.util.List;

/**
 *
 * @author martinbl
 */
public interface IAlbumDAO {
    void guardar(Album album);

    void actualizar(Album album);

    void eliminar(Long id);

    Album buscarPorId(Long id);

    List<Album> buscarPorUsuario(Long usuarioId);
}
