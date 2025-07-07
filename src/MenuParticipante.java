import Classes.Atividade;
import Classes.Evento;
import Classes.Participante;
import Classes.TarefasParticipanteDB;
import ENUM.PapelUsuario;

import java.util.List;
import java.util.Scanner;

public class MenuParticipante {
    public static void areaParticipante(Participante p){
        int op = 0;
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("-----------------------------");
            System.out.println("ÁREA PESSOAL DO PARTICIPANTE");
            System.out.println("-----------------------------");
            System.out.println("1 - Inscrever-se em um evento");//feito
            System.out.println("2 - Vizualizar Eventos inscritos");//feito
            System.out.println("3 - Inscrever-se em uma atividade");//feito
            System.out.println("4 - Vizualizar Atividades inscritas");//feito
            System.out.println("5 - Cancelar inscrição");//feito
            System.out.println("6 - Efetuar Pagamento");//feito
            System.out.println();
            System.out.println("0 - Sair");

            op = Integer.parseInt(scan.nextLine());

            switch (op){
                case 1:
                    InscricaoEvento(p);
                    break;
                case 2:
                    eventosInscritos(p);
                    break;
                case 3:
                    InscricaoAtividade(p);
                    break;
                case 4:
                    atvidadesInscritas(p);
                    break;
                case 5:
                    cancelarInscricao(p);
                    break;
                case 6:
                    efetuarPagamento(p);
                    break;

            }

        } while(op != 0);
    }

    public static void eventosInscritos(Participante p){
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasParticipanteDB.ListarEventosParticipando(p.getId());
        System.out.println("Eventos inscritos:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }
    }

    public static  void atvidadesInscritas(Participante p){
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasParticipanteDB.ListarEventosParticipando(p.getId());
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        List<Atividade> atividades = TarefasParticipanteDB.ListarAtividadesParticipando(eventoId, p.getId());
        System.out.println("Atividades Inscritas: ");
        for(Atividade a : atividades){
            System.out.println(String.format("%s - %s", a.getId(), a.getNome()));
        }

    }

    public static void InscricaoEvento(Participante p){
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasParticipanteDB.ListarEventosNaoParticipando(p.getId());
        System.out.println("Escolha o evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        TarefasParticipanteDB.inscricaoEvento(p.getId(), eventoId);
        System.out.println("Inscrito com sucesso!");
    }

    public static void InscricaoAtividade(Participante p){
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasParticipanteDB.ListarEventosParticipando(p.getId());
        System.out.println("Escolha o evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        List<Atividade> atividades = TarefasParticipanteDB.ListarAtividadesNaoParticipando(eventoId, p.getId());
        System.out.println("Escolha uma atividade:");

        for(Atividade at :atividades){
            System.out.println(String.format("%s - %s", at.getId(), at.getNome()));
        }

        int atividadeID = Integer.parseInt(scan.nextLine());


        TarefasParticipanteDB.inscricaoAtividades(p.getId(), atividadeID);
    }

    public static void efetuarPagamento(Participante p){
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasParticipanteDB.ListarEventosPagPendente(p.getId());
        System.out.println("Escolha o evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());
        double valor = 0.0;
        switch(p.getPapel()){
            case PapelUsuario.ALUNO:
                valor = Participante.getValorAluno();
                break;

            case PapelUsuario.PROFESSOR:
                valor = Participante.getValorProfessor();
                break;

            case PapelUsuario.PROFISSIONAL:
                valor = Participante.getValorProfissional();
                break;

        }
        System.out.println("Você confirma o pagamento de R$"+ valor);
        System.out.println("1- confirmar");
        System.out.println("2- cancelar");
        int escolha = Integer.parseInt(scan.nextLine());
        if(escolha == 1) {
            TarefasParticipanteDB.pagar(eventoId, p.getId());
            System.out.println("Pagamento Realizado");
        }else{
            System.out.println("Pagamento não Efetuado");
        }
    }

    public static void cancelarInscricao(Participante p){
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasParticipanteDB.ListarEventosParticipando(p.getId());
        System.out.println("Escolha o evento que deseja cancelar:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }
        int eventoId = Integer.parseInt(scan.nextLine());

        System.out.println("Você confirma o cancelamento dessa inscrição?");
        System.out.println("1- Sim");
        System.out.println("2- Não");
        int escolha = Integer.parseInt(scan.nextLine());
        if(escolha == 1) {
            TarefasParticipanteDB.cancelarInscricao(p.getId(), eventoId);
            System.out.println("Inscrição cancelada");
        }else{
            System.out.println("Cancelamento não efetuado");
        }

    }
}