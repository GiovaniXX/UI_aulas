package Módulo_5_UI_aula_11;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GerenciadorTarefas extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public GerenciadorTarefas() {
        setTitle("Gerenciador de Tarefas");
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

        // Painel de botões
        JPanel painelBotoes = new JPanel(new MigLayout("wrap 4", "[grow,fill][grow,fill][grow,fill][grow,fill]"));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnConcluir = new JButton("Concluir");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnConcluir);

        panel.add(painelBotoes, "growx");
        add(panel);

        // Menu de relatórios
        JMenuBar menuBar = new JMenuBar();
        JMenu menuRelatorios = new JMenu("Relatórios");
        JMenuItem itemEstatisticas = new JMenuItem("Estatísticas de Tarefas");

        itemEstatisticas.addActionListener(e -> mostrarEstatisticas());
        menuRelatorios.add(itemEstatisticas);
        menuBar.add(menuRelatorios);
        setJMenuBar(menuBar);

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Estudar Java", "Revisar MigLayout", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Projeto UI", "Aplicar FlatLaf", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Entrega relatório", "Finalizar documento", "Concluída"});
    }

    private void mostrarEstatisticas() {
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

        JOptionPane.showMessageDialog(this,
                "📊 Estatísticas de Tarefas:\n\n"
                + "Pendentes: " + pendentes + "\n"
                + "Em andamento: " + andamento + "\n"
                + "Concluídas: " + concluidas,
                "Relatório", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new GerenciadorTarefas().setVisible(true);
        });
    }
}
