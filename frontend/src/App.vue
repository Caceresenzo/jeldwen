<template>
	<v-app>
		<v-navigation-drawer v-model="drawer" app clipped>
			<v-list dense>
				<v-list-item link to="/">
					<v-list-item-action>
						<v-icon>mdi-view-dashboard</v-icon>
					</v-list-item-action>
					<v-list-item-content>
						<v-list-item-title>{{ $t("route.home") }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				<v-subheader>{{ $t("route.beacons._") }}</v-subheader>
				<v-list-item link to="/beacons">
					<v-list-item-action>
						<v-icon>mdi-car-light-high</v-icon>
					</v-list-item-action>
					<v-list-item-content>
						<v-list-item-title>{{ $t("route.beacons.editor") }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				<glial-machine-list />
				<v-subheader>{{ $t("route.others._") }}</v-subheader>
				<v-list-item link to="/settings">
					<v-list-item-action>
						<v-icon>mdi-cog</v-icon>
					</v-list-item-action>
					<v-list-item-content>
						<v-list-item-title>{{ $t("route.others.settings") }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>

		<v-app-bar app clipped-left>
			<v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
			<v-toolbar-title>JELD-WEN</v-toolbar-title>
			<v-spacer></v-spacer>
			<socket-state />
		</v-app-bar>

		<v-main>
			<transition name="fade" mode="out-in">
				<router-view></router-view>
			</transition>
		</v-main>

		<v-footer app>
			<v-spacer />
			<span>&copy; 2020-{{ new Date().getFullYear() }}
				--
				{{ $t('copyright.by') }} <a href="https://github.com/Caceresenzo" target="_blank">Enzo CACERES</a>
				{{ $t('copyright.for') }} <a href="https://www.jeld-wen.com/" target="_blank">JELD-WEN</a>
				(<a href="https://github.com/Caceresenzo/jeldwen" target="_blank">{{ $t('copyright.source-code') }}</a>)
			</span>
		</v-footer>
	</v-app>
</template>

<script>
import SocketState from "./views/components/SocketState";
import GlialMachineList from "./views/glial/MachineList";

export default {
	components: {
		SocketState,
		GlialMachineList
	},
	name: "App",
	data: () => ({
		drawer: null,
	}),
	mounted() {
		this.$socket.start();
		this.$store.dispatch("glial/machine/fetch");
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