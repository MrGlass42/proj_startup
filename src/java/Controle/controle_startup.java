/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Interfaces.iCommand;
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
    private HashMap<String, iCommand> HashMapCommand;

    public controle_startup() {

        this.hashMapViewHelper = new HashMap<String, iViewHelper>();
        this.hashMapViewHelper.put("Empresa", new ViewHelperEmpresa());
        this.hashMapViewHelper.put("Telefone", new ViewHelperTelefone());
        
        this.HashMapCommand = new HashMap<String, iCommand>();
        this.HashMapCommand.put("1", new salvarCommand());
        this.HashMapCommand.put("3", new consultarIdCommand());
        this.HashMapCommand.put("4", new excluirCommand());
        this.HashMapCommand.put("5", new consultarCommand());
        this.HashMapCommand.put("6", new atualizarSituacaoCommand());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String opcao = request.getParameter("opcao");

        switch (opcao) {
            default:
                EntidadeDominio entidade = this.hashMapViewHelper.get(request.getParameter("chaveHash")).getEntidade(request);
                
                Mensagem respostaCommand = this.HashMapCommand.get(opcao).executar(entidade);
                
                if (respostaCommand.getCodigo() == 0) {
                    out.print(new Gson().toJson(respostaCommand));
                } else {
                    response.sendRedirect("paginaErro.html");
                }
                
                break;
                
            case "2":
                // Autenticação dos Usuários

                Usuario usuario = (Usuario) new ViewHelperUsuario().getEntidade(request);

                Mensagem respostaFachada = new Fachada().autenticar(usuario);

                EntidadeDominio[] dados = (EntidadeDominio[]) respostaFachada.getDados();

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
