package Módulo_5_UI_aula_35;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.IntelliJTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class DashboardComToastSons extends JFrame {

    private final JTable tabela;
    private DefaultTableModel modeloTabela;

    public DashboardComToastSons() {
        setTitle("Dashboard com Toasts e Sons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[]"));

        JLabel titulo = new JLabel("🌙 Dashboard de Tarefas - Tema Escuro");
        titulo.setFont(titulo.getFont().deriveFont(20f));
        panel.add(titulo, "align center");

        String[] colunas = {"ID", "Título", "Descrição", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        // Botões
        JButton btnAdicionar = new JButton("Adicionar Tarefa");
        JButton btnExportarPDF = new JButton("Exportar PDF");
        JButton btnExportarExcel = new JButton("Exportar Excel");
        JButton btnErro = new JButton("Simular Erro");

        JPanel painelBotoes = new JPanel(new MigLayout("wrap 4", "[grow,fill][grow,fill][grow,fill][grow,fill]"));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnExportarPDF);
        painelBotoes.add(btnExportarExcel);
        painelBotoes.add(btnErro);

        panel.add(painelBotoes, "growx");

        add(panel);

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});

        // Ações com toast + sons
        btnAdicionar.addActionListener(e -> {
            modeloTabela.addRow(new Object[]{modeloTabela.getRowCount() + 1, "Nova Tarefa", "Descrição...", "Pendente"});
            mostrarToast("✅ Nova tarefa adicionada!", TipoToast.SUCESSO);
        });

        btnExportarPDF.addActionListener(e -> mostrarToast("📄 Relatório PDF exportado com sucesso!", TipoToast.SUCESSO));
        btnExportarExcel.addActionListener(e -> mostrarToast("📊 Relatório Excel exportado com sucesso!", TipoToast.AVISO));
        btnErro.addActionListener(e -> mostrarToast("❌ Erro ao processar tarefa!", TipoToast.ERRO));
    }

    // Enum para tipos de toast
    enum TipoToast {
        SUCESSO, ERRO, AVISO
    }

    // Método para exibir toast com som
    private void mostrarToast(String mensagem, TipoToast tipo) {
        JWindow toast = new JWindow();
        toast.setLayout(new BorderLayout());

        JLabel label = new JLabel(mensagem, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Definir cor conforme tipo
        switch (tipo) {
            case SUCESSO -> {
                label.setBackground(new Color(46, 125, 50, 220)); // Verde
                Toolkit.getDefaultToolkit().beep(); // Som leve
            }
            case ERRO -> {
                label.setBackground(new Color(198, 40, 40, 220)); // Vermelho
                Toolkit.getDefaultToolkit().beep(); // Som mais forte (pode ser o mesmo beep)
            }
            case AVISO -> {
                label.setBackground(new Color(255, 143, 0, 220)); // Laranja
                Toolkit.getDefaultToolkit().beep(); // Som leve
            }
        }

        toast.add(label, BorderLayout.CENTER);
        toast.pack();

        // Posição no canto inferior direito
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - toast.getWidth() - 20;
        int y = screenSize.height - toast.getHeight() - 50;
        toast.setLocation(x, y);

        toast.setVisible(true);

        // Fechar automaticamente após 2 segundos
        new Timer(2000, e -> toast.dispose()).start();
    }

    public static void main(String[] args) {
        try {
            IntelliJTheme.setup(new FileInputStream("tema_escuro.json"));
        } catch (IOException e) {
            FlatDarkLaf.setup();
            System.err.println("Falha ao carregar tema escuro personalizado: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new DashboardComToastSons().setVisible(true);
        });
    }
}
