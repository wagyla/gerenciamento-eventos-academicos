package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conexao {
    private Connection connection;

    public void conectar(){
        System.out.println("Abrindo conexão com o banco de dados");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
            System.out.println("Conexão Estabelecida.");
        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco de dados");
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    public void initBD() {
        this.conectar();
        try {
            System.out.println("Iniciando Banco de Dados");
            String create = "CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT,email TEXT, senha TEXT";
            Statement stm = connection.createStatement();
            stm.execute(create);
            System.out.println("Inicializado com sucesso!");
        }catch (Exception e){
            System.out.println("Falha ao iniciar  banco de dados");
            System.out.println(e.getMessage());
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
