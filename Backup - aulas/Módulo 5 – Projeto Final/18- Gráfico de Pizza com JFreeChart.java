import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

public class GraficoPizzaTeste extends JFrame {

    public GraficoPizzaTeste() {
        setTitle("Teste JFreeChart - Gráfico de Pizza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Criar dataset de exemplo
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Pendentes", 5);
        dataset.setValue("Em andamento", 3);
        dataset.setValue("Concluídas", 7);

        // Criar gráfico de pizza
        JFreeChart chart = ChartFactory.createPieChart(
                "Distribuição de Tarefas",
                dataset,
                true,   // legenda
                true,   // tooltips
                false   // URLs
        );

        // Adicionar gráfico ao painel
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraficoPizzaTeste().setVisible(true);
        });
    }
}
