package impulsojob.gui;

import impulsojob.config.SessaoUsuario;
import impulsojob.dao.FavoritoDAO;
import impulsojob.dao.ModeloCurriculoDAO;
import impulsojob.model.Curriculo;
import impulsojob.model.ModeloCurriculo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaEstilos extends javax.swing.JFrame {

    private final ModeloCurriculoDAO modeloDAO;
    private final FavoritoDAO favoritoDAO;

    public TelaEstilos() {
        initComponents();
        this.modeloDAO = new ModeloCurriculoDAO();
        this.favoritoDAO = new FavoritoDAO();

        carregarModelos();
        setLocationRelativeTo(null);
    }

    private void carregarModelos() {
        pnlGradeModelos.removeAll();
        try {
            List<ModeloCurriculo> modelos = modeloDAO.listar();
            int clienteId = SessaoUsuario.isAutenticado() ? SessaoUsuario.getUsuarioLogado().getId() : 0;

            for (ModeloCurriculo m : modelos) {
                JPanel card = criarCardModelo(m, clienteId);
                pnlGradeModelos.add(card);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar modelos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        pnlGradeModelos.revalidate();
        pnlGradeModelos.repaint();
    }

    private JPanel criarCardModelo(ModeloCurriculo modelo, int clienteId) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(new Color(24, 34, 53));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Cabeçalho do Card
        JLabel lblNome = new JLabel(modelo.getNome(), SwingConstants.CENTER);
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNome.setForeground(Color.WHITE);

        // Descrição
        JTextArea txtDesc = new JTextArea(modelo.getDescricao());
        txtDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDesc.setForeground(new Color(203, 213, 225));
        txtDesc.setBackground(new Color(24, 34, 53));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);

        // Painel de Ações
        JPanel pnlAcoes = new JPanel(new GridLayout(2, 1, 8, 8));
        pnlAcoes.setOpaque(false);

        boolean isFav = false;
        if (clienteId > 0) {
            try {
                isFav = favoritoDAO.isFavorito(clienteId, modelo.getId());
            } catch (Exception ignored) {
            }
        }

        JButton btnFav = new JButton(isFav ? "★ Favorito" : "☆ Favoritar");
        btnFav.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnFav.setBackground(isFav ? new Color(202, 138, 4) : new Color(40, 50, 70));
        btnFav.setForeground(Color.WHITE);
        btnFav.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final boolean finalIsFav = isFav;
        btnFav.addActionListener(e -> {
            if (!SessaoUsuario.isAutenticado()) {
                JOptionPane.showMessageDialog(this, "Faça login para favoritar modelos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                if (finalIsFav) {
                    favoritoDAO.remover(clienteId, modelo.getId());
                    JOptionPane.showMessageDialog(this, "Modelo removido dos favoritos.");
                } else {
                    favoritoDAO.adicionar(clienteId, modelo.getId());
                    JOptionPane.showMessageDialog(this, "Modelo adicionado aos favoritos!");
                }
                carregarModelos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnUsar = new JButton("Usar este Modelo");
        btnUsar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnUsar.setBackground(new Color(16, 185, 129));
        btnUsar.setForeground(Color.WHITE);
        btnUsar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUsar.addActionListener(e -> {
            Curriculo c = new Curriculo();
            c.setModeloId(modelo.getId());
            TelaEditorCurriculo editor = new TelaEditorCurriculo(c);
            editor.setVisible(true);
            this.dispose();
        });

        pnlAcoes.add(btnFav);
        pnlAcoes.add(btnUsar);

        card.add(lblNome, BorderLayout.NORTH);
        card.add(txtDesc, BorderLayout.CENTER);
        card.add(pnlAcoes, BorderLayout.SOUTH);

        return card;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTopo = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        pnlGradeModelos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ImpulsoJob - Modelos de Currículo");

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
        lblTitulo.setText("Modelos de Currículo Profissionais");

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

        pnlGradeModelos.setBackground(new java.awt.Color(13, 20, 32));
        pnlGradeModelos.setLayout(new java.awt.GridLayout(1, 3, 16, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTopo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(pnlGradeModelos, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTopo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlGradeModelos, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        TelaPrincipal principal = new TelaPrincipal();
        principal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ignored) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaEstilos().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlGradeModelos;
    private javax.swing.JPanel pnlTopo;
    // End of variables declaration//GEN-END:variables
}
