import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
	theme: {
		primary: '#1D446B',
		dark: localStorage.getItem("dark") === "true"
	},
});
