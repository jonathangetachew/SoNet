
var ip = "http://localhost";
var baseUrl = ip + ":8080";
var webSocketPath = baseUrl + '/gkz-stomp-endpoint';


function adminConnect() {
	console.log('>>>>> admin want to subscrip: ');
	const socket = new SockJS(webSocketPath);
	var stompClient = Stomp.over(socket);

	console.log('>>start admin connection for user notifications <<<<');
	stompClient.connect({}, function (frame) {

		console.log('>>>> ===== connect topicnotify ===== <<<<<');
		stompClient.subscribe('/notifications/admin', function (message) {
			console.log('>>>> web socket body recieved : ', JSON.parse(message.body));
			let adminNotification = JSON.parse(message.body);

			var table = document.getElementById("notify-table");
			var row = table.insertRow(1);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);
			cell1.innerHTML = adminNotification.type;
			cell2.innerHTML = adminNotification.user.name;
			cell3.innerHTML = adminNotification.text;
		});

	});


}
// ============================================================
// var url = window.location.pathname;
// var contextRoot = 'http://localhost:8080';
//
// var socket = new SockJS(contextRoot+"/gkz-stomp-endpoint");
// var stompClient = Stomp.over(socket);
//
// var connectCallback = function() {
// 	console.log('***** subscript onconnectCallback:  /notifications/admin')
// 	stompClient.subscribe('/notifications/admin/', renderObject);
//
// };
//
// function renderObject(message) {
// 	console.log('>>>> web socket body recieved : ', JSON.parse(message.body));
// 	let adminNotification = JSON.parse(message.body);
//
// 	var table = document.getElementById("notify-table");
// 	var row = table.insertRow(1);
// 	var cell1 = row.insertCell(0);
// 	var cell2 = row.insertCell(1);
// 	var cell3 = row.insertCell(2);
// 	cell1.innerHTML = adminNotification.type;
// 	cell2.innerHTML = adminNotification.user.name;
// 	cell3.innerHTML = adminNotification.text;
// }
//
// // Callback function to be called when stomp client could not connect to server
// var errorCallback = function(error) {
// 	console.log('***** error connect onconnectCallback:  /notifications/admin')
// 	console.log(('>>> error: ',error));
// 	// alert(error);
// };
//
// stompClient.connect("guest", "guest", connectCallback, errorCallback);
//
// // function connect() {
// // 	console.log(' ===== connect =====')
// // 	if (socket.readyState !== SockJS.OPEN) {
// // 		setTimeout(connect, 100);
// // 		return;
// // 	}
// //
// // 	stompClient.subscribe('/notifications/admin/', renderObject);
// // }
//
// // var connectCallback2 = function() {
// // 	console.log('***** subscript onconnectCallback:  /notifications/admin')
// // 	stompClient.subscribe('/notifications/admin/', renderObject);
// //
// // };
//
// var subscript = function() {
// 	console.log('>>>> load data page ajax <<<<');
//
// 	if (socket.readyState !== WebSocket.OPEN) {
// 		setTimeout(subscript, 100);
// 		return;
// 	}
// 	stompClient.connect({}, function (frame) {
// 		console.log('*** start subscription <<<')
// 		stompClient.subscribe('/notifications/admin/', function (message) {
// 			console.log('>>>> web socket body recieved : ', JSON.parse(message.body));
// 			let adminNotification = JSON.parse(message.body);
//
// 			var table = document.getElementById("notify-table");
// 			var row = table.insertRow(1);
// 			var cell1 = row.insertCell(0);
// 			var cell2 = row.insertCell(1);
// 			var cell3 = row.insertCell(2);
// 			cell1.innerHTML = adminNotification.type;
// 			cell2.innerHTML = adminNotification.user.name;
// 			cell3.innerHTML = adminNotification.text;
// 		});
// 	});
// }
// ===================================================================


	// document.querySelector('#welcomeForm').addEventListener('click', connect, true)
	// document.querySelector('#dialogueForm').addEventListener('submit', sendMessage, true)
	//
	// var stompClient = null;
	// var name = null;
	//
	// function connect() {
	// 	// name = document.querySelector('#name').value.trim();
	//
	// 	// if (name) {
	// 	// 	document.querySelector('#welcome-page').classList.add('hidden');
	// 	// 	document.querySelector('#dialogue-page').classList.remove('hidden');
	//
	// 		var socket = new SockJS('/gkz-stomp-endpoint');
	// 		stompClient = Stomp.over(socket);
	// 		console.log('>>>> syopmclient -> connect<<<')
	// 		stompClient.connect({}, connectionSuccess);
	// 	// }
	// 	// event.preventDefault();
	// }
	//
	// function connectionSuccess() {
	// 	console.log('>>>> subscrip to /topic/public')
	// 	stompClient.subscribe('/topic/public', onMessageReceived);
	//
	// }
	//
	// function onMessageReceived(payload) {
	// 	var messager = JSON.parse(payload.body);
	//
	// 	console.log(('>>>> rabbidMQ recieveid: ',messager))
	//
	// }
