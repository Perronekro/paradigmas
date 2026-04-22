package vista;

import modeloDTO.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

/**
 * UsuarioVista - Capa: Vista
 *
 * Interfaz gráfica del sistema de gestión de usuarios (Java Swing).
 * Estilo formal / corporativo en azul y gris.
 * Los usuarios se muestran como tarjetas individuales en un panel desplazable.
 *
 * En el diagrama UML es parte del UsuarioControlador:
 * UsuarioControlador ..> UsuarioVista : es parte
 *
 * Expone los campos y botones para que el Controlador registre los eventos.
 */
public class UsuarioVista extends JFrame {

    // --- Paleta corporativa ---
    private static final Color AZUL_PRIMARIO   = new Color(26, 82, 145);
    private static final Color AZUL_SECUNDARIO = new Color(41, 128, 185);
    private static final Color GRIS_FONDO      = new Color(236, 240, 241);
    private static final Color GRIS_PANEL      = new Color(189, 195, 199);
    private static final Color BLANCO          = Color.WHITE;
    private static final Color TEXTO_CLARO     = new Color(44, 62, 80);
    private static final Font  FUENTE_TITULO   = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font  FUENTE_NORMAL   = new Font("Segoe UI", Font.PLAIN, 12);

    public JTextField txtId;
    public JTextField txtNombre;
    public JTextField txtEmail;

    public JButton btnInsertar;
    public JButton btnBuscar;
    public JButton btnModificar;
    public JButton btnEliminar;
    public JButton btnMostrar;

    public JPanel panelTarjetas;


    public JLabel lblEstado;

    public UsuarioVista() {
        initComponents();
    }

    public void initComponents() {
        setTitle("Sistema de Gestión de Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setBackground(GRIS_FONDO);

        setLayout(new BorderLayout(0, 0));

        // ── Header ──
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AZUL_PRIMARIO);
        header.setBorder(new EmptyBorder(14, 20, 14, 20));
        JLabel titulo = new JLabel("  Sistema de Gestión de Usuarios");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(BLANCO);
        titulo.setIcon(crearIconoUsuario());
        header.add(titulo, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ── Panel central: formulario + botones ──
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setBackground(GRIS_FONDO);
        centro.setBorder(new EmptyBorder(15, 15, 5, 15));

        // Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(BLANCO);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(GRIS_PANEL, 1, true),
            new EmptyBorder(12, 16, 12, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelForm.add(labelForm("ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtId = campoTexto();
        panelForm.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelForm.add(labelForm("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtNombre = campoTexto();
        panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panelForm.add(labelForm("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtEmail = campoTexto();
        panelForm.add(txtEmail, gbc);

        centro.add(panelForm, BorderLayout.NORTH);

        // Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelBotones.setBackground(GRIS_FONDO);

        btnInsertar  = boton("Insertar",       new Color(39, 174, 96));
        btnBuscar    = boton("Buscar",          AZUL_SECUNDARIO);
        btnModificar = boton("Modificar",       new Color(243, 156, 18));
        btnEliminar  = boton("Eliminar",        new Color(192, 57, 43));
        btnMostrar   = boton("Mostrar Todos",   AZUL_PRIMARIO);

        panelBotones.add(btnInsertar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);

        centro.add(panelBotones, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        panelTarjetas = new JPanel();
        panelTarjetas.setLayout(new BoxLayout(panelTarjetas, BoxLayout.Y_AXIS));
        panelTarjetas.setBackground(GRIS_FONDO);
        panelTarjetas.setBorder(new EmptyBorder(8, 15, 8, 15));

        JScrollPane scroll = new JScrollPane(panelTarjetas);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(0, 15, 10, 15),
            new TitledBorder(new LineBorder(GRIS_PANEL, 1, true), "Usuarios Registrados",
                TitledBorder.LEFT, TitledBorder.TOP, FUENTE_TITULO, TEXTO_CLARO)
        ));
        scroll.setPreferredSize(new Dimension(0, 220));
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        add(scroll, BorderLayout.SOUTH);

        lblEstado = new JLabel(" Listo");
        lblEstado.setFont(FUENTE_NORMAL);
        lblEstado.setForeground(BLANCO);
        lblEstado.setBackground(AZUL_SECUNDARIO);
        lblEstado.setOpaque(true);
        lblEstado.setBorder(new EmptyBorder(4, 12, 4, 12));
        // Se agrega dentro del SOUTH junto al scroll mediante un panel envolvente
        JPanel sur = new JPanel(new BorderLayout());
        sur.add(scroll, BorderLayout.CENTER);
        sur.add(lblEstado, BorderLayout.SOUTH);
        remove(scroll);
        add(sur, BorderLayout.SOUTH);

        setVisible(true);
    }
    private JLabel labelForm(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FUENTE_TITULO);
        l.setForeground(TEXTO_CLARO);
        return l;
    }

    private JTextField campoTexto() {
        JTextField tf = new JTextField();
        tf.setFont(FUENTE_NORMAL);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(GRIS_PANEL, 1, true),
            new EmptyBorder(4, 6, 4, 6)
        ));
        return tf;
    }

    private JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(color);
        btn.setForeground(BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(7, 14, 7, 14));
        return btn;
    }

    /**
     * Muestra una lista de usuarios como tarjetas en el panel.
     * Llamado desde el Controlador tras cada operación de consulta.
     * @param usuarios lista de usuarios a renderizar.
     */
    public void mostrarTarjetas(List<Usuario> usuarios) {
        panelTarjetas.removeAll();
        if (usuarios.isEmpty()) {
            JLabel vacio = new JLabel("No hay usuarios registrados.");
            vacio.setFont(FUENTE_NORMAL);
            vacio.setForeground(Color.GRAY);
            vacio.setBorder(new EmptyBorder(10, 10, 10, 10));
            panelTarjetas.add(vacio);
        } else {
            for (Usuario u : usuarios) {
                panelTarjetas.add(crearTarjeta(u));
                panelTarjetas.add(Box.createVerticalStrut(6));
            }
        }
        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }

    private JPanel crearTarjeta(Usuario u) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 0));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AZUL_SECUNDARIO, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel franja = new JPanel();
        franja.setBackground(AZUL_PRIMARIO);
        franja.setPreferredSize(new Dimension(5, 0));
        tarjeta.add(franja, BorderLayout.WEST);

        // Contenido
        JPanel contenido = new JPanel(new GridLayout(2, 1, 0, 2));
        contenido.setBackground(BLANCO);

        JLabel lNombre = new JLabel("  " + u.getNombre());
        lNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lNombre.setForeground(TEXTO_CLARO);

        JLabel lDetalle = new JLabel("  ID: " + u.getId() + "   |   " + u.getEmail());
        lDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lDetalle.setForeground(Color.GRAY);

        contenido.add(lNombre);
        contenido.add(lDetalle);
        tarjeta.add(contenido, BorderLayout.CENTER);

        return tarjeta;
    }

    public void setEstado(String mensaje) {
        lblEstado.setText("  " + mensaje);
    }

    private Icon crearIconoUsuario() {
        return new Icon() {
            public int getIconWidth()  { return 22; }
            public int getIconHeight() { return 22; }
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BLANCO);
                g2.fillOval(x + 5, y + 1, 12, 12);
                g2.fillRoundRect(x + 1, y + 14, 20, 8, 6, 6);
            }
        };
    }
}
