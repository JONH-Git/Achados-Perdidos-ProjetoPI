$(document).ready(function() {
    $('#nome').focus();

    $('#formObjeto').on('submit', function() {
        var btn = $(this).find('button[type="submit"]');
        btn.text('Registrando...');
        btn.prop('disabled', true);
    });
});