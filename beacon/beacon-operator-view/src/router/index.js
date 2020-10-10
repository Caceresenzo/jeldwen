import Vue from 'vue'
import VueRouter from 'vue-router'
import Controls from '../views/Controls.vue'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'Controls',
		component: Controls
	},
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
