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
		component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/BeaconDetails.vue')
	},
	{
		path: '/beacon/:id/edit',
		component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/BeaconEdit.vue')
	},
	{
		path: '/beacon/stop-reason/group/:id',
		component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/StopReasonGroupDetails.vue')
	},
	// {
	// 	path: '/beacon',
	// 	name: 'Beacon',
	// 	component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/BeaconPage.vue')
	// },
	// {
	// 	path: '/beacon/:id',
	// 	component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/BeaconDetails.vue')
	// },
	// {
	// 	path: '/beacon/detector/:id',
	// 	component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/DetectorEdit.vue')
	// },
	// {
	// 	path: '/beacon/stop-reason-group/:id',
	// 	component: () => import(/* webpackChunkName: "beacons" */ '../views/beacon/StopReasonGroupDetails.vue')
	// },
	// {
	// 	path: '/operator-selector',
	// 	component: () => import(/* webpackChunkName: "operator" */ '../views/operator/OperatorBeaconSelector.vue')
	// },
	// {
	// 	path: '/operator/:id',
	// 	component: () => import(/* webpackChunkName: "operator" */ '../views/operator/OperatorRoom.vue')
	// },
	// {
	// 	path: '/about',
	// 	name: 'About',
	// 	component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
	// },
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
