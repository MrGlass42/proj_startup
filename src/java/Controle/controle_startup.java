/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Interfaces.iViewHelper;
import Modelo.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;

/**
 *
 * @author Mr.Glass
 */
public class controle_startup extends HttpServlet {

    private HashMap<String, iViewHelper> hashMapViewHelper;

    public controle_startup() {

        this.hashMapViewHelper = new HashMap<String, iViewHelper>();
        this.hashMapViewHelper.put("Empresa", new ViewHelperEmpresa());
        this.hashMapViewHelper.put("Telefone", new ViewHelperTelefone());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String opcao = request.getParameter("opcao");

        switch (opcao) {
            case "1":

                EntidadeDominio entidade = this.hashMapViewHelper.get(request.getParameter("chaveHash")).getEntidade(request);

                Mensagem resposta_fachada = null;
                if (entidade.getId() > 0) {
                    resposta_fachada = new Fachada().editar(entidade);
                } else {
                    resposta_fachada = new Fachada().cadastrar(entidade);
                }

                System.out.println("RESPOSTA FACHADA --> " + resposta_fachada.getMensagem());

                if (resposta_fachada.getCodigo() == 0) {
                    response.sendRedirect("index.html");
                } else {
                    // Redireciona para a pagina de erros
                }

                break;
            case "2":
                // Autenticação dos Usuários

                Usuario usuario = (Usuario) new ViewHelperUsuario().getEntidade(request);

                Mensagem respostaFachada = new Fachada().autenticar(usuario);

                EntidadeDominio[] dados = (EntidadeDominio[]) respostaFachada.getDados();

                //System.out.println(respostaFachada.getMensagem());

                usuario = (Usuario) dados[0];

                if (usuario.getId() > 0) {
                    
                    System.out.println("Usuario Autenticado Com Sucesso !!!");
                    
                    switch (usuario.getTipo()) {
                        case "empresa":
                            response.sendRedirect("cadastro_startup.html?id_startup=" + dados[1].getId());
                            break;
                        case "admin":
                            response.sendRedirect("pagina_admin.html");
                            break;
                    }
                } else {
                    System.out.println("USUARIO NAO EXISTE !!!");
                }
                break;
            case "3":
                int id_entidade = Integer.valueOf(request.getParameter("id_entidade"));
                String classeEntidade = request.getParameter("classeEntidade");

                out.print(new Gson().toJson(new Fachada().consultar(classeEntidade, id_entidade).getDados()));
                break;
            case "4":                
                EntidadeDominio entidadeParaExclusao = this.hashMapViewHelper.get(request.getParameter("chaveHash")).getEntidade(request);
                
                Mensagem repostaFachada = new Fachada().excluir(entidadeParaExclusao);
                
                out.print(new Gson().toJson(repostaFachada));
                break;
            case "5":
                EntidadeDominio[] respostaFachadaComEntidades = (EntidadeDominio[]) new Fachada().consultar(request.getParameter("chaveHash")).getDados();
                
                out.print(new Gson().toJson(respostaFachadaComEntidades));
                break;
            case "6":
                Mensagem repostaFachadaAposAtualizacao = new Fachada().atualizar_situacao_empresa(Integer.valueOf(request.getParameter("id_startup")));
                
                out.print(new Gson().toJson(repostaFachadaAposAtualizacao));
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
