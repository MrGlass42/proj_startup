

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

function excluirTelefone(id_telefone) {
    jQuery.ajax({
        url: 'http://localhost:8082/proj_startup/controle_startup',
        type: 'POST',
        dataType: "json",
        data: {
            opcao: 4,
            id: id_telefone,
            chaveHash: "Telefone"
        },
        beforeSend: function () {
            console.log("Iniciando Requisição Para Excluir Telefone...");
        },
        complete: function (e, xhr, result) {
            if (e.readyState == 4 && e.status == 200) {
                try { //Converte a resposta HTTP JSON em um objeto JavaScript
                    var resposta_controle = JSON.parse(e.responseText); //Combo OS

                } catch (err) {
                    console.log("Erro na conversão do objeto JSON");
                }
                if (resposta_controle != null) {

                    console.log(resposta_controle);

                    iziToast.success({
                        message: "<strong>Telefone Excluido Com Sucesso !!!</strong>"
                    });
                    
                } else {
                    console.log("Resposta nula do servidor...");
                }
            } else {
                console.log("Erro na comunicação com o servidor...");
            }

        }


    });
}

function buscar_empresa(id_empresa) {

    jQuery.ajax({
        url: 'http://localhost:8082/proj_startup/controle_startup',
        type: 'POST',
        dataType: "json",
        data: {
            opcao: 3,
            id: id_empresa,
            chaveHash: "Empresa"
        },
        beforeSend: function () {
            console.log("Iniciando Requisição Para Buscar Dados Da Empresa...");
        },
        complete: function (e, xhr, result) {
            if (e.readyState == 4 && e.status == 200) {
                try { //Converte a resposta HTTP JSON em um objeto JavaScript
                    var resposta_controle = JSON.parse(e.responseText); //Combo OS

                } catch (err) {
                    console.log("Erro na conversão do objeto JSON");
                }
                if (resposta_controle != null) {

                    console.log(resposta_controle);
                    
                    var dados = resposta_controle.dados;

                    jQuery("#id_startup").val(dados.id);
                    jQuery("#email").val(dados.email);
                    jQuery("#nomeFantasia").val(dados.nomeFantasia);
                    jQuery("#razaoSocial").val(dados.razaoSocial);
                    jQuery("#cnpj").val(dados.CNPJ);
                    jQuery("#categoria").val(dados.categoria.categoria);
                    jQuery("#complemento").val(dados.endereco.complemento);
                    jQuery("#logradouro").val(dados.endereco.logradouro);
                    jQuery("#numero").val(dados.endereco.numero);
                    jQuery("#cidade").val(dados.endereco.cidade.nome);
                    jQuery("#estado").val(dados.endereco.cidade.estado.nome);
                    jQuery("#pais").val(dados.endereco.cidade.estado.pais.nome);

                    jQuery(".campos_senha").hide();

                    var telefones = dados.telefones;
                    var htmlTelefones = '';
                    for (var i = 0; i < telefones.length; i++) {
                        htmlTelefones += '<p><a style="cursor: pointer;" class="excluir_telefone" id_telefone="' + telefones[i].id + '">' + telefones[i].numero + ' - </a></p>'
                    }

                    jQuery("#coluna_mostrar_teleofones").html(htmlTelefones);
                } else {
                    console.log("Resposta nula do servidor...");
                }
            } else {
                console.log("Erro na comunicação com o servidor...");
            }

        }


    });
}



function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');

    for (var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        hash[1] = unescape(hash[1]);
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }

    return vars;
}
var urlVars = getUrlVars();