package Classes;
import ENUM.PapelUsuario;

public class Participante extends Usuario {

    public Participante(String email, String nome, String senha, PapelUsuario papel) {
        super(email, nome, senha, papel);
    }

    public Participante(int id, String email, String nome, String senha, PapelUsuario papel) {
        super(id, email, nome, senha, papel);
    }

    public static void cadastrar(String email,String nome,String senha, PapelUsuario papel){
        if(papel == PapelUsuario.ADMINISTRADOR) {
            throw new RuntimeException("Participante não pode ser admin");
        }
        UsuarioDB.cadastrarParticipante(email,nome,senha,papel);
    }


    public static Usuario login(String email, String senha){
        Participante pt = UsuarioDB.selecionarParticipantePorEmail(email);

        if(pt.comparePassword(senha)){
            return pt;
        }else{
//           Exceção de Senha incorreta
            return null;
        }
    }

    public void inscreverEvento(int eventoId){

    }

    @Override
    public void visualizarEventos() {
        System.out.println("VSUALIZAR EVENTOS");

    }
}
