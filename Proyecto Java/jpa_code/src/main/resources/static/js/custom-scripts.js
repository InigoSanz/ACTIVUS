/**
 * Función que maneja el comportamiento del navbar y otros elementos al hacer scroll y redimensionar la ventana.
 */
function checkScrollAndResize() {
    var nav = document.querySelector('nav');
    var customNavLinks = document.querySelectorAll('.navbar-nav .nav-link.custom-nav-link');
    var welcomeMessage = document.querySelector('.welcome-message');
    var languageSelector = document.querySelector('.language-selector span');

    // Añade o quita la clase 'navbar-scrolled' al navbar basado en la posición de scroll
    if (window.scrollY > 50) {
        nav.classList.add('navbar-scrolled');
    } else {
        nav.classList.remove('navbar-scrolled');
    }

    // Añade o quita la clase 'small-window' a ciertos elementos basado en el tamaño de la ventana
    if (window.innerWidth <= 991) {
        customNavLinks.forEach(function(link) {
            link.classList.add('small-window');
        });
        if (welcomeMessage) {
            welcomeMessage.classList.add('small-window');
        }
        if (languageSelector) {
            languageSelector.classList.add('small-window');
        }
    } else {
        customNavLinks.forEach(function(link) {
            link.classList.remove('small-window');
        });
        if (welcomeMessage) {
            welcomeMessage.classList.remove('small-window');
        }
        if (languageSelector) {
            languageSelector.classList.remove('small-window');
        }
    }
}

// Agrega eventos para ejecutar la función checkScrollAndResize en scroll, resize y cuando el contenido del DOM está cargado
window.addEventListener('scroll', checkScrollAndResize);
window.addEventListener('resize', checkScrollAndResize);
window.addEventListener('DOMContentLoaded', checkScrollAndResize);

/**
 * Maneja el cambio de idioma cuando se selecciona una opción del selector de idiomas.
 */
$(document).ready(function() {
    $("#locales").change(function() {
        var selectedOption = $('#locales').val();
        var ref = window.location.pathname;
        if (selectedOption != '') {
            window.location.replace(ref + '?lang=' + selectedOption);
        }
    });
});