var ip = "http://localhost";
var baseUrl = ip + ":8080";
var webSocketPath = baseUrl + '/gkz-stomp-endpoint';


function userConnect() {

    let email = document.getElementById('currentUserEmail').innerText;
    console.log('>>>>> user want to subscrip: ', email);
    const socket = new SockJS(webSocketPath);
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        stompClient.subscribe("/notifications/" + email, function (data) {
            let notif = JSON.parse(data.body);

            console.log(' =======++++++>>> get new Notifications by pushing: ', Notification);

            let dec = "<div id='notifications-dialog' >";
        // <a href='/user/post/"+post.id+"'>
            let p1 = "<a  class='navbar-item' > " +
                " <p style='color: crimson;' text='" + notif.postOwnerName + "'>" + notif.postOwnerName + " </p> &nbsp; <span>add new post</span><br> </a>" +
                "<a class='navbar-item' > <p><span>" + notif.postText + "</span> </p> </a>" +
                " <hr class='navbar-divider'>";

            let num = document.getElementById("notification-num").innerText;
            num = parseInt(num, 10) + 1;
            document.getElementById("notification-num").innerText = num;
            let element = document.getElementById("notifications-dialog");
            if (element != null) {
                element.innerHTML += p1;
            } else {
                element = document.getElementById("notifications-parent");
                let body = dec + p1 + '</div>';

                element.innerHTML = body;
            }
        });


    });
}