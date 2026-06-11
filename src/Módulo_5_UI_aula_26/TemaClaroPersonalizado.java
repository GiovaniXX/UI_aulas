package Módulo_5_UI_aula_26;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;

public class TemaClaroPersonalizado {

    public static void main(String[] args) {
        try {
            // Carregar tema customizado a partir de um arquivo JSON
            IntelliJTheme.setup(new FileInputStream("tema_personalizado.json"));
        } catch (IOException e) {
            // Se não encontrar o tema, usa o padrão
            FlatLightLaf.setup();
            System.err.println("Falha ao carregar tema personalizado: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard com Tema Personalizado");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            JLabel label = new JLabel("📊 Bem-vindo ao Dashboard!");
            label.setFont(label.getFont().deriveFont(18f));

            JButton botao = new JButton("Exportar Relatório");

            JPanel panel = new JPanel();
            panel.add(label);
            panel.add(botao);

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}
