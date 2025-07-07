package Classes;

import Exceptions.SenhaIncorretaException;

public interface UsuarioInterface {
    public boolean comparePassword(String password);
    public static Usuario login(String email, String senha) throws SenhaIncorretaException {
        return null;
    }
}
