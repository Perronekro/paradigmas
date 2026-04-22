package modeloDAO;

import accesoDatos.ICrud;
import accesoDatos.Persistencia;
import modeloDTO.Usuario;

import java.util.List;

/**
 * UsuarioDAO - Capa: Modelo DAO
 *
 * Implementa la lógica de acceso a datos para la entidad Usuario.
 * En el diagrama UML:
 *   - Depende de Persistencia (conexión al archivo): UsuarioDAO ..> Persistencia : es una
 *   - Implementa ICrud (operaciones CRUD):           UsuarioDAO ..|> ICrud : usa
 *   - Crea instancias de Usuario:                    UsuarioDAO ..> Usuario : crea
 *
 * Se suprime rawtypes porque ICrud usa List sin tipo genérico por diseño del diagrama UML.
 *
 * Atributo del diagrama: listaUsuarios.
 */
@SuppressWarnings("rawtypes")
public class UsuarioDAO extends Persistencia<Usuario> implements ICrud {

    private List<Usuario> listaUsuarios;

    public UsuarioDAO() {
        super("usuarios.dat");
        this.listaUsuarios = getLista();
    }
    @Override
    public boolean insertar(Object obj) {
        try {
            listaUsuarios.add((Usuario) obj);
            guardar();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object leer(Object obj) {
        Usuario u = (Usuario) obj;
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == u.getId()) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Object actualizar(int index, Object obj) {
        if (index >= 0 && index < listaUsuarios.size()) {
            listaUsuarios.set(index, (Usuario) obj);
            guardar();
            return listaUsuarios.get(index);
        }
        return null;
    }
    @Override
    public boolean eliminar(Object obj) {
        Usuario u = (Usuario) obj;
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getId() == u.getId()) {
                listaUsuarios.remove(i);
                guardar();
                return true;
            }
        }
        return false;
    }
    @Override
    public List leerTodos() {
        return listaUsuarios;
    }

    public int buscarIndex(Usuario u) {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getId() == u.getId()) {
                return i;
            }
        }
        return -1;
    }
}
