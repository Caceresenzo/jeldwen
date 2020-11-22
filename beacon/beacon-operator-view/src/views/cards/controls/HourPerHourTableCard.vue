<template>
	<v-card outlined>
		<v-data-table :headers="headers" :items="items" item-key="hour" :value="selected" :disable-sort="true" :hide-default-footer="true" class="fixed-table">
			<template v-for="h in headers" v-slot:[`header.${h.value}`]="{ header }">
				<h2 :key="h.value">{{ $t(`controls.hourPerHour.table.${header.value}`) }}</h2>
			</template>
			<template v-slot:item.hour="{ item }">
				<div v-if="typeof item.hour === 'string'" v-text="item.hour" />
				<div v-else>
					{{ formatHour(item.hour) }}
					<br />
					{{ formatHour(item.hour + 1) }}
				</div>
			</template>
			<template v-slot:item.open="{ item }">
				<div v-if="item.open !== undefined">{{ formatTime(item.open) }}</div>
			</template>
			<template v-slot:item.stop="{ item }">
				<div v-if="item.stop !== undefined">{{ formatTime(item.stop) }}</div>
			</template>
			<template v-slot:item.percent="{ item }">
				<v-chip v-if="item.percent" :color="item.percent > threshold ? 'success' : 'error'">{{ formatPercent(item.percent) }}</v-chip>
			</template>
		</v-data-table>
		<slot name="bottom" />
	</v-card>
</template>

<script>
export default {
	name: "hour-per-hour-table-card",
	props: {
		getter: {
			type: String,
			required: true,
			validator: (val) => ["data", "summary", "total"].includes(val),
		},
		selected: {
			type: Array,
			default: () => [],
		},
	},
	data: () => ({
		headers: [
			{
				align: "center",
				divider: true,
				value: "hour",
				width: "15%",
			},
			{
				align: "center",
				divider: true,
				value: "open",
			},
			{
				align: "center",
				divider: true,
				value: "stop",
			},
			{
				align: "center",
				divider: true,
				value: "produced",
			},
			{
				align: "center",
				divider: true,
				value: "objective",
			},
			{
				align: "center",
				divider: true,
				value: "percent",
			},
		],
	}),
	computed: {
		items() {
			return this.$store.getters[`hourPerHour/${this.getter}`];
		},
		threshold() {
			return this.$store.state.config?.syntheticPerformanceRateThreshold || 0;
		},
	},
	methods: {
		formatTime(seconds) {
			let seconds60 = seconds % 60;
			let minutes = (seconds - seconds60) / 60;

			return `${minutes.toString().padStart(2, "0")}:${seconds60.toString().padStart(2, "0")}`;
		},
		formatHour(hour) {
			return `${hour.toString().padStart(2, "0")}h`;
		},
		formatPercent(percent) {
			return `${(percent * 100).toFixed(2)}%`;
		},
	},
};
</script>

<style scoped>
	.fixed-table >>> table {
		table-layout: fixed;
	}
</style>