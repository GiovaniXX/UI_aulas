package Módulo_5_UI_aula_1;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GerenciadorTarefas extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public GerenciadorTarefas() {
        setTitle("Gerenciador de Tarefas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Painel principal com MigLayout
        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[]"));

        // Título
        panel.add(new JLabel("📋 Minhas Tarefas"), "align center");

        // Modelo da tabela
        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollTabela = new JScrollPane(tabela);

        panel.add(scrollTabela, "grow, push");

        // Botões de ação
        JPanel painelBotoes = new JPanel(new MigLayout("wrap 4", "[grow,fill][grow,fill][grow,fill][grow,fill]"));
        painelBotoes.add(new JButton("Adicionar"));
        painelBotoes.add(new JButton("Editar"));
        painelBotoes.add(new JButton("Remover"));
        painelBotoes.add(new JButton("Concluir"));

        panel.add(painelBotoes, "growx");

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
            new GerenciadorTarefas().setVisible(true);
        });
    }
}
