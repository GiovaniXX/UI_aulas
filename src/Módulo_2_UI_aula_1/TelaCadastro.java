package Módulo_2_UI_aula_1;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class TelaCadastro extends JFrame {

    public TelaCadastro() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // centraliza na tela

        // Painel principal com MigLayout
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]10[]10[]10[]10[]"));

        // Campos do formulário
        panel.add(new JLabel("Nome:"));
        panel.add(new JTextField(20));

        panel.add(new JLabel("E-mail:"));
        panel.add(new JTextField(20));

        panel.add(new JLabel("Telefone:"));
        panel.add(new JTextField(15));

        panel.add(new JLabel("Senha:"));
        panel.add(new JPasswordField(15));

        panel.add(new JLabel("Confirmar Senha:"));
        panel.add(new JPasswordField(15));

        // Botões de ação
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(btnSalvar, "span, split 2, align center");
        panel.add(btnCancelar);

        add(panel);
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new TelaCadastro().setVisible(true);
        });
    }
}
