package pessoas;

import java.util.MissingFormatArgumentException;

public class Pessoa {
    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        if(cpf==null || cpf.isEmpty()){
            throw new IllegalArgumentException("CPF não pode ser vazio!");
        }
        if(!cpf.matches("\\d{11}")){
            throw new MissingFormatArgumentException("CPF Deve conter 11 digitos!");
        }
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
