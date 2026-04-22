package controlador;

import modeloDAO.UsuarioDAO;
import modeloDTO.Usuario;
import vista.UsuarioVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * UsuarioControlador - Capa: Controlador
 *
 * Intermediario entre la Vista y el Modelo. Recibe los eventos de la UI,
 * construye el Usuario con los datos del formulario y delega las operaciones al DAO.
 *
 * En el diagrama UML:
 *   - Implementa ActionListener:           UsuarioControlador ..|> ActionListener
 *   - Usa la vista como parte de él:       UsuarioControlador ..> UsuarioVista : es parte
 *   - Usa el DAO como modelo:              UsuarioControlador ..> UsuarioDAO : tiene
 *   - Crea y usa el DTO:                   UsuarioControlador ..> Usuario : crea
 *
 * Atributos del diagrama: vista, dao, usuario.
 */
public class UsuarioControlador implements ActionListener {

    private UsuarioVista vista;
    private UsuarioDAO dao;
    private Usuario usuario;

    public UsuarioControlador(UsuarioVista vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();
        this.usuario = new Usuario();

        vista.btnInsertar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnMostrar.addActionListener(this);
    }

    private void cargarUsuarioDesdeCampos() {
        try {
            int id = Integer.parseInt(vista.txtId.getText().trim());
            String nombre = vista.txtNombre.getText().trim();
            String email = vista.txtEmail.getText().trim();
            this.usuario = new Usuario(id, nombre, email);
        } catch (NumberFormatException ex) {
            vista.setEstado("Error: el ID debe ser un número entero.");
            this.usuario = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cargarUsuarioDesdeCampos();
        if (this.usuario == null) return;

        Object fuente = e.getSource();
        if (fuente == vista.btnInsertar) {
            insertar();
        } else if (fuente == vista.btnBuscar) {
            buscar();
        } else if (fuente == vista.btnModificar) {
            modificar();
        } else if (fuente == vista.btnEliminar) {
            eliminar();
        } else if (fuente == vista.btnMostrar) {
            mostrar();
        }
    }

    public void insertar() {
        boolean resultado = dao.insertar(this.usuario);
        vista.setEstado(resultado ? "Usuario insertado correctamente." : "Error al insertar usuario.");
    }

    public void buscar() {
        Object resultado = dao.leer(this.usuario);
        if (resultado != null) {
            List<Usuario> encontrado = new java.util.ArrayList<>();
            encontrado.add((Usuario) resultado);
            vista.mostrarTarjetas(encontrado);
            vista.setEstado("Usuario encontrado.");
        } else {
            vista.mostrarTarjetas(new java.util.ArrayList<>());
            vista.setEstado("Usuario no encontrado.");
        }
    }

    public void modificar() {
        int index = dao.buscarIndex(this.usuario);
        if (index < 0) {
            vista.setEstado("Usuario no encontrado para modificar.");
            return;
        }
        Object resultado = dao.actualizar(index, this.usuario);
        vista.setEstado(resultado != null ? "Usuario modificado correctamente." : "Error al modificar usuario.");
    }

    public void eliminar() {
        boolean resultado = dao.eliminar(this.usuario);
        vista.setEstado(resultado ? "Usuario eliminado correctamente." : "Usuario no encontrado para eliminar.");
    }

    @SuppressWarnings("unchecked")
    public void mostrar() {
        List<Usuario> lista = (List<Usuario>) dao.leerTodos();
        vista.mostrarTarjetas(lista);
        vista.setEstado(lista.isEmpty() ? "No hay usuarios registrados." : lista.size() + " usuario(s) registrado(s).");
    }
}
