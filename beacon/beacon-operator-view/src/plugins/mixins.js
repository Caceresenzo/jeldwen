import socketService from '../service/socketService'
import beaconService from '../service/beaconService'

let socketEvents = ["onSocketConnect", "onSocketOpen", "onSocketClose", "onSocketError", "onSocketMessage"]
let beaconEvents = ["onWorkstationOpen", "onWorkstationClose", "onRhythmSync", "onFamilyChanged", "onConfig", "onHistory", "onReported"]

let events = {
	socket: {
		list: socketEvents,
		emitter: socketService
	},
	beacon: {
		list: beaconEvents,
		emitter: beaconService
	},
}

let use = function(method, context) {
	for (let config of Object.values(events)) {
		for (let event of config.list) {
			let listener = context[event];

			if (listener) {
				config.emitter[method](event, listener);

				console.log(`${context.$vnode.tag}: ${method}: ${event}`)
			}
		}
	}
}

export default {
	install(Vue) {
		Vue.prototype.$socket = socketService
		Vue.prototype.$beacon = beaconService

		Vue.mixin({
			created() {
				use("addListener", this);
			},
			destroyed() {
				use("removeListener", this);
			}
		})
	}
}