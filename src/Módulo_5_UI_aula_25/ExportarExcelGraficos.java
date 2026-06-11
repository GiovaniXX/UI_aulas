package Módulo_5_UI_aula_25;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.util.IOUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ExportarExcelGraficos extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public ExportarExcelGraficos() {
        setTitle("Exportação de Tarefas para Excel com Gráficos");
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

        JButton btnExportarExcel = new JButton("Exportar para Excel com Gráficos");
        panel.add(btnExportarExcel, "growx");

        btnExportarExcel.addActionListener(e -> exportarExcel());

        add(panel);

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    private void exportarExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tarefas");

        // Cabeçalho
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < modeloTabela.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(modeloTabela.getColumnName(i));
        }

        // Linhas
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < modeloTabela.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                Object valor = modeloTabela.getValueAt(i, j);
                cell.setCellValue(valor != null ? valor.toString() : "");
            }
        }

        // Estatísticas
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

        // Gráfico de Barras
        DefaultCategoryDataset datasetBarras = new DefaultCategoryDataset();
        datasetBarras.addValue(pendentes, "Tarefas", "Pendentes");
        datasetBarras.addValue(andamento, "Tarefas", "Em andamento");
        datasetBarras.addValue(concluidas, "Tarefas", "Concluídas");

        JFreeChart chartBarras = ChartFactory.createBarChart(
                "Distribuição de Tarefas (Barras)",
                "Status",
                "Quantidade",
                datasetBarras
        );

        // Gráfico de Pizza
        DefaultPieDataset datasetPizza = new DefaultPieDataset();
        datasetPizza.setValue("Pendentes", pendentes);
        datasetPizza.setValue("Em andamento", andamento);
        datasetPizza.setValue("Concluídas", concluidas);

        JFreeChart chartPizza = ChartFactory.createPieChart(
                "Distribuição de Tarefas (Pizza)",
                datasetPizza,
                true,
                true,
                false
        );

        try {
            // Converter gráficos em imagens temporárias
            BufferedImage imgBarras = chartBarras.createBufferedImage(500, 300);
            BufferedImage imgPizza = chartPizza.createBufferedImage(500, 300);

            // Inserir imagens no Excel
            ByteArrayOutputStream baosBarras = new ByteArrayOutputStream();
            ImageIO.write(imgBarras, "png", baosBarras);
            int pictureIdxBarras = workbook.addPicture(baosBarras.toByteArray(), Workbook.PICTURE_TYPE_PNG);

            ByteArrayOutputStream baosPizza = new ByteArrayOutputStream();
            ImageIO.write(imgPizza, "png", baosPizza);
            int pictureIdxPizza = workbook.addPicture(baosPizza.toByteArray(), Workbook.PICTURE_TYPE_PNG);

            Drawing<?> drawing = sheet.createDrawingPatriarch();

            ClientAnchor anchorBarras = workbook.getCreationHelper().createClientAnchor();
            anchorBarras.setCol1(0);
            anchorBarras.setRow1(modeloTabela.getRowCount() + 3);
            drawing.createPicture(anchorBarras, pictureIdxBarras);

            ClientAnchor anchorPizza = workbook.getCreationHelper().createClientAnchor();
            anchorPizza.setCol1(8);
            anchorPizza.setRow1(modeloTabela.getRowCount() + 3);
            drawing.createPicture(anchorPizza, pictureIdxPizza);

            try (FileOutputStream fileOut = new FileOutputStream("relatorio_tarefas_com_graficos.xlsx")) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(this, "Relatório Excel com gráficos gerado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar Excel: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
            new ExportarExcelGraficos().setVisible(true);
        });
    }
}
