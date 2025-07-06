package Classes;

public class Participante extends Usuario {
    private String papel;

    public Participante(String email, String nome, String senha, String papel) {
        super(email, nome, senha);
        this.papel = papel;
    }
    public void inscreverEvento(int eventoId){

    }

    @Override
    public boolean login(String email, String senha) {
        return getEmail().equals(email) && getSenha().equals(senha);
    }

    @Override
    public void visualizarEventos() {
        System.out.println("VSUALIZAR EVENTOS");

    }
}
