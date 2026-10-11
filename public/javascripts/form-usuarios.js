$(document).ready(function () {
    $('#nome').focus();

    $('#formUsuario').on('submit', function () {
        var btnSubmit = $(this).find('button[type="submit"]');
        btnSubmit.text('Salvando...');
        btnSubmit.css('opacity', '0.7');
    });
});