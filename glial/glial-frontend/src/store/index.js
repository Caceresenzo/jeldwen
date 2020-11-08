import Vue from 'vue'
import Vuex from 'vuex'

import i18n from '@/plugins/i18n';
import http from '@/plugins/axios';

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
	},
	mutations: {
	},
	actions: {
	},
	modules: {
	},
	modules: {
		machine: {
			namespaced: true,
			state: {
				loading: false,
				error: null,
				items: [],
			},
			mutations: {
				setMachines: (state, machines) => state.items = machines,
				setError: (state, error) => state.error = error,
				setLoading: (state, loading) => state.loading = loading,
			},
			actions: {
				fetch({ commit, state }) {
					if (state.loading) {
						return;
					}

					commit('setLoading', true);

					http.get("/machines")
						.then((response) => {
							commit('setMachines', response.data.payload);
							commit('setError', null);
						})
						.catch((error) => commit('setError', error))
						.then(() => commit('setLoading', false));
				}
			}
		},
	}
})
