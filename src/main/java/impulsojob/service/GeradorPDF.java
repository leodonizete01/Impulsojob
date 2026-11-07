package impulsojob.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.borders.SolidBorder;
import impulsojob.model.Cliente;
import impulsojob.model.Curriculo;
import impulsojob.model.Experiencia;
import impulsojob.model.Formacao;
import impulsojob.model.Habilidade;
import impulsojob.model.Idioma;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GeradorPDF {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/yyyy");

    /**
     * Gera PDF completo a partir do Cliente e do Curriculo reais.
     */
    public static File gerarCurriculoCompleto(Component tela, Cliente cliente, Curriculo curriculo) {
        if (cliente == null || curriculo == null) {
            JOptionPane.showMessageDialog(tela,
                    "Não há dados suficientes para gerar o currículo.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        JFileChooser seletor = new JFileChooser();
        seletor.setDialogTitle("Salvar currículo em PDF - ImpulsoJob");
        String nomeSugerido = (curriculo.getNome() != null && !curriculo.getNome().trim().isEmpty())
                ? curriculo.getNome().trim().replaceAll("[^a-zA-Z0-9_-]", "_") + ".pdf"
                : "Curriculo_" + (cliente.getNome() != null ? cliente.getNome().replaceAll(" ", "_") : "Profissional") + ".pdf";

        seletor.setSelectedFile(new File(nomeSugerido));
        seletor.setFileFilter(new FileNameExtensionFilter("Documentos PDF (*.pdf)", "pdf"));

        if (seletor.showSaveDialog(tela) != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File arquivo = seletor.getSelectedFile();
        if (!arquivo.getName().toLowerCase().endsWith(".pdf")) {
            arquivo = new File(arquivo.getAbsolutePath() + ".pdf");
        }

        // Estilos de acordo com o modelo (1 = Moderno, 2 = Clássico, 3 = Minimalista)
        int modelo = curriculo.getModeloId() > 0 ? curriculo.getModeloId() : 1;
        DeviceRgb corTitulo;
        DeviceRgb corLinha;

        switch (modelo) {
            case 2: // Clássico (tons de cinza e preto elegante)
                corTitulo = new DeviceRgb(30, 41, 59);
                corLinha = new DeviceRgb(148, 163, 184);
                break;
            case 3: // Minimalista (verde escuro / neutro)
                corTitulo = new DeviceRgb(16, 110, 80);
                corLinha = new DeviceRgb(52, 211, 153);
                break;
            case 1: // Moderno (azul profissional)
            default:
                corTitulo = new DeviceRgb(30, 58, 138);
                corLinha = new DeviceRgb(59, 130, 246);
                break;
        }

        try (PdfWriter writer = new PdfWriter(arquivo);
             PdfDocument pdf = new PdfDocument(writer);
             Document documento = new Document(pdf)) {

            documento.setMargins(36, 40, 36, 40);

            // 1. Cabeçalho com Nome do Candidato
            String nomeCandidato = texto(cliente.getNome());
            if (nomeCandidato.isEmpty()) {
                nomeCandidato = texto(curriculo.getNome());
            }

            documento.add(new Paragraph(nomeCandidato)
                    .setFontSize(22)
                    .setBold()
                    .setFontColor(corTitulo));

            // Informações de contato
            StringBuilder contato = new StringBuilder();
            if (!texto(cliente.getEmail()).isEmpty()) {
                contato.append("E-mail: ").append(cliente.getEmail().trim());
            }
            if (!texto(cliente.getTelefone()).isEmpty()) {
                if (contato.length() > 0) contato.append(" | ");
                contato.append("Telefone: ").append(cliente.getTelefone().trim());
            }
            if (!texto(curriculo.getCidade()).isEmpty()) {
                if (contato.length() > 0) contato.append(" | ");
                contato.append("Localidade: ").append(curriculo.getCidade().trim());
            }

            if (contato.length() > 0) {
                documento.add(new Paragraph(contato.toString())
                        .setFontSize(10)
                        .setFontColor(ColorConstants.DARK_GRAY));
            }

            // 2. Resumo Profissional
            if (!texto(curriculo.getResumo()).isEmpty()) {
                adicionarSecao(documento, "RESUMO PROFISSIONAL", corTitulo, corLinha);
                documento.add(new Paragraph(curriculo.getResumo().trim()).setFontSize(10.5f));
            }

            // 3. Objetivo Profissional
            if (!texto(curriculo.getObjetivo()).isEmpty()) {
                adicionarSecao(documento, "OBJETIVO PROFISSIONAL", corTitulo, corLinha);
                documento.add(new Paragraph(curriculo.getObjetivo().trim()).setFontSize(10.5f));
            }

            // 4. Experiências Profissionais
            if (curriculo.getExperiencias() != null && !curriculo.getExperiencias().isEmpty()) {
                adicionarSecao(documento, "EXPERIÊNCIA PROFISSIONAL", corTitulo, corLinha);
                for (Experiencia exp : curriculo.getExperiencias()) {
                    String cargoEmpresa = texto(exp.getCargo()) +
                            (!texto(exp.getEmpresa()).isEmpty() ? " — " + exp.getEmpresa() : "");

                    Paragraph pCargo = new Paragraph(cargoEmpresa).setBold().setFontSize(11);

                    String periodo = formatarPeriodo(exp.getDataInicio(), exp.getDataFim());
                    if (!periodo.isEmpty()) {
                        pCargo.add(" (" + periodo + ")");
                    }
                    documento.add(pCargo);

                    if (!texto(exp.getDescricao()).isEmpty()) {
                        documento.add(new Paragraph(exp.getDescricao().trim())
                                .setFontSize(10)
                                .setMarginLeft(10)
                                .setMarginBottom(8));
                    }
                }
            }

            // 5. Formação Acadêmica e Cursos
            if (curriculo.getFormacoes() != null && !curriculo.getFormacoes().isEmpty()) {
                adicionarSecao(documento, "FORMAÇÃO ACADÊMICA E CURSOS", corTitulo, corLinha);
                for (Formacao f : curriculo.getFormacoes()) {
                    String cursoInst = texto(f.getCurso()) +
                            (!texto(f.getInstituicao()).isEmpty() ? " — " + f.getInstituicao() : "");

                    Paragraph pCurso = new Paragraph(cursoInst).setBold().setFontSize(11);
                    String periodo = formatarPeriodo(f.getDataInicio(), f.getDataFim());
                    if (!periodo.isEmpty()) {
                        pCurso.add(" (" + periodo + ")");
                    }
                    documento.add(pCurso);

                    if (!texto(f.getDescricao()).isEmpty()) {
                        documento.add(new Paragraph(f.getDescricao().trim())
                                .setFontSize(10)
                                .setMarginLeft(10)
                                .setMarginBottom(8));
                    }
                }
            }

            // 6. Habilidades
            if (curriculo.getHabilidades() != null && !curriculo.getHabilidades().isEmpty()) {
                adicionarSecao(documento, "HABILIDADES E COMPETÊNCIAS", corTitulo, corLinha);
                for (Habilidade hab : curriculo.getHabilidades()) {
                    String textoHab = "• " + texto(hab.getNome());
                    if (!texto(hab.getNivel()).isEmpty()) {
                        textoHab += " (" + hab.getNivel() + ")";
                    }
                    if (!texto(hab.getDescricao()).isEmpty()) {
                        textoHab += ": " + hab.getDescricao();
                    }
                    documento.add(new Paragraph(textoHab).setFontSize(10));
                }
            }

            // 7. Idiomas
            if (curriculo.getIdiomas() != null && !curriculo.getIdiomas().isEmpty()) {
                adicionarSecao(documento, "IDIOMAS", corTitulo, corLinha);
                for (Idioma idi : curriculo.getIdiomas()) {
                    String textoIdi = "• " + texto(idi.getNome());
                    if (!texto(idi.getNivel()).isEmpty()) {
                        textoIdi += " — Nível: " + idi.getNivel();
                    }
                    documento.add(new Paragraph(textoIdi).setFontSize(10));
                }
            }

            // Rodapé
            documento.add(new Paragraph("\nDocumento gerado pelo sistema ImpulsoJob")
                    .setFontSize(8.5f)
                    .setItalic()
                    .setFontColor(ColorConstants.GRAY)
                    .setMarginTop(20));

            JOptionPane.showMessageDialog(tela,
                    "Currículo em PDF gerado com sucesso!\nSalvo em: " + arquivo.getAbsolutePath(),
                    "PDF Gerado",
                    JOptionPane.INFORMATION_MESSAGE);

            return arquivo;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(tela,
                    "Erro ao gerar o arquivo PDF: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Sobrecarga legada para manter compatibilidade com telas existentes.
     */
    public static void gerarPDF(
            Component tela,
            String nome,
            String email,
            String telefone,
            String objetivo,
            List<String> experiencias,
            List<String> formacoes,
            List<String> habilidades,
            List<String> idiomas) {

        Cliente c = new Cliente();
        c.setNome(nome);
        c.setEmail(email);
        c.setTelefone(telefone);

        Curriculo cur = new Curriculo();
        cur.setNome(nome);
        cur.setObjetivo(objetivo);

        if (experiencias != null) {
            List<Experiencia> expList = new ArrayList<>();
            for (String s : experiencias) {
                Experiencia e = new Experiencia();
                e.setCargo(s);
                expList.add(e);
            }
            cur.setExperiencias(expList);
        }

        if (formacoes != null) {
            List<Formacao> formList = new ArrayList<>();
            for (String s : formacoes) {
                Formacao f = new Formacao();
                f.setCurso(s);
                formList.add(f);
            }
            cur.setFormacoes(formList);
        }

        if (habilidades != null) {
            List<Habilidade> habList = new ArrayList<>();
            for (String s : habilidades) {
                Habilidade h = new Habilidade();
                h.setNome(s);
                habList.add(h);
            }
            cur.setHabilidades(habList);
        }

        if (idiomas != null) {
            List<Idioma> idiList = new ArrayList<>();
            for (String s : idiomas) {
                Idioma i = new Idioma();
                i.setNome(s);
                idiList.add(i);
            }
            cur.setIdiomas(idiList);
        }

        gerarCurriculoCompleto(tela, c, cur);
    }

    private static void adicionarSecao(Document doc, String titulo, DeviceRgb corTitulo, DeviceRgb corLinha) {
        doc.add(new Paragraph(titulo)
                .setFontSize(13)
                .setBold()
                .setFontColor(corTitulo)
                .setMarginTop(12)
                .setMarginBottom(2));

        LineSeparator linha = new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.SolidLine(1f));
        linha.setFontColor(corLinha);
        linha.setMarginBottom(6);
        doc.add(linha);
    }

    private static String formatarPeriodo(java.sql.Date ini, java.sql.Date fim) {
        if (ini == null && fim == null) return "";
        String sIni = ini != null ? DATE_FORMAT.format(ini) : "";
        String sFim = fim != null ? DATE_FORMAT.format(fim) : "Atual";
        if (!sIni.isEmpty()) {
            return sIni + " a " + sFim;
        }
        return sFim;
    }

    private static String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
