package Módulo_5_UI_aula_31;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.IntelliJTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;

public class DashboardComToolbar extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public DashboardComToolbar() {
        setTitle("Dashboard com Toolbar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]"));

        JLabel titulo = new JLabel("🌙 Dashboard de Tarefas - Tema Escuro");
        titulo.setFont(titulo.getFont().deriveFont(20f));
        panel.add(titulo, "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        // Criar toolbar
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false); // fixa no topo

        JButton btnAdicionar = new JButton(new ImageIcon("icons/add_large.png"));
        btnAdicionar.setToolTipText("Adicionar Tarefa (Ctrl+N)");

        JButton btnExportarPDF = new JButton(new ImageIcon("icons/pdf_large.png"));
        btnExportarPDF.setToolTipText("Exportar PDF (Ctrl+P)");

        JButton btnExportarExcel = new JButton(new ImageIcon("icons/excel_large.png"));
        btnExportarExcel.setToolTipText("Exportar Excel (Ctrl+E)");

        toolbar.add(btnAdicionar);
        toolbar.add(btnExportarPDF);
        toolbar.add(btnExportarExcel);

        panel.add(toolbar, "dock north");

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
            new DashboardComToolbar().setVisible(true);
        });
    }
}
