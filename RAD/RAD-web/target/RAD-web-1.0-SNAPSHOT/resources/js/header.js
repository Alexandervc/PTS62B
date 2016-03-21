$(window).bind('scroll', function () {
    if ($(window).scrollTop() > 120) {
        $('header').addClass('fixed');
        $('#page').addClass('top');
    } else {
        $('header').removeClass('fixed');
        $('#page').removeClass('top');
    }
});