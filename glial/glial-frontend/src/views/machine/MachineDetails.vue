<template>
	<v-container fluid>
		<v-row>
			<v-col cols="4">
				<v-card>
					<v-card-text class="text-center">
						<small class="mb-2">{{ $t("machines.card.percent") }}</small>
						<br />
						<v-btn icon :loading="loading" x-large color="blue" @click>
							<h1>{{ percent }}%</h1>
						</v-btn>
					</v-card-text>
				</v-card>
			</v-col>
			<v-col cols="4">
				<v-card>
					<v-card-text class="text-center">
						<small class="mb-2">{{ $t("machines.card.csv") }}</small>
						<br />
						<v-btn icon :loading="loading" x-large color="green" :href="`http://localhost:8000/download/${machine}`" target="_blank">
							<v-icon>mdi-download</v-icon>
						</v-btn>
					</v-card-text>
				</v-card>
			</v-col>
			<v-col cols="4">
				<v-card>
					<v-card-text class="text-center">
						<small class="mb-2">{{ $t("machines.card.delete") }}</small>
						<br />
						<v-btn icon :loading="loading" x-large color="red" @click="confirmDelete()">
							<v-icon>mdi-delete</v-icon>
						</v-btn>
					</v-card-text>
				</v-card>
			</v-col>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title>
						<v-dialog ref="dialog" v-model="modal" :return-value.sync="date" persistent width="290px">
							<template v-slot:activator="{ on, attrs }">
								<v-text-field v-model="date" :label="$t('machines.selector.at')" prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
							</template>
							<v-date-picker v-model="date" :events="dates" scrollable>
								<v-spacer></v-spacer>
								<v-btn text color="primary" @click="modal = false"> Cancel </v-btn>
								<v-btn text color="primary" @click="$refs.dialog.save(date)"> OK </v-btn>
							</v-date-picker>
						</v-dialog>
						<v-btn icon :loading="loading" @click="refresh()">
							<v-icon>mdi-refresh</v-icon>
						</v-btn>
					</v-card-title>
					<v-divider />
					<template v-if="entries.length">
						<v-row>
							<v-col cols="8">
								<apexchart type="line" height="350" :options="stopChartOptions" :series="stopSeries"></apexchart>
							</v-col>
							<v-col cols="4">
								<apexchart type="pie" height="350" :options="stopPieChartOptions" :series="stopPieSeries"></apexchart>
							</v-col>
						</v-row>
						<v-divider />
						<apexchart type="line" height="350" :options="percentChartOptions" :series="percentSeries"></apexchart>
					</template>
					<empty v-else />
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
const colors = ["#cbd600", "#F39C12", "#FF00FF", "#00FF00", "#0000FF", "#FFFFFF", "#FF0000", "#C0C0C0"];

export default {
	name: "MachineDetails",
	data: () => ({
		entries: [],
		dates: [],
		loading: false,
		first: true,
		error: null,
		date: null,
		modal: false,
	}),
	computed: {
		machine() {
			return this.$route.params.name;
		},
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
		stopSeries() {
			let names = ["yellowStop", "orangeStop", "pinkStop", "greenStop", "blueStop", "otherStop", "totalStop", "nonQualifiedStop"];

			let series = names.map((x) => ({
				name: this.$t(`machines.fields.${x}`),
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
		stopChartOptions() {
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
						formatter(val) {
							return `${val}s`;
						},
					},
				},
			};
		},
		stopPieSeries() {
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
		stopPieChartOptions() {
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
				labels: ["yellowStop", "orangeStop", "pinkStop", "greenStop", "blueStop", "otherStop"].map((x) => this.$t(`machines.fields.${x}`)),
				legend: {
					
      position: 'bottom',
				}
			};
		},
		percentSeries() {
			let names = ["objective", "produced"];

			let series = names.map((x) => ({
				name: this.$t(`machines.fields.${x}`),
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
		percentChartOptions() {
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
				colors: ["#F39C12", "#0000FF"],
				xaxis: {
					categories: this.entries.map((x) => x.hour),
					tooltip: {
						enabled: false,
					},
				},
				yaxis: {
					tickAmount: 1,
					labels: {
						formatter(val) {
							return `${val}s`;
						},
					},
				},
			};
		},
	},
	watch: {
		date(val, old) {
			this.refresh();
		},
		$route() {
			Object.assign(this.$data, this.$options.data.apply(this));
			this.refresh();
		},
	},
	methods: {
		refresh() {
			if (this.loading) {
				return;
			}

			this.loading = true;

			this.$http
				.get(`/dates/${this.machine}`)
				.then((response) => {
					this.dates = response.data.payload;

					if (this.date === null) {
						if (this.dates.length) {
							this.date = this.dates[this.dates.length - 1];
						} else {
							this.date = new Date().toISOString().substr(0, 10);
						}
					}
				})
				.then(() =>
					this.$http
						.get(`/entries/${this.machine}`, {
							params: {
								date: this.date,
							},
						})
						.then((response) => {
							this.entries = response.data.payload;
							this.error = null;
						})
				)
				.catch((error) => (this.error = error))
				.then(() => (this.loading = false));
		},
		confirmDelete() {
			this.$confirm({
				title: "Deletion confirmation",
				text: `This will delete data for the date: ${this.date}`,
			})
				.then(() => {
					this.loading = true;

					this.$http
						.post(`/beacon/${this.id}/force-trigger`, {})
						.then((response) => {
							let success = response.data.payload;

							if (success === null) {
								this.$error("Trigger failed: Beacon not found");
							} else if (success) {
								this.$inform("Beacon's sensor triggered!");
							} else {
								this.$warn("Trigger failed: Beacon not connected");
							}
						})
						.catch((error) => {
							this.error = error;
						})
						.then(() => {
							this.loading = false;
						});
				})
				.catch(() => {});
		},
	},
	mounted() {
		this.refresh();
	},
};
</script>
