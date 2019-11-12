/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DAOs.util.conectaBanco;
import Interfaces.iDAO;
import Modelo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mr.Glass
 */
public class EnderecoDAO implements iDAO {

    private Connection conexao;

    public EnderecoDAO() {
        this.conexao = conectaBanco.getConexao();
    }

    public EnderecoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {

        Mensagem respostaDAO = new Mensagem();

        try {
            Endereco endereco = (Endereco) entidade;

            String sql = "INSERT INTO endereco (complemento, logradouro, numero, cidade, estado, pais) VALUES (?, ?, ?, ?, ?, ?) returning id;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setString(1, endereco.getComplemento());
            pstmt.setString(2, endereco.getLogradouro());
            pstmt.setString(3, endereco.getNumero());
            pstmt.setString(4, endereco.getCidade().getNome());
            pstmt.setString(5, endereco.getCidade().getEstado().getNome());
            pstmt.setString(6, endereco.getCidade().getEstado().getPais().getNome());

            ResultSet rs = pstmt.executeQuery();
            int id_endereco = 0;
            while (rs.next()) {
                id_endereco = rs.getInt("id");
            }

            conexao.close();

            endereco.setId(id_endereco);

            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados cadastrados com sucesso !!!");
            respostaDAO.setDados(endereco);

        } catch (SQLException erro) {

            System.out.println("ERRO --> " + erro.getMessage());

            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao cadastrar Dados !!!");
        }

        return respostaDAO;
    }

    @Override
    public Mensagem editar(EntidadeDominio entidade) {
        Mensagem respostaDAO = new Mensagem();

        try {
            Endereco endereco = (Endereco) entidade;

            String sql = "UPDATE endereco SET complemento = ?, logradouro = ?, numero = ?, cidade = ?, estado = ?, pais = ?;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setString(1, endereco.getComplemento());
            pstmt.setString(2, endereco.getLogradouro());
            pstmt.setString(3, endereco.getNumero());
            pstmt.setString(4, endereco.getCidade().getNome());
            pstmt.setString(5, endereco.getCidade().getEstado().getNome());
            pstmt.setString(6, endereco.getCidade().getEstado().getPais().getNome());

            pstmt.execute();
            
            conexao.close();

            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Endereco atualizado com sucesso !!!");
            respostaDAO.setDados(endereco);

        } catch (SQLException erro) {

            System.out.println("ERRO --> " + erro.getMessage());

            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao atualizar Endereco !!!");
        }

        return respostaDAO;
    }

    @Override
    public Mensagem excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensagem consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Mensagem consultar(int id_endereco) {

        Mensagem respostaDAO = new Mensagem();

        try {
            
            String sql = "SELECT * FROM endereco WHERE id = ?";
            
            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setInt(1, id_endereco);
            
            ResultSet rs = pstmt.executeQuery();
            Endereco endereco = null;
            while (rs.next()) {
                endereco = new Endereco();
                endereco.setId(rs.getInt("id"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getString("numero"));
                
                Pais pais = new Pais();
                pais.setNome(rs.getString("pais"));
                
                Estado estado = new Estado();
                estado.setNome(rs.getString("estado"));
                estado.setPais(pais);
                
                Cidade cidade = new Cidade();
                cidade.setNome(rs.getString("cidade"));
                cidade.setEstado(estado);
                
                endereco.setCidade(cidade);
            }

            conexao.close();
            
            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados encontrados com sucesso !!!");
            respostaDAO.setDados(endereco);
            
        } catch (SQLException error) {
            
            System.out.println(error.getMessage());
            
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao buscar dados de endereco");
        }

        return respostaDAO;
    }
}
