$(window).bind('scroll', function () {
    if ($(window).scrollTop() > 120) {
        $('header').addClass('fixed');
        $('header').height(50);
        $('#brand').css("padding", "5px 0");
        //width: ?, height: 40
        $('#brand').height(40);
        $('#brand').width(152.5);
        $('#page').addClass('top');
    } else {
        $('header').removeClass('fixed');
        $('header').height(120);
        //width: 350, height: ? 
        $('#brand').height(91.8);
        $('#brand').width(350);
        $('#page').removeClass('top');
    }
});