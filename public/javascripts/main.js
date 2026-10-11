    $(document).ready(function() {
        console.log("Sistema Achados&Perdidos carregado com sucesso!");
     
        var currentUrl = window.location.pathname;
        $('.nav-links a').each(function() {
            if ($(this).attr('href') === currentUrl) {
                $(this).css('font-weight', 'bold');
                $(this).css('color', '#3498db');
            }
        });
    });