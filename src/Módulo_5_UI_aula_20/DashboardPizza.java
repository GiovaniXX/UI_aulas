package Módulo_5_UI_aula_20;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardPizza extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;
    private final ChartPanel chartPanel;

    public DashboardPizza() {
        setTitle("Dashboard de Tarefas - Gráfico de Pizza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
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

        JButton btnAtualizarGrafico = new JButton("Atualizar Gráfico de Pizza");
        panel.add(btnAtualizarGrafico, "growx");

        // Criar gráfico inicial
        chartPanel = new ChartPanel(criarGraficoPizza());
        panel.add(chartPanel, "grow");

        add(panel);

        // Listener para atualizar gráfico
        btnAtualizarGrafico.addActionListener(e -> atualizarGrafico());

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    private JFreeChart criarGraficoPizza() {
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

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Pendentes", pendentes);
        dataset.setValue("Em andamento", andamento);
        dataset.setValue("Concluídas", concluidas);

        return ChartFactory.createPieChart(
                "Distribuição de Tarefas",
                dataset,
                true,
                true,
                false
        );
    }

    private void atualizarGrafico() {
        chartPanel.setChart(criarGraficoPizza());
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new DashboardPizza().setVisible(true);
        });
    }
}
