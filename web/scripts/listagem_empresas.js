jQuery(document).ready(function () {

    var id_startup = getUrlVars().id_startup;

    if (id_startup > 0) {
        buscar_empresa(id_startup);
    }


    jQuery("#add_telefone").on('click', function () {
        if (jQuery("#telefone_digitado").val() == '') {
            iziToast.error({
                message: "<strong>Digite um Número !!!</strong>"
            });
            return;
        }

        var telefone_digitado = jQuery("#telefone_digitado").val();

        var telefones_adicionados = jQuery("#telefones").val();

        telefones_adicionados += "," + telefone_digitado;

        jQuery("#telefones").val(telefones_adicionados);
        jQuery("#telefone_digitado").val("");

        iziToast.success({
            message: "<strong>Telefone Adicionado com Sucesso !!!</strong>"
        });
    });

    jQuery(document).on("click", ".excluir_telefone", function () {
        var id_telefone = jQuery(this).attr("id_telefone");
        jQuery(this).remove();

        excluirTelefone(id_telefone);
    });
    
    jQuery("#btn_cadastro_startup").on("click", function(e) {
        e.preventDefault();
        
        if(jQuery("#senha").val() === jQuery("#confirmarSenha").val()) {
            jQuery("#form_cadastro_startup").submit();
            
            setTimeout(function() {
                window.location("index.html");
            }, 500);
        } else {
            iziToast.error({
                message: "<strong>Senhas diferentes</strong>"
            });
        }
    });

});