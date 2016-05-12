var bus;
$(document).ready(function() {
    var eb = new EventBus('/eventbus');
    eb.onopen = function () {
        bus = eb;
        console.log("Event bus ready");
        browser_query();
        invoke_chain();
    };
});


$('#mytabs').find('a').click(function(e) {
    e.preventDefault();
    $(this).tab('show');
});

$('#refresh-browser').click(function() {
    browser_query();
    return false;
});
$('#refresh-browser2').click(function() {
    browser_query();
    return false;
});


$('#refresh-chain').click(function() {
    $('#service-chain').text("Loading...");
    invoke_chain();
    return false;
});

$('#refresh-chain2').click(function() {
    $('#service-chain').text("Loading...");
    invoke_chain();
    return false;
});


$('#refresh-apigateway').click(function() {
    $('#api-gateway').text("Loading...");
    api_gateway();
    return false;
});

$('#refresh-apigateway2').click(function() {
    $('#api-gateway').text("Loading...");
    api_gateway();
    return false;
});

