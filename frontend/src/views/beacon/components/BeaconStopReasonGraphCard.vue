<template>
	<v-card :loading="loading">
		<v-card-title>Stop Reason</v-card-title>
		<v-divider></v-divider>
		<v-row class="mx-2">
			<v-col lg="9" cols="12">
				<apexchart type="bar" height="350" :options="chartData.chartOptions" :series="chartData.series"></apexchart>
			</v-col>
			<v-col lg="3" cols="12">
				<v-select v-model="inputs.groupBy" :items="items.groupBy" item-text="name" item-value="key" label="Grouping" return-object></v-select>
				<v-select v-model="inputs.order" :items="items.order" item-text="name" item-value="key" label="Ordering" clearable></v-select>
				<v-row>
					<v-col lg="6" md="6" sm="6" cols="12">
						<v-dialog ref="startingDateDialog" v-model="modals.startDate" :return-value.sync="inputs.startDate" width="290px">
							<template v-slot:activator="{ on, attrs }">
								<v-text-field v-model="inputs.startDate" label="Start" prepend-icon="mdi-sort-calendar-descending" readonly v-bind="attrs" v-on="on"></v-text-field>
							</template>
							<v-date-picker v-model="inputs.startDate" scrollable>
								<v-spacer></v-spacer>
								<v-btn text color="primary" @click="modal = false"> Cancel </v-btn>
								<v-btn text color="primary" @click="$refs.startingDateDialog.save(inputs.startDate)"> OK </v-btn>
							</v-date-picker>
						</v-dialog>
					</v-col>
					<v-col lg="6" md="6" sm="6" cols="12">
						<v-dialog ref="endingDateDialog" v-model="modals.endDate" :return-value.sync="inputs.endDate" width="290px">
							<template v-slot:activator="{ on, attrs }">
								<v-text-field v-model="inputs.endDate" label="End" prepend-icon="mdi-sort-calendar-ascending" readonly v-bind="attrs" v-on="on"></v-text-field>
							</template>
							<v-date-picker v-model="inputs.endDate" scrollable>
								<v-spacer></v-spacer>
								<v-btn text color="primary" @click="modal = false"> Cancel </v-btn>
								<v-btn text color="primary" @click="$refs.endingDateDialog.save(inputs.endDate)"> OK </v-btn>
							</v-date-picker>
						</v-dialog>
					</v-col>
				</v-row>
			</v-col>
		</v-row>
		<v-divider></v-divider>
		<v-card-actions>
			<v-btn v-if="error" text color="error" :disabled="loading" v-text="error" @click="refresh()"></v-btn>
			<v-spacer></v-spacer>
			<v-btn text color="primary" :disabled="loading" @click="refresh()">REFRESH</v-btn>
		</v-card-actions>
	</v-card>
</template>

<script>
export default {
	name: "beacon-stop-reason-graph-card",
	props: {
		beaconId: {
			type: [Number, String],
			default: null,
		},
	},
	data: () => ({
		loading: false,
		error: null,
		inputs: {
			startDate: null,
			endDate: null,
			groupBy: {
				name: "Stop Reason",
				map: "stopReasons",
				key: "stopReasonId",
			},
			order: "desc",
		},
		modals: {
			startDate: false,
			endDate: false,
		},
		items: {
			groupBy: [
				{
					name: "Stop Reason",
					map: "stopReasons",
					key: "stopReasonId",
				},
				{
					name: "Product Family",
					map: "productFamilies",
					key: "productFamilyId",
				},
			],
			order: [
				{
					name: "Ascending",
					key: "asc",
					asc: true,
				},
				{
					name: "Descending",
					key: "desc",
					asc: false,
				},
			],
		},
		raw: {
			beacons: {},
			productFamilies: {},
			stopReasons: {},
			reportedStopReasons: [],
		},
	}),
	computed: {
		chartData() { // TODO Need rework
			let byKey = this.inputs.groupBy.key;

			let seconds = {};

			for (let reportedStopReason of this.raw.reportedStopReasons) {
				let key = reportedStopReason[byKey];
				if (reportedStopReason.custom && byKey === "stopReasonId") {
					key = reportedStopReason.message;
				}

				let x = seconds[key] || 0;
				x += reportedStopReason.duration || 0;
				seconds[key] = x;
			}

			let data = [];
			for (let second in seconds) {
				data.push({
					x: second,
					y: seconds[second],
				});
			}

			if (this.inputs.order) {
				if (this.inputs.order === "asc") {
					data.sort((a, b) => a.y - b.y);
				} else {
					data.sort((a, b) => b.y - a.y);
				}
			}

			return {
				series: [
					{
						name: "Stopped Time",
						data: data,
					},
				],
				chartOptions: {
					theme: {
						mode: this.$vuetify.theme.dark ? "dark" : "light",
					},
					chart: {
						type: "bar",
					},
					plotOptions: {
						bar: {
							horizontal: true,
						},
					},
					dataLabels: {
						enabled: false,
					},
					xaxis: {
						categories: data.map((point) => point.x).map((id) => {
							if (!Number.isNaN(Number(id))) {
								return this.raw[this.inputs.groupBy.map][id].name;
							}

							return `(other) ${id}`;
						}),
					},
				},
			};
		},
	},
	watch: {
		inputs: {
			deep: true,
			handler() {
				this.refresh();
			},
		},
	},
	methods: {
		refresh() {
			if (this.loading) {
				return;
			}

			this.loading = true;

			let url = "beacon/stop-reason/reported";
			if (this.beaconId !== null) {
				url = `beacon/${this.beaconId}/stop-reason/reported`;
			}

			return this.$http
				.post(url, this.inputs)
				.then((response) => (this.raw = response.data.payload))
				.catch((error) => (this.error = error))
				.then(() => (this.loading = false));
		},
	},
	mounted() {
		this.refresh();
	},
};
</script>