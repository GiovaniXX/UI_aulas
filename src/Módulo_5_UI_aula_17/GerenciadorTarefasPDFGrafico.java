package Módulo_5_UI_aula_17;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class GerenciadorTarefasPDFGrafico extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public GerenciadorTarefasPDFGrafico() {
        setTitle("Gerenciador de Tarefas - PDF com Gráfico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[]"));

        panel.add(new JLabel("📋 Minhas Tarefas"), "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        JButton btnExportarPDF = new JButton("Exportar Relatório PDF com Gráfico");
        panel.add(btnExportarPDF, "growx");

        btnExportarPDF.addActionListener(e -> exportarPDFComGrafico());

        add(panel);

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    private void exportarPDFComGrafico() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorio_tarefas_com_grafico.pdf"));
            document.open();

            document.add(new Paragraph("📊 Relatório de Tarefas com Gráfico\n\n"));

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

            document.add(new Paragraph("Pendentes: " + pendentes));
            document.add(new Paragraph("Em andamento: " + andamento));
            document.add(new Paragraph("Concluídas: " + concluidas));
            document.add(new Paragraph("\nDetalhes das tarefas:\n"));

            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                document.add(new Paragraph(
                        "ID: " + modeloTabela.getValueAt(i, 0)
                        + " | Título: " + modeloTabela.getValueAt(i, 1)
                        + " | Descrição: " + modeloTabela.getValueAt(i, 2)
                        + " | Status: " + modeloTabela.getValueAt(i, 3)
                ));
            }

            // Criar dataset e gráfico
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(pendentes, "Tarefas", "Pendentes");
            dataset.addValue(andamento, "Tarefas", "Em andamento");
            dataset.addValue(concluidas, "Tarefas", "Concluídas");

            JFreeChart chart = ChartFactory.createBarChart(
                    "Distribuição de Tarefas",
                    "Status",
                    "Quantidade",
                    dataset
            );

            // Converter gráfico em imagem e adicionar ao PDF
            BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
            Image chartImage = Image.getInstance(bufferedImage, null);
            document.add(chartImage);

            JOptionPane.showMessageDialog(this, "Relatório PDF com gráfico gerado com sucesso!");
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            document.close();
        }
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new GerenciadorTarefasPDFGrafico().setVisible(true);
        });
    }
}
