package impulsojob.gui;

import impulsojob.config.SessaoUsuario;
import impulsojob.dao.CartaApresentacaoDAO;
import impulsojob.model.CartaApresentacao;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaCartaApresentacao extends javax.swing.JFrame {

    private final CartaApresentacaoDAO cartaDAO;
    private DefaultTableModel modelTabela;
    private List<CartaApresentacao> listaCartas = new ArrayList<>();
    private CartaApresentacao cartaAtual;

    public TelaCartaApresentacao() {
        initComponents();
        this.cartaDAO = new CartaApresentacaoDAO();

        configurarTabela();
        carregarTabelaCartas();
        setLocationRelativeTo(null);
    }

    private void configurarTabela() {
        modelTabela = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Título", "Atualizado em"}
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblCartas.setModel(modelTabela);
        tblCartas.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblCartas.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblCartas.getColumnModel().getColumn(2).setPreferredWidth(90);

        tblCartas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarCartaSelecionada();
            }
        });
    }

    private void carregarTabelaCartas() {
        modelTabela.setRowCount(0);
        if (!SessaoUsuario.isAutenticado()) return;

        try {
            listaCartas = cartaDAO.listarPorCliente(SessaoUsuario.getUsuarioLogado().getId());
            for (CartaApresentacao c : listaCartas) {
                modelTabela.addRow(new Object[]{
                        c.getId(),
                        c.getTitulo(),
                        c.getDataAtualizacao() != null ? c.getDataAtualizacao().toString() : "Hoje"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar cartas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarCartaSelecionada() {
        int row = tblCartas.getSelectedRow();
        if (row >= 0 && row < listaCartas.size()) {
            cartaAtual = listaCartas.get(row);
            txtTituloCarta.setText(cartaAtual.getTitulo());
            txtConteudoCarta.setText(cartaAtual.getConteudo());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTopo = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnNovaCarta = new javax.swing.JButton();
        pnlLista = new javax.swing.JPanel();
        lblMinhasCartas = new javax.swing.JLabel();
        btnExcluirCarta = new javax.swing.JButton();
        jScrollPaneLista = new javax.swing.JScrollPane();
        tblCartas = new javax.swing.JTable();
        pnlEditor = new javax.swing.JPanel();
        lblTituloCarta = new javax.swing.JLabel();
        txtTituloCarta = new javax.swing.JTextField();
        lblConteudoCarta = new javax.swing.JLabel();
        jScrollPaneEditor = new javax.swing.JScrollPane();
        txtConteudoCarta = new javax.swing.JTextArea();
        btnSalvarCarta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ImpulsoJob - Carta de Apresentação");

        pnlTopo.setBackground(new java.awt.Color(15, 23, 42));

        btnVoltar.setBackground(new java.awt.Color(30, 58, 138));
        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltar.setText("← Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Cartas de Apresentação");

        btnNovaCarta.setBackground(new java.awt.Color(16, 185, 129));
        btnNovaCarta.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnNovaCarta.setForeground(new java.awt.Color(255, 255, 255));
        btnNovaCarta.setText("+ Nova Carta");
        btnNovaCarta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovaCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaCartaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTopoLayout = new javax.swing.GroupLayout(pnlTopo);
        pnlTopo.setLayout(pnlTopoLayout);
        pnlTopoLayout.setHorizontalGroup(
            pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNovaCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlTopoLayout.setVerticalGroup(
            pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo)
                    .addComponent(btnNovaCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlLista.setBackground(new java.awt.Color(13, 20, 32));

        lblMinhasCartas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMinhasCartas.setForeground(new java.awt.Color(255, 255, 255));
        lblMinhasCartas.setText("Cartas Salvas");

        btnExcluirCarta.setBackground(new java.awt.Color(220, 38, 51));
        btnExcluirCarta.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnExcluirCarta.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluirCarta.setText("Excluir");
        btnExcluirCarta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirCartaActionPerformed(evt);
            }
        });

        tblCartas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblCartas.setRowHeight(24);
        jScrollPaneLista.setViewportView(tblCartas);

        javax.swing.GroupLayout pnlListaLayout = new javax.swing.GroupLayout(pnlLista);
        pnlLista.setLayout(pnlListaLayout);
        pnlListaLayout.setHorizontalGroup(
            pnlListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneLista, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addGroup(pnlListaLayout.createSequentialGroup()
                        .addComponent(lblMinhasCartas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluirCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        pnlListaLayout.setVerticalGroup(
            pnlListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListaLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMinhasCartas)
                    .addComponent(btnExcluirCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneLista, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        pnlEditor.setBackground(new java.awt.Color(13, 20, 32));

        lblTituloCarta.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblTituloCarta.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloCarta.setText("Título / Referência da Carta:");

        txtTituloCarta.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblConteudoCarta.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblConteudoCarta.setForeground(new java.awt.Color(255, 255, 255));
        lblConteudoCarta.setText("Conteúdo da Carta:");

        txtConteudoCarta.setColumns(20);
        txtConteudoCarta.setFont(new java.awt.Font("Segoe UI", 14, 0)); // NOI18N
        txtConteudoCarta.setLineWrap(true);
        txtConteudoCarta.setRows(5);
        txtConteudoCarta.setWrapStyleWord(true);
        jScrollPaneEditor.setViewportView(txtConteudoCarta);

        btnSalvarCarta.setBackground(new java.awt.Color(16, 185, 129));
        btnSalvarCarta.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSalvarCarta.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarCarta.setText("Salvar Carta");
        btnSalvarCarta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvarCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarCartaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditorLayout = new javax.swing.GroupLayout(pnlEditor);
        pnlEditor.setLayout(pnlEditorLayout);
        pnlEditorLayout.setHorizontalGroup(
            pnlEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditorLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                    .addComponent(txtTituloCarta)
                    .addGroup(pnlEditorLayout.createSequentialGroup()
                        .addComponent(lblTituloCarta)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlEditorLayout.createSequentialGroup()
                        .addComponent(lblConteudoCarta)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditorLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvarCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        pnlEditorLayout.setVerticalGroup(
            pnlEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditorLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTituloCarta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTituloCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblConteudoCarta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalvarCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTopo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTopo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnNovaCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaCartaActionPerformed
        cartaAtual = null;
        txtTituloCarta.setText("");
        txtConteudoCarta.setText("");
        txtTituloCarta.requestFocus();
    }//GEN-LAST:event_btnNovaCartaActionPerformed

    private void btnSalvarCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarCartaActionPerformed
        if (!SessaoUsuario.isAutenticado()) {
            JOptionPane.showMessageDialog(this, "Nenhum usuário logado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = txtTituloCarta.getText().trim();
        String conteudo = txtConteudoCarta.getText().trim();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe um título para a carta.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (conteudo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O conteúdo da carta não pode ficar vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (cartaAtual == null) {
                cartaAtual = new CartaApresentacao();
                cartaAtual.setClienteId(SessaoUsuario.getUsuarioLogado().getId());
                cartaAtual.setTitulo(titulo);
                cartaAtual.setConteudo(conteudo);
                cartaDAO.cadastrar(cartaAtual);
            } else {
                cartaAtual.setTitulo(titulo);
                cartaAtual.setConteudo(conteudo);
                cartaDAO.atualizar(cartaAtual);
            }

            JOptionPane.showMessageDialog(this, "Carta salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarTabelaCartas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar carta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSalvarCartaActionPerformed

    private void btnExcluirCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirCartaActionPerformed
        if (cartaAtual == null || cartaAtual.getId() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma carta salva para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opc = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir a carta '" + cartaAtual.getTitulo() + "'?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opc == JOptionPane.YES_OPTION) {
            try {
                cartaDAO.excluir(cartaAtual.getId(), SessaoUsuario.getUsuarioLogado().getId());
                JOptionPane.showMessageDialog(this, "Carta excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                btnNovaCartaActionPerformed(null);
                carregarTabelaCartas();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnExcluirCartaActionPerformed

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ignored) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaCartaApresentacao().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluirCarta;
    private javax.swing.JButton btnNovaCarta;
    private javax.swing.JButton btnSalvarCarta;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JScrollPane jScrollPaneEditor;
    private javax.swing.JScrollPane jScrollPaneLista;
    private javax.swing.JLabel lblConteudoCarta;
    private javax.swing.JLabel lblMinhasCartas;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloCarta;
    private javax.swing.JPanel pnlEditor;
    private javax.swing.JPanel pnlLista;
    private javax.swing.JPanel pnlTopo;
    private javax.swing.JTable tblCartas;
    private javax.swing.JTextArea txtConteudoCarta;
    private javax.swing.JTextField txtTituloCarta;
    // End of variables declaration//GEN-END:variables
}
