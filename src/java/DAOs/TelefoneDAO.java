package DAOs;

import DAOs.util.conectaBanco;
import Interfaces.iDAO;
import Modelo.EntidadeDominio;
import Modelo.Mensagem;
import Modelo.Telefone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mr.Glass
 */
public class TelefoneDAO implements iDAO {

    private Connection conexao;

    public TelefoneDAO() {
        this.conexao = conectaBanco.getConexao();
    }

    public TelefoneDAO(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {
        
        Mensagem respostaDAO = new Mensagem();
        
        try {

            Telefone telefone = (Telefone) entidade;

            String sql = "INSERT INTO telefone(numero) VALUES (?) returning id;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            
            pstmt.setString(1, telefone.getNumero());

            ResultSet rs = pstmt.executeQuery();
            int id_telefone = 0;
            while (rs.next()) {
                id_telefone = rs.getInt("id");
            }
            
            conexao.close();

            telefone.setId(id_telefone);

            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados cadastrados com sucesso !!!");
            respostaDAO.setDados(telefone);
        } catch (SQLException erro) {
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao cadastrar dados !!!");
            
            System.out.print(erro.getMessage());
        }
        
        return respostaDAO;
    }

    @Override
    public Mensagem editar(EntidadeDominio entidade) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensagem excluir(int id) {
        
        Mensagem respostaDAO = new Mensagem();
        
        try {

            String sql = "DELETE FROM empresa_telefone WHERE id_telefone = ?";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            
            pstmt.setInt(1, id);

            pstmt.execute();
            
            sql = "DELETE FROM telefone WHERE id = ?";
            
            pstmt = this.conexao.prepareCall(sql);
            
            pstmt.setInt(1, id);

            pstmt.execute();
            
            conexao.close();


            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Telefone excluido com sucesso !!!");
        } catch (SQLException erro) {
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao excluir Telefone !!!");
            
            System.out.print(erro.getMessage());
        }
        
        return respostaDAO;
    }

    @Override
    public Mensagem consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Mensagem consultar(int id_telefone) {
        
        Mensagem respostaDAO = new Mensagem();
        
        
        try {
            
            String sql = "SELECT * FROM telefone WHERE id = ?";
            
            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setInt(1, id_telefone);
            
            ResultSet rs = pstmt.executeQuery();
            
            Telefone telefone = null;
            while(rs.next()) {
                telefone = new Telefone();
                telefone.setId(rs.getInt("id"));
                telefone.setNumero(rs.getString("numero"));
            }
            
            conexao.close();
            
            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados encontrados com Sucessoo !!!");
            respostaDAO.setDados(telefone);
        } catch (SQLException error) {
            
            System.out.println("Erro ao Buscar Telefone --> " + error.getMessage());
            
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao buscar dados de telefone !!!");
        }
        
        return respostaDAO;
    }
}
