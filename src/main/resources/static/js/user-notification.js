var ip = "http://localhost";
var baseUrl = ip + ":8085";
var webSocketPath = baseUrl + '/gkz-stomp-endpoint';


function userConnect(userName) {
    console.log('>>>>> user want to subscrip: ',userName);
    const socket = new SockJS(webSocketPath);
    var stompClient = Stomp.over(socket);

    console.log('>>start user connection for user notifications <<<<');
    stompClient.connect({}, function (frame) {

        console.log('>>----- conect on: notifications');
        _this.stompClient.subscribe("/user/notifications" + userName, function (data) {
            let Notification = JSON.parse(data.body);

            console.log(' =======++++++>>> get new Notifications by pushing: ', Notification);
        });



    });
}