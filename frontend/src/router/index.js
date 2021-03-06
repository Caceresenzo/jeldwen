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
		path: '/beacons',
		name: 'Beacons',
		component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/Beacons.vue')
	},
	{
		path: '/beacon/:id',
		name: 'Beacon Details',
		component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/BeaconDetails.vue')
	},
	{
		path: '/beacon/:id/edit',
		name: 'Beacon Edit',
		component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/BeaconEdit.vue')
	},
	{
		path: '/beacon/stop-reason/group/:id',
		name: 'Beacon Group Details',
		component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/StopReasonGroupDetails.vue')
	},
	{
		path: '/glial/overview',
		name: 'Glial Machine Overview',
		component: () => import(/* webpackChunkName: "machine" */ '../views/glial/machine/MachinesOverview.vue')
	},
	{
		path: '/glial/settings',
		name: 'Glial Machine Settings',
		component: () => import(/* webpackChunkName: "machine" */ '../views/glial/machine/MachineSettings.vue')
	},
	{
		path: '/glial/machine/:name',
		name: 'Glial Machines Details',
		component: () => import(/* webpackChunkName: "machine" */ '../views/glial/machine/MachineDetails.vue')
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
