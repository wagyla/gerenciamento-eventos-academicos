package Classes;

import DAO.Conexao;
import ENUM.PapelUsuario;
import ENUM.StatusPagamento;
import Exceptions.ConexaoBancoException;
import Exceptions.InformacoesInvalidasException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TarefasAdminDB {

    public static void criarEvento(String nome, String descricao, LocalDate data_inicio,LocalDate data_fim) throws InformacoesInvalidasException, ClassNotFoundException, SQLException{
        Conexao cx = new Conexao();
        cx.conectar();
        try{
            String criarEvento = """
                insert into eventos(nome, descricao, data_inicio, data_fim) values
                (?, ?, ?, ?)
                """;

            PreparedStatement st = cx.getConnection().prepareStatement(criarEvento);
            st.setString(1, nome);
            st.setString(2, descricao);
            st.setString(3, data_inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            st.setString(4, data_fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            st.executeUpdate();
        }catch(Exception e){
            throw new InformacoesInvalidasException("Informações Inválidas! Erro: "+e.getMessage());
//            Informações invalidas!

        }finally {
            cx.fechar();
        }

    }


    public static void AdicionarAtividade(String nome, String descricao,int evento_id, LocalDate data, int limite_inscricao,String tipo_atividade) throws InformacoesInvalidasException, ClassNotFoundException, SQLException{
        Conexao cx = new Conexao();
        cx.conectar();
        try{

            String AdicionarAtividade = """
                insert into atividades(nome, descricao,evento_id, data, limite_inscricao,tipo_atividade) values
                (?, ?, ?, ?, ?, ?)
                """;

            PreparedStatement st = cx.getConnection().prepareStatement(AdicionarAtividade);
            st.setString(1, nome);
            st.setString(2, descricao);
            st.setInt(3, evento_id);
            st.setString(4, data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            st.setInt(5, limite_inscricao);
            st.setString(6, tipo_atividade);

            st.executeUpdate();
        }catch(Exception e){
            throw new InformacoesInvalidasException("Informações de atividade invalidas! Erro: "+e.getMessage());
        }finally{
            cx.fechar();
        }

    }

    public static List<Evento> ListarEventos() throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        List<Evento> eventos = new ArrayList();

        try{
            String query = "select * from eventos";
            Statement st = cx.getConnection().createStatement();
            ResultSet resultado = st.executeQuery(query);
            while (resultado.next()){
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String descricao = resultado.getString("descricao");
                LocalDate data_inicio = LocalDate.parse(resultado.getString("data_inicio"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate data_fim = LocalDate.parse(resultado.getString("data_fim"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Evento e = new Evento(id, nome, descricao, data_inicio, data_fim);
                eventos.add(e);
            }

        }catch(ConexaoBancoException er){
//            Erro com banco
            throw new ConexaoBancoException("Erro ao conectar banco de dados! ");
        }finally {
            cx.fechar();
        }

        return eventos;
    }
    public static void editarEvento(int id, String nome, String descricao, LocalDate data_inicio,LocalDate data_fim) throws InformacoesInvalidasException, ClassNotFoundException, SQLException{
        Conexao cx = new Conexao();
        cx.conectar();
        try{

            String editarEvento = """
                update eventos 
                set 
                    nome = ?,
                    descricao = ?,
                    data_inicio = ?,
                    data_fim = ?
                    where id = ?
                """;

            PreparedStatement st = cx.getConnection().prepareStatement(editarEvento);
            st.setString(1, nome);
            st.setString(2, descricao);
            st.setString(3, data_inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            st.setString(4, data_fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            st.setInt(5, id);

            st.executeUpdate();
        }finally {
            cx.fechar();
        }

    }

    public static void excluirEvento(int id) throws InformacoesInvalidasException, ClassNotFoundException, SQLException, InformacoesInvalidasException{
        Conexao cx = new Conexao();
        cx.conectar();
        try{

            String excluirInscritos_atividade = """
            DELETE FROM inscritos_atividade
            WHERE id_atividade IN (
                SELECT id FROM atividades WHERE evento_id = ?
            );

                """;

            String excluirInscritos_evento = """
            DELETE FROM inscritos_evento
            WHERE id_evento = ?;
                    """;

            String excluirAtividades_evento = """
            DELETE FROM atividades
            WHERE evento_id = ?;
                    """;

            String excluirEvento = """
            DELETE FROM eventos
            WHERE id = ?;
                    """;

            PreparedStatement st = cx.getConnection().prepareStatement(excluirInscritos_atividade);
            PreparedStatement stm = cx.getConnection().prepareStatement(excluirInscritos_evento);
            PreparedStatement sm = cx.getConnection().prepareStatement(excluirAtividades_evento);
            PreparedStatement s = cx.getConnection().prepareStatement(excluirEvento);

            st.setInt(1, id);
            stm.setInt(1, id);
            sm.setInt(1,id);
            s.setInt(1,id);

            st.executeUpdate();
            stm.executeUpdate();
            sm.executeUpdate();
            s.executeUpdate();

        }catch(InformacoesInvalidasException e){
            throw new InformacoesInvalidasException("Informações inválidas: "+e.getMessage());
//            Informações invalidas!

        }finally {
            cx.fechar();
        }

    }

    public static void confirmarPagamento(int idEvento, int idParticipante) throws ClassNotFoundException, SQLException, InformacoesInvalidasException{
        Conexao cx = new Conexao();
        cx.conectar();

        try{
            String up = "update inscritos_evento " +
                    " set " +
                    " status_pagamento = ?" +
                    " where id_evento = ? and " +
                    " id_usuario = ? and" +
                    " status_pagamento = ? ";

            PreparedStatement ps = cx.getConnection().prepareStatement(up);
            ps.setString(1, StatusPagamento.PAGO.name());
            ps.setInt(2, idEvento);
            ps.setInt(3, idParticipante);
            ps.setString(4, StatusPagamento.AGUARDANDO_CONFIRMACAO.name());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            cx.fechar();
        }

    }

    public static List<Participante> ListarParticipantesAguardandoConfirmacao(int idEvento) throws ClassNotFoundException, SQLException, InformacoesInvalidasException{
        Conexao cx = new Conexao();
        cx.conectar();
        List<Participante> participantes = new ArrayList();
        try{
            String query = "select * from usuarios where id in (select id_usuario from inscritos_evento where id_evento = ? and status_pagamento = ?)";
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setInt(1, idEvento);
            st.setString(2, StatusPagamento.AGUARDANDO_CONFIRMACAO.name());

            ResultSet resultado = st.executeQuery();

            while (resultado.next()){
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String email = resultado.getString("email");
                String senha = resultado.getString("senha");
                String papel = resultado.getString("papel");

                participantes.add(new Participante(id, email, nome, senha, PapelUsuario.valueOf(papel)));
            }
        }catch (Exception err){
            System.out.println(err.getMessage()); // nao entendi qual é esse erro
        }finally {
            cx.fechar();
        }

        return participantes;
    }
}
