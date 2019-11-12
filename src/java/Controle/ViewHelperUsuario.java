/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Interfaces.iViewHelper;
import Modelo.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mr.Glass
 */
public class ViewHelperUsuario implements iViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        
        Usuario usuario = new Usuario();
        usuario.setLogin(request.getParameter("login"));
        usuario.setSenha(request.getParameter("senha"));
        usuario.setTipo(request.getParameter("tipo"));
        
        return usuario;
    }
    
}
