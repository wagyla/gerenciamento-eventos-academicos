package DAO;

import Classes.Participante;
import java.io.IOException;
import java.util.List;

public interface AtividadeDAO {
    Participante adicionar(Participante participante)throws IOException;
    List<Participante> listar() throws IOException;
    void remover(int id);
}
