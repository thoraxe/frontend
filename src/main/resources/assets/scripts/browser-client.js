var client_calls = 0;

var services = [{
    'address': 'hola',
    'id': 'hola-service'
}, {
    'address': 'hello',
    'id': 'hello-service'
}, {
    'address': 'ola',
    'id': 'ola-service'
}, {
    'address': 'namaste',
    'id': 'namaste-service'
}, {
    'address': 'bonjour',
    'id': 'bonjour-service'
}];

function invoke(address, id) {
    bus.send(address, "vert.x - " + client_calls, function (err, msg) {
        if (msg) {
            $('#' + id).text(msg.body[address]);
        } else {
            $('#' + id).text('Error getting value from service ' + id);
        }
    });
    client_calls = client_calls + 1;
}

function browser_query() {
    var x;
    //Clear all responses
    for (x = 0; x < services.length; x++) {
        $('#' + services[x].id).text("Loading...");
    }
    //Make the invocation
    for (x = 0; x < services.length; x++) {
        invoke(services[x].address, services[x].id)
    }
}