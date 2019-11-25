/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAOs.EmpresaDAO;
import Interfaces.iStrategy;
import Modelo.Empresa;
import Modelo.EntidadeDominio;
import Modelo.Mensagem;

/**
 *
 * @author Mr.Glass
 */
public class validadorExistenciaEmpresa implements iStrategy {

    @Override
    public Mensagem processar(EntidadeDominio entidade) {
        Empresa empresa = (Empresa) entidade;
        
        Mensagem respostaDAO = new EmpresaDAO().consultar(empresa.getCNPJ());
        if(respostaDAO.getDados() != null) {
            respostaDAO.setCodigo(1);
            respostaDAO.setMensagem("Empresa Ja existente no sistema");            
        }
        
        return respostaDAO;
    }
    
}
