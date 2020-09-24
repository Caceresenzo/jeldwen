import EventEmitter from 'events'

class SocketService extends EventEmitter {

	start() {
		if (this.ws) {
			this.ws.close();
		}
		this.ws = null;

		this.connect();
	}

	connect() {
		this.ws = new WebSocket("ws://localhost:5700");

		this.onSocketConnect();

		this.ws.onclose = () => this.onSocketClose();
		this.ws.onopen = () => this.onSocketOpen();
		this.ws.onmessage = (message) => this.onSocketRawMessage(message);
		this.ws.onerror = (error) => this.onSocketError(error);
	}

	onSocketConnect() {
		this.emit('onSocketConnect');
		console.log("onSocketConnect")
	}

	onSocketOpen() {
		this.emit('onSocketOpen');
		console.log("onSocketOpen")
	}

	onSocketClose() {
		this.emit('onSocketClose');
		console.log("onSocketClose")

		setTimeout(() => this.connect(), 1000);
	}

	onSocketError(error) {
		this.emit('onSocketError', error);
		console.log("onSocketError: " + error)
	}

	onSocketRawMessage(message) {
		this.emit('onSocketRawMessage', message);
		console.log("onSocketRawMessage: " + message)

		this.onSocketMessage(message)
	}

	onSocketMessage(message) {
		this.emit('onSocketMessage', JSON.parse(message.data));
		console.log("onSocketMessage: " + JSON.parse(message.data))
	}

}

export default new SocketService();