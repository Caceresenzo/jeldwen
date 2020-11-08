<template>
	<v-app>
		<v-navigation-drawer v-model="drawer" app clipped>
			<v-list dense>
				<v-subheader>{{ $t("route.overview._") }}</v-subheader>
				<v-list-item link to="/">
					<v-list-item-action>
						<v-icon>mdi-view-dashboard</v-icon>
					</v-list-item-action>
					<v-list-item-content>
						<v-list-item-title>{{ $t("route.overview.home") }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				<machine-list />
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
			<v-toolbar-title>JELD-WEN GLIAL</v-toolbar-title>
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
import MachineList from "@/views/components/MachineList.vue";

export default {
	name: "App",
	components: {
		MachineList,
	},
	data: () => ({
		drawer: null,
	}),
	mounted() {
		this.$store.dispatch("machine/fetch");
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