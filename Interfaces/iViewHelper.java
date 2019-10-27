
package Interfaces;

import Modelo.EntidadeDominio;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mr.Glass
 */
public interface iViewHelper {
    public EntidadeDominio getEntidade(HttpServletRequest request);
}
