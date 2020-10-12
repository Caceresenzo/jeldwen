import EventEmitter from 'events'
import socketService from "./socketService"
import store from "@/store"

class BeaconService extends EventEmitter {

	constructor() {
		super();

		socketService.addListener("onSocketMessage", (message) => this.onSocketMessage(message));

		this.dispatch = {
			"workstation-state": (message) => this.onWorkstationState(message),
			"rhythm-sync": (message) => this.onRhythmSync(message),
			"family-changed": (message) => this.onFamilyChanged(message),
			"config": (message) => this.onConfig(message),
			"reported": (message) => this.onReported(message),
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
		this.onHistory(message);
	}

	onRhythmSync(message) {
		const { seconds, currentHourPerHour } = message;

		this.emit("onRhythmSync", seconds, currentHourPerHour);
		store.commit('cycle/setSeconds', seconds);
		store.commit('hourPerHour/setCurrent', currentHourPerHour);
	}

	onFamilyChanged(message) {
		const id = message.activeFamilyId;

		this.emit("onFamilyChanged", id);
		store.commit('cycle/setActiveFamilyId', id);
	}

	onWorkstationOpen() {
		this.emit("onWorkstationOpen");
		store.commit('setOpened', true);
	}

	onWorkstationClose() {
		this.emit("onWorkstationClose");
		store.commit('setOpened', false);
	}

	onConfig(message) {
		const config = message.beaconConfig;

		this.emit("onConfig", config);
		store.commit('setConfig', config);
	}

	onHistory(message) {
		const history = message.hourPerHourHistory;

		if (history !== null) {
			this.emit("onHistory", history);
			store.commit('hourPerHour/setHistory', history);
		}
	}

	onReported(message) {
		const { success } = message;

		this.emit("onReported", history);
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