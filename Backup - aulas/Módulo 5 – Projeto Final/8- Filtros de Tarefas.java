import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class GerenciadorTarefas extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;

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
        sorter = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(sorter);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        panel.add(scrollTabela, "grow, push");

        // Painel de botões
        JPanel painelBotoes = new JPanel(new MigLayout("wrap 6", "[grow,fill][grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]"));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnConcluir = new JButton("Concluir");
        JButton btnSalvar = new JButton("Salvar");

        // Botões de filtro
        JButton btnMostrarTodos = new JButton("Todos");
        JButton btnMostrarPendentes = new JButton("Pendentes");
        JButton btnMostrarEmAndamento = new JButton("Em andamento");
        JButton btnMostrarConcluidas = new JButton("Concluídas");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnMostrarTodos);
        painelBotoes.add(btnMostrarPendentes);
        painelBotoes.add(btnMostrarEmAndamento);
        painelBotoes.add(btnMostrarConcluidas);

        panel.add(painelBotoes, "growx");
        add(panel);

        // Ações de filtro
        btnMostrarTodos.addActionListener(e -> sorter.setRowFilter(null));
        btnMostrarPendentes.addActionListener(e -> sorter.setRowFilter(RowFilter.regexFilter("Pendente", 3)));
        btnMostrarEmAndamento.addActionListener(e -> sorter.setRowFilter(RowFilter.regexFilter("Em andamento", 3)));
        btnMostrarConcluidas.addActionListener(e -> sorter.setRowFilter(RowFilter.regexFilter("Concluída", 3)));

        // Exemplo inicial
        modeloTabela.addRow(new Object[]{1, "Tarefa A", "Descrição A", "Pendente"});
        modeloTabela.addRow(new Object[]{2, "Tarefa B", "Descrição B", "Em andamento"});
        modeloTabela.addRow(new Object[]{3, "Tarefa C", "Descrição C", "Concluída"});
    }

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new GerenciadorTarefas().setVisible(true);
        });
    }
}
