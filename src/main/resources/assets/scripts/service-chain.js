var chain_calls = 0;

function invoke_chain() {
    bus.send("hello/chain", "bob - " + chain_calls, function (err, msg) {
        var element = $('#service-chain');
        element.empty();
        if (msg) {
            var str = "<ul>";
            $.each(msg.body, function(key, value) {
                str += "<li>" + key + " : " + value + "</li>";
            });
            str += "</ul>";
            element.append(str);
        } else {
            console.log(err);
            element.append("Cannot invoke the chain");
        }
    });
    chain_calls = chain_calls + 1;
}

