package modeloDTO;

import java.io.Serializable;

/**
 * Usuario - Capa: Modelo DTO
 *
 * Objeto de transferencia de datos que representa un Usuario.
 * Implementa Serializable para poder ser almacenado en archivos
 * de objetos mediante Persistencia.
 *
 * En el diagrama UML es creado por UsuarioDAO y por UsuarioControlador:
 * UsuarioDAO ..> Usuario : crea
 * UsuarioControlador ..> Usuario : crea
 *
 * Atributos del diagrama: id, nombre, email.
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String email;

    public Usuario() {}

    public Usuario(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Email: " + email;
    }
}
