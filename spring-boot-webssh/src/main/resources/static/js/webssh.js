function WSSHClient() {
};
WSSHClient.prototype._generateEndpoint = function () {
    if (window.location.protocol == 'https:') {
        var protocol = 'wss://';
    } else {
        var protocol = 'ws://';
    }
    var endpoint = protocol + '127.0.0.1:8080/webssh';
    return endpoint;
}
WSSHClient.prototype.connect = function (options) {
    var endpoint = this._generateEndpoint();
    if (window.WebSocket) {
        //如果支持websocket
        this._connection = new WebSocket(endpoint);
    } else {
        //否则报错
        options.rollbackError('WebSocket Not Supported');
        return;
    }
    //WebSocket 链接
    this._connection.onopen = function () {
        options.rollbackConnect();
    }
    //发送消息
    this._connection.onmessage = function (evt) {
        var data = evt.data.toString();
        options.rollbackSshMessage(data);
    }
    //链接关闭
    this._connection.onclose = function (evt) {
        options.rollbackClose();
    }
}
//发送消息
WSSHClient.prototype.send = function (data) {
    this._connection.send(JSON.stringify(data))
}
//SSH链接
WSSHClient.prototype.sshConnection = function (options) {
    //连接参数
    this._connection.send(JSON.stringify(options))
}
//发送指令
WSSHClient.prototype.sendMessage = function (data) {
    //发送指令
    this._connection.send(JSON.stringify({"operate": "command", "command": data}))
}
var client = new WSSHClient();
