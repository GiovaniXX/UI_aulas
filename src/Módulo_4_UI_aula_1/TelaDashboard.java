package Módulo_4_UI_aula_1;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class TelaDashboard extends JFrame {

    public TelaDashboard() {
        setTitle("Dashboard Moderno");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Painel principal com MigLayout
        JPanel panel = new JPanel(new MigLayout("wrap 1", "[grow,fill]", "[]10[grow]10[]"));

        // Barra de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        menuArquivo.add(new JMenuItem("Novo"));
        menuArquivo.add(new JMenuItem("Abrir"));
        menuArquivo.add(new JMenuItem("Sair"));
        menuBar.add(menuArquivo);

        JMenu menuAjuda = new JMenu("Ajuda");
        menuAjuda.add(new JMenuItem("Sobre"));
        menuBar.add(menuAjuda);

        setJMenuBar(menuBar);

        // Tabela de dados
        String[] colunas = {"ID", "Nome", "Status"};
        Object[][] dados = {
            {1, "Tarefa A", "Concluída"},
            {2, "Tarefa B", "Em andamento"},
            {3, "Tarefa C", "Pendente"}
        };
        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollTabela = new JScrollPane(tabela);

        // Botões de ação
        JPanel painelBotoes = new JPanel(new MigLayout("wrap 3", "[grow,fill][grow,fill][grow,fill]"));
        painelBotoes.add(new JButton("Adicionar"));
        painelBotoes.add(new JButton("Editar"));
        painelBotoes.add(new JButton("Remover"));

        // Adiciona componentes ao painel principal
        panel.add(new JLabel("📊 Lista de Tarefas"), "align center");
        panel.add(scrollTabela, "grow, push");
        panel.add(painelBotoes, "growx");

        add(panel);
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            //FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new TelaDashboard().setVisible(true);
        });
    }
}
