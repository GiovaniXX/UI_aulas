package Módulo_5_UI_aula_28;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.IntelliJTheme;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;

public class DashboardTemaEscuro extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;
    private final ChartPanel chartPanelBarras;
    private final ChartPanel chartPanelPizza;

    public DashboardTemaEscuro() {
        setTitle("Dashboard de Tarefas - Tema Escuro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[grow]"));

        JLabel titulo = new JLabel("🌙 Dashboard de Tarefas - Tema Escuro");
        titulo.setFont(titulo.getFont().deriveFont(20f));
        panel.add(titulo, "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        // Criar gráficos iniciais
        chartPanelBarras = new ChartPanel(criarGraficoBarras());
        chartPanelPizza = new ChartPanel(criarGraficoPizza());

        JPanel painelGraficos = new JPanel(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[grow]"));
        painelGraficos.add(chartPanelBarras, "grow");
        painelGraficos.add(chartPanelPizza, "grow");

        panel.add(painelGraficos, "grow");

        add(panel);

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
                case "Pendente" ->
                    pendentes++;
                case "Em andamento" ->
                    andamento++;
                case "Concluída" ->
                    concluidas++;
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
                case "Pendente" ->
                    pendentes++;
                case "Em andamento" ->
                    andamento++;
                case "Concluída" ->
                    concluidas++;
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

    public static void main(String[] args) {
        try {
            // Carregar tema escuro personalizado
            IntelliJTheme.setup(new FileInputStream("tema_escuro.json"));
        } catch (IOException e) {
            // Se não encontrar, usa FlatDark padrão
            FlatDarkLaf.setup();
            System.err.println("Falha ao carregar tema escuro personalizado: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new DashboardTemaEscuro().setVisible(true);
        });
    }
}
