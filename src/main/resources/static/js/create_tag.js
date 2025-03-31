$(document).ready(function() {
    // Инициализируем Select2 на поле #tags
    $('#tags').select2({
        placeholder: "Select one or more tags",
        allowClear: true,
        width: '100%'
    });
});