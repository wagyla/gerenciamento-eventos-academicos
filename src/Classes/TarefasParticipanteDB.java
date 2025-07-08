package Classes;

import Conexao.Conexao;
import ENUM.StatusPagamento;
import Exceptions.ConexaoBancoException;
import Exceptions.StatusPagamentoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class TarefasParticipanteDB {
    public static List<Evento> ListarEventosParticipando(int idParticipante) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();

        cx.conectar();
        List<Evento> eventos = new ArrayList();

        try{
            String query = "select * from eventos where id in (select id_evento from inscritos_evento where id_usuario = ?)";
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setInt(1, idParticipante);
            ResultSet resultado = st.executeQuery();
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
            throw new ConexaoBancoException("Erro ao conectar com banco de dados! "+er.getMessage());
        }finally {
            cx.fechar();
        }

        return eventos;
    }

    public static List<Evento> ListarEventosNaoParticipando(int idParticipante) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        List<Evento> eventos = new ArrayList();

        try{
            String query = "select * from eventos where id not in (select id_evento from inscritos_evento where id_usuario = ?)";
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setInt(1, idParticipante);
            ResultSet resultado = st.executeQuery();
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
            throw new ConexaoBancoException("Erro ao conectar com banco de dados! "+er.getMessage());
        }finally {
            cx.fechar();
        }

        return eventos;
    }

    public static void inscricaoEvento(int idParticipante, int idEvento) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();

        try{
            String insert = "insert into inscritos_evento values (?, ?, ?)";

            PreparedStatement st = cx.getConnection().prepareStatement(insert);
            st.setInt(1, idParticipante);
            st.setInt(2, idEvento);
            st.setString(3, StatusPagamento.PENDENTE.name());

            st.executeUpdate();
        }catch(ConexaoBancoException e){
            throw new ConexaoBancoException("Erro ao iniciar banco de dados! "+e.getMessage());
        }finally {
            cx.fechar();
        }
    }

    public static void inscricaoAtividades(int idParticipante, int idAtividade) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();

        try{
            String insert = "insert into inscritos_atividade values (?, ?)";

            PreparedStatement st = cx.getConnection().prepareStatement(insert);
            st.setInt(1, idParticipante);
            st.setInt(2, idAtividade);

            st.executeUpdate();
        }catch(ConexaoBancoException e){
            throw new ConexaoBancoException("Erro ao conectar com banco de dados! "+e.getMessage());
        }finally {
            cx.fechar();
        }
    }

    public static List<Atividade> ListarAtividadesParticipando(int idEvento, int idParticipante) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        List<Atividade> atividades = new ArrayList();

        try{
            String query = "select *  from atividades where evento_id = ? and id in (select id_atividade from inscritos_atividade where id_usuario = ?)";
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setInt(1, idEvento);
            st.setInt(2, idParticipante);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()){
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String descricao = resultado.getString("descricao");
                LocalDate data = LocalDate.parse(resultado.getString("data"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int limite_inscricao = resultado.getInt("limite_inscricao");
                String tipo_atividade = resultado.getString("tipo_atividade");

                Atividade e = new Atividade(id, nome, descricao, data, limite_inscricao,tipo_atividade);
                atividades.add(e);
            }

        }catch(ConexaoBancoException er){
//            Erro com banco
            throw new ConexaoBancoException("Erro ao conectar co banco de dados! "+er.getMessage());
        }finally {
            cx.fechar();
        }

        return atividades;
    }

    public static List<Atividade> ListarAtividadesNaoParticipando(int idEvento, int idParticipante) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        List<Atividade> atividades = new ArrayList();

        try{
            String query = "select *  from atividades where evento_id = ? and id not in (select id_atividade from inscritos_atividade where id_usuario = ?)";
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setInt(1, idEvento);
            st.setInt(2, idParticipante);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()){
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String descricao = resultado.getString("descricao");
                LocalDate data = LocalDate.parse(resultado.getString("data"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int limite_inscricao = resultado.getInt("limite_inscricao");
                String tipo_atividade = resultado.getString("tipo_atividade");

                Atividade e = new Atividade(id, nome, descricao, data, limite_inscricao,tipo_atividade);
                atividades.add(e);
            }

        }catch(ConexaoBancoException er){
//            Erro com banco
            throw new ConexaoBancoException("Erro ao conectar com banco de dados! "+er.getMessage());
        }finally {
            cx.fechar();
        }

        return atividades;
    }

    public static void SelecionaraPagamentosPendentes(int idParticipante) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        try{
            String query = "select * from eventos where id in (select id_evento from inscritos_evento where status_pagamento = ? and id_usuario = ?)";
            PreparedStatement ps = cx.getConnection().prepareStatement(query);
            ps.setString(1, StatusPagamento.PENDENTE.name());
            ps.setInt(2, idParticipante);

        } catch (ConexaoBancoException e) {
            throw new ConexaoBancoException("Erro ao conectar com banco de dados!"+e.getMessage());
        }finally {
            cx.fechar();
        }

    }
    public static void pagar(int idEvento, int idParticipante) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        try{
            String up = "UPDATE inscritos_evento" +
                    " SET status_pagamento = ?" +
                    " WHERE status_pagamento = ?" +
                    "  AND id_evento = ?" +
                    " and id_usuario = ?";

            PreparedStatement st = cx.getConnection().prepareStatement(up);
            st.setString(1, StatusPagamento.AGUARDANDO_CONFIRMACAO.name());
            st.setString(2, StatusPagamento.PENDENTE.name());
            st.setInt(3, idEvento);
            st.setInt(4, idParticipante);

            st.executeUpdate();
        } catch (ConexaoBancoException e) {
            throw new ConexaoBancoException("Erro ao conectar com banco de dados! "+e.getMessage());
        }
        finally {
            cx.fechar();
        }


    }

    public static List<Evento> ListarEventosPagPendente(int idParticipante) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        List<Evento> eventos = new ArrayList();

        try{
            String query = "select * from eventos where id in (select id_evento from inscritos_evento where id_usuario = ? and status_pagamento = ?)";
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setInt(1, idParticipante);
            st.setString(2, StatusPagamento.PENDENTE.name());

            ResultSet resultado = st.executeQuery();
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
            throw new ConexaoBancoException("Erro ao conectar com banco de dados! "+er.getMessage());
        }finally {
            cx.fechar();
        }

        return eventos;
    }

    public static StatusPagamento coletarStatusPagamento(int idParticipante, int idEvento) throws ClassNotFoundException, SQLException, StatusPagamentoException{
        Conexao cx = new Conexao();
        cx.conectar();
        StatusPagamento st = null;
        try{
            String query = "select status_pagamento from inscritos_evento where id_usuario = ? and id_evento = ?;";
            PreparedStatement ps = cx.getConnection().prepareStatement(query);
            ps.setInt(1, idParticipante);
            ps.setInt(2, idEvento);

            ResultSet resultado = ps.executeQuery();
            if(resultado.next()){
                st =  StatusPagamento.valueOf(resultado.getString("status_pagamento"));
            }
        } finally {
            cx.fechar();
        }

        return st;
    }

    public static void cancelarInscricao(int idParticipante, int idEvento) throws ClassNotFoundException, SQLException, ConexaoBancoException{
        Conexao cx = new Conexao();
        cx.conectar();
        try{
            String query = "delete from inscritos_evento where id_usuario = ? and id_evento = ?;";
            PreparedStatement ps = cx.getConnection().prepareStatement(query);
            ps.setInt(1, idParticipante);
            ps.setInt(2, idEvento);

            ps.executeUpdate();

        } catch (ConexaoBancoException e) {
            throw new SQLException("Erro ao conectar com banco de dados! "+e.getMessage());
        }finally{
            cx.fechar();
        }

    }
}
