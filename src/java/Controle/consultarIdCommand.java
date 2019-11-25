/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Interfaces.iCommand;
import Modelo.EntidadeDominio;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public class consultarIdCommand implements iCommand {

    @Override
    public Mensagem executar(EntidadeDominio entidade) {
        return new Fachada().consultar(entidade.getClass().getSimpleName(), entidade.getId());
    }
    
}
