var contextRoot2 = "/" + window.location.pathname.split('/')[1];
var url = window.location.pathname;
var contextRoot = 'http://localhost:8080';

var socket = new SockJS(contextRoot+"/gkz-stomp-endpoint");
var stompClient = Stomp.over(socket);

// Callback function[renderPrice] to be called when stomp client is connected to
// server
var connectCallback = function() {
	console.log(' >>> table rows: ',$('#notify-table > tbody > tr').length)
	if ($('#notify-table > tbody > tr').length == 0){
		$('#notify-table > thead > th').css('display','none');
	}
	console.log('***** subscript onconnectCallback:  /notifications/admin')
	stompClient.subscribe('/notifications/admin', renderObject);

};

// Render price data from server into HTML, registered as callback
// when subscribing to price topic
function renderObject(message) {
	console.log('>>>>(renderPrice) web socket body recieved : ', JSON.parse(message.body));
	let adminNotification = JSON.parse(message.body);

	var table = document.getElementById("notify-table");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	cell1.innerHTML = adminNotification.type;
	cell2.innerHTML = adminNotification.user.name;
	cell3.innerHTML = adminNotification.text;
}

// Callback function to be called when stomp client could not connect to server
var errorCallback = function(error) {
	// alert(error);
};

// Connect to server via websocket
// with function to display stock in banner[connectCallback]
// and function to handle errors [errorCallback]
stompClient.connect("guest", "guest", connectCallback, errorCallback);

