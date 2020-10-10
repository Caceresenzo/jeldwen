import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';

import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.config.productionTip = false

import Mixins from './plugins/mixins'
Vue.use(Mixins)

import Empty from './components/Empty'
Vue.component(Empty.name, Empty);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
