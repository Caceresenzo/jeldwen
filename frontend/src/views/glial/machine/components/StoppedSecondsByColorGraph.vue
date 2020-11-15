<template>
	<apexchart type="line" :height="height" :options="options" :series="series"></apexchart>
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
			let names = ["yellowStop", "orangeStop", "pinkStop", "greenStop", "blueStop", "otherStop", "totalStop", "nonQualifiedStop"];

			let series = names.map((x) => ({
				name: this.$t(`glial.machines.fields.${x}`),
				data: [],
			}));

			for (let index in names) {
				let name = names[index];
				let serie = series[index];

				for (let entry of this.entries) {
					serie.data.push(entry[name]);
				}
			}

			return series;
		},
		options() {
			return {
				chart: {
					height: 350,
					type: "line",
					zoom: {
						enabled: false,
					},
					background: this.$vuetify.theme.dark ? "#1E1E1E" : "#FFFFFF",
					toolbar: {
						show: false,
					},
				},
				theme: {
					mode: this.$vuetify.theme.dark ? "dark" : "light",
				},
				dataLabels: {
					enabled: false,
				},
				stroke: {
					curve: "smooth",
				},
				colors,
				xaxis: {
					categories: this.entries.map((x) => x.hour),
					tooltip: {
						enabled: false,
					},
				},
				yaxis: {
					tickAmount: 1,
					labels: {
						formatter: (val) => {
							return `${val} ${this.$tc('glial.machines.tooltips.seconds', val)}`;
						},
					},
				},
				tooltip: {
					x: {
						show: false
					}
				}
			};
		},
	},
};
</script>