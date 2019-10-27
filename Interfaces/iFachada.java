
package Interfaces;

import Modelo.*;

/**
 *
 * @author Mr.Glass
 */
public interface iFachada {
    public Mensagem cadastrar(EntidadeDominio entidade);
    public EntidadeDominio editar(int id);
    public Mensagem excluir(int id);
    public EntidadeDominio[] consultar();
}
