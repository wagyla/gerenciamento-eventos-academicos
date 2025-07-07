package pessoas;

import java.util.List;
import java.util.MissingFormatArgumentException;

public class Principal {
    public static PessoaDao pessoaDao = new PessoaDaoArquivo();

    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        int op = 0;
        Entrada entrada = Entrada.getInstance();
        do {
            System.out.println("MENU PRINCIPAL");
            System.out.println("--------------");
            System.out.println("1 - Listar");
            System.out.println("2 - Cadastrar");
            System.out.println("3 - Pesquisar");
            System.out.println("4 - Remover");
            System.out.println();
            System.out.println("0 - Sair");

            op = Integer.valueOf(entrada.lerString());

            switch (op){
                case 1:
                    listar();
                    break;
                case 2:
                    cadastrar();
            }

        } while(op != 0);
    }

    public static void cadastrar(){
        while (true) {
            Entrada entrada = Entrada.getInstance();

            System.out.println("Cadastro de Pessoa");
            System.out.print("Informe o nome: ");
            String nome = entrada.lerString();

            System.out.print("Informe o CPF: ");
            String cpf = entrada.lerString();

            try {
                Pessoa nova = new Pessoa(nome, cpf);
                pessoaDao.adicionar(nova);
                return;
            } catch (RuntimeException ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
        }
    }

    public static void listar(){
        System.out.println("Lista de Pessoas");
        List<Pessoa> pessoas = pessoaDao.listar();
        for(Pessoa pessoa: pessoas){
            System.out.println(pessoa);
        }
    }
}
