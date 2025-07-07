import Classes.*;
import DAO.Conexao;
import ENUM.PapelUsuario;
import Exceptions.SenhaIncorretaException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
         Conexao conexao = new Conexao();
         conexao.initBD();
         menu();
    }

    public static void menu(){
        int op = 0;
        do {
            Scanner scan = new Scanner(System.in);

            System.out.println("MENU PRINCIPAL");
            System.out.println("--------------");
            System.out.println("1 - Login");
            System.out.println("2 - Login como Admin");
            System.out.println("3 - Cadastrar");
            System.out.println();
            System.out.println("0 - Sair");

            op = Integer.parseInt(scan.nextLine());

            switch (op){
                case 1:
                    login();
                    break;
                case 2:
                    loginComoAdmin();
                    break;
                case 3:
                    cadastrar();
            }

        } while(op != 0);
    }

    public static void cadastrar(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o email:");
        String email = scan.nextLine();
        System.out.println("Digite o seu nome:");
        String nome = scan.nextLine();
        System.out.println("Digite a senha:");
        String senha = scan.nextLine();
        System.out.println("Digite o seu papel:");
        System.out.println("1 - " + PapelUsuario.ALUNO.name());
        System.out.println("2 - " + PapelUsuario.PROFESSOR.name());
        System.out.println("3 - " + PapelUsuario.PROFISSIONAL.name());

        int papel = Integer.parseInt(scan.nextLine());
        PapelUsuario papelSelecionado = null;

        switch (papel){
            case 1:
                papelSelecionado = PapelUsuario.ALUNO;
                break;
            case 2:
                papelSelecionado = PapelUsuario.PROFESSOR;
                break;
            case 3:
                papelSelecionado = PapelUsuario.PROFISSIONAL;
                break;
            default:
                System.out.println("Papel Inválido!");
                return;
        }
        Participante.cadastrar(email,nome,senha,papelSelecionado);
        System.out.println("Cadastrado com sucesso!!!!!");
    }

    public static void login() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o email:");
        String email = scan.nextLine();
        System.out.println("Digite a senha:");
        String senha = scan.nextLine();
        try{
            Participante p = (Participante) Participante.login(email, senha);
            System.out.println(p.getNome());
            areaParticipante(p); 
        } catch (SenhaIncorretaException e){
            System.out.println("Erro: " + e.getMessage());
        } 


    }

    public static void loginComoAdmin(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o email:");
        String email = scan.nextLine();
        System.out.println("Digite a senha:");
        String senha = scan.nextLine();

        try{
            Administrador a = (Administrador) Administrador.login(email,senha);
            System.out.println(a.getNome());
            menuAdmin();
        }catch (SenhaIncorretaException e){
            System.out.println("Erro de login: "+e.getLocalizedMessage());
        }catch (Exception e){
            System.out.println("Ocorreu um erro inesperado durante o login: "+e.getMessage());
        }
        
        

        menuAdmin();

    }
    public static void menuAdmin(){
        int op = 0;
        do {
            Scanner scan = new Scanner(System.in);

            System.out.println("MENU DO ADMINISTRADOR");
            System.out.println("--------------");
            System.out.println("1 - Criar Evento"); //feito
            System.out.println("2 - Editar Evento");//feito
            System.out.println("3 - Excluir Evento");//feito
            System.out.println("4 - Cadastrar Atividade"); //feito
            System.out.println("5 - Confirmar Pagamento");
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

    public static void cadastrarAtividade() {
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

    public static void  confirmarStatusPag(){
        Scanner scan = new Scanner(System.in);
        List<Evento> eventos = TarefasAdminDB.ListarEventos();
        System.out.println("Selecione um evento:");

        for(Evento e :eventos){
            System.out.println(String.format("%s - %s", e.getId(), e.getNome()));
        }

        int eventoId = Integer.parseInt(scan.nextLine());

        List<Participante> pPendentes = TarefasAdminDB.ListarParticipantesAguardandoConfirmacao(eventoId);

        System.out.println("Selecione um arrombado:");

        for(Participante part :pPendentes){
            System.out.println(String.format("%s - %s", part.getId(), part.getNome()));
        }

        int participanteId = Integer.parseInt(scan.nextLine());
        TarefasAdminDB.confirmarPagamento(eventoId, participanteId);
        System.out.println("Pagamento confirmado");




    }

    public static void menuEditarEvento(){
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

    public static void excluirEvento(){
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

        TarefasParticipanteDB.pagar(eventoId, p.getId());
        System.out.println("Pagamento feito");

    }

    public static void areaParticipante(Participante p){
        int op = 0;
        do {
            Scanner scan = new Scanner(System.in);

            System.out.println("ÁREA PESSOAL DO PARTICIPANTE");
            System.out.println("--------------");
            System.out.println("1 - Inscrever-se em um evento");//feito
            System.out.println("2 - Vizualizar Eventos inscritos");//feito
            System.out.println("3 - Inscrever-se em uma atividade");
            System.out.println("4 - Vizualizar Atividades inscritas");
            System.out.println("5 - Efetuar Pagamento");
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
                    efetuarPagamento(p);
                    break;
            }

        } while(op != 0);
    }

}


