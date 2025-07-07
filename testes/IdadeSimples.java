import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class IdadeSimples {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite sua data de nascimento (dia mês ano): ");
        int dia = scanner.nextInt();
        int mes = scanner.nextInt();
        int ano = scanner.nextInt();

        LocalDate dataNascimento = LocalDate.of(ano, mes, dia);
        LocalDate hoje = LocalDate.now();

        Period idade = Period.between(dataNascimento, hoje);

        System.out.println("Você tem " + idade.getYears() + " anos.");

        scanner.close();
    }
}
