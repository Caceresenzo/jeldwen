import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'Home',
		component: Home
	},
	{
		path: '/machine/:name',
		name: 'Machine Details',
		component: () => import(/* webpackChunkName: "machine" */ '../views/machine/MachineDetails.vue')
	},
	{
		path: '/settings',
		name: 'Settings',
		component: () => import(/* webpackChunkName: "settings" */ '../views/settings/Settings.vue')
	},
	{
		path: '*',
		name: '404',
		component: () => import(/* webpackChunkName: "error" */ '../views/404.vue')
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
