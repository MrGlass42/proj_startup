/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAOs.InvestidorDAO;
import Interfaces.iStrategy;
import Modelo.EntidadeDominio;
import Modelo.Investidor;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public class validadorExistenciaInvestidor implements iStrategy {

    @Override
    public Mensagem processar(EntidadeDominio entidade) {
        Investidor investidor = (Investidor) entidade;
        
        Mensagem respostaDAO = new InvestidorDAO().consultar(investidor.getUsuario().getLogin());
        if(respostaDAO.getDados() != null) {
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Investidor ja cadastrado no sistema");            
        }
        
        return respostaDAO;
    }
    
}
