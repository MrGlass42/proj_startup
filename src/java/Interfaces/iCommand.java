/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelo.EntidadeDominio;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public interface iCommand {
    public Mensagem executar(EntidadeDominio entidade);
}
