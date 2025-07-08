package Classes;

import Exceptions.InformacoesInvalidasException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Evento( int id, String nome , String descricao, LocalDate dataInicio, LocalDate dataFim ) {
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
        this.descricao = descricao;
        this.id = id;
        this.nome = nome;
    }


    public LocalDate getDataFim() {
        return dataFim;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public String getDescricao() {
        return descricao;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static void criarEvento(String nome, String descricao, LocalDate data_inicio, LocalDate data_fim) throws SQLException, ClassNotFoundException, InformacoesInvalidasException {
        TarefasAdminDB.criarEvento(nome, descricao, data_inicio, data_fim);
    }

}
