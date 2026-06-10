import com.formdev.flatlaf.FlatDarkLaf;
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

        JPanel panel = new JPanel(new BorderLayout());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setIcon(new FlatSVGIcon("icons/save.svg", 16, 16));

        // Customização de UI
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

            // Correção: agora passando o atalho como parâmetro
            FlatInspector.install("ctrl shift alt X");
            FlatUIDefaultsInspector.install("ctrl shift alt Y");

        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            new TelaCustomizada().setVisible(true);
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
}
