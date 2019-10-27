/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modelo.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mr.Glass
 */
public class controle_startup extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String opcao = request.getParameter("opcao");

        System.out.println("Valor Recebido -- >" + opcao);
        
        switch(opcao) {
            case "1":
                
                ViewHelperEmpresa vh = new ViewHelperEmpresa();
                Empresa empresa = (Empresa) vh.getEntidade(request);
                Fachada fachada = new Fachada();
                
                Mensagem resposta_fachada = fachada.cadastrar(empresa);
                
                if(resposta_fachada.getCodigo() == 0) {
                    // Redireciona para a edição de dados da empresa
                } else {
                    // Redireciona para a pagina de erros
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
    }// </editor-fold>

}
