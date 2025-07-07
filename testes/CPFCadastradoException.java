package pessoas;

public class CPFCadastradoException extends RuntimeException {
    public CPFCadastradoException(String cpf) {
        super("CPF já cadastrado: " + cpf);
    }
}
