package Classes;

import java.sql.SQLException;

import ENUM.PapelUsuario;
import ENUM.StatusPagamento;
import Exceptions.SenhaIncorretaException;

public class Participante extends Usuario implements UsuarioInterface{
    private static Double valorAluno = 200.0;
    private static Double valorProfissional = 0.1;
    private static Double valorProfessor = 400.0;

    public static Double getValorAluno() {
        return valorAluno;
    }

    public static void setValorAluno(Double valorAluno) {
        Participante.valorAluno = valorAluno;
    }

    public static Double getValorProfessor() {
        return valorProfessor;
    }

    public static void setValorProfessor(Double valorProfessor) {
        Participante.valorProfessor = valorProfessor;
    }

    public static Double getValorProfissional() {
        return valorProfissional;
    }

    public static void setValorProfissional(Double valorProfissional) {
        Participante.valorProfissional = valorProfissional;
    }

    public Participante(String email, String nome, String senha, PapelUsuario papel) {
        super(email, nome, senha, papel);
    }

    public Participante(int id, String email, String nome, String senha, PapelUsuario papel) {
        super(id, email, nome, senha, papel);
    }

    public static void cadastrar(String email, String nome, String senha, PapelUsuario papel) {
        if (papel == PapelUsuario.ADMINISTRADOR) {
            throw new RuntimeException("Participante não pode ser admin");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido: deve conter '@'.");
        }
        UsuarioDB.cadastrarParticipante(email, nome, senha, papel);
    }

    public static Usuario login(String email, String senha) throws SenhaIncorretaException, ClassNotFoundException, SQLException {
        Participante pt = UsuarioDB.selecionarParticipantePorEmail(email);

        if (pt == null) {
            throw new SenhaIncorretaException("Usuário não encontrado para o email " + email);
        }
        if (pt.comparePassword(senha)) {
            return pt;
        } else {
            throw new SenhaIncorretaException("Senha incorreta para o email: " + email);
        }
    }

    public StatusPagamento coletarStatusDePagamento(int eventoID){
        return TarefasParticipanteDB.coletarStatusPagamento(this.getId(), eventoID);

    }

}
