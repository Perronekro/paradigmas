package run;

import controlador.UsuarioControlador;
import vista.UsuarioVista;

/**
 * SistemaUsuariosMVC - Punto de entrada del programa.
 *
 * Instancia la Vista y el Controlador para arrancar el sistema.
 * El Controlador recibe la UI, conecta el DAO y registra los listeners,
 * siguiendo el flujo del diagrama UML:
 * UsuarioVista → UsuarioControlador → UsuarioDAO → Persistencia (archivo)
 */
public class SistemaUsuariosMVC {
    public static void main(String[] args) {
        UsuarioVista vista = new UsuarioVista();
        new UsuarioControlador(vista);
    }
}
