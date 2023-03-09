// 메뉴 드롭다운

var menu = document.querySelector('#menu')
var menu_openers = menu.querySelectorAll('.opener')

menu_openers.forEach(function (elements) {
    elements.addEventListener('click', function (event) {

        event.preventDefault();

        var menu2 = document.querySelector('#menu')
        var menu_openers2 = menu2.querySelectorAll('.opener')
        // Toggle.
        menu_openers2.forEach(function (element) {
            element.classList.remove('active')
        })
        event.target.classList.add('active')
        // Trigger resize (sidebar lock).
        // window.triggerHandler('resize.sidebar-lock');

    });

});