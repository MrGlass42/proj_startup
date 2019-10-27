
package Controle;

import DAOs.EmpresaDAO;
import Interfaces.iFachada;
import Modelo.EntidadeDominio;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public class Fachada implements iFachada{

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {
        Mensagem resposta_dao = null;
        
        if(entidade.getClass().getSimpleName() == "Empresa") {
            
            EmpresaDAO dao = new EmpresaDAO();
            resposta_dao = dao.cadastrar(entidade);
            
            return resposta_dao;
            
        }
        
        return resposta_dao;
    }

    @Override
    public EntidadeDominio editar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mensagem excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntidadeDominio[] consultar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
