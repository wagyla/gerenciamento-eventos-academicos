package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexao {
    private Connection connection;

    public void conectar(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void initBD() {
        this.conectar();
        try {
            String resetDataBase = """
                    select 'drop table if exists "' || name || '";' as comando
                    from sqlite_master
                    where type = 'table' and name not like 'sqlite_%';
                    """;


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
                foreign key (id_usuario) references usuarios(id),
                foreign key (id_evento) references eventos(id)
            );
                    """;

            Statement stm = connection.createStatement();
            ResultSet resultado = stm.executeQuery(resetDataBase);

            while (resultado.next()){
                stm.executeUpdate(resultado.getString("comando"));
            }
            stm.execute(createUsuarios);
            stm.execute(createEventos);
            stm.execute(createAtividades);
            stm.execute(createInscritos_atividade);
            stm.execute(createInscritos_eventos);

            String seederUsuario = """
                    insert into usuarios(nome, email, senha, papel) values
                    ("leticia", "let@email.com", "123", "ADMINISTRADOR");
                    """;

            stm.executeUpdate(seederUsuario);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }finally {
            this.fechar();
        }
    }

    public void fechar() {
        try {
            this.connection.close();
        }catch (Exception e){
            System.out.println("Falha ao tentar fechar Banco de dados");
            throw new RuntimeException(e.getMessage());
        }
    }
    public Connection getConnection(){return connection;}
}
