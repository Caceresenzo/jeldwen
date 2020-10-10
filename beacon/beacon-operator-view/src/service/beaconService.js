import EventEmitter from 'events'
import socketService from "./socketService"

class BeaconService extends EventEmitter {

	constructor() {
		super();

		socketService.addListener("onSocketMessage", (message) => this.onSocketMessage(message));

		this.dispatch = {
			"workstation-state": (message) => this.onWorkstationState(message),
			"rhythm-sync": (message) => this.onRhythmSync(message),
			"family-changed": (message) => this.onFamilyChanged(message),
			"config": (message) => this.onConfig(message),
		}
	}

	onSocketMessage(message) {
		let callback = this.dispatch[message.name];

		if (callback) {
			callback(message);
		}
	}

	onWorkstationState(message) {
		if (message.opened) {
			this.onWorkstationOpen();
		} else {
			this.onWorkstationClose();
		}

		this.onConfig(message);
		this.onFamilyChanged(message);
		this.onRhythmSync(message);
	}

	onRhythmSync(message) {
		this.emit("onRhythmSync", message.seconds, message.currentHourPerHour);
	}

	onFamilyChanged(message) {
		this.emit("onFamilyChanged", message.activeFamilyId);
	}

	onWorkstationOpen() {
		this.emit("onWorkstationOpen");
	}

	onWorkstationClose() {
		this.emit("onWorkstationClose");
	}

	onConfig(message) {
		this.emit("onConfig", message.beaconConfig);
	}

	open() {
		socketService.request("workstation-open");
	}

	close() {
		socketService.request("workstation-close");
	}

	changeFamily(familyId) {
		socketService.request("family-change", { familyId });
	}

	report(stopReasonId) {
		socketService.request("report", { stopReasonId });
	}

}

window.x = new BeaconService();

export default window.x;