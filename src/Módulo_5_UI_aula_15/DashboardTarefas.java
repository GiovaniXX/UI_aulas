package Módulo_5_UI_aula_15;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class DashboardTarefas extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;
    private final DefaultCategoryDataset dataset;
    private final ChartPanel chartPanel;

    public DashboardTarefas() {
        setTitle("Dashboard de Tarefas");
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

        // Painel de botões com ícones SVG
        JPanel painelBotoes = new JPanel(new MigLayout("wrap 4", "[grow,fill][grow,fill][grow,fill][grow,fill]"));
        JButton btnAdicionar = new JButton("Adicionar", new FlatSVGIcon("icons/add.svg"));
        JButton btnEditar = new JButton("Editar", new FlatSVGIcon("icons/edit.svg"));
        JButton btnRemover = new JButton("Remover", new FlatSVGIcon("icons/delete.svg"));
        JButton btnConcluir = new JButton("Concluir", new FlatSVGIcon("icons/check.svg"));

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnConcluir);

        panel.add(painelBotoes, "growx");

        // Dataset e gráfico inicial
        dataset = new DefaultCategoryDataset();
        atualizarDataset();

        var chart = ChartFactory.createBarChart(
                "Distribuição de Tarefas",
                "Status",
                "Quantidade",
                dataset
        );

        chartPanel = new ChartPanel(chart);
        panel.add(chartPanel, "grow");

        add(panel);

        // Listener para atualizar gráfico automaticamente
        modeloTabela.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                atualizarDataset();
            }
        });

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    private void atualizarDataset() {
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

        dataset.clear();
        dataset.addValue(pendentes, "Tarefas", "Pendentes");
        dataset.addValue(andamento, "Tarefas", "Em andamento");
        dataset.addValue(concluidas, "Tarefas", "Concluídas");
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
