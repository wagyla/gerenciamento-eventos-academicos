package Classes;

import ENUM.PapelUsuario;
import Exceptions.UsuarioInvalido;

import java.sql.SQLException;

public class Administrador extends Usuario implements UsuarioInterface {

    public Administrador (String nome,String email,String senha, PapelUsuario papel){
        super(nome,email,senha, papel);
    }

    public Administrador (int id, String nome,String email,String senha, PapelUsuario papel){
        super(id, nome,email,senha, papel);
    }

    public static Usuario login(String email, String senha) throws SQLException, ClassNotFoundException, UsuarioInvalido {
        Administrador admin = UsuarioDB.selecionarAdminPorEmail(email);

        if(admin.comparePassword(senha)){
            return admin;
        }else{
            return null;
        }
    }
}