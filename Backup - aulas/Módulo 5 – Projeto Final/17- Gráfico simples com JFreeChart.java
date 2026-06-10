import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class GraficoTeste extends JFrame {

    public GraficoTeste() {
        setTitle("Teste JFreeChart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Criar dataset de exemplo
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(5, "Tarefas", "Pendentes");
        dataset.addValue(3, "Tarefas", "Em andamento");
        dataset.addValue(7, "Tarefas", "Concluídas");

        // Criar gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
                "Distribuição de Tarefas",
                "Status",
                "Quantidade",
                dataset
        );

        // Adicionar gráfico ao painel
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraficoTeste().setVisible(true);
        });
    }
}
