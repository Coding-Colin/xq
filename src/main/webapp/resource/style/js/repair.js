
(function ($) {
    var cards = $('.card-drop'), toggler = cards.find('.toggle'), links = cards.find('ul>li>a'), li = links.parent('li'), count = links.length, width = 100;
    li.each(function (i) {
        $(this).css('z-index', count - i);
    });
    function setClosed() {
        li.each(function (index) {
            $(this).css('top', index * 4).css('width', width - index * 0.5 + '%').css('margin-left', index * 0.25 + '%');
        });
        li.addClass('closed');
        toggler.removeClass('active');
    }
    setClosed();
    toggler.on('mousedown', function () {
        var $this = $(this);
        if ($this.is('.active')) {
            setClosed();
        } else {
            $this.addClass('active');
            li.removeClass('closed');
            li.each(function (index) {
                $(this).css('top', 60 * (index + 1)).css('width', '100%').css('margin-left', '0px');
            });
        }
    });
    links.on('click', function (e) {
        var $this = $(this), label = $this.data('label');
        icon = $this.children('i').attr('class');
        li.removeClass('active');
        if ($this.parent('li').is('active')) {
            $this.parent('li').removeClass('active');
        } else {
            $this.parent('li').addClass('active');
        }
        toggler.children('span').text(label);
        toggler.children('i').removeClass().addClass(icon);
        setClosed();
        e.preventDefault;
    });
}(jQuery));