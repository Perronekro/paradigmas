package accesoDatos;

import java.util.List;

/**
 * ICrud - Capa: Acceso a Datos
 *
 * Define las operaciones CRUD del sistema usando Object genérico.
 * En el diagrama UML es implementada por UsuarioDAO:
 * UsuarioDAO ..|> ICrud : usa
 *
 * Se usa List sin tipo genérico (raw type) por diseño del diagrama UML,
 * la advertencia se suprime intencionalmente con @SuppressWarnings.
 */
@SuppressWarnings("rawtypes")
public interface ICrud {

    boolean insertar(Object obj);


    Object leer(Object obj);

    Object actualizar(int index, Object obj);


    boolean eliminar(Object obj);

    List leerTodos();
}
