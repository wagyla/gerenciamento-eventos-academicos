package pessoas;

import java.io.IOException;
import java.util.List;

public interface PessoaDao {
    Pessoa adicionar(Pessoa pessoa) throws IOException;
    List<Pessoa> listar() throws IOException;
    void remover(String cpf);
    Pessoa pesquisar(String cpf);
}
