package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Atividade {
    private int id;
    private String nome;
    private String descricao;
    private LocalDate data;
    private int limiteInscricoes;
    private String tipo;

    public Atividade(int id, String nome,String descricao,LocalDate data, int limiteInscricoes,String tipo) {
        this.data = data;
        this.descricao = descricao;
        this.id = id;
        this.limiteInscricoes = limiteInscricoes;
        this.nome = nome;
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getLimiteInscricoes() {
        return limiteInscricoes;
    }

    public void setLimiteInscricoes(int limiteInscricoes) {
        this.limiteInscricoes = limiteInscricoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
