/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Interfaces.iViewHelper;
import Modelo.EntidadeDominio;
import Modelo.Telefone;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mr.Glass
 */
public class ViewHelperTelefone implements iViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        
        Telefone telefone = new Telefone();
        telefone.setId(Integer.valueOf(request.getParameter("id")));
        telefone.setNumero(request.getParameter("numero"));
        
        return telefone;
    }
}
