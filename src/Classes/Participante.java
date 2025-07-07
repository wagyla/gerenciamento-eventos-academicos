package Classes;
import ENUM.PapelUsuario;
import Exceptions.SenhaIncorretaException;

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


    public static Usuario login(String email, String senha) throws SenhaIncorretaException{
        Participante pt = UsuarioDB.selecionarParticipantePorEmail(email);
        
        if (pt == null){
            throw new SenhaIncorretaException("Usuário não encontrado para o email "+email);
        }
        if(pt.comparePassword(senha)){
            return pt;
        }else{
//           Exceção de Senha incorreta
            throw new SenhaIncorretaException("Senha incorreta para o email: " +email);
        }
    }

    public void inscreverEvento(int eventoId){

    }

    @Override
    public void visualizarEventos() {
        System.out.println("VISUALIZAR EVENTOS");

    }
}
