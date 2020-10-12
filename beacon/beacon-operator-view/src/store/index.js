import Vue from 'vue'
import Vuex from 'vuex'

import i18n from '@/plugins/i18n';

import application from "@/../application.js";

Vue.use(Vuex);

function sum(historyMap, current, lower, upper) {
	const out = {
		open: 0,
		stop: 0,
		produced: 0,
		objective: 0,
		percent: 0,
	};

	let n = upper - lower;
	if (lower > upper) {
		n = 24 - lower + upper;
	}

	for (let offset = 0; offset < n; offset++) {
		let index = lower + offset;
		if (index > 23) {
			index = index - 24;
		}

		let item = historyMap[index];
		if (!item && index == new Date().getHours()) {
			item = current;
		}

		if (item) {
			out.open += item.open || 0;
			out.stop += item.stop || 0;
			out.produced += item.produced || 0;
			out.objective += item.objective || 0;
		}
	}

	out.percent = (out.produced + 1) / (out.objective + 1);

	return out;
};

export default new Vuex.Store({
	state: {
		opened: false,
		firstConnect: false,
		connected: false,
		config: {
			name: null,
			stopReasons: [],
			stopReasonCategories: [],
			productFamilies: [],
			syntheticPerformanceRateThreshold: 0.0,
		},
	},
	getters: {
		isConfigured: (state) => state?.config?.name !== undefined,
		beaconName: (state) => state?.config?.name,
	},
	mutations: {
		setConnected(state, val) {
			state.connected = val;
			state.firstConnect |= val;
		},
		setOpened(state, val) {
			state.opened = val;
		},
		setConfig: (state, config) => state.config = config,
	},
	actions: {
	},
	modules: {
		cycle: {
			namespaced: true,
			state: {
				seconds: null,
				activeFamily: null,
			},
			getters: {
				max(state, getters, rootState) {
					if (state.activeFamily !== null) {
						for (let productFamily of rootState.config?.productFamilies) {
							if (productFamily.id == state.activeFamily) {
								return productFamily.cycleTime || 0;
							}
						}
					}

					return 0;
				}
			},
			mutations: {
				setSeconds: (state, seconds) => state.seconds = seconds,
				setActiveFamily: (state, family) => state.activeFamily = family.id,
				setActiveFamilyId: (state, id) => state.activeFamily = id
			},
			actions: {
			}
		},
		hourPerHour: {
			namespaced: true,
			state: {
				history: {
					map: {},
					size: 0
				},
				offset: {
					value: 0,
					max: 0
				},
				current: null
			},
			getters: {
				data(state) {
					let entries = [];

					const { backward, forward } = application.ui.hourPerHour.past;
					
					const now = new Date().getHours();
					for (let offset = -backward; offset < forward; offset++) {
						let hour = now + offset + (state.offset.value - state.offset.max);

						if (hour > 23) {
							hour -= 24;
						} else if (hour < 0) {
							hour += 23;
						}

						let item = state.current;
						if (!item || hour != now) {
							item = state.history.map[hour];
						}

						entries.push({
							...(item || {}),
							hour,
						});
					}

					return entries;
				},
				summary(state, getters) {
					getters['data'];

					return [
						{
							hour: i18n.t("controls.hourPerHour.sums.morning"),
							...sum(state.history.map, state.history.current, 5, 13),
						},
						{
							hour: i18n.t("controls.hourPerHour.sums.evening"),
							...sum(state.history.map, state.history.current, 13, 21),
						},
						{
							hour: i18n.t("controls.hourPerHour.sums.night"),
							...sum(state.history.map, state.history.current, 21, 5),
						},
					];
				},
				total(state, getters) {
					getters['data'];

					const now = new Date().getHours();
					return [
						{
							hour: i18n.t("controls.hourPerHour.sums.total"),
							...sum(state.history.map, state.history.current, now, now + 24),
						},
					];
				}
			},
			mutations: {
				setHistory(state, history) {
					state.history.map = history.reduce((result, item) => {
						result[item.hour] = item;
						return result;
					}, {});

					const n =  history.length;

					state.history.size = n;
					state.offset.value = n;
					state.offset.max = n - (application.ui.hourPerHour.past.backward + 1);
				},
				setCurrent: (state, current) => state.current = current,
				setOffset: (state, offset) => state.offset.value = offset || 0
			},
			actions: {
			}
		}
	}
});
