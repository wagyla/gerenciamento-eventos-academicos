package Classes;

import ENUM.PapelUsuario;

public class Administrador extends Usuario {

    public Administrador (String nome,String email,String senha, PapelUsuario papel){
        super(nome,email,senha, papel);
    }

    public Administrador (int id, String nome,String email,String senha, PapelUsuario papel){
        super(id, nome,email,senha, papel);
    }

    public static Usuario login(String email, String senha){
        Administrador admin = UsuarioDB.selecionarAdminPorEmail(email);

        if(admin.comparePassword(senha)){
            return admin;
        }else{
//           Exceção de Senha incorreta
            return null;
        }
    }




    @Override
    public void visualizarEventos() {
        System.out.println("listar os eventos");
    }
}
