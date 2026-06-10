package Módulo_3_UI_aula_1;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;

import javax.swing.*;
import java.awt.*;

public class TelaCustomizada extends JFrame {

    public TelaCustomizada() {
        setTitle("Exemplo FlatLaf Customizado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        //--> Painel principal
        JPanel panel = new JPanel(new BorderLayout());

        //--> Botão com ícone SVG
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setIcon(new FlatSVGIcon("icons/save.svg", 16, 16));

        //--> Customização de cores e fontes
        //--> bordas arredondadas
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.focusWidth", 2);
        UIManager.put("Button.background", new Color(60, 120, 200));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(btnSalvar, BorderLayout.CENTER);

        add(panel);
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();

            //--> permite inspecionar componentes em tempo real
            FlatInspector.install("ctrl shift alt X");

            //--> inspecionar valores de UI
            FlatUIDefaultsInspector.install("ctrl shift alt Y");
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            //--> animação suave ao aplicar tema
            FlatAnimatedLafChange.showSnapshot();
            new TelaCustomizada().setVisible(true);
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
}
