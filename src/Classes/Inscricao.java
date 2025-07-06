package Classes;

import java.util.ArrayList;
import java.util.List;

public class Inscricao {
    private Participante participante;
    private Evento evento;
    private List <Atividade> atividades;
    private boolean statusPagamento;

    public Inscricao(List<Atividade> atividades, Evento evento, Participante participante) {
        this.atividades = atividades;
        this.evento = evento;
        this.participante = participante;
        this.statusPagamento = false;
    }

    public void cancelarInscricao(){

    }
}
