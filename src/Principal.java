import Conexao.Conexao;
import Exceptions.SenhaIncorretaException;

public class Principal {

    public static void main(String[] args) {
         Conexao conexao = new Conexao();
//          conexao.initBD();
         try{
            MenuController.menu();
        } catch (SenhaIncorretaException e) {
            throw new RuntimeException(e);
        }
    }








}


