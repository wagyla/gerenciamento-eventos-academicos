import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class Data {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ano de nascimento (ex: 2000): ");
        int ano = scanner.nextInt();

        System.out.print("Digite o mês de nascimento (1 a 12): ");
        int mes = scanner.nextInt();

        System.out.print("Digite o dia de nascimento: ");
        int dia = scanner.nextInt();

        LocalDate dataNascimento = LocalDate.of(ano, mes, dia);
        LocalDate dataAtual = LocalDate.now();

        Period idade = Period.between(dataNascimento, dataAtual);

        System.out.println("Você tem " + idade.getYears() + " anos.");

        scanner.close();
    }
}
