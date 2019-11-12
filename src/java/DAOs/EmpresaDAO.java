package DAOs;

import DAOs.util.conectaBanco;
import DAOs.*;
import Interfaces.iDAO;
import Modelo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mr.Glass
 */
public class EmpresaDAO implements iDAO {

    private Connection conexao;

    public EmpresaDAO() {
        conexao = conectaBanco.getConexao();
    }

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {

        Mensagem respostaDAO = new Mensagem();

        try {

            Empresa empresa = (Empresa) entidade;

            Mensagem respostaUsuarioDAO = new UsuarioDAO().cadastrar(empresa.getUsuario());
            Mensagem respostaEnderecoDAO = new EnderecoDAO().cadastrar(empresa.getEndereco());

            if (respostaUsuarioDAO.getCodigo() == 0 && respostaEnderecoDAO.getCodigo() == 0) {

                empresa.setEndereco((Endereco) respostaEnderecoDAO.getDados());
                empresa.setUsuario((Usuario) respostaUsuarioDAO.getDados());

                String sql = "INSERT INTO empresa (faturamento, dre, nomeFantasia, razaoSocial, CNPJ, email, situacao, categoria, id_usuario, id_endereco) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id;";

                PreparedStatement pstmt = this.conexao.prepareCall(sql);
                pstmt.setString(1, String.valueOf(empresa.getFaturamento()));
                pstmt.setString(2, empresa.getDRE().getCamninho());
                pstmt.setString(3, empresa.getNomeFantasia());
                pstmt.setString(4, empresa.getRazaoSocial());
                pstmt.setString(5, empresa.getCNPJ());
                pstmt.setString(6, empresa.getEmail());
                pstmt.setString(7, empresa.getSituacao());
                pstmt.setString(8, empresa.getCategoria().getCategoria());
                pstmt.setInt(9, empresa.getUsuario().getId());
                pstmt.setInt(10, empresa.getEndereco().getId());

                ResultSet rs = pstmt.executeQuery();
                int id_empresa = 0;
                while (rs.next()) {
                    id_empresa = rs.getInt("id");
                }

                empresa.setId(id_empresa);

                for (int i = 0; i < empresa.getTelefones().length; i++) {
                    Mensagem respostaTelefoneDAO = new TelefoneDAO().cadastrar(empresa.getTelefones()[i]);
                    Telefone telefoneCadastrado = (Telefone) respostaTelefoneDAO.getDados();

                    sql = "INSERT INTO empresa_telefone (id_telefone, id_empresa) VALUES(?, ?);";

                    pstmt = this.conexao.prepareCall(sql);
                    pstmt.setInt(1, telefoneCadastrado.getId());
                    pstmt.setInt(2, empresa.getId());

                    pstmt.execute();
                }

                this.conexao.close();

                respostaDAO.setCodigo(0);
                respostaDAO.setMensagem("Dados da empresa cadastrados com sucesso !!!");
            }

        } catch (SQLException error) {

            System.out.println(error.getMessage());

            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao cadastrar a empresa !!!");
        }

        return respostaDAO;
    }

    @Override
    public Mensagem editar(EntidadeDominio entidade) {
        Mensagem respostaDAO = new Mensagem();

        try {

            Empresa empresa = (Empresa) entidade;

            Mensagem respostaEnderecoDAO = new EnderecoDAO().editar(empresa.getEndereco());

            if (respostaEnderecoDAO.getCodigo() == 0) {

                empresa.setEndereco((Endereco) respostaEnderecoDAO.getDados());

                String sql = "UPDATE empresa SET faturamento = ?, dre = ?, nomeFantasia = ?, razaoSocial = ?, CNPJ = ?, email = ?, categoria = ? WHERE id = ?;";

                PreparedStatement pstmt = this.conexao.prepareCall(sql);
                pstmt.setString(1, String.valueOf(empresa.getFaturamento()));
                pstmt.setString(2, empresa.getDRE().getCamninho());
                pstmt.setString(3, empresa.getNomeFantasia());
                pstmt.setString(4, empresa.getRazaoSocial());
                pstmt.setString(5, empresa.getCNPJ());
                pstmt.setString(6, empresa.getEmail());
                pstmt.setString(7, empresa.getCategoria().getCategoria());
                pstmt.setInt(8, empresa.getId());

                pstmt.execute();


                for (int i = 0; i < empresa.getTelefones().length; i++) {
                    Mensagem respostaTelefoneDAO = new TelefoneDAO().cadastrar(empresa.getTelefones()[i]);
                    Telefone telefoneCadastrado = (Telefone) respostaTelefoneDAO.getDados();

                    sql = "INSERT INTO empresa_telefone (id_telefone, id_empresa) VALUES(?, ?);";

                    pstmt = this.conexao.prepareCall(sql);
                    pstmt.setInt(1, telefoneCadastrado.getId());
                    pstmt.setInt(2, empresa.getId());

                    pstmt.execute();
                }

                this.conexao.close();

                respostaDAO.setCodigo(0);
                respostaDAO.setMensagem("Dados da empresa atualizados com sucesso !!!");
            }

        } catch (SQLException error) {

            System.out.println(error.getMessage());

            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao atualizar a empresa !!!");
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

    public Mensagem consultar(int id_empresa) {

        Mensagem respostaDAO = new Mensagem();
        
        try {

            String sql = "SELECT * FROM empresa WHERE id = ?;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setInt(1, id_empresa);

            
            ResultSet rs = pstmt.executeQuery();
            Empresa empresa = null;
            while (rs.next()) {
                
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setCNPJ(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setFaturamento(Double.valueOf(rs.getString("faturamento")));
                empresa.setNomeFantasia(rs.getString("nomeFantasia"));
                empresa.setRazaoSocial(rs.getString("razaoSocial"));
                empresa.setSituacao(rs.getString("situacao"));
                
                Categoria categoria = new Categoria();
                categoria.setCategoria(rs.getString("categoria"));
                
                empresa.setCategoria(categoria);
                
                empresa.setEndereco((Endereco) new EnderecoDAO().consultar(rs.getInt("id_endereco")).getDados());
            }
            
            if(empresa != null) {                
                
                sql = "SELECT id_telefone FROM empresa_telefone WHERE id_empresa = ?";
                
                pstmt = conexao.prepareCall(sql);
                pstmt.setInt(1, empresa.getId());
                
                rs = pstmt.executeQuery();
                ArrayList<Telefone> listaTelefone = new ArrayList<Telefone>();
                while(rs.next()) {
                    listaTelefone.add((Telefone) new TelefoneDAO().consultar(rs.getInt("id_telefone")).getDados());
                }
                
                Telefone[] telefones = new Telefone[listaTelefone.size()];
                for (int i = 0; i < listaTelefone.size(); i++) {
                    telefones[i] = listaTelefone.get(i);
                }
                
                empresa.setTelefones(telefones);
            }
            
            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados encontrados com sucesso !!!");
            respostaDAO.setDados(empresa);
        } catch (SQLException error) {

            System.out.println("Erro Ao buscar telefone da empresa--> " + error.getMessage());
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao buscar Empresa !!!");
        }

        return respostaDAO;
    }
}
