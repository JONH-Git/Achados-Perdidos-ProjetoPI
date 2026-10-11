$(document).ready(function() {
    $('tbody tr').hover(
        function() { $(this).css('background-color', '#f1f1f1'); },
        function() { $(this).css('background-color', 'transparent'); }
    );
});