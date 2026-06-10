import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardBarrasPizza extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ChartPanel chartPanelBarras;
    private ChartPanel chartPanelPizza;

    public DashboardBarrasPizza() {
        setTitle("Dashboard de Tarefas - Barras e Pizza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[grow]"));

        JLabel titulo = new JLabel("📊 Dashboard de Tarefas");
        titulo.setFont(titulo.getFont().deriveFont(20f));
        panel.add(titulo, "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        JButton btnAtualizar = new JButton("Atualizar Gráficos");
        panel.add(btnAtualizar, "growx");

        // Criar gráficos iniciais
        chartPanelBarras = new ChartPanel(criarGraficoBarras());
        chartPanelPizza = new ChartPanel(criarGraficoPizza());

        JPanel painelGraficos = new JPanel(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[grow]"));
        painelGraficos.add(chartPanelBarras, "grow");
        painelGraficos.add(chartPanelPizza, "grow");

        panel.add(painelGraficos, "grow");

        add(panel);

        // Listener para atualizar gráficos
        btnAtualizar.addActionListener(e -> atualizarGraficos());

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    private JFreeChart criarGraficoBarras() {
        int pendentes = 0, andamento = 0, concluidas = 0;

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            String status = (String) modeloTabela.getValueAt(i, 3);
            switch (status) {
                case "Pendente": pendentes++; break;
                case "Em andamento": andamento++; break;
                case "Concluída": concluidas++; break;
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(pendentes, "Tarefas", "Pendentes");
        dataset.addValue(andamento, "Tarefas", "Em andamento");
        dataset.addValue(concluidas, "Tarefas", "Concluídas");

        return ChartFactory.createBarChart(
                "Distribuição de Tarefas (Barras)",
                "Status",
                "Quantidade",
                dataset
        );
    }

    private JFreeChart criarGraficoPizza() {
        int pendentes = 0, andamento = 0, concluidas = 0;

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            String status = (String) modeloTabela.getValueAt(i, 3);
            switch (status) {
                case "Pendente": pendentes++; break;
                case "Em andamento": andamento++; break;
                case "Concluída": concluidas++; break;
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Pendentes", pendentes);
        dataset.setValue("Em andamento", andamento);
        dataset.setValue("Concluídas", concluidas);

        return ChartFactory.createPieChart(
                "Distribuição de Tarefas (Pizza)",
                dataset,
                true,
                true,
                false
        );
    }

    private void atualizarGraficos() {
        chartPanelBarras.setChart(criarGraficoBarras());
        chartPanelPizza.setChart(criarGraficoPizza());
    }

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new DashboardBarrasPizza().setVisible(true);
        });
    }
}
