package Módulo_5_UI_aula_13;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class GerenciadorTarefas extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;
    private final String arquivoJSON = "tarefas.json";

    public GerenciadorTarefas() {
        setTitle("Importação de Tarefas - Gson");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[]"));

        panel.add(new JLabel("📋 Minhas Tarefas"), "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        JButton btnImportar = new JButton("Importar JSON");
        panel.add(btnImportar, "growx");

        btnImportar.addActionListener(e -> importarJSON());

        add(panel);
    }

    private void importarJSON() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(arquivoJSON)) {
            JsonArray tarefas = gson.fromJson(reader, JsonArray.class);
            modeloTabela.setRowCount(0); // limpa tabela antes de carregar

            for (int i = 0; i < tarefas.size(); i++) {
                JsonObject tarefa = tarefas.get(i).getAsJsonObject();
                modeloTabela.addRow(new Object[]{
                    tarefa.get("id").getAsInt(),
                    tarefa.get("titulo").getAsString(),
                    tarefa.get("descricao").getAsString(),
                    tarefa.get("status").getAsString()
                });
            }

            JOptionPane.showMessageDialog(this, "Tarefas importadas de " + arquivoJSON + " com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao importar JSON: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new GerenciadorTarefas().setVisible(true);
        });
    }
}
