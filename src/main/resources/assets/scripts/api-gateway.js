var api_gateway_calls = 0;

function api_gateway() {
    $.ajax({
        url: '/api?name=sam-' + api_gateway_calls,
        cache: false,
        success: function (data) {
            var element = $('#api-gateway');
            element.empty();
            var str = '<ul>';
            $.each(data, function (index, content) {
                str += '<li>' + JSON.stringify(content) + '</li>';
            });
            str += '</ul>';
            element.append(str);
        },
        error: function (error) {
            $('#api-gateway').append('Error invoking api-gateway');
        }
    });
    api_gateway_calls = api_gateway_calls + 1;
}

$(document).ready(function () {
    api_gateway();
});
