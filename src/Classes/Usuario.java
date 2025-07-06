package Classes;

public abstract class Usuario {
    static int contagem =0;
    private int id = 0;
    private String nome;
    private String email;
    private String senha;

    public Usuario(String email, String nome, String senha) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.id = ++Usuario.contagem;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }


    public String getSenha() {
        return senha;
    }

    public abstract boolean login(String email, String senha);

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
