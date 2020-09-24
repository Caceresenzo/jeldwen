import EventEmitter from 'events'
import socketService from "./socketService"

class BeaconService extends EventEmitter {

	constructor() {
		super();

		socketService.addListener("onSocketMessage", (message) => this.onSocketMessage(message));

		this.dispatch = {
			"beacon-connected": (message) => this.onBeaconConnect(message),
			"beacon-disconnected": (message) => this.onBeaconDisconnect(message),
			"connected-beacon-list": (message) =>  this.updateConnected(message.uniques)
		}

		this.connected = {};
	}

	onSocketMessage(message) {
		let callback = this.dispatch[message.name];

		if (callback) {
			callback(message);
		}
	}

	onBeaconConnect(message) {
		let unique = message.unique;

		this.connected[unique] = true;

		this.emit("onBeaconConnected", unique);
	}

	onBeaconDisconnect(message) {
		let unique = message.unique;

		delete this.connected[message.unique];

		this.emit("onBeaconDisconnected", unique);
	}

	updateConnected(uniques) {
		this.connected = {};

		uniques.forEach((unique) => this.connected[unique] = true);
	}

	isConnected(unique) {
		return this.connected[unique];
	}

}

window.x = new BeaconService();

export default window.x;