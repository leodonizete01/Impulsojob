package impulsojob.gui;

import impulsojob.config.SessaoUsuario;
import impulsojob.dao.FavoritoDAO;
import impulsojob.model.Curriculo;
import impulsojob.model.Favorito;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TelaFavoritos extends javax.swing.JFrame {

    private final FavoritoDAO favoritoDAO;
    private DefaultTableModel modelTabela;
    private List<Favorito> listaFavoritos = new ArrayList<>();

    public TelaFavoritos() {
        initComponents();
        this.favoritoDAO = new FavoritoDAO();

        configurarTabela();
        carregarTabela();
        setLocationRelativeTo(null);
    }

    private void configurarTabela() {
        modelTabela = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Favorito", "Nome do Modelo", "Descrição do Modelo"}
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblFavoritos.setModel(modelTabela);
        tblFavoritos.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblFavoritos.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblFavoritos.getColumnModel().getColumn(2).setPreferredWidth(500);
    }

    private void carregarTabela() {
        modelTabela.setRowCount(0);
        if (!SessaoUsuario.isAutenticado()) return;

        try {
            listaFavoritos = favoritoDAO.listarPorCliente(SessaoUsuario.getUsuarioLogado().getId());
            for (Favorito f : listaFavoritos) {
                modelTabela.addRow(new Object[]{
                        f.getId(),
                        f.getModeloNome(),
                        f.getModeloDescricao()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar favoritos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Favorito obterSelecionado() {
        int row = tblFavoritos.getSelectedRow();
        if (row < 0 || row >= listaFavoritos.size()) {
            JOptionPane.showMessageDialog(this, "Selecione um modelo favorito na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return listaFavoritos.get(row);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTopo = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        pnlConteudo = new javax.swing.JPanel();
        lblDescricao = new javax.swing.JLabel();
        btnExplorar = new javax.swing.JButton();
        btnCriarComModelo = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFavoritos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ImpulsoJob - Meus Modelos Favoritos");

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
        lblTitulo.setText("Modelos de Currículo Favoritados");

        javax.swing.GroupLayout pnlTopoLayout = new javax.swing.GroupLayout(pnlTopo);
        pnlTopo.setLayout(pnlTopoLayout);
        pnlTopoLayout.setHorizontalGroup(
            pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTopoLayout.setVerticalGroup(
            pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlConteudo.setBackground(new java.awt.Color(13, 20, 32));

        lblDescricao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescricao.setForeground(new java.awt.Color(100, 133, 148));
        lblDescricao.setText("Seus estilos preferidos para criação rápida de novos currículos");

        btnExplorar.setBackground(new java.awt.Color(30, 58, 138));
        btnExplorar.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnExplorar.setForeground(new java.awt.Color(255, 255, 255));
        btnExplorar.setText("🎨 Ver Todos Modelos");
        btnExplorar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExplorar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExplorarActionPerformed(evt);
            }
        });

        btnCriarComModelo.setBackground(new java.awt.Color(16, 185, 129));
        btnCriarComModelo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCriarComModelo.setForeground(new java.awt.Color(255, 255, 255));
        btnCriarComModelo.setText("+ Criar com este");
        btnCriarComModelo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCriarComModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarComModeloActionPerformed(evt);
            }
        });

        btnRemover.setBackground(new java.awt.Color(220, 38, 51));
        btnRemover.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnRemover.setForeground(new java.awt.Color(255, 255, 255));
        btnRemover.setText("Remover Favorito");
        btnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        tblFavoritos.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblFavoritos.setRowHeight(28);
        jScrollPane1.setViewportView(tblFavoritos);

        javax.swing.GroupLayout pnlConteudoLayout = new javax.swing.GroupLayout(pnlConteudo);
        pnlConteudo.setLayout(pnlConteudoLayout);
        pnlConteudoLayout.setHorizontalGroup(
            pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                    .addGroup(pnlConteudoLayout.createSequentialGroup()
                        .addComponent(lblDescricao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExplorar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCriarComModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        pnlConteudoLayout.setVerticalGroup(
            pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(btnExplorar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCriarComModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTopo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTopo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnExplorarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExplorarActionPerformed
        TelaEstilos estilos = new TelaEstilos();
        estilos.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnExplorarActionPerformed

    private void btnCriarComModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarComModeloActionPerformed
        Favorito fav = obterSelecionado();
        if (fav != null) {
            Curriculo c = new Curriculo();
            c.setModeloId(fav.getModeloId());
            TelaEditorCurriculo editor = new TelaEditorCurriculo(c);
            editor.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnCriarComModeloActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        Favorito fav = obterSelecionado();
        if (fav != null) {
            try {
                favoritoDAO.remover(fav.getClienteId(), fav.getModeloId());
                JOptionPane.showMessageDialog(this, "Modelo removido dos favoritos!");
                carregarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ignored) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaFavoritos().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCriarComModelo;
    private javax.swing.JButton btnExplorar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlConteudo;
    private javax.swing.JPanel pnlTopo;
    private javax.swing.JTable tblFavoritos;
    // End of variables declaration//GEN-END:variables
}
