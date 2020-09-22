<template>
	<v-app>
		<v-navigation-drawer v-model="drawer" app clipped>
			<v-list dense>
				<v-list-item link to="/">
					<v-list-item-action>
						<v-icon>mdi-view-dashboard</v-icon>
					</v-list-item-action>
					<v-list-item-content>
						<v-list-item-title>Dashboard</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				<v-list-item link to="/beacons">
					<v-list-item-action>
						<v-icon>mdi-view-dashboard</v-icon>
					</v-list-item-action>
					<v-list-item-content>
						<v-list-item-title>Beacons</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>

		<v-app-bar app clipped-left>
			<v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
			<v-toolbar-title>JELD-WEN</v-toolbar-title>
			<v-spacer></v-spacer>
			<v-btn :color="socketState.color" v-text="socketState.message">CONNECTED</v-btn>
		</v-app-bar>

		<v-main>
			<transition name="fade" mode="out-in">
				<router-view></router-view>
			</transition>
		</v-main>

		<v-footer app>
			<span>&copy; {{ new Date().getFullYear() }}</span>
		</v-footer>
	</v-app>
</template>

<script>
import Vue from "vue";

export default {
	name: "App",
	data: () => ({
		drawer: null,
		socketState: {
			message: "WAITING",
			color: "warning",
		},
	}),
	methods: {
		onSocketConnect() {
			this.socketState.message = "CONNECTING...";
			this.socketState.color = "warning";
		},
		onSocketOpen() {
			this.socketState.message = "CONNECTED";
			this.socketState.color = "success";
		},
		onSocketClose() {
			this.socketState.message = "NOT CONNECTED";
			this.socketState.color = "error";
		},
		onSocketError(error) {
			this.socketState.message = "ERROR: " + error;
			this.socketState.color = "error";
		},
	},
	created() {
		this.$vuetify.theme.dark = true;
	},
	mounted() {
		this.$socket.start();
	},
};
</script>

<style scoped>
	.fade-enter-active,
	.fade-leave-active {
		transition-duration: 0.3s;
		transition-property: opacity;
		transition-timing-function: ease;
	}

	.fade-enter,
	.fade-leave-active {
		opacity: 0;
	}
</style>