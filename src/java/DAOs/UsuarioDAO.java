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
public class UsuarioDAO implements iDAO {
    
    private Connection conexao;
    
    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    public UsuarioDAO() {
        this.conexao = conectaBanco.getConexao();
    }

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {
        
        Mensagem respostaDAO = new Mensagem();
        
        try {
            
            Usuario usuario = (Usuario) entidade;
            
            
            String sql = "INSERT INTO usuario (login, senha, tipo) VALUES (?, ?, ?) returning id;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getTipo());
            
            
            ResultSet rs = pstmt.executeQuery();
            int id_usuario = 0;
            while (rs.next()) {
                id_usuario = rs.getInt("id");
            }
            
            conexao.close();
            
            usuario.setId(id_usuario);
            
            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados cadastrados com sucesso !!!");
            respostaDAO.setDados(usuario);
        }catch(SQLException erro) {
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao cadastrar Dados !!!");
        }

       
        return respostaDAO;
    }

    @Override
    public Mensagem editar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensagem excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensagem consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Mensagem consultar(Usuario usuario) {
        
        Mensagem respostaDAO = new Mensagem();
        
        try {
         
            String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?;";
            
            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, usuario.getSenha());
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setTipo(rs.getString("tipo"));
            }
            
            this.conexao.close();
            
            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados encontrados com sucesso !!!");
            respostaDAO.setDados(usuario);
            
        } catch(SQLException error) {
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem(error.getMessage());
        }
        
        return respostaDAO;
    }
    
    public Mensagem buscarEntidadePai(Usuario usuario) {
        
        Mensagem respostaDAO = new Mensagem();
        
        try {
            
            String sql = "SELECT id FROM " + usuario.getTipo() + " WHERE id_usuario = ?";
            
            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setInt(1, usuario.getId());
            
            System.out.println(pstmt);
            
            
            ResultSet rs = pstmt.executeQuery();
            EntidadeDominio entidadePai = null;
            while(rs.next()) {
                entidadePai = new EntidadeDominio();
                entidadePai.setId(rs.getInt("id"));
            }
            
            conexao.close();
            
            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Entidade pai encontrada com sucesso !!!");
            respostaDAO.setDados(entidadePai);
            
        } catch(SQLException error) {
            System.out.println("Erro ao buscar entidade Pai -- >" + error.getMessage());
            
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao buscar Entidade Pai");
        }
        
        return respostaDAO;
    }

    @Override
    public Mensagem consultar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
