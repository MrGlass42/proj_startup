
package Interfaces;

import Modelo.EntidadeDominio;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public interface iDAO {
    public Mensagem cadastrar(EntidadeDominio entidade);
    public EntidadeDominio editar(int id);
    public Mensagem excluir(int id);
    public EntidadeDominio[] consultar();
}
