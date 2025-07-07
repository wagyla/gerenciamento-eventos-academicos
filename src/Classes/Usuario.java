package Classes;

import ENUM.PapelUsuario;

public abstract class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private PapelUsuario papel;

    public Usuario(String email, String nome, String senha, PapelUsuario papel) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.papel = papel;
        this.id = -1;
    }

    public Usuario(int id, String email, String nome, String senha, PapelUsuario papel){
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.papel = papel;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public PapelUsuario getPapel() {
        return papel;
    }

    public String getNome() {
        return nome;
    }

    protected String getSenha() {
        return senha;
    }


    public boolean comparePassword(String password){
        return password.equals(this.senha);
    }

    public static Usuario login(String email, String senha){
        return null;
    }

    public abstract void visualizarEventos();

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
