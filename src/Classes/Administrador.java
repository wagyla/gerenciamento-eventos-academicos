package Classes;

public class Administrador extends Usuario {

    public Administrador (String nome,String email,String senha){
        super(nome,email,senha);
    }

    @Override
    public boolean login(String email, String senha) {
        return this.getEmail().equals(email) && this.getSenha().equals(senha);
    }

    public void criarEvento(Evento evento){
        System.out.println("CRIAR EVENTO");
    }
    public void editarEvento(Evento evento){
        System.out.println("EDITAR EVENTO");
    }
    public void excluirEvento(){
        System.out.println("EXCLUIR EVENTO");
    }
    public void confirmarPagamento(){
        System.out.println("Confirmando pagamento");
    }
    public void gerenciarValores(){
        System.out.println("Gerenciar valores");
    }

    @Override
    public void visualizarEventos() {
        System.out.println("listar os eventos");
    }
}
