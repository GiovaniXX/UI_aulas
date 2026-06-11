package Módulo_5_UI_aula_29;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.IntelliJTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;

public class DashboardComIcones extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public DashboardComIcones() {
        setTitle("Dashboard com Ícones Customizados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[]"));

        JLabel titulo = new JLabel("🌙 Dashboard de Tarefas - Tema Escuro");
        titulo.setFont(titulo.getFont().deriveFont(20f));
        panel.add(titulo, "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        // Botões com ícones
        JButton btnAdicionar = new JButton("Adicionar Tarefa", new ImageIcon("icons/add.png"));
        JButton btnExportarPDF = new JButton("Exportar PDF", new ImageIcon("icons/pdf.png"));
        JButton btnExportarExcel = new JButton("Exportar Excel", new ImageIcon("icons/excel.png"));

        JPanel painelBotoes = new JPanel(new MigLayout("wrap 3", "[grow,fill][grow,fill][grow,fill]"));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnExportarPDF);
        painelBotoes.add(btnExportarExcel);

        panel.add(painelBotoes, "growx");

        add(panel);

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    public static void main(String[] args) {
        try {
            IntelliJTheme.setup(new FileInputStream("tema_escuro.json"));
        } catch (IOException e) {
            FlatDarkLaf.setup();
            System.err.println("Falha ao carregar tema escuro personalizado: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new DashboardComIcones().setVisible(true);
        });
    }
}
