
package Interfaces;

import Modelo.EntidadeDominio;
import Modelo.Mensagem;
import java.sql.SQLException;

/**
 *
 * @author Mr.Glass
 */
public interface iDAO {
    public Mensagem cadastrar(EntidadeDominio entidade);
    public Mensagem editar(EntidadeDominio entidade);
    public Mensagem excluir(int id);
    public Mensagem consultar();
    public Mensagem consultar(int id);
}
