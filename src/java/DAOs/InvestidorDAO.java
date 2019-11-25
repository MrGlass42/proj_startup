/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import DAOs.*;
import DAOs.util.conectaBanco;
import Interfaces.iDAO;
import Modelo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC-Carlos
 */
public class InvestidorDAO implements iDAO {

    private Connection conexao;

    public InvestidorDAO() {
        conexao = conectaBanco.getConexao();
    }

    @Override
   public Mensagem cadastrar(EntidadeDominio entidade) {

        Mensagem respostaDAO = new Mensagem();

        try {

            Investidor investidor = (Investidor) entidade;

            Mensagem respostaUsuarioDAO = new UsuarioDAO().cadastrar(investidor.getUsuario());
            Mensagem respostaEnderecoDAO = new EnderecoDAO().cadastrar(investidor.getEndereco());

            if (respostaUsuarioDAO.getCodigo() == 0 && respostaEnderecoDAO.getCodigo() == 0) {

                investidor.setEndereco((Endereco) respostaEnderecoDAO.getDados());
                investidor.setUsuario((Usuario) respostaUsuarioDAO.getDados());

                String sql = "INSERT INTO investidor (nome, cpf, rg, qtdeStartups, id_usuario, id_endereco) VALUES (?, ?, ?, ?, ?, ?) returning id;";

                PreparedStatement pstmt = this.conexao.prepareCall(sql);
                pstmt.setString(1, investidor.getNome());
                pstmt.setString(2, investidor.getCPF());
                pstmt.setString(3, investidor.getRG());
                pstmt.setString(4, investidor.getQtdeStartups());
                pstmt.setInt(5, investidor.getUsuario().getId());
                pstmt.setInt(6, investidor.getEndereco().getId());

                ResultSet rs = pstmt.executeQuery();
                int id_investidor = 0;
                while (rs.next()) {
                    id_investidor = rs.getInt("id");
                }

                investidor.setId(id_investidor);

                for (Telefone telefone : investidor.getTelefones()) {
                    Mensagem respostaTelefoneDAO = new TelefoneDAO().cadastrar(telefone);
                    Telefone telefoneCadastrado = (Telefone) respostaTelefoneDAO.getDados();
                    sql = "INSERT INTO investidor_telefone (id_telefone, id_investidor) VALUES(?, ?);";
                    pstmt = this.conexao.prepareCall(sql);
                    pstmt.setInt(1, telefoneCadastrado.getId());
                    pstmt.setInt(2, investidor.getId());
                    pstmt.execute();
                }

                this.conexao.close();

                respostaDAO.setCodigo(0);
                respostaDAO.setMensagem("Dados de investidor cadastrados com sucesso !!!");
            }

        } catch (SQLException error) {

            System.out.println(error.getMessage());

            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao cadastrar o investidor !!!");
        }

        return respostaDAO;
    }

    @Override
    public Mensagem editar(EntidadeDominio entidade) {
        Mensagem respostaDAO = new Mensagem();

        try {

            Investidor investidor = (Investidor) entidade;

            Mensagem respostaUsuarioDAO = new UsuarioDAO().cadastrar(investidor.getUsuario());
            Mensagem respostaEnderecoDAO = new EnderecoDAO().cadastrar(investidor.getEndereco());

            if (respostaUsuarioDAO.getCodigo() == 0 && respostaEnderecoDAO.getCodigo() == 0) {

                investidor.setEndereco((Endereco) respostaEnderecoDAO.getDados());
                investidor.setUsuario((Usuario) respostaUsuarioDAO.getDados());

                String sql = "UPDATE empresa SET nome = ?, cpf = ?, rg = ?, qtdestartups = ? WHERE id = ?;";

                PreparedStatement pstmt = this.conexao.prepareCall(sql);
                pstmt.setString(1, investidor.getNome());
                pstmt.setString(2, investidor.getCPF());
                pstmt.setString(3, investidor.getRG());
                pstmt.setString(4, investidor.getQtdeStartups());

                pstmt.execute();

                for (Telefone telefone : investidor.getTelefones()) {
                    Mensagem respostaTelefoneDAO = new TelefoneDAO().cadastrar(telefone);
                    Telefone telefoneCadastrado = (Telefone) respostaTelefoneDAO.getDados();
                    sql = "INSERT INTO investidor_telefone (id_telefone, id_investidor) VALUES(?, ?);";
                    pstmt = this.conexao.prepareCall(sql);
                    pstmt.setInt(1, telefoneCadastrado.getId());
                    pstmt.setInt(2, investidor.getId());
                    pstmt.execute();
                }

                this.conexao.close();

                respostaDAO.setCodigo(0);
                respostaDAO.setMensagem("Dados de investidor atualizados com sucesso !!!");
            }

        } catch (SQLException error) {

            System.out.println(error.getMessage());

            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao cadastrar o investidor !!!");
        }

        return respostaDAO;
    }

    @Override
    public Mensagem excluir(int id) {
        Mensagem respostaDAO = new Mensagem();

        try {

            String sql = "DELETE FROM investidor_telefone WHERE id_investidor = ?";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);

            pstmt.setInt(1, id);

            pstmt.execute();

            sql = "DELETE FROM investidor WHERE id = ?";

            pstmt = this.conexao.prepareCall(sql);

            pstmt.setInt(1, id);

            pstmt.execute();

            conexao.close();

            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Investidor excluido com sucesso !!!");
        } catch (SQLException erro) {
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao excluir Investidor !!!");

            System.out.print(erro.getMessage());
        }

        return respostaDAO;
    }

    @Override
    public Mensagem consultar() {
       Mensagem respostaDAO = new Mensagem();

        try {

            String sql = "SELECT * FROM empresa;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);

            ResultSet rs = pstmt.executeQuery();
            ArrayList<Empresa> empresas = new ArrayList<Empresa>();
            while (rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setCNPJ(rs.getString("cnpj"));
                empresa.setEmail(rs.getString("email"));
                empresa.setNomeFantasia(rs.getString("nomeFantasia"));

                Categoria categoria = new Categoria();
                categoria.setCategoria(rs.getString("categoria"));

                empresa.setCategoria(categoria);

                empresas.add(empresa);
            }

            Empresa[] vetorEmpresas = new Empresa[empresas.size()];
            for (int i = 0; i < empresas.size(); i++) {
                vetorEmpresas[i] = empresas.get(i);
            }

            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados encontrados com sucesso !!!");
            respostaDAO.setDados(vetorEmpresas);
        } catch (SQLException error) {

            System.out.println("Erro Ao buscar telefone da empresa--> " + error.getMessage());
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao buscar Empresa !!!");
        }

        return respostaDAO;
    }

    @Override
    public Mensagem consultar(int id) {
        
        Mensagem respostaDAO = new Mensagem();

        try {

            String sql = "SELECT * FROM empresa WHERE id = ?;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setInt(1, id);

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

            if (empresa != null) {

                sql = "SELECT id_telefone FROM empresa_telefone WHERE id_empresa = ?";

                pstmt = conexao.prepareCall(sql);
                pstmt.setInt(1, empresa.getId());

                rs = pstmt.executeQuery();
                ArrayList<Telefone> listaTelefone = new ArrayList<>();
                while (rs.next()) {
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

    public Mensagem consultar(String email) {

        Mensagem respostaDAO = new Mensagem();

        try {

            String sql = "SELECT * FROM usuario WHERE login = ?;";

            PreparedStatement pstmt = this.conexao.prepareCall(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            Investidor investidor = null;
            while (rs.next()) {

                investidor = new Investidor();
                investidor.setId(rs.getInt("id"));
                investidor.setCPF(rs.getString("cpf"));
                investidor.setRG(rs.getString("rg"));
                investidor.setNome(rs.getString("nome"));
                investidor.setQtdeStartups(rs.getString("qtdeStartups"));
                
                investidor.setEndereco((Endereco) new EnderecoDAO().consultar(rs.getInt("id_endereco")).getDados());
            }

            respostaDAO.setCodigo(0);
            respostaDAO.setMensagem("Dados encontrados com sucesso !!!");
            respostaDAO.setDados(investidor);
        } catch (SQLException error) {

            System.out.println("Erro Ao buscar telefone do investidor--> " + error.getMessage());
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Erro ao buscar Investidor !!!");
        }

        return respostaDAO;
    }
    
}
