package Módulo_5_UI_aula_30;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.IntelliJTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;

public class DashboardComHotkeys extends JFrame {

    private final JTable tabela;
    private DefaultTableModel modeloTabela;

    public DashboardComHotkeys() {
        setTitle("Dashboard com Ícones e Hotkeys");
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

        // Atalhos de teclado (Hotkeys)
        btnAdicionar.setMnemonic(KeyEvent.VK_N); // Alt+N
        btnExportarPDF.setMnemonic(KeyEvent.VK_P); // Alt+P
        btnExportarExcel.setMnemonic(KeyEvent.VK_E); // Alt+E

        // Também podemos usar Key Bindings para Ctrl+N, Ctrl+P, Ctrl+E
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK), "novaTarefa");
        actionMap.put("novaTarefa", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeloTabela.addRow(new Object[]{modeloTabela.getRowCount() + 1, "Nova Tarefa", "Descrição...", "Pendente"});
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK), "exportarPDF");
        actionMap.put("exportarPDF", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DashboardComHotkeys.this, "Exportando para PDF...");
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK), "exportarExcel");
        actionMap.put("exportarExcel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DashboardComHotkeys.this, "Exportando para Excel...");
            }
        });

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
            new DashboardComHotkeys().setVisible(true);
        });
    }
}
