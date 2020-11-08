<template>
	<div>
		<v-subheader>
			{{ $t("route.machines._") }}
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
		<div v-if="machines.length">
			<v-list-item v-for="machine in machines" :key="machine" link :to="`/machine/${machine}`">
				<v-list-item-action>
					<v-icon>mdi-washing-machine</v-icon>
				</v-list-item-action>
				<v-list-item-content>
					<v-list-item-title>{{ machine }}</v-list-item-title>
				</v-list-item-content>
			</v-list-item>
		</div>
		<v-list-item v-else-if="!error">
			<v-list-item-action>
				<v-icon>mdi-washing-machine-off</v-icon>
			</v-list-item-action>
			<v-list-item-content>
				<v-list-item-title>No machine</v-list-item-title>
			</v-list-item-content>
		</v-list-item>
	</div>
</template>

<script>
export default {
	name: "machine-list",
	computed: {
		machines() {
			return this.$store.state.machine.items;
		},
		loading() {
			return this.$store.state.machine.loading;
		},
		error() {
			return this.$store.state.machine.error;
		},
	},
	methods: {
		refresh() {
			return this.$store.dispatch("machine/fetch");
		},
	},
};
</script>