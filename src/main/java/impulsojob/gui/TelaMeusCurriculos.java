package impulsojob.gui;

import impulsojob.config.SessaoUsuario;
import impulsojob.model.Cliente;
import impulsojob.model.Curriculo;
import impulsojob.service.CurriculoService;
import impulsojob.service.GeradorPDF;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaMeusCurriculos extends javax.swing.JFrame {

    private final CurriculoService curriculoService;
    private DefaultTableModel modelTabela;
    private List<Curriculo> listaCurriculos = new ArrayList<>();

    public TelaMeusCurriculos() {
        initComponents();
        this.curriculoService = new CurriculoService();

        configurarTabela();
        carregarDadosPerfil();
        carregarTabelaCurriculos();
        setLocationRelativeTo(null);
    }

    private void configurarTabela() {
        modelTabela = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Título / Identificação do Currículo", "Modelo Visual", "Última Atualização"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCurriculos.setModel(modelTabela);
        tblCurriculos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblCurriculos.getColumnModel().getColumn(1).setPreferredWidth(350);
        tblCurriculos.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblCurriculos.getColumnModel().getColumn(3).setPreferredWidth(120);

        tblCurriculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tblCurriculos.getSelectedRow() >= 0) {
                    abrirEditorCurriculoSelecionado();
                }
            }
        });
    }

    private void carregarDadosPerfil() {
        if (SessaoUsuario.isAutenticado()) {
            Cliente usuario = SessaoUsuario.getUsuarioLogado();
            txtNNome.setText(usuario.getNome());
            txtTelefone.setText(usuario.getTelefone());
            txtEmail.setText(usuario.getEmail());
            txtCPF.setText(usuario.getCpf());
        }
    }

    private void carregarTabelaCurriculos() {
        modelTabela.setRowCount(0);
        try {
            listaCurriculos = curriculoService.listarDoUsuarioLogado();
            for (Curriculo c : listaCurriculos) {
                String nomeModelo = "Moderno";
                if (c.getModeloId() == 2) nomeModelo = "Clássico";
                else if (c.getModeloId() == 3) nomeModelo = "Minimalista";

                String dataStr = c.getDataAtualizacao() != null ? c.getDataAtualizacao().toString() : "Recente";

                modelTabela.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        nomeModelo,
                        dataStr
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar currículos: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Curriculo obterCurriculoSelecionado() {
        int row = tblCurriculos.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um currículo na tabela para realizar esta ação.",
                    "Nenhum Currículo Selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int id = (int) modelTabela.getValueAt(row, 0);
        try {
            return curriculoService.buscarPorId(id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao recuperar dados do currículo: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void abrirEditorCurriculoSelecionado() {
        Curriculo c = obterCurriculoSelecionado();
        if (c != null) {
            TelaEditorCurriculo editor = new TelaEditorCurriculo(c);
            editor.setVisible(true);
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMenuPerfil = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        lblMeuPerfil = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        txtNNome = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblCPF = new javax.swing.JLabel();
        txtCPF = new javax.swing.JTextField();
        pnlConteudo = new javax.swing.JPanel();
        lblTituloLista = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnBaixarPDF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCurriculos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ImpulsoJob - Meus Currículos");

        pnlMenuPerfil.setBackground(new java.awt.Color(15, 23, 42));

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

        lblMeuPerfil.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMeuPerfil.setForeground(new java.awt.Color(255, 255, 255));
        lblMeuPerfil.setText("Meu Perfil");

        lblNome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNome.setForeground(new java.awt.Color(241, 232, 226));
        lblNome.setText("Nome:");

        txtNNome.setEditable(false);
        txtNNome.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblTelefone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTelefone.setForeground(new java.awt.Color(241, 232, 226));
        lblTelefone.setText("Telefone:");

        txtTelefone.setEditable(false);
        txtTelefone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(241, 232, 226));
        lblEmail.setText("E-mail:");

        txtEmail.setEditable(false);
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblCPF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCPF.setForeground(new java.awt.Color(241, 232, 226));
        lblCPF.setText("CPF:");

        txtCPF.setEditable(false);
        txtCPF.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout pnlMenuPerfilLayout = new javax.swing.GroupLayout(pnlMenuPerfil);
        pnlMenuPerfil.setLayout(pnlMenuPerfilLayout);
        pnlMenuPerfilLayout.setHorizontalGroup(
            pnlMenuPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuPerfilLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlMenuPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMeuPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(lblNome)
                    .addComponent(txtNNome)
                    .addComponent(lblTelefone)
                    .addComponent(txtTelefone)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail)
                    .addComponent(lblCPF)
                    .addComponent(txtCPF))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pnlMenuPerfilLayout.setVerticalGroup(
            pnlMenuPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuPerfilLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblMeuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNNome, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTelefone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCPF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        pnlConteudo.setBackground(new java.awt.Color(13, 20, 32));

        lblTituloLista.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTituloLista.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloLista.setText("Meus Currículos Cadastrados");

        btnNovo.setBackground(new java.awt.Color(16, 185, 129));
        btnNovo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnNovo.setForeground(new java.awt.Color(255, 255, 255));
        btnNovo.setText("+ Novo Currículo");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(30, 58, 138));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("✏ Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(220, 38, 51));
        btnExcluir.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluir.setText("🗑 Excluir");
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnBaixarPDF.setBackground(new java.awt.Color(30, 58, 138));
        btnBaixarPDF.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnBaixarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnBaixarPDF.setText("📄 Baixar PDF");
        btnBaixarPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBaixarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaixarPDFActionPerformed(evt);
            }
        });

        tblCurriculos.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblCurriculos.setRowHeight(26);
        jScrollPane1.setViewportView(tblCurriculos);

        javax.swing.GroupLayout pnlConteudoLayout = new javax.swing.GroupLayout(pnlConteudo);
        pnlConteudo.setLayout(pnlConteudoLayout);
        pnlConteudoLayout.setHorizontalGroup(
            pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlConteudoLayout.createSequentialGroup()
                        .addComponent(lblTituloLista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBaixarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        pnlConteudoLayout.setVerticalGroup(
            pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloLista)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBaixarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMenuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(pnlConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenuPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        TelaEditorCurriculo editor = new TelaEditorCurriculo();
        editor.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        abrirEditorCurriculoSelecionado();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        Curriculo c = obterCurriculoSelecionado();
        if (c == null) return;

        int opc = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o currículo '" + c.getNome() + "'?\nEsta ação não poderá ser desfeita.",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opc == JOptionPane.YES_OPTION) {
            try {
                curriculoService.excluir(c.getId());
                JOptionPane.showMessageDialog(this,
                        "Currículo excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarTabelaCurriculos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao excluir currículo: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnBaixarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaixarPDFActionPerformed
        Curriculo c = obterCurriculoSelecionado();
        if (c == null) return;

        Cliente cliente = SessaoUsuario.isAutenticado() ? SessaoUsuario.getUsuarioLogado() : new Cliente();
        GeradorPDF.gerarCurriculoCompleto(this, cliente, c);
    }//GEN-LAST:event_btnBaixarPDFActionPerformed

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ignored) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaMeusCurriculos().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaixarPDF;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMeuPerfil;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTituloLista;
    private javax.swing.JPanel pnlConteudo;
    private javax.swing.JPanel pnlMenuPerfil;
    private javax.swing.JTable tblCurriculos;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNNome;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
