package Módulo_5_UI_aula_24;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportarExcelTarefas extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public ExportarExcelTarefas() {
        setTitle("Exportação de Tarefas para Excel");
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

        JButton btnExportarExcel = new JButton("Exportar para Excel (XLSX)");
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

        // Criar cabeçalho
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < modeloTabela.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(modeloTabela.getColumnName(i));
        }

        // Preencher linhas
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < modeloTabela.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                Object valor = modeloTabela.getValueAt(i, j);
                cell.setCellValue(valor != null ? valor.toString() : "");
            }
        }

        // Ajustar largura das colunas
        for (int i = 0; i < modeloTabela.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream("relatorio_tarefas.xlsx")) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(this, "Relatório Excel gerado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar Excel: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new ExportarExcelTarefas().setVisible(true);
        });
    }
}
