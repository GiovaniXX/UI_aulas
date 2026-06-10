import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class GerenciadorTarefasGson extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private final String arquivoJSON = "tarefas.json";

    public GerenciadorTarefasGson() {
        setTitle("Gerenciador de Tarefas - Gson");
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

        JPanel painelBotoes = new JPanel(new MigLayout("wrap 2", "[grow,fill][grow,fill]"));
        JButton btnExportar = new JButton("Exportar JSON");
        JButton btnImportar = new JButton("Importar JSON");

        painelBotoes.add(btnExportar);
        painelBotoes.add(btnImportar);

        panel.add(painelBotoes, "growx");
        add(panel);

        btnExportar.addActionListener(e -> exportarJSON());
        btnImportar.addActionListener(e -> importarJSON());

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    private void exportarJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray tarefas = new JsonArray();

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            JsonObject tarefa = new JsonObject();
            tarefa.addProperty("id", (Integer) modeloTabela.getValueAt(i, 0));
            tarefa.addProperty("titulo", (String) modeloTabela.getValueAt(i, 1));
            tarefa.addProperty("descricao", (String) modeloTabela.getValueAt(i, 2));
            tarefa.addProperty("status", (String) modeloTabela.getValueAt(i, 3));
            tarefas.add(tarefa);
        }

        try (FileWriter writer = new FileWriter(arquivoJSON)) {
            gson.toJson(tarefas, writer);
            JOptionPane.showMessageDialog(this, "Tarefas exportadas para " + arquivoJSON + " com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar JSON: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importarJSON() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(arquivoJSON)) {
            JsonArray tarefas = gson.fromJson(reader, JsonArray.class);
            modeloTabela.setRowCount(0); // limpa tabela

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
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new GerenciadorTarefasGson().setVisible(true);
        });
    }
}
