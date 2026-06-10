package Módulo_5_UI_aula_14;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardTarefas extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public DashboardTarefas() {
        setTitle("Dashboard de Tarefas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[]"));

        JLabel titulo = new JLabel("📊 Dashboard de Tarefas");
        titulo.setFont(titulo.getFont().deriveFont(20f));
        panel.add(titulo, "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        // Painel de botões com ícones SVG
        JPanel painelBotoes = new JPanel(new MigLayout("wrap 5", "[grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]"));
        JButton btnAdicionar = new JButton("Adicionar", new FlatSVGIcon("icons/add.svg"));
        JButton btnEditar = new JButton("Editar", new FlatSVGIcon("icons/edit.svg"));
        JButton btnRemover = new JButton("Remover", new FlatSVGIcon("icons/delete.svg"));
        JButton btnConcluir = new JButton("Concluir", new FlatSVGIcon("icons/check.svg"));
        JButton btnRelatorio = new JButton("Relatório", new FlatSVGIcon("icons/chart.svg"));

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnRelatorio);

        panel.add(painelBotoes, "growx");

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});

        // Ação do botão Relatório -> gráfico
        btnRelatorio.addActionListener(e -> mostrarGrafico());

        add(panel);
    }

    private void mostrarGrafico() {
        int pendentes = 0, andamento = 0, concluidas = 0;

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            String status = (String) modeloTabela.getValueAt(i, 3);
            switch (status) {
                case "Pendente":
                    pendentes++;
                    break;
                case "Em andamento":
                    andamento++;
                    break;
                case "Concluída":
                    concluidas++;
                    break;
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(pendentes, "Tarefas", "Pendentes");
        dataset.addValue(andamento, "Tarefas", "Em andamento");
        dataset.addValue(concluidas, "Tarefas", "Concluídas");

        var chart = ChartFactory.createBarChart(
                "Distribuição de Tarefas",
                "Status",
                "Quantidade",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        JOptionPane.showMessageDialog(this, chartPanel, "Relatório Gráfico", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new DashboardTarefas().setVisible(true);
        });
    }
}
