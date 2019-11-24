
package Interfaces;

import Modelo.*;

/**
 *
 * @author Mr.Glass
 */
public interface iFachada {
    public Mensagem cadastrar(EntidadeDominio entidade);
    public Mensagem editar(EntidadeDominio entidade);
    public Mensagem excluir(EntidadeDominio entidade);
    public Mensagem consultar(String nomeClasse);
}
