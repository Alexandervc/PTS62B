$(window).bind('scroll', function () {
    if ($(window).scrollTop() > 120) {
        $('header').addClass('fixed');
        $('header').height(50);
        $('header').find('img:first').css("padding", "5px 0");
        //width: ?, height: 40
        $('header').find('img:first').height(40);
        $('header').find('img:first').width(152.5);
        $('#page').addClass('top');
    } else {
        $('header').removeClass('fixed');
        $('header').height(120);
        //width: 350, height: ? 
        $('header').find('img:first').height(91.8);
        $('header').find('img:first').width(350);
        $('#page').removeClass('top');
    }
});