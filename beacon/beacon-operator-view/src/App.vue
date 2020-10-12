<template>
	<v-app id="inspire">
		<v-navigation-drawer v-model="drawer" app disable-resize-watcher disable-route-watcher clipped>
			<v-list dense>
				<i18n-setting />
				<dark-mode-setting />
				<update-config-action />
			</v-list>
		</v-navigation-drawer>

		<v-app-bar app color="primary" class="white--text" flat dense clipped-left>
			<v-app-bar-nav-icon @click.stop="drawer = !drawer" color="white">
				<v-icon>mdi-cog</v-icon>
			</v-app-bar-nav-icon>
			<v-toolbar-title>{{ $store.getters.beaconName || $t('controls.emptyName') }}</v-toolbar-title>
			<v-spacer></v-spacer>
			<socket-state />
		</v-app-bar>

		<v-main>
			<transition name="fade" mode="out-in">
				<router-view></router-view>
			</transition>
		</v-main>

		<v-footer app style="z-index: 100" dark>
			<v-spacer></v-spacer>
			<span>{{ $t("version") }}</span>
		</v-footer>
	</v-app>
</template>

<script>
import SocketState from "@/views/components/SocketState";
import UpdateConfigAction from "@/views/components/drawer/UpdateConfigAction";
import I18nSetting from "@/views/components/drawer/I18nSetting";
import DarkModeSetting from "@/views/components/drawer/DarkModeSetting";

export default {
	components: {
		SocketState,
		UpdateConfigAction,
		I18nSetting,
		DarkModeSetting,
	},
	name: "App",
	data: () => ({
		drawer: false,
	}),
	mounted() {
		this.$socket.start();
	},
};
</script>

<style>
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
	html {
		overflow-y: auto;
	}
</style>