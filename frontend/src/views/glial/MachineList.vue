<template>
	<div>
		<v-subheader>
			{{ $t("route.glial-machines._") }}
			<v-spacer />
			<v-btn icon x-small :loading="loading" @click="refresh()">
				<v-icon>mdi-refresh</v-icon>
			</v-btn>
		</v-subheader>
		<v-list-item v-if="error" @click="refresh()">
			<v-list-item-action>
				<v-icon>mdi-washing-machine-alert</v-icon>
			</v-list-item-action>
			<v-list-item-content>
				<v-list-item-title>{{ error }}</v-list-item-title>
			</v-list-item-content>
		</v-list-item>
		<template v-if="machines.length">
			<v-list-item link :to="`/glial/overview`">
				<v-list-item-action>
					<v-icon>mdi-graph</v-icon>
				</v-list-item-action>
				<v-list-item-content>
					<v-list-item-title>{{ $t("route.glial-machines.overview") }}</v-list-item-title>
				</v-list-item-content>
			</v-list-item>
			<v-list-item v-for="machine in machines" :key="machine.machine" link :to="`/glial/machine/${machine.machine}`">
				<!-- TODO: Change for better variable names, use beacon instead of machine? -->
				<v-list-item-action>
					<v-icon>mdi-washing-machine</v-icon>
				</v-list-item-action>
				<v-list-item-title>
					{{ machine.machine }}
				</v-list-item-title>
				<v-list-item-icon v-if="machine.url">
					<v-btn icon @click="openExternal(machine.url)">
						<v-icon>mdi-open-in-new</v-icon>
					</v-btn>
				</v-list-item-icon>
			</v-list-item>
		</template>
		<v-list-item v-else-if="!error">
			<v-list-item-action>
				<v-icon>mdi-washing-machine-off</v-icon>
			</v-list-item-action>
			<v-list-item-content>
				<v-list-item-title>No machine</v-list-item-title>
			</v-list-item-content>
		</v-list-item>
		<v-list-item link :to="`/glial/settings`">
			<v-list-item-action>
				<v-icon>mdi-cog</v-icon>
			</v-list-item-action>
			<v-list-item-content>
				<v-list-item-title>{{ $t("route.glial-machines.settings") }}</v-list-item-title>
			</v-list-item-content>
		</v-list-item>
	</div>
</template>

<script>
export default {
	name: "glial-machine-list",
	computed: {
		machines() {
			return this.$store.state.glial.machine.items;
		},
		loading() {
			return this.$store.state.glial.machine.loading;
		},
		error() {
			return this.$store.state.glial.machine.error;
		},
	},
	methods: {
		refresh() {
			return this.$store.dispatch("glial/machine/fetch");
		},
		openExternal(url) {
			var win = window.open(url, "_blank");
			win.focus();
		},
	},
};
</script>