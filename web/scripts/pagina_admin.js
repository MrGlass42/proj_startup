


jQuery(document).ready(function () {
    buscar_empresas();

    jQuery(document).on('click', ".excluir", function () {
        var id_startup = jQuery(this).attr('id_empresa');

        excluir_empresa(id_startup);
    });

    jQuery(document).on('click', ".atualizar_situacao" ,function () {
        var id_startup = jQuery(this).attr('id_empresa');
        
        atualizar_situacao(id_startup);
    });
});

function atualizar_situacao(id_startup) {
    jQuery.ajax({
        url: 'http://localhost:8082/proj_startup/controle_startup',
        type: 'POST',
        dataType: "json",
        data: {
            opcao: 6,
            id: id_startup,
            chaveHash: "Empresa",
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
                    buscar_empresas();
                } else {
                    console.log("Resposta nula do servidor...");
                }
            } else {
                console.log("Erro na comunicação com o servidor...");
            }

        }
    });
}

function excluir_empresa(id_startup) {

    jQuery.ajax({
        url: 'http://localhost:8082/proj_startup/controle_startup',
        type: 'POST',
        dataType: "json",
        data: {
            opcao: 4,
            chaveHash: "Empresa",
            id: id_startup
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
                    buscar_empresas();
                } else {
                    console.log("Resposta nula do servidor...");
                }
            } else {
                console.log("Erro na comunicação com o servidor...");
            }

        }
    });
}

function buscar_empresas() {
    jQuery.ajax({
        url: 'http://localhost:8082/proj_startup/controle_startup',
        type: 'POST',
        dataType: "json",
        data: {
            opcao: 5,
            chaveHash: "Empresa"
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
                    
                    var dados = resposta_controle.dados;

                    var htmlTabela = '';
                    for (var i = 0; i < dados.length; i++) {
                        htmlTabela += '<tr><td>' + dados[i].nomeFantasia + '</td>';
                        htmlTabela += '<td>' + dados[i].CNPJ + '</td>';
                        htmlTabela += '<td>' + dados[i].email + '</td>';
                        
                        if (dados[i].situacao != 'aprovada') {
                            htmlTabela += '<td><a class="atualizar_situacao" style="cursor: pointer" id_empresa="' + dados[i].id + '"><u>' + dados[i].situacao + '<u></a></td>';
                        } else {
                            htmlTabela += '<td>' + dados[i].situacao + '</td>';
                        }
                        
                        htmlTabela += '<td><button type="button" class="excluir btn btn-danger" style="cursor: pointer" id_empresa="' + dados[i].id + '">Excluir</button></td></tr>';
                    }

                    jQuery("#tbody_tabela_empresas").html(htmlTabela);

                } else {
                    console.log("Resposta nula do servidor...");
                }
            } else {
                console.log("Erro na comunicação com o servidor...");
            }

        }
    });
}
