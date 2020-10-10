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
		this.ws = new WebSocket("ws://localhost:5800");

		this.onSocketConnect();

		this.ws.onclose = (event) => this.onSocketClose(event);
		this.ws.onopen = (event) => this.onSocketOpen(event);
		this.ws.onmessage = (message) => this.onSocketRawMessage(message);
		this.ws.onerror = (error) => this.onSocketError(error);
	}

	onSocketConnect() {
		this.emit('onSocketConnect');
	}

	onSocketOpen(event) {
		this.emit('onSocketOpen', event);
	}

	onSocketClose(event) {
		this.emit('onSocketClose', event);

		setTimeout(() => this.connect(), 1000);
	}

	onSocketError(error) {
		this.emit('onSocketError', error);
	}

	onSocketRawMessage(message) {
		this.emit('onSocketRawMessage', message);

		this.onSocketMessage(message)
	}

	onSocketMessage(message) {
		this.emit('onSocketMessage', JSON.parse(message.data));
	}

	send(payload) {
		this.ws.send(JSON.stringify(payload));
	}

	request(name, properties) {
		this.send({
			type: "REQUEST",
			name,
			...(properties || {})
		});
	}

}

export default new SocketService();