import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class GerenciadorTarefas extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private Connection conexao;

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

        JPanel painelBotoes = new JPanel(new MigLayout("wrap 5", "[grow,fill][grow,fill][grow,fill][grow,fill][grow,fill]"));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnConcluir = new JButton("Concluir");
        JButton btnSalvar = new JButton("Salvar");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnConcluir);
        painelBotoes.add(btnSalvar);

        panel.add(painelBotoes, "growx");
        add(panel);

        // Conectar ao banco SQLite
        conectarBanco();
        criarTabelaSeNaoExistir();
        carregarTarefas();

        // Ações
        btnAdicionar.addActionListener(e -> abrirDialogoAdicionar());
        btnEditar.addActionListener(e -> abrirDialogoEditar());
        btnRemover.addActionListener(e -> removerTarefa());
        btnConcluir.addActionListener(e -> concluirTarefa());
        btnSalvar.addActionListener(e -> salvarTarefas());
    }

    private void conectarBanco() {
        try {
            conexao = DriverManager.getConnection("jdbc:sqlite:tarefas.db");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarTabelaSeNaoExistir() {
        try (Statement stmt = conexao.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS tarefas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo TEXT," +
                    "descricao TEXT," +
                    "status TEXT)");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar tabela: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTarefas() {
        modeloTabela.setRowCount(0);
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tarefas")) {
            while (rs.next()) {
                modeloTabela.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarTarefas() {
        try (Statement stmt = conexao.createStatement()) {
            stmt.execute("DELETE FROM tarefas"); // limpa antes de salvar novamente
            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                String sql = "INSERT INTO tarefas (titulo, descricao, status) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                    ps.setString(1, (String) modeloTabela.getValueAt(i, 1));
                    ps.setString(2, (String) modeloTabela.getValueAt(i, 2));
                    ps.setString(3, (String) modeloTabela.getValueAt(i, 3));
                    ps.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(this, "Tarefas salvas no banco com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar tarefas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Métodos abrirDialogoAdicionar, abrirDialogoEditar, removerTarefa, concluirTarefa
    // continuam iguais aos exemplos anteriores, apenas manipulando a tabela em memória.
    // O botão "Salvar" grava no banco, e ao iniciar o programa, os dados são carregados.

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
