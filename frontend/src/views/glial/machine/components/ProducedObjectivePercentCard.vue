<template>
	<v-card>
		<v-card-text class="text-center">
			<small class="mb-2">{{ $t("glial.machines.card.percent") }}</small>
			<br />
			<v-btn icon :loading="loading" x-large color="blue" @click>
				<h1>{{ percent }}%</h1>
			</v-btn>
		</v-card-text>
	</v-card>
</template>

<script>
export default {
	name: "produced-objective-percent-card",
	props: {
		loading: {
			type: Boolean,
			default: false
		},
		entries: {
			type: Array,
			default: () => []
		}
	},
	computed: {
		percent() {
			let produced = 0,
				objective = 0;

			for (let entry of this.entries) {
				produced += entry.produced;
				objective += entry.objective;
			}

			let percent = produced / objective;

			if (isNaN(percent) || !isFinite(percent)) {
				return "--";
			}

			return (percent * 100).toFixed(0);
		},
	}
}
</script>