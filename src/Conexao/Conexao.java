package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
    private Connection connection;

    public void conectar() throws SQLException, ClassNotFoundException{
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar no banco de dados" +e.getMessage());
        }
    }

    public void initBD() throws SQLException, ClassNotFoundException {
        this.conectar();
        try {
            System.out.println("Iniciando Banco de Dados");
            String dropUsuarios = "DROP TABLE IF EXISTS usuarios;";
            String dropEventos = "DROP TABLE IF EXISTS eventos;";
            String dropAtividades = "DROP TABLE IF EXISTS atividades;";
            String dropInscritosEvento = "DROP TABLE IF EXISTS inscritos_evento;";
            String dropInscritosAtividade = "DROP TABLE IF EXISTS inscritos_atividade;";

            String createUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT,nome TEXT,email TEXT, senha TEXT, papel TEXT)";
            String createEventos = """
                    CREATE TABLE if not exists eventos (
                        id integer primary key autoincrement,
                        nome text,
                        descricao text,
                        data_inicio text,
                        data_fim text
                    );
                    """;
            String createAtividades = """
                    CREATE TABLE IF NOT EXISTS atividades(
                        id integer primary key autoincrement,
                        evento_id integer,
                        descricao text,
                        nome text,
                        data text,
                        limite_inscricao integer,
                        tipo_atividade text,

                        foreign key (evento_id) references eventos(id)
                    );
                            """;
            String createInscritos_atividade = """
                    CREATE TABLE IF NOT EXISTS inscritos_atividade(
                        id_usuario integer not null,
                        id_atividade integer,

                        primary key (id_usuario, id_atividade),
                        foreign key (id_usuario) references usuarios(id)
                    );
                            """;
            String createInscritos_eventos = """
                    CREATE TABLE IF NOT EXISTS inscritos_evento(
                        id_usuario integer not null,
                        id_evento integer,
                        status_pagamento text,

                        primary key (id_usuario, id_evento),
                        foreign key (id_usuario) references usuarios(id)
                    );
                            """;

            String insert = """
                    INSERT INTO usuarios (nome, email, senha, papel)
                    SELECT 'leticia', 'let@email.com', '123', 'ADMINISTRADOR'
                    WHERE NOT EXISTS (
                        SELECT 1 FROM usuarios WHERE email = 'let@email.com'
                    );
                    """;
            Statement stm = connection.createStatement();
            stm.executeUpdate(dropInscritosAtividade);
            stm.executeUpdate(dropInscritosEvento);
            stm.executeUpdate(dropAtividades);
            stm.executeUpdate(dropEventos);
            stm.executeUpdate(dropUsuarios);
            stm.execute(createUsuarios);
            stm.execute(createEventos);
            stm.execute(createAtividades);
            stm.execute(createInscritos_atividade);
            stm.execute(createInscritos_eventos);
            stm.executeUpdate(insert);
            System.out.println("Inicializado com sucesso!");
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar com banco de dados! " + e.getMessage());
        } finally {
            this.fechar();
        }
    }

    public void fechar() {
        try {
            this.connection.close();
        } catch (Exception e) {
            System.out.println("Falha ao tentar fechar Banco de dados");
            throw new RuntimeException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
