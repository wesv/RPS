var scrollSpeed = 750;

$(document).ready(function () {
    $('#preview').click(function () {
        
        $('html, body').animate({
            scrollTop:
                ($('#previewContent').position().top - $('.header-inside').height() + 3)
        }, scrollSpeed);
        
        return false;
        
    });

    $('#about').click(function () {

        $('html, body').animate({
            scrollTop:
                $('#aboutContent').position().top - $('.header-inside').height() + 3
        }, scrollSpeed);

        return false;

    });

    $('#home').click(function () {

        $('html, body').animate({ scrollTop: 0 }, scrollSpeed);

        return false;

    });

});