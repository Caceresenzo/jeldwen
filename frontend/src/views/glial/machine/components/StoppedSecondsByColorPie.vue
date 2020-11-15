<template>
	<apexchart type="pie" :height="height" :options="options" :series="series"></apexchart>
</template>

<script>
import colors from "../colors"

export default {
	name: "stopped-seconds-by-color-graph",
	props: {
		height: {
			type: [Number, String],
			default: 350
		},
		entries: {
			type: Array,
			default: () => [],
		},
	},
	computed: {
		series() {
			let names = ["yellowStop", "orangeStop", "pinkStop", "greenStop", "blueStop", "otherStop"];

			let series = names.map((x) => []);

			for (let index in names) {
				let name = names[index];
				let serie = series[index];

				for (let entry of this.entries) {
					serie.push(entry[name]);
				}
			}

			return series.map((x) => x.reduce((a, b) => a + b, 0));
		},
		options() {
			return {
				chart: {
					height: 350,
					type: "pie",
					zoom: {
						enabled: false,
					},
					background: this.$vuetify.theme.dark ? "#1E1E1E" : "#FFFFFF",
					toolbar: {
						show: false,
					},
				},
				colors,
				theme: {
					mode: this.$vuetify.theme.dark ? "dark" : "light",
				},
				labels: ["yellowStop", "orangeStop", "pinkStop", "greenStop", "blueStop", "otherStop"].map((x) => this.$t(`glial.machines.fields.${x}`)),
				legend: {
					position: "bottom",
				},
				yaxis: {
					tickAmount: 1,
					labels: {
						formatter: (val) => {
							return `${val} ${this.$tc('glial.machines.tooltips.seconds', val)}`;
						},
					},
				},
			};
		},
	},
};
</script>