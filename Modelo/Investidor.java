
package Modelo;

/**
 *
 * @author Mr.Glass
 */
public class  extends EntidadeDominio{
    private String investidor;
    private String nomeCompleto;
    private String documentoPrincipal;
	private String documentoAuxiliar;
    private String numeroStartup;
    private String email;
    private Endereco endereco;
    private Telefone[] telefone;
    private Usuario usuario;
    
    public String getinvestidor() {
        return investidor;
    }

    public void setinvestidor(string investidor) {
        this.investidor = investidor;
    }

    public String getNomeCompleto() {
        return nomeFantasia;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getdocumentoPrincipal() {
        return documentoPrincipal;
    }

    public void setdocumentoPrincipal(String documentoPrincipal) {
        this.documentoPrincipal = documentoPrincipal;
    }
	
	public String documentoAuxiliar() {
        return documentoPrincipal;
    }

    public void setdocumentoPrincipal(String documentoAuxiliar) {
        this.documentoAuxiliar = documentoAuxiliar;
    }

    public String getnumeroStartup() {
        return numeroStartup;
    }

    public void setnumeroStartup(String numeroStartup) {
        this.numeroStartup = numeroStartup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Telefone[] getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone[] telefone) {
        this.telefone = telefone;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
