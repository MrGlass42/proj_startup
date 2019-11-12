/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mr.Glass
 */
public class conectaBanco {
    public static Connection getConexao() {
        Connection conexao = null;
        try {

            Class.forName("org.postgresql.Driver");
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Guitarhero");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro (Banco): " + e);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return conexao;
    }
}
