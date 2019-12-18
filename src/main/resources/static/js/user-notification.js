var ip = "http://localhost";
var baseUrl = ip + ":8080";
var webSocketPath = baseUrl + '/gkz-stomp-endpoint';


function userConnect() {
    console.log('>>>>> user want to subscrip: ');
    const socket = new SockJS(webSocketPath);
    var stompClient = Stomp.over(socket);

    console.log('>>start user connection for user notifications <<<<');
    stompClient.connect({}, function (frame) {

        console.log('>>----- conect on: notifications');
        _this.stompClient.subscribe("/notifications" , function (data) {
            let Notification = JSON.parse(data.body);

            console.log(' =======++++++>>> get new Notifications by pushing: ', Notification);
        });



    });
}