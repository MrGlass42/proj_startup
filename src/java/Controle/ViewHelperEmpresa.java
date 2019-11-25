package Controle;

import Interfaces.iViewHelper;
import Modelo.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mr.Glass
 */
public class ViewHelperEmpresa implements iViewHelper {

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
        dre.setNome("Dre");
        dre.setCamninho("A definir");

        Usuario usuario = new Usuario();
        usuario.setLogin(request.getParameter("email"));
        usuario.setSenha(request.getParameter("senha"));
        usuario.setTipo("empresa");

        Telefone[] telefones = null;
        try {
            String[] telefones_recebidos = request.getParameter("telefones").split(",");

            telefones = new Telefone[telefones_recebidos.length - 1];
            for (int i = 1, j = 0; i < telefones_recebidos.length; i++, j++) {
                Telefone telefone = new Telefone();
                telefone.setNumero(telefones_recebidos[i]);

                telefones[j] = telefone;
            }
        } catch (Exception error) {
            System.out.print("Erro EmpresaVH --> " + error.getMessage());
        }

        Empresa empresa = new Empresa();

        try {
            empresa.setId(Integer.valueOf(request.getParameter("id")));
        } catch (NumberFormatException error) {
            System.out.println("Cadastro de Empresa Nova...");
        }

        empresa.setSituacao("em analise");
        empresa.setCNPJ(request.getParameter("cnpj"));
        empresa.setEmail(request.getParameter("email"));
        empresa.setNomeFantasia(request.getParameter("nomeFantasia"));
        empresa.setRazaoSocial(request.getParameter("razaoSocial"));
        empresa.setDRE(dre);
        empresa.setUsuario(usuario);
        empresa.setEndereco(endereco);
        empresa.setCategoria(categoria);
        empresa.setTelefones(telefones);

        return empresa;
    }

}
