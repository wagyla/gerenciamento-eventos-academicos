package pessoas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PessoaDaoArquivo implements PessoaDao {
    private final String nomeArquivo = "pessoas.txt";

    public PessoaDaoArquivo() {
        try {
            File arquivo = new File(nomeArquivo);
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                System.out.println("Arquivo criado!");
            }
        } catch (IOException ex){
            System.out.println("Não foi possível criar o arquivo!");
        }
    }

    @Override
    public Pessoa adicionar(Pessoa pessoa) throws IOException {
        List<Pessoa> pessoas = listar();
        for(Pessoa p: pessoas){
            if(p.getCpf().equals(pessoa.getCpf())){
                throw new CPFCadastradoException(pessoa.getCpf());
            }
        }
        pessoas.add(pessoa);
        this.salvar(pessoas);
        return pessoa;
    }

    private void salvar(List<Pessoa> pessoas) throws IOException {
        List<String> linhas = new ArrayList<>();
        for(Pessoa p:pessoas){
            linhas.add(p.getNome()+","+p.getCpf());
        }
        Files.write(Paths.get(nomeArquivo), linhas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public List<Pessoa> listar() throws IOException {
        List<Pessoa> pessoas = new ArrayList<>();
        try {
            List<String> linhas = Files.readAllLines(Paths.get("pessoas.txt"));
            for (String linha : linhas) {
                String[] partes = linha.split(",");
                String nome = partes[0];
                String cpf = partes[1];

                Pessoa pessoa = new Pessoa(nome, cpf);
                pessoas.add(pessoa);
            }
        } catch (RuntimeException ex){
            throw new IOException("Erro ao ler arquivo: " + ex.getMessage());
        }

        return pessoas;
    }

    @Override
    public void remover(String cpf) {
        
    }

    @Override
    public Pessoa pesquisar(String cpf) {
        return null;
    }
}
