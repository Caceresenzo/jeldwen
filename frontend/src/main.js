import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import axios from './plugins/axios.js';
import i18n from './plugins/i18n';

import 'material-design-icons-iconfont/dist/material-design-icons.css'

import VuetifyDialogPromise from "vuetify-dialog-promise";
Vue.use(VuetifyDialogPromise);

import VueApexCharts from 'vue-apexcharts'
Vue.use(VueApexCharts)

Vue.component('apexchart', VueApexCharts)

Vue.config.productionTip = false

Vue.prototype.$http = axios

import Mixins from './plugins/mixins'
Vue.use(Mixins)

import LoadingCard from './components/LoadingCard'
Vue.component(LoadingCard.name, LoadingCard);

import ListCard from './components/ListCard'
Vue.component(ListCard.name, ListCard);

import Empty from './components/Empty'
Vue.component(Empty.name, Empty);

new Vue({
	router,
	store,
	vuetify,
	i18n,
	render: h => h(App)
}).$mount('#app')
