package Exceptions;
import java.sql.SQLException;

public class ConexaoBancoException extends SQLException{
    public ConexaoBancoException (String mensagem){
        super(mensagem);
    }
}
