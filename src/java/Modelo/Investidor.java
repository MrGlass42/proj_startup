/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Carlos-PC
 */
public class Investidor extends EntidadeDominio{
    private String nome;
    private String cpf;
    private String rg;
    private Endereco endereco;
    private String qtdeStartups;
    private Telefone[] telefone;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    public Telefone[] getTelefones() {
        return telefone;
    }

    public void setTelefones(Telefone[] telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String Nome) {
        this.nome = Nome;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String CPF) {
        this.cpf = CPF;
    }

    public String getRG() {
        return rg;
    }

    public void setRG(String RG) {
        this.rg = RG;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getQtdeStartups() {
        return qtdeStartups;
    }

    public void setQtdeStartups(String qtdeStartups) {
        this.qtdeStartups = qtdeStartups;
    }
    
    
}
