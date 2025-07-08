import Classes.Evento;
import Classes.Participante;
import Classes.TarefasAdminDB;
import ENUM.PapelUsuario;
import Exceptions.InformacoesInvalidasException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuAdministrador {
    public static void menuAdmin() throws SQLException, ClassNotFoundException, InformacoesInvalidasException {
        int op = 0;
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("---------------------");
            System.out.println("MENU DO ADMINISTRADOR");
            System.out.println("---------------------");
            System.out.println("1 - Criar Evento"); //feito
            System.out.println("2 - Editar Evento");//feito
            System.out.println("3 - Excluir Evento");//feito
            System.out.println("4 - Cadastrar Atividade"); //feito
            System.out.println("5 - Confirmar Pagamento");//feito
            System.out.println("6 - Vizualizar Painel Administrativo");
            System.out.println();
            System.out.println("0 - Sair");

            op = Integer.parseInt(scan.nextLine());

            switch (op){
                case 1:
                    menuCriarEvento();
                    break;
                case 2:
                    menuEditarEvento();
                    break;
                case 3:
                    excluirEvento();
                    break;
                case 4:
                    cadastrarAtividade();
                    break;
                case 5:
                    confirmarStatusPag();
                    break;
                case 6:
                    painelAdministrativo();
                    break;
            }

        } while(op != 0);
    }

    public static void menuCriarEvento() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome do evento:");
        String nome = scan.nextLine();
        System.out.println("Digite a descrição do evento:");
        String descricao = scan.nextLine();
        System.out.println("Digite a data de inicio do evento:");
        LocalDate data_inicio = LocalDate.parse(scan.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("Digite a data do fim do evento:");
        LocalDate data_fim = LocalDate.parse(scan.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Evento.criarEvento(nome, descricao,data_inicio,data_fim);
        System.out.println("Evento Criado com sucesso.");
    }

    public static void cadastrarAtividade() throws SQLException, ClassNotFoundException, InformacoesInvalidasException {
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        System.out.println("Digite o nome da atividade:");
        String nome = scan.nextLine();
        System.out.println("Digite a descrição da atividade:");
        String descricao = scan.nextLine();
        System.out.println("Digite a data da atividade:");
        LocalDate data = LocalDate.parse(scan.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("Digite o tipo da atividade (palestra, simpósio, curso etc.):");
        String tipo_atividade = scan.nextLine();
        System.out.println("Digite o limite de inscrições da atividade:");
        int limite_inscricao = scan.nextInt();

        TarefasAdminDB.AdicionarAtividade(nome, descricao,eventoId,data,limite_inscricao,tipo_atividade);
        System.out.println("Atividade adicionada com sucesso!");

    }

    public static void  confirmarStatusPag() throws SQLException, ClassNotFoundException, InformacoesInvalidasException {
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        List<Participante> pPendentes = TarefasAdminDB.ListarParticipantesAguardandoConfirmacao(eventoId);

        System.out.println("Selecione um Participante:");

        for(Participante part :pPendentes){
            System.out.println(String.format("%s - %s", part.getId(), part.getNome()));
        }

        int participanteId = Integer.parseInt(scan.nextLine());
        TarefasAdminDB.confirmarPagamento(eventoId, participanteId);
        System.out.println("Pagamento confirmado");




    }

    public static void menuEditarEvento() throws SQLException, ClassNotFoundException, InformacoesInvalidasException {
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        System.out.println("Digite o nome do evento:");
        String nome = scan.nextLine();
        System.out.println("Digite a descrição do evento:");
        String descricao = scan.nextLine();
        System.out.println("Digite a data de inicio do evento:");
        LocalDate data_inicio = LocalDate.parse(scan.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("Digite a data do fim do evento:");
        LocalDate data_fim = LocalDate.parse(scan.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        TarefasAdminDB.editarEvento(eventoId, nome, descricao, data_inicio, data_fim);
        System.out.println("Evento Editado");

    }

    public static void excluirEvento() throws SQLException, ClassNotFoundException, InformacoesInvalidasException {
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        TarefasAdminDB.excluirEvento(eventoId);
        System.out.println("Evento excluído com sucesso!");
    }

    public static void painelAdministrativo() throws SQLException, ClassNotFoundException {
        int op = 0;
        do {
            Scanner scan = new Scanner(System.in);

            System.out.println("----------------------");
            System.out.println("PAINEL ADMINISTRATIVO");
            System.out.println("----------------------");
            System.out.println("1 - Visualizar os eventos.");//feito
            System.out.println("2 - Consultar Participantes");//feito
            System.out.println("3 - Visualizar o status de pagamento do participante.");//feito
            System.out.println("4 - Gerenciar valores");
            System.out.println();
            System.out.println("0 - Sair");

            op = Integer.parseInt(scan.nextLine());

            switch (op){
                case 1:
                    ListarEventos();
                    break;
                case 2:
                    ConsultarParticipantes();
                    break;
                case 3:
                    VizualizaStatusPagamento();
                    break;
                case 4:
                    alterarValorPagamento();
                    break;

            }

        } while(op != 0);
    }

    public static  void ListarEventos() throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Eventos:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }
    }

    public static void ConsultarParticipantes() throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());
        List<Participante> participantes = TarefasAdminDB.ListarParticipantes(eventoId);

        for(Participante e :participantes){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }
    }

    public static void VizualizaStatusPagamento() throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());
        List<Participante> participantes = TarefasAdminDB.ListarParticipantes(eventoId);

        for(Participante e :participantes){
            System.out.println(String.format("%s - %s, Status de pagamento: %s", e.getId(), e.getNome(), e.coletarStatusDePagamento(eventoId)));
        }

    }

    public static void alterarValorPagamento() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Escolha um tipo de participante-");
        System.out.println("1 - " + PapelUsuario.ALUNO.name());
        System.out.println("2 - " + PapelUsuario.PROFESSOR.name());
        System.out.println("3 - " + PapelUsuario.PROFISSIONAL.name());

        int papel = Integer.parseInt(scan.nextLine());

        System.out.println("Agora digite o valor para esse tipo de participante:");
        Double valor = Double.parseDouble(scan.nextLine());

        switch (papel){
            case 1:
                Participante.setValorAluno(valor);
                break;
            case 2:
                Participante.setValorProfessor(valor);
                break;
            case 3:
                Participante.setValorProfissional(valor);
                break;
            default:
                System.out.println("Papel Inválido!");
                return;
        }

        System.out.println("Valor alterado com sucesso!");


    }
}