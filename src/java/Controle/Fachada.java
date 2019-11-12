package Controle;

import DAOs.EmpresaDAO;
import DAOs.TelefoneDAO;
import DAOs.UsuarioDAO;
import DAOs.EnderecoDAO;
import Interfaces.iDAO;
import Interfaces.iFachada;
import Modelo.*;
import java.util.HashMap;

/**
 *
 * @author Mr.Glass
 */
public class Fachada implements iFachada {

    private HashMap<String, iDAO> hashMapDao;

    public Fachada() {
        this.hashMapDao = new HashMap<String, iDAO>();
        this.hashMapDao.put("Empresa", new EmpresaDAO());
        this.hashMapDao.put("Telefone", new TelefoneDAO());
    }

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {
        Mensagem respostaDAO = this.hashMapDao.get(entidade.getClass().getSimpleName()).cadastrar(entidade);

        return respostaDAO;
    }

    @Override
    public Mensagem editar(EntidadeDominio entidade) {
        Mensagem respostaDAO = this.hashMapDao.get(entidade.getClass().getSimpleName()).editar(entidade);
        
        return respostaDAO;
    }

    @Override
    public Mensagem excluir(EntidadeDominio entidade) {
        Mensagem respostaDAO = this.hashMapDao.get(entidade.getClass().getSimpleName()).excluir(entidade.getId());
        
        return respostaDAO;
    }

    @Override
    public Mensagem consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Mensagem consultar(String classeEntidade, int id_entidade) {
        return this.hashMapDao.get(classeEntidade).consultar(id_entidade);
    }

    public Mensagem autenticar(Usuario usuario) {

        Mensagem respostaFachada = new Mensagem();

        EntidadeDominio[] itensResposta = new EntidadeDominio[2];
        Mensagem respostaDAO1 = new UsuarioDAO().consultar(usuario);
        Mensagem respostaDAO2 = new UsuarioDAO().buscarEntidadePai(usuario);

        if (respostaDAO1.getCodigo() == 0 && respostaDAO2.getCodigo() == 0) {

            respostaFachada.setCodigo(0);
            respostaFachada.setMensagem("Usuario autenticado com sucesso !!!");

            EntidadeDominio[] dados = new EntidadeDominio[2];
            dados[0] = (Usuario) respostaDAO1.getDados();
            dados[1] = (EntidadeDominio) respostaDAO2.getDados();

            respostaFachada.setDados(dados);
        } else {
            respostaFachada.setCodigo(1);
            respostaFachada.setMensagem("Erro ao buscar autenticar Usuario !!!");
        }

        return respostaFachada;
    }

}
