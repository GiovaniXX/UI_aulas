import com.formdev.flatlaf.FlatDarkLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        setTitle("Login - Exemplo FlatLaf + MigLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // centraliza na tela

        // Painel principal com MigLayout
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]10[]"));
        
        // Componentes
        panel.add(new JLabel("Usuário:"));
        panel.add(new JTextField(15));

        panel.add(new JLabel("Senha:"));
        panel.add(new JPasswordField(15));

        JButton btnLogin = new JButton("Entrar");
        panel.add(btnLogin, "span, align center");

        add(panel);
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup(); // aplica tema escuro
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
