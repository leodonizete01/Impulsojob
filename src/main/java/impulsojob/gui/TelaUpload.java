package impulsojob.gui;

import impulsojob.config.SessaoUsuario;
import impulsojob.dao.UploadDAO;
import impulsojob.model.Upload;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TelaUpload extends javax.swing.JFrame {

    private final UploadDAO uploadDAO;
    private DefaultTableModel modelTabela;
    private List<Upload> listaUploads = new ArrayList<>();

    private static final List<String> EXTENSOES_PERMITIDAS = Arrays.asList(
            "pdf", "doc", "docx", "png", "jpg", "jpeg", "txt"
    );

    public TelaUpload() {
        initComponents();
        this.uploadDAO = new UploadDAO();

        configurarTabela();
        carregarTabela();
        setLocationRelativeTo(null);
    }

    private void configurarTabela() {
        modelTabela = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome do Arquivo", "Tipo", "Data do Envio", "Caminho no Disco"}
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tblUploads.setModel(modelTabela);
        tblUploads.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblUploads.getColumnModel().getColumn(1).setPreferredWidth(220);
        tblUploads.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblUploads.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblUploads.getColumnModel().getColumn(4).setPreferredWidth(350);
    }

    private void carregarTabela() {
        modelTabela.setRowCount(0);
        if (!SessaoUsuario.isAutenticado()) return;

        try {
            listaUploads = uploadDAO.listarPorCliente(SessaoUsuario.getUsuarioLogado().getId());
            for (Upload u : listaUploads) {
                modelTabela.addRow(new Object[]{
                        u.getId(),
                        u.getNomeArquivo(),
                        u.getTipoArquivo() != null ? u.getTipoArquivo().toUpperCase() : "-",
                        u.getDataUpload() != null ? u.getDataUpload().toString() : "Hoje",
                        u.getCaminhoArquivo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar uploads: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Upload obterSelecionado() {
        int row = tblUploads.getSelectedRow();
        if (row < 0 || row >= listaUploads.size()) {
            JOptionPane.showMessageDialog(this, "Selecione um arquivo na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return listaUploads.get(row);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTopo = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnEnviarArquivo = new javax.swing.JButton();
        pnlConteudo = new javax.swing.JPanel();
        lblDescricao = new javax.swing.JLabel();
        btnAbrirArquivo = new javax.swing.JButton();
        btnExcluirArquivo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUploads = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ImpulsoJob - Upload de Documentos");

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
        lblTitulo.setText("Upload e Gestão de Arquivos");

        btnEnviarArquivo.setBackground(new java.awt.Color(16, 185, 129));
        btnEnviarArquivo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnEnviarArquivo.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarArquivo.setText("📤 Enviar Arquivo");
        btnEnviarArquivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarArquivoActionPerformed(evt);
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
                .addComponent(btnEnviarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlTopoLayout.setVerticalGroup(
            pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo)
                    .addComponent(btnEnviarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlConteudo.setBackground(new java.awt.Color(13, 20, 32));

        lblDescricao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescricao.setForeground(new java.awt.Color(100, 133, 148));
        lblDescricao.setText("Documentos, certificados e currículos antigos anexados com segurança");

        btnAbrirArquivo.setBackground(new java.awt.Color(30, 58, 138));
        btnAbrirArquivo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnAbrirArquivo.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirArquivo.setText("📂 Abrir Arquivo");
        btnAbrirArquivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbrirArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirArquivoActionPerformed(evt);
            }
        });

        btnExcluirArquivo.setBackground(new java.awt.Color(220, 38, 51));
        btnExcluirArquivo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnExcluirArquivo.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluirArquivo.setText("Excluir");
        btnExcluirArquivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirArquivoActionPerformed(evt);
            }
        });

        tblUploads.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblUploads.setRowHeight(26);
        jScrollPane1.setViewportView(tblUploads);

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
                        .addComponent(btnAbrirArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluirArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        pnlConteudoLayout.setVerticalGroup(
            pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(btnAbrirArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluirArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnEnviarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarArquivoActionPerformed
        if (!SessaoUsuario.isAutenticado()) {
            JOptionPane.showMessageDialog(this, "Nenhum usuário conectado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Selecione um documento para upload");
        seletor.setFileFilter(new FileNameExtensionFilter("Documentos e Imagens (*.pdf, *.doc, *.docx, *.png, *.jpg, *.txt)",
                "pdf", "doc", "docx", "png", "jpg", "jpeg", "txt"));

        if (seletor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = seletor.getSelectedFile();
            String nome = arquivo.getName();
            String extensao = "";
            int i = nome.lastIndexOf('.');
            if (i > 0) {
                extensao = nome.substring(i + 1).toLowerCase();
            }

            if (!EXTENSOES_PERMITIDAS.contains(extensao)) {
                JOptionPane.showMessageDialog(this,
                        "Tipo de arquivo não permitido.\nFormatos aceitos: PDF, DOC, DOCX, PNG, JPG, TXT.",
                        "Formato Inválido",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Upload u = new Upload();
                u.setClienteId(SessaoUsuario.getUsuarioLogado().getId());
                u.setNomeArquivo(nome);
                u.setCaminhoArquivo(arquivo.getAbsolutePath());
                u.setTipoArquivo(extensao);
                u.setDataUpload(new Date(System.currentTimeMillis()));

                uploadDAO.cadastrar(u);
                JOptionPane.showMessageDialog(this, "Arquivo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarTabela();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao registrar upload: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEnviarArquivoActionPerformed

    private void btnAbrirArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirArquivoActionPerformed
        Upload u = obterSelecionado();
        if (u != null) {
            File f = new File(u.getCaminhoArquivo());
            if (!f.exists()) {
                JOptionPane.showMessageDialog(this,
                        "O arquivo não foi encontrado no caminho especificado:\n" + u.getCaminhoArquivo(),
                        "Arquivo Não Encontrado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Desktop.getDesktop().open(f);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Não foi possível abrir o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAbrirArquivoActionPerformed

    private void btnExcluirArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirArquivoActionPerformed
        Upload u = obterSelecionado();
        if (u != null) {
            int opc = JOptionPane.showConfirmDialog(this,
                    "Deseja remover o registro do arquivo '" + u.getNomeArquivo() + "'?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (opc == JOptionPane.YES_OPTION) {
                try {
                    uploadDAO.excluir(u.getId(), SessaoUsuario.getUsuarioLogado().getId());
                    JOptionPane.showMessageDialog(this, "Arquivo removido!");
                    carregarTabela();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnExcluirArquivoActionPerformed

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ignored) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaUpload().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirArquivo;
    private javax.swing.JButton btnEnviarArquivo;
    private javax.swing.JButton btnExcluirArquivo;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlConteudo;
    private javax.swing.JPanel pnlTopo;
    private javax.swing.JTable tblUploads;
    // End of variables declaration//GEN-END:variables
}
