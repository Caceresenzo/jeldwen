import EventEmitter from 'events'
import store from "@/store"

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
		store.commit('setConnected', true);
	}

	onSocketClose(event) {
		this.emit('onSocketClose', event);
		store.commit('setConnected', false);

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