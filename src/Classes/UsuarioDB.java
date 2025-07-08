package Classes;

import Conexao.Conexao;
import ENUM.PapelUsuario;
import Exceptions.UsuarioInvalido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDB {

    public static Participante selecionarParticipantePorEmail(String email) throws ClassNotFoundException, SQLException, UsuarioInvalido{
        Conexao cx = new Conexao();
        cx.conectar();
        Participante p = null;
        try{
            String codigoSql = "select * from usuarios where email = ? and papel != 'ADMINISTRADOR'";

            PreparedStatement st = cx.getConnection().prepareStatement(codigoSql);
            st.setString(1, email);

            ResultSet resultado = st.executeQuery();
            
            if (resultado.next()){
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String senha = resultado.getString("senha");
                String papel = resultado.getString("papel");
                
                p = new Participante(id, email, nome, senha, PapelUsuario.valueOf(papel));
            }else{
//                Não temos o usuario, Exceção usuario não encontrado
            }

        }catch(UsuarioInvalido e){
            throw new UsuarioInvalido("Usuário de senha não correspondem! "+e.getMessage());
            //  Fazer tratamento de exceção, Usuario não encontrado
            //  Possivel nome: Usuario e senha não correspondem!
        }finally {
            cx.fechar();
        }

        return p;
    }

    public static void cadastrarParticipante (String email,String nome,String senha, PapelUsuario papel) throws ClassNotFoundException, SQLException, UsuarioInvalido{
        Conexao cx = new Conexao();
        cx.conectar();
        try{
            String codigoSql = "INSERT INTO usuarios (email, nome, senha, papel) values (?,?,?,?)";

            PreparedStatement st = cx.getConnection().prepareStatement(codigoSql);
            st.setString(1, email);
            st.setString(2, nome);
            st.setString(3, senha);
            st.setString(4, papel.name());

            st.executeUpdate();
        }catch(UsuarioInvalido e){
            throw new UsuarioInvalido("Usuário já cadastrado! "+e.getMessage());
            //Erro Usuario ja cadastrado
        }finally {
            cx.fechar();
        }
    }

    public static Administrador selecionarAdminPorEmail(String email) throws ClassNotFoundException, SQLException, UsuarioInvalido{
        Conexao cx = new Conexao();
        cx.conectar();
        Administrador adm = null;

        try{


            String codigoSql = "select * from usuarios where email = ? and papel = 'ADMINISTRADOR'";

            PreparedStatement st = cx.getConnection().prepareStatement(codigoSql);
            st.setString(1, email);

            ResultSet resultado = st.executeQuery();

            if (resultado.next()){
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String senha = resultado.getString("senha");
                String papel = resultado.getString("papel");

                adm = new Administrador(id, email, nome, senha, PapelUsuario.valueOf(papel));
            }else{
                throw new UsuarioInvalido("Usuário não encontrado.");

            }

        }catch(UsuarioInvalido e){
            throw new UsuarioInvalido("Usuário e senha não correspondem! "+e.getMessage());
            //  Fazer tratamento de exceção, Usuario não enconterado
            //  Possivel nome: Usuario e senha não correspondem!
        }finally {
            cx.fechar();
        }
        return adm;
    }


}
