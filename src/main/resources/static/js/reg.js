// Проверка на совпадение паролей при отправке формы
document.getElementById("registrationForm").addEventListener("submit", function(e) {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    if (password !== confirmPassword) {
        e.preventDefault(); // Отменяем отправку формы
        document.getElementById("clientError").style.display = "block";
    } else {
        document.getElementById("clientError").style.display = "none";
    }
});