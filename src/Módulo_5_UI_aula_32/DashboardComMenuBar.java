package Módulo_5_UI_aula_32;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.IntelliJTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;

public class DashboardComMenuBar extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public DashboardComMenuBar() {
        setTitle("Dashboard com Menu Bar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Criar barra de menus
        JMenuBar menuBar = new JMenuBar();

        // Menu Arquivo
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemNovo = new JMenuItem("Nova Tarefa");
        JMenuItem itemExportarPDF = new JMenuItem("Exportar PDF");
        JMenuItem itemExportarExcel = new JMenuItem("Exportar Excel");
        JMenuItem itemSair = new JMenuItem("Sair");

        menuArquivo.add(itemNovo);
        menuArquivo.addSeparator();
        menuArquivo.add(itemExportarPDF);
        menuArquivo.add(itemExportarExcel);
        menuArquivo.addSeparator();
        menuArquivo.add(itemSair);

        // Menu Editar
        JMenu menuEditar = new JMenu("Editar");
        JMenuItem itemEditar = new JMenuItem("Editar Tarefa");
        JMenuItem itemRemover = new JMenuItem("Remover Tarefa");
        menuEditar.add(itemEditar);
        menuEditar.add(itemRemover);

        // Menu Relatórios
        JMenu menuRelatorios = new JMenu("Relatórios");
        JMenuItem itemResumo = new JMenuItem("Resumo de Tarefas");
        JMenuItem itemGraficos = new JMenuItem("Gerar Gráficos");
        menuRelatorios.add(itemResumo);
        menuRelatorios.add(itemGraficos);

        // Adicionar menus à barra
        menuBar.add(menuArquivo);
        menuBar.add(menuEditar);
        menuBar.add(menuRelatorios);

        setJMenuBar(menuBar);

        // Painel principal
        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]"));

        JLabel titulo = new JLabel("🌙 Dashboard de Tarefas - Tema Escuro");
        titulo.setFont(titulo.getFont().deriveFont(20f));
        panel.add(titulo, "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        add(panel);

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});

        // Ações dos menus
        itemNovo.addActionListener(e -> modeloTabela.addRow(new Object[]{modeloTabela.getRowCount() + 1, "Nova Tarefa", "Descrição...", "Pendente"}));
        itemExportarPDF.addActionListener(e -> JOptionPane.showMessageDialog(this, "Exportando para PDF..."));
        itemExportarExcel.addActionListener(e -> JOptionPane.showMessageDialog(this, "Exportando para Excel..."));
        itemEditar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Editar tarefa selecionada..."));
        itemRemover.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row >= 0) {
                modeloTabela.removeRow(row);
            }
        });
        itemResumo.addActionListener(e -> JOptionPane.showMessageDialog(this, "Resumo de tarefas gerado!"));
        itemGraficos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Gerando gráficos..."));
        itemSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        try {
            IntelliJTheme.setup(new FileInputStream("tema_escuro.json"));
        } catch (IOException e) {
            FlatDarkLaf.setup();
            System.err.println("Falha ao carregar tema escuro personalizado: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new DashboardComMenuBar().setVisible(true);
        });
    }
}
