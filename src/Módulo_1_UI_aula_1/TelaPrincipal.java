package Módulo_1_UI_aula_1;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Exemplo MigLayout + FlatLaf");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 2", "[grow][grow]", "[]10[]"));
        panel.add(new JLabel("Usuário:"), "align right");
        panel.add(new JTextField(15), "growx");
        panel.add(new JLabel("Senha:"), "align right");
        panel.add(new JPasswordField(15), "growx");
        panel.add(new JButton("Login"), "span, align center");

        add(panel);
    }
}
