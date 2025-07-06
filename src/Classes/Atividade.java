package Classes;

import java.util.ArrayList;
import java.util.List;

public class Atividade {
    private int id;
    private String nome;
    private String descricao;
    private String data;
    private int limiteInscricoes;
    private String tipo;
    private List<Participante> inscritos = new ArrayList<>();

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public List<Participante> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<Participante> inscritos) {
        this.inscritos = inscritos;
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
