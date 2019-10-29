
package DAOs;

import Interfaces.iDAO;
import Modelo.EntidadeDominio;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public class InvestidorDAO implements iDAO{
    
    public InvestidorDAO() {
        
    }

    @Override
    public Mensagem cadastrar(EntidadeDominio entidade) {
        
        
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
