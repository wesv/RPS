var listOfHeaderItems = [".header-inside", ".loginPopup"];

//Scroll Event
$(document).scroll(function (e) {
    
    //Changing the Header color
    firstScrollEffect(e);

    //Having the text follow the picture
    secondScrollEffect(e);
})

function firstScrollEffect(e)
{
    //Height to the bottom of the header
    var targetHeight = $('.header-inside').height();

    //The bottom of the picture
    var containerHeight = $('#aboutContent').position().top;

    //The distance between the header and the bottom of the picture
    var maxScroll = containerHeight - targetHeight;

    //Current position on page
    var scrollY = $(window).scrollTop();

    //How much the page is scrolled, as a percent. If this becomes > 1, set to 1.
    var scrollPercent = Math.min((scrollY * 1.0) / (maxScroll * 1.0), 1.0);

    //The color the banner will become when fully colered.
    var red = 63;
    var green = 164;
    var blue = 38

    //Set the header to the darker, more solid color. Lowest opacity = 0.3.
    for (var x = 0; x < listOfHeaderItems.length; x++) {
        $(listOfHeaderItems[x]).css('background', 'rgba( ' +
            Math.floor(red * scrollPercent) + ', ' + Math.floor(green * scrollPercent) + ', ' +
            Math.floor(blue * scrollPercent) + ', ' + Math.max(scrollPercent, 0.3) + ')');

    }
}


//Used to keep track of precious position for second scroll effect.
var lastScroll = 0;

function secondScrollEffect(e)
{
    var currentScroll = $(window).scrollTop();

    //If the page moved "up"
    if (currentScroll < lastScroll) {

        //Stop all current animations, and move to the newest position immediately
        $('.title-text').stop(true, true).animate({
            top: '-=' + Math.abs(currentScroll - lastScroll)
        }, 0);//Speed of 0: This text doesn't slide
    }

    //If the page moved "down"
    if (currentScroll > lastScroll) {

        //Stop all animations and move to the new position immediately
        $('.title-text').stop(true, true).animate({
            top: '+=' + Math.abs(currentScroll - lastScroll)
        }, 0);
    }

    //Now at the end, this was the last scroll.
    lastScroll = currentScroll;
}