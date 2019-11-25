package Controle;

import DAOs.EmpresaDAO;
import DAOs.TelefoneDAO;
import DAOs.UsuarioDAO;
import DAOs.EnderecoDAO;
import Interfaces.iDAO;
import Interfaces.iFachada;
import Interfaces.iStrategy;
import Modelo.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Mr.Glass
 */
public class Fachada implements iFachada {

    private HashMap<String, iDAO> hashMapDao;
    private HashMap<String, ArrayList<iStrategy>> hashMapStrategy;

    public Fachada() {
        this.hashMapDao = new HashMap<String, iDAO>();
        this.hashMapDao.put("Empresa", new EmpresaDAO());
        this.hashMapDao.put("Telefone", new TelefoneDAO());
        
        ArrayList<iStrategy> listaValidadores = new ArrayList<iStrategy>();
        listaValidadores.add(new validadorExistenciaEmpresa());
        
        this.hashMapStrategy = new HashMap<String, ArrayList<iStrategy>>();
        this.hashMapStrategy.put("Empresa", listaValidadores);
    }

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {
        Mensagem respostaDAO = null;
        
        boolean flagEntidadeValidada = true;
        ArrayList<iStrategy> listaValidadores = this.hashMapStrategy.get(entidade.getClass().getSimpleName());
        for (int i = 0; i < listaValidadores.size(); i++) {
            respostaDAO = listaValidadores.get(i).processar(entidade);
            
            if(respostaDAO.getCodigo() != 0) {
                flagEntidadeValidada = false;
            }
            
        }
        
        if (flagEntidadeValidada) {
            return this.hashMapDao.get(entidade.getClass().getSimpleName()).cadastrar(entidade);
        }
       
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
    public Mensagem consultar(String nomeClasse) {
        Mensagem respostaDao = this.hashMapDao.get(nomeClasse).consultar();
        
        return respostaDao;
    }

    public Mensagem consultar(String classeEntidade, int id_entidade) {
        return this.hashMapDao.get(classeEntidade).consultar(id_entidade);
    }

    public Mensagem autenticar(Usuario usuario) {

        Mensagem respostaFachada = new Mensagem();

        EntidadeDominio[] itensResposta = new EntidadeDominio[2];
        Mensagem respostaDAO1 = new UsuarioDAO().consultar(usuario);
        Mensagem respostaDAO2 = new UsuarioDAO().buscarEntidadePai(usuario);

        if (respostaDAO1.getCodigo() == 0 && (respostaDAO2.getCodigo() == 0 || respostaDAO2.getCodigo() == 1)) {

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
    
    public Mensagem atualizar_situacao_empresa (int id) {
        return new EmpresaDAO().atualizarSituacao(id);
    }

}
