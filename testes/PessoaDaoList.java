package pessoas;

import java.util.ArrayList;
import java.util.List;

public class PessoaDaoList implements PessoaDao{
    private static List<Pessoa> pessoas = new ArrayList<>();


    @Override
    public Pessoa adicionar(Pessoa pessoa) {
        if(this.pesquisar(pessoa.getCpf()) == null) {
            pessoas.add(pessoa);
        } else {
            throw new CPFCadastradoException(pessoa.getCpf());
        }
        return pessoa;
    }

    @Override
    public List<Pessoa> listar() {
        return pessoas;
    }

    @Override
    public Pessoa pesquisar(String cpf) {
        for(Pessoa pessoa : pessoas){
            if(pessoa.getCpf().equals(cpf)){
                return pessoa;
            }
        }
        return null;
    }

    @Override
    public void remover(String cpf) {
        Pessoa pessoa = this.pesquisar(cpf);
        if(pessoa!=null){
            pessoas.remove(pessoa);
        }
    }
}
