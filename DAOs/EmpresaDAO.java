
package DAOs;

import Interfaces.iDAO;
import Modelo.EntidadeDominio;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public class EmpresaDAO implements iDAO{
    
    public EmpresaDAO() {
        
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
