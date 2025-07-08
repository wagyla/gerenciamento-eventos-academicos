import Conexao.Conexao;
import Exceptions.InformacoesInvalidasException;
import Exceptions.SenhaIncorretaException;

import java.sql.SQLException;

public class Principal {

    public static void main(String[] args) {
         Conexao conexao = new Conexao();
//          conexao.initBD();
         try{
            MenuController.menu();
        } catch (SenhaIncorretaException | SQLException | ClassNotFoundException | InformacoesInvalidasException e) {
            throw new RuntimeException(e);
        }
    }
}


