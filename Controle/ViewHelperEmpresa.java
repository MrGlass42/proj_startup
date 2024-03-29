
package Controle;

import Interfaces.iViewHelper;
import Modelo.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mr.Glass
 */
public class ViewHelperEmpresa implements iViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        
        Pais pais = new Pais();
        pais.setNome(request.getParameter("pais"));
        
        Estado estado = new Estado();
        estado.setNome(request.getParameter("estado"));
        estado.setPais(pais);
        
        Cidade cidade = new Cidade();
        cidade.setNome(request.getParameter("cidade"));
        cidade.setEstado(estado);
        
        Endereco endereco = new Endereco();
        endereco.setComplemento(request.getParameter("complemento"));
        endereco.setLogradouro(request.getParameter("logradouro"));
        endereco.setNumero(request.getParameter("numero"));
        endereco.setCidade(cidade);
        
        Categoria categoria = new Categoria();
        categoria.setCategoria(request.getParameter("categoria"));
        
        Documento dre = new Documento();
        dre.setNome("A definir");
        dre.setCamninho("A definir");
        
        Usuario usuario = new Usuario();
        usuario.setLogin(request.getParameter("email"));
        usuario.setSenha(request.getParameter("senha"));
        
        Empresa empresa = new Empresa();
        empresa.setCNPJ(request.getParameter("cnpj"));
        empresa.setEmail(request.getParameter("email"));
        empresa.setNomeFantasia(request.getParameter("nomeFantasia"));
        empresa.setRazaoSocial(request.getParameter("razaoSocial"));
        empresa.setDRE(dre);
        empresa.setUsuario(usuario);
        empresa.setEndereco(endereco);
        empresa.setCategoria(categoria);
        
        return empresa;
    }
    
}
