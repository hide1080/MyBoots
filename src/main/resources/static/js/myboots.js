function categoryChange () {
    const $topCategory = $('#topCategoryId');
    const $subCategory = $('#categoryId');
    $subCategory.empty();
    $subCategory.css('visibility', 'visible');
    $.ajax({
        type: 'GET',
        url: '/api/category/' + $topCategory.val(),
        dataType: 'json',
        success: function(json) {
            const len = json.length;
            for (let i = 0; i < len; i++) {
                $subCategory.append($("<option>").val(json[i].categoryId).text(json[i].categoryName));
            }
        }
    });
}

function queryNotices() {
    const $myboots = $('#myboots-js');
    const accountId = $myboots.data('accountId');
    const myPage = $myboots.data('myPageUrl');
    const csrfToken = $myboots.data('csrfToken');
    const csrfHeaderName = $myboots.data('csrfHeaderName');
    const putHeader = [];
    putHeader[csrfHeaderName] = csrfToken;
    console.log(putHeader);
    if (!myPage) {
        console.log("not logged in.");
        return;
    } else {
        console.log("logged in.");
    }
    setInterval(function() {
        $.ajax({
            type: 'GET',
            url: '/api/notice/' + accountId,
            dataType: 'json',
            success: function(json) {
                if (json.length === 0) {
                    return;
                }
                const len = json.length;
                const $popup = $('<div>')
                        .append('<p>' + len + '個の商品が売れました。<br />マイページをご確認ください。</p>');
                $popup.addClass('notice-popup text-center');
                const button = $('<button>')
                    .text('確認')
                    .click(function() {
                        $popup.remove();
                    }
                );
                $popup.append(button)
                $('body').append($popup);
                const left = Math.floor(($(window).width() - $popup[0].offsetWidth) / 2);
                const top  = Math.floor(($(window).height() - $popup[0].offsetHeight) / 2);
                $popup.css({
                    "top": top,
                    "left": left,
                    "opacity": 1.0
                });
                const data = [];
                for (let i = 0; i < len; i++) {
                    data.push(
                        {
                            'accountId' : json[i].accountId,
                            'goodsId'   : json[i].goodsId,
                            'status'    : 1
                        }
                    );
                }
                console.log('data: ' + data);
                (function updateNotices(data) {
                    $.ajax({
                        type: 'PUT',
                        url: '/api/notice',
                        headers: putHeader,
                        contentType: 'application/json',
                        dataType: 'json',
                        data: JSON.stringify(data),
                        success: function() {}
                    });
                })(data);
            }
        });
    }, 1000 * 60);
}
