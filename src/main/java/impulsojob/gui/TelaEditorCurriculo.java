package impulsojob.gui;

import impulsojob.config.SessaoUsuario;
import impulsojob.model.Cliente;
import impulsojob.model.Curriculo;
import impulsojob.model.Experiencia;
import impulsojob.model.Formacao;
import impulsojob.model.Habilidade;
import impulsojob.model.Idioma;
import impulsojob.service.CurriculoService;
import impulsojob.service.GeradorPDF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaEditorCurriculo extends javax.swing.JFrame {

    private final CurriculoService curriculoService;
    private Curriculo curriculoAtual;

    // Componentes da aba de Dados Principais
    private JTextField txtNomeCurriculo;
    private JTextField txtNomeProfissional;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtCidade;
    private JComboBox<String> cmbModelo;
    private JTextArea txtObjetivo;
    private JTextArea txtResumo;

    // Componentes de Experiência
    private DefaultTableModel modelExp;
    private JTable tblExp;

    // Componentes de Formação
    private DefaultTableModel modelForm;
    private JTable tblForm;

    // Componentes de Habilidades e Idiomas
    private DefaultTableModel modelHab;
    private JTable tblHab;
    private DefaultTableModel modelIdi;
    private JTable tblIdi;

    // Componentes da Prévia Visual em Tempo Real
    private JPanel pnlPrevia;
    private JEditorPane txtPreviaHtml;
    private JLabel lblStatusPrevia;

    public TelaEditorCurriculo() {
        this(null);
    }

    public TelaEditorCurriculo(Curriculo curriculoParaEditar) {
        initComponents();
        this.curriculoService = new CurriculoService();
        this.curriculoAtual = curriculoParaEditar != null ? curriculoParaEditar : new Curriculo();

        inicializarAbasCustomizadas();
        carregarDadosDoUsuarioECurriculo();
        setLocationRelativeTo(null);
    }

    private void inicializarAbasCustomizadas() {
        // Aba 1: Dados Principais e Objetivo
        JPanel pnlDados = new JPanel(new BorderLayout(15, 15));
        pnlDados.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel formGrid = new JPanel(new GridLayout(6, 2, 10, 10));
        txtNomeCurriculo = new JTextField();
        txtNomeProfissional = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();
        txtCidade = new JTextField();
        cmbModelo = new JComboBox<>(new String[]{"1 - Modelo Moderno (Azul Executivo)", "2 - Modelo Clássico (Elegante Preto e Branco)", "3 - Modelo Minimalista (Verde Neutro)"});

        formGrid.add(new JLabel("Identificação do Currículo (ex: Desenvolvedor Pleno):"));
        formGrid.add(txtNomeCurriculo);
        formGrid.add(new JLabel("Nome Completo:"));
        formGrid.add(txtNomeProfissional);
        formGrid.add(new JLabel("E-mail de Contato:"));
        formGrid.add(txtEmail);
        formGrid.add(new JLabel("Telefone com DDD:"));
        formGrid.add(txtTelefone);
        formGrid.add(new JLabel("Cidade / Estado:"));
        formGrid.add(txtCidade);
        formGrid.add(new JLabel("Modelo Visual do Currículo:"));
        formGrid.add(cmbModelo);

        JPanel pnlTextos = new JPanel(new GridLayout(2, 1, 10, 10));

        // Objetivo
        txtObjetivo = new JTextArea(3, 20);
        txtObjetivo.setLineWrap(true);
        txtObjetivo.setWrapStyleWord(true);
        JScrollPane spObj = new JScrollPane(txtObjetivo);
        spObj.setBorder(BorderFactory.createTitledBorder("Objetivo Profissional"));

        // Resumo
        txtResumo = new JTextArea(4, 20);
        txtResumo.setLineWrap(true);
        txtResumo.setWrapStyleWord(true);
        JScrollPane spRes = new JScrollPane(txtResumo);
        spRes.setBorder(BorderFactory.createTitledBorder("Resumo Profissional / Qualificações"));

        pnlTextos.add(spObj);
        pnlTextos.add(spRes);

        pnlDados.add(formGrid, BorderLayout.NORTH);
        pnlDados.add(pnlTextos, BorderLayout.CENTER);
        pnlAbas.addTab("👤 Dados Principais & Objetivo", pnlDados);

        // Aba 2: Experiências Profissionais
        JPanel pnlExp = new JPanel(new BorderLayout(10, 10));
        pnlExp.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        modelExp = new DefaultTableModel(new Object[]{"Cargo", "Empresa", "Descrição das Atividades"}, 0);
        tblExp = new JTable(modelExp);
        tblExp.setRowHeight(24);
        pnlExp.add(new JScrollPane(tblExp), BorderLayout.CENTER);

        JPanel pnlExpBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddExp = new JButton("+ Adicionar Experiência");
        JButton btnRemExp = new JButton("- Remover Selecionada");

        btnAddExp.addActionListener(e -> adicionarExperienciaDialog());
        btnRemExp.addActionListener(e -> {
            int row = tblExp.getSelectedRow();
            if (row >= 0) {
                modelExp.removeRow(row);
                atualizarPrevia();
            }
        });
        pnlExpBotoes.add(btnAddExp);
        pnlExpBotoes.add(btnRemExp);
        pnlExp.add(pnlExpBotoes, BorderLayout.SOUTH);
        pnlAbas.addTab("💼 Experiências Profissionais", pnlExp);

        // Aba 3: Formação Acadêmica
        JPanel pnlForm = new JPanel(new BorderLayout(10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        modelForm = new DefaultTableModel(new Object[]{"Curso", "Instituição", "Descrição / Detalhes"}, 0);
        tblForm = new JTable(modelForm);
        tblForm.setRowHeight(24);
        pnlForm.add(new JScrollPane(tblForm), BorderLayout.CENTER);

        JPanel pnlFormBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddForm = new JButton("+ Adicionar Formação / Curso");
        JButton btnRemForm = new JButton("- Remover Selecionada");
        btnAddForm.addActionListener(e -> adicionarFormacaoDialog());
        btnRemForm.addActionListener(e -> {
            int row = tblForm.getSelectedRow();
            if (row >= 0) {
                modelForm.removeRow(row);
                atualizarPrevia();
            }
        });
        pnlFormBotoes.add(btnAddForm);
        pnlFormBotoes.add(btnRemForm);
        pnlForm.add(pnlFormBotoes, BorderLayout.SOUTH);
        pnlAbas.addTab("🎓 Formação Acadêmica & Cursos", pnlForm);

        // Aba 4: Habilidades & Idiomas
        JPanel pnlHabIdi = new JPanel(new GridLayout(1, 2, 15, 15));
        pnlHabIdi.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Painel Habilidades
        JPanel pnlHabilidades = new JPanel(new BorderLayout(5, 5));
        pnlHabilidades.setBorder(BorderFactory.createTitledBorder("Habilidades Técnicas & Comportamentais"));
        modelHab = new DefaultTableModel(new Object[]{"Habilidade", "Nível"}, 0);
        tblHab = new JTable(modelHab);
        pnlHabilidades.add(new JScrollPane(tblHab), BorderLayout.CENTER);
        JPanel pnlHabBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddHab = new JButton("+ Adicionar Habilidade");
        JButton btnRemHab = new JButton("- Remover");
        btnAddHab.addActionListener(e -> adicionarHabilidadeDialog());
        btnRemHab.addActionListener(e -> {
            int row = tblHab.getSelectedRow();
            if (row >= 0) {
                modelHab.removeRow(row);
                atualizarPrevia();
            }
        });
        pnlHabBtns.add(btnAddHab);
        pnlHabBtns.add(btnRemHab);
        pnlHabilidades.add(pnlHabBtns, BorderLayout.SOUTH);

        // Painel Idiomas
        JPanel pnlIdiomas = new JPanel(new BorderLayout(5, 5));
        pnlIdiomas.setBorder(BorderFactory.createTitledBorder("Idiomas & Fluência"));
        modelIdi = new DefaultTableModel(new Object[]{"Idioma", "Nível"}, 0);
        tblIdi = new JTable(modelIdi);
        pnlIdiomas.add(new JScrollPane(tblIdi), BorderLayout.CENTER);
        JPanel pnlIdiBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddIdi = new JButton("+ Adicionar Idioma");
        JButton btnRemIdi = new JButton("- Remover");
        btnAddIdi.addActionListener(e -> adicionarIdiomaDialog());
        btnRemIdi.addActionListener(e -> {
            int row = tblIdi.getSelectedRow();
            if (row >= 0) {
                modelIdi.removeRow(row);
                atualizarPrevia();
            }
        });
        pnlIdiBtns.add(btnAddIdi);
        pnlIdiBtns.add(btnRemIdi);
        pnlIdiomas.add(pnlIdiBtns, BorderLayout.SOUTH);

        pnlHabIdi.add(pnlHabilidades);
        pnlHabIdi.add(pnlIdiomas);
        pnlAbas.addTab("⚡ Habilidades & Idiomas", pnlHabIdi);

        // Aba 5: Prévia do Currículo em Tempo Real
        pnlPrevia = criarPainelPrevia();
        pnlAbas.addTab("👁️ Prévia em Tempo Real", pnlPrevia);

        pnlAbas.addChangeListener(e -> {
            if (pnlAbas.getSelectedComponent() == pnlPrevia) {
                atualizarPrevia();
            }
        });

        cmbModelo.addActionListener(e -> atualizarPrevia());
    }

    private void carregarDadosDoUsuarioECurriculo() {
        if (SessaoUsuario.isAutenticado()) {
            Cliente usuario = SessaoUsuario.getUsuarioLogado();
            txtNomeProfissional.setText(usuario.getNome());
            txtEmail.setText(usuario.getEmail());
            txtTelefone.setText(usuario.getTelefone());
        }

        if (curriculoAtual != null && curriculoAtual.getId() > 0) {
            lblTituloEditor.setText("Editando Currículo: " + curriculoAtual.getNome());
            txtNomeCurriculo.setText(curriculoAtual.getNome());
            txtObjetivo.setText(curriculoAtual.getObjetivo());
            txtResumo.setText(curriculoAtual.getResumo());
            txtCidade.setText(curriculoAtual.getCidade());

            if (curriculoAtual.getModeloId() >= 1 && curriculoAtual.getModeloId() <= 3) {
                cmbModelo.setSelectedIndex(curriculoAtual.getModeloId() - 1);
            }

            modelExp.setRowCount(0);
            for (Experiencia exp : curriculoAtual.getExperiencias()) {
                modelExp.addRow(new Object[]{exp.getCargo(), exp.getEmpresa(), exp.getDescricao()});
            }

            modelForm.setRowCount(0);
            for (Formacao f : curriculoAtual.getFormacoes()) {
                modelForm.addRow(new Object[]{f.getCurso(), f.getInstituicao(), f.getDescricao()});
            }

            modelHab.setRowCount(0);
            for (Habilidade hab : curriculoAtual.getHabilidades()) {
                modelHab.addRow(new Object[]{hab.getNome(), hab.getNivel()});
            }

            modelIdi.setRowCount(0);
            for (Idioma idi : curriculoAtual.getIdiomas()) {
                modelIdi.addRow(new Object[]{idi.getNome(), idi.getNivel()});
            }
        } else {
            txtNomeCurriculo.setText("Meu Currículo Profissional");
        }
    }

    private void adicionarExperienciaDialog() {
        JTextField cargoField = new JTextField();
        JTextField empresaField = new JTextField();
        JTextArea descArea = new JTextArea(4, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Cargo:"));
        panel.add(cargoField);
        panel.add(new JLabel("Empresa:"));
        panel.add(empresaField);
        panel.add(new JLabel("Descrição das Atividades:"));
        panel.add(new JScrollPane(descArea));

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Experiência Profissional", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !cargoField.getText().trim().isEmpty()) {
            modelExp.addRow(new Object[]{cargoField.getText().trim(), empresaField.getText().trim(), descArea.getText().trim()});
            atualizarPrevia();
        }
    }

    private void adicionarFormacaoDialog() {
        JTextField cursoField = new JTextField();
        JTextField instField = new JTextField();
        JTextField descField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Curso / Graduação:"));
        panel.add(cursoField);
        panel.add(new JLabel("Instituição de Ensino:"));
        panel.add(instField);
        panel.add(new JLabel("Detalhes (ex: Concluído em 2024):"));
        panel.add(descField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Formação / Curso", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !cursoField.getText().trim().isEmpty()) {
            modelForm.addRow(new Object[]{cursoField.getText().trim(), instField.getText().trim(), descField.getText().trim()});
            atualizarPrevia();
        }
    }

    private void adicionarHabilidadeDialog() {
        JTextField nomeField = new JTextField();
        JComboBox<String> nivelBox = new JComboBox<>(new String[]{"Iniciante", "Intermediário", "Avançado", "Especialista"});

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Habilidade / Tecnologia:"));
        panel.add(nomeField);
        panel.add(new JLabel("Nível de Domínio:"));
        panel.add(nivelBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Habilidade", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !nomeField.getText().trim().isEmpty()) {
            modelHab.addRow(new Object[]{nomeField.getText().trim(), (String) nivelBox.getSelectedItem()});
            atualizarPrevia();
        }
    }

    private void adicionarIdiomaDialog() {
        JTextField nomeField = new JTextField();
        JComboBox<String> nivelBox = new JComboBox<>(new String[]{"Básico", "Intermediário", "Avançado", "Fluente / Nativo"});

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Idioma:"));
        panel.add(nomeField);
        panel.add(new JLabel("Nível de Conversação:"));
        panel.add(nivelBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Idioma", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !nomeField.getText().trim().isEmpty()) {
            modelIdi.addRow(new Object[]{nomeField.getText().trim(), (String) nivelBox.getSelectedItem()});
            atualizarPrevia();
        }
    }

    private JPanel criarPainelPrevia() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel barraControle = new JPanel(new BorderLayout(10, 10));
        barraControle.setBackground(new Color(24, 34, 53));
        barraControle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        lblStatusPrevia = new JLabel("Modelo Visual: 1 - Modelo Moderno (Azul Executivo)");
        lblStatusPrevia.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatusPrevia.setForeground(Color.WHITE);

        JButton btnAtualizar = new JButton("🔄 Atualizar Prévia");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAtualizar.setBackground(new Color(16, 185, 129));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.addActionListener(e -> atualizarPrevia());

        barraControle.add(lblStatusPrevia, BorderLayout.WEST);
        barraControle.add(btnAtualizar, BorderLayout.EAST);

        txtPreviaHtml = new JEditorPane();
        txtPreviaHtml.setContentType("text/html");
        txtPreviaHtml.setEditable(false);
        txtPreviaHtml.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

        JScrollPane scrollPrevia = new JScrollPane(txtPreviaHtml);
        scrollPrevia.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85), 1));

        pnl.add(barraControle, BorderLayout.NORTH);
        pnl.add(scrollPrevia, BorderLayout.CENTER);
        return pnl;
    }

    public void atualizarPrevia() {
        if (txtPreviaHtml == null) return;
        int modeloIndex = cmbModelo != null ? cmbModelo.getSelectedIndex() : 0;
        String nomeModelo = cmbModelo != null ? (String) cmbModelo.getSelectedItem() : "Modelo Padrão";
        if (lblStatusPrevia != null) {
            lblStatusPrevia.setText("Modelo Visual: " + nomeModelo);
        }
        txtPreviaHtml.setText(gerarHtmlCurriculo(modeloIndex));
        txtPreviaHtml.setCaretPosition(0);
    }

    private String gerarHtmlCurriculo(int modeloIndex) {
        String corPrimaria = "#1e3a8a"; // Modelo 1 (Moderno Azul)
        String corSecundaria = "#2563eb";
        String fontFamily = "Segoe UI, Arial, sans-serif";

        if (modeloIndex == 1) {
            // Modelo 2: Clássico Preto e Branco
            corPrimaria = "#111827";
            corSecundaria = "#4b5563";
            fontFamily = "Georgia, serif";
        } else if (modeloIndex == 2) {
            // Modelo 3: Minimalista Verde
            corPrimaria = "#065f46";
            corSecundaria = "#10b981";
            fontFamily = "Segoe UI, Arial, sans-serif";
        }

        String nome = txtNomeProfissional != null && !txtNomeProfissional.getText().trim().isEmpty() ? txtNomeProfissional.getText().trim() : "Seu Nome Completo";
        String email = txtEmail != null && !txtEmail.getText().trim().isEmpty() ? txtEmail.getText().trim() : "seu.email@exemplo.com";
        String telefone = txtTelefone != null && !txtTelefone.getText().trim().isEmpty() ? txtTelefone.getText().trim() : "(00) 00000-0000";
        String cidade = txtCidade != null && !txtCidade.getText().trim().isEmpty() ? txtCidade.getText().trim() : "Sua Cidade, Estado";
        String objetivo = txtObjetivo != null && !txtObjetivo.getText().trim().isEmpty() ? txtObjetivo.getText().trim() : "";
        String resumo = txtResumo != null && !txtResumo.getText().trim().isEmpty() ? txtResumo.getText().trim() : "";

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family: ").append(fontFamily).append("; background-color: #ffffff; color: #1e293b; padding: 25px;'>");

        // Cabeçalho do Currículo
        sb.append("<div style='border-bottom: 3px solid ").append(corPrimaria).append("; padding-bottom: 12px; margin-bottom: 16px;'>");
        sb.append("<h1 style='color: ").append(corPrimaria).append("; margin: 0; font-size: 24px;'>").append(nome).append("</h1>");
        sb.append("<p style='color: #64748b; margin: 5px 0 0 0; font-size: 13px;'>");
        sb.append(email).append(" &nbsp;|&nbsp; ").append(telefone).append(" &nbsp;|&nbsp; ").append(cidade);
        sb.append("</p></div>");

        // Objetivo Profissional
        sb.append("<h3 style='color: ").append(corPrimaria).append("; border-bottom: 1px solid #cbd5e1; padding-bottom: 4px; margin-top: 14px; margin-bottom: 6px; font-size: 15px;'>OBJETIVO PROFISSIONAL</h3>");
        if (!objetivo.isEmpty()) {
            sb.append("<p style='font-size: 13px; line-height: 1.4; margin: 4px 0 10px 0;'>").append(objetivo.replace("\n", "<br>")).append("</p>");
        } else {
            sb.append("<p style='color: #94a3b8; font-style: italic; font-size: 12px; margin: 4px 0 10px 0;'>[Preencha seu objetivo profissional para destacar seu foco de carreira nesta seção]</p>");
        }

        // Resumo Profissional
        sb.append("<h3 style='color: ").append(corPrimaria).append("; border-bottom: 1px solid #cbd5e1; padding-bottom: 4px; margin-top: 14px; margin-bottom: 6px; font-size: 15px;'>RESUMO PROFISSIONAL</h3>");
        if (!resumo.isEmpty()) {
            sb.append("<p style='font-size: 13px; line-height: 1.4; margin: 4px 0 10px 0;'>").append(resumo.replace("\n", "<br>")).append("</p>");
        } else {
            sb.append("<p style='color: #94a3b8; font-style: italic; font-size: 12px; margin: 4px 0 10px 0;'>[Sintetize suas maiores realizações, qualificações e diferenciais competitivos]</p>");
        }

        // Experiências Profissionais
        sb.append("<h3 style='color: ").append(corPrimaria).append("; border-bottom: 1px solid #cbd5e1; padding-bottom: 4px; margin-top: 14px; margin-bottom: 6px; font-size: 15px;'>EXPERIÊNCIAS PROFISSIONAIS</h3>");
        if (modelExp != null && modelExp.getRowCount() > 0) {
            for (int i = 0; i < modelExp.getRowCount(); i++) {
                String cargo = (String) modelExp.getValueAt(i, 0);
                String empresa = (String) modelExp.getValueAt(i, 1);
                String desc = (String) modelExp.getValueAt(i, 2);
                sb.append("<div style='margin-bottom: 10px;'>");
                sb.append("<b style='font-size: 13px; color: #0f172a;'>").append(cargo).append("</b>");
                if (empresa != null && !empresa.isEmpty()) {
                    sb.append(" &mdash; <span style='color: ").append(corSecundaria).append("; font-weight: bold;'>").append(empresa).append("</span>");
                }
                if (desc != null && !desc.isEmpty()) {
                    sb.append("<p style='font-size: 12px; line-height: 1.35; margin: 3px 0 0 0;'>").append(desc.replace("\n", "<br>")).append("</p>");
                }
                sb.append("</div>");
            }
        } else {
            sb.append("<p style='color: #94a3b8; font-style: italic; font-size: 12px; margin: 4px 0 10px 0;'>[Nenhuma experiência adicionada. Adicione seus cargos na aba 'Experiências Profissionais']</p>");
        }

        // Formação Acadêmica & Cursos
        sb.append("<h3 style='color: ").append(corPrimaria).append("; border-bottom: 1px solid #cbd5e1; padding-bottom: 4px; margin-top: 14px; margin-bottom: 6px; font-size: 15px;'>FORMAÇÃO ACADÊMICA & CURSOS</h3>");
        if (modelForm != null && modelForm.getRowCount() > 0) {
            for (int i = 0; i < modelForm.getRowCount(); i++) {
                String curso = (String) modelForm.getValueAt(i, 0);
                String inst = (String) modelForm.getValueAt(i, 1);
                String desc = (String) modelForm.getValueAt(i, 2);
                sb.append("<div style='margin-bottom: 8px;'>");
                sb.append("<b style='font-size: 13px;'>").append(curso).append("</b>");
                if (inst != null && !inst.isEmpty()) {
                    sb.append(" &mdash; <span>").append(inst).append("</span>");
                }
                if (desc != null && !desc.isEmpty()) {
                    sb.append("<div style='font-size: 12px; color: #64748b;'>").append(desc).append("</div>");
                }
                sb.append("</div>");
            }
        } else {
            sb.append("<p style='color: #94a3b8; font-style: italic; font-size: 12px; margin: 4px 0 10px 0;'>[Nenhuma formação cadastrada. Adicione cursos ou graduações na aba 'Formação Acadêmica']</p>");
        }

        // Habilidades e Idiomas em duas colunas
        sb.append("<table width='100%' style='margin-top: 14px;' cellpadding='0' cellspacing='0'><tr>");
        sb.append("<td width='50%' valign='top' style='padding-right: 15px;'>");
        sb.append("<h3 style='color: ").append(corPrimaria).append("; border-bottom: 1px solid #cbd5e1; padding-bottom: 4px; margin-bottom: 6px; font-size: 15px;'>HABILIDADES</h3>");
        if (modelHab != null && modelHab.getRowCount() > 0) {
            sb.append("<ul style='margin: 0; padding-left: 18px; font-size: 12px;'>");
            for (int i = 0; i < modelHab.getRowCount(); i++) {
                sb.append("<li><b>").append(modelHab.getValueAt(i, 0)).append("</b> (").append(modelHab.getValueAt(i, 1)).append(")</li>");
            }
            sb.append("</ul>");
        } else {
            sb.append("<p style='color: #94a3b8; font-style: italic; font-size: 12px;'>[Adicione competências na aba 'Habilidades & Idiomas']</p>");
        }
        sb.append("</td>");

        sb.append("<td width='50%' valign='top'>");
        sb.append("<h3 style='color: ").append(corPrimaria).append("; border-bottom: 1px solid #cbd5e1; padding-bottom: 4px; margin-bottom: 6px; font-size: 15px;'>IDIOMAS</h3>");
        if (modelIdi != null && modelIdi.getRowCount() > 0) {
            sb.append("<ul style='margin: 0; padding-left: 18px; font-size: 12px;'>");
            for (int i = 0; i < modelIdi.getRowCount(); i++) {
                sb.append("<li><b>").append(modelIdi.getValueAt(i, 0)).append("</b>: ").append(modelIdi.getValueAt(i, 1)).append("</li>");
            }
            sb.append("</ul>");
        } else {
            sb.append("<p style='color: #94a3b8; font-style: italic; font-size: 12px;'>[Adicione idiomas na aba 'Habilidades & Idiomas']</p>");
        }
        sb.append("</td></tr></table>");

        sb.append("</body></html>");
        return sb.toString();
    }

    private void sincronizarCamposParaCurriculo() {
        curriculoAtual.setNome(txtNomeCurriculo.getText().trim());
        curriculoAtual.setObjetivo(txtObjetivo.getText().trim());
        curriculoAtual.setResumo(txtResumo.getText().trim());
        curriculoAtual.setCidade(txtCidade.getText().trim());
        curriculoAtual.setModeloId(cmbModelo.getSelectedIndex() + 1);

        // Experiências
        List<Experiencia> exps = new ArrayList<>();
        for (int i = 0; i < modelExp.getRowCount(); i++) {
            Experiencia e = new Experiencia();
            e.setCargo((String) modelExp.getValueAt(i, 0));
            e.setEmpresa((String) modelExp.getValueAt(i, 1));
            e.setDescricao((String) modelExp.getValueAt(i, 2));
            exps.add(e);
        }
        curriculoAtual.setExperiencias(exps);

        // Formações
        List<Formacao> forms = new ArrayList<>();
        for (int i = 0; i < modelForm.getRowCount(); i++) {
            Formacao f = new Formacao();
            f.setCurso((String) modelForm.getValueAt(i, 0));
            f.setInstituicao((String) modelForm.getValueAt(i, 1));
            f.setDescricao((String) modelForm.getValueAt(i, 2));
            forms.add(f);
        }
        curriculoAtual.setFormacoes(forms);

        // Habilidades
        List<Habilidade> habs = new ArrayList<>();
        for (int i = 0; i < modelHab.getRowCount(); i++) {
            Habilidade h = new Habilidade();
            h.setNome((String) modelHab.getValueAt(i, 0));
            h.setNivel((String) modelHab.getValueAt(i, 1));
            habs.add(h);
        }
        curriculoAtual.setHabilidades(habs);

        // Idiomas
        List<Idioma> idis = new ArrayList<>();
        for (int i = 0; i < modelIdi.getRowCount(); i++) {
            Idioma idi = new Idioma();
            idi.setNome((String) modelIdi.getValueAt(i, 0));
            idi.setNivel((String) modelIdi.getValueAt(i, 1));
            idis.add(idi);
        }
        curriculoAtual.setIdiomas(idis);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTopo = new javax.swing.JPanel();
        btnVoltar = new javax.swing.JButton();
        lblTituloEditor = new javax.swing.JLabel();
        btnBaixarPDF = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        pnlAbas = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ImpulsoJob - Editor de Currículos");

        pnlTopo.setBackground(new java.awt.Color(15, 23, 42));

        btnVoltar.setBackground(new java.awt.Color(30, 42, 58));
        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltar.setText("← Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        lblTituloEditor.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTituloEditor.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloEditor.setText("Editor de Currículo Profissional");

        btnBaixarPDF.setBackground(new java.awt.Color(30, 58, 138));
        btnBaixarPDF.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBaixarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnBaixarPDF.setText("📄 Baixar em PDF");
        btnBaixarPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBaixarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaixarPDFActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(16, 185, 129));
        btnSalvar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("💾 Salvar Currículo");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTopoLayout = new javax.swing.GroupLayout(pnlTopo);
        pnlTopo.setLayout(pnlTopoLayout);
        pnlTopoLayout.setHorizontalGroup(
            pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTituloEditor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBaixarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        pnlTopoLayout.setVerticalGroup(
            pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlTopoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTituloEditor)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBaixarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlAbas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTopo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlAbas, javax.swing.GroupLayout.DEFAULT_SIZE, 968, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTopo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlAbas, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
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

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        try {
            sincronizarCamposParaCurriculo();
            curriculoService.salvar(curriculoAtual);

            JOptionPane.showMessageDialog(this,
                    "Currículo '" + curriculoAtual.getNome() + "' salvo com sucesso no banco de dados!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            lblTituloEditor.setText("Editando Currículo: " + curriculoAtual.getNome());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar currículo: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnBaixarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaixarPDFActionPerformed
        try {
            sincronizarCamposParaCurriculo();

            Cliente cliente = SessaoUsuario.isAutenticado() ? SessaoUsuario.getUsuarioLogado() : new Cliente();
            if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
                cliente.setNome(txtNomeProfissional.getText());
            }
            if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
                cliente.setEmail(txtEmail.getText());
            }
            if (cliente.getTelefone() == null || cliente.getTelefone().isEmpty()) {
                cliente.setTelefone(txtTelefone.getText());
            }

            GeradorPDF.gerarCurriculoCompleto(this, cliente, curriculoAtual);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar PDF: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBaixarPDFActionPerformed

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ignored) {
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaEditorCurriculo().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaixarPDF;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel lblTituloEditor;
    private javax.swing.JTabbedPane pnlAbas;
    private javax.swing.JPanel pnlTopo;
    // End of variables declaration//GEN-END:variables
}
