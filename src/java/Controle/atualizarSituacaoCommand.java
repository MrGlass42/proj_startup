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
public class atualizarSituacaoCommand implements iCommand {

    @Override
    public Mensagem executar(EntidadeDominio entidade) {
        return new Fachada().atualizar_situacao_empresa(entidade.getId());
    }

}
