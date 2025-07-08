import Classes.Administrador;
import Classes.Participante;
import ENUM.PapelUsuario;
import Exceptions.InformacoesInvalidasException;
import Exceptions.SenhaIncorretaException;
import Exceptions.StatusPagamentoException;
import Exceptions.UsuarioInvalido;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuController {
    public static void menu() throws SenhaIncorretaException, SQLException, ClassNotFoundException, InformacoesInvalidasException, UsuarioInvalido, StatusPagamentoException {
        int op = 0;
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("--------------");
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

    public static void cadastrar() throws SQLException, ClassNotFoundException, UsuarioInvalido {
        Scanner scan = new Scanner(System.in);
        System.out.println("---------");
        System.out.println("CADASTRO:");
        System.out.println("---------");
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

    public static void login() throws SenhaIncorretaException, SQLException, ClassNotFoundException, UsuarioInvalido {
        Scanner scan = new Scanner(System.in);
        System.out.println("------");
        System.out.println("LOGIN:");
        System.out.println("------");
        System.out.println("Digite o email:");
        String email = scan.nextLine();
        System.out.println("Digite a senha:");
        String senha = scan.nextLine();

        Participante p = (Participante) Participante.login(email, senha);
        System.out.println("Nome do Participante: ");
        System.out.println(p.getNome());
        MenuParticipante.areaParticipante(p);
    }

    public static void loginComoAdmin() throws SQLException, ClassNotFoundException, InformacoesInvalidasException, StatusPagamentoException, UsuarioInvalido {
        Scanner scan = new Scanner(System.in);
        System.out.println("---------------------");
        System.out.println("LOGIN ADIMINISTRADOR:");
        System.out.println("---------------------");
        System.out.println("Digite o email:");
        String email = scan.nextLine();
        System.out.println("Digite a senha:");
        String senha = scan.nextLine();

        Administrador a = (Administrador) Administrador.login(email,senha);
        System.out.println("Nome do Administrador: ");
        System.out.println(a.getNome());

        MenuAdministrador.menuAdmin();
    }
}
