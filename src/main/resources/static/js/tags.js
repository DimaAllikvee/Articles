$(document).ready(function() {
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Initialize Select2
    $('#tags').select2({
        tags: true,
        placeholder: "Выберите или добавьте теги",
        tokenSeparators: [',', ' '],
        theme: "classic"
    });

    // Initialize Quill editor
    var quill = new Quill('#editor-container', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                ['bold', 'italic', 'underline', 'strike'],
                [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                [{ 'script': 'sub'}, { 'script': 'super' }],
                [{ 'indent': '-1'}, { 'indent': '+1' }],
                [{ 'direction': 'rtl' }],
                [{ 'color': [] }, { 'background': [] }],
                [{ 'align': [] }],
                ['link', 'image', 'code-block', 'blockquote'],
                ['clean']
            ]
        },
        placeholder: 'Начните писать содержание статьи...',
    });

    // Set initial content from the hidden textarea
    const contentField = document.getElementById('content');
    if (contentField.value) {
        quill.root.innerHTML = contentField.value;
    }

    // Update hidden form field before submit
    $('form').on('submit', function() {
        $('#content').val(quill.root.innerHTML);
    });

    // Auto-generate slug from title
    $('#title').on('blur', function() {
        if ($('#slug').val() === '') {
            const title = $(this).val();
            const slug = title.toLowerCase()
                .replace(/[^\w\s-]/g, '')
                .replace(/\s+/g, '-')
                .replace(/-+/g, '-');
            $('#slug').val(slug);
        }
    });

    // Preview button functionality
    $('#preview-btn').on('click', function() {
        // Update content field first
        $('#content').val(quill.root.innerHTML);

        // Here you could open a modal or navigate to a preview page
        alert('Функция предпросмотра будет реализована в следующей версии.');
    });
});