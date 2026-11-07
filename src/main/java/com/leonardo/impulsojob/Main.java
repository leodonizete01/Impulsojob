package com.leonardo.impulsojob;

import com.formdev.flatlaf.FlatDarkLaf;
import impulsojob.config.Banco;
import impulsojob.gui.TelaLogin;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        // 1. Configura o tema FlatDarkLaf para toda a aplicação Swing
        try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Aviso: FlatLaf não pôde ser inicializado: " + ex.getMessage());
        }

        // 2. Testa a conectividade com o banco de dados PostgreSQL Neon
        try {
            Banco.conectar();
            System.out.println("ImpulsoJob: Conexão com banco de dados estabelecida com sucesso.");
        } catch (SQLException e) {
            System.err.println("Aviso: Falha na conexão com o banco de dados: " + e.getMessage());
        }

        // 3. Abre a Tela de Login como ponto de entrada do sistema
        SwingUtilities.invokeLater(() -> {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        });
    }
}