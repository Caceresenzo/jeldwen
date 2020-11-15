<template>
	<v-container fluid>
		<v-row>
			<v-col sm="4" cols="12">
				<produced-objective-percent-card :entries="entries" :loading="loading" />
			</v-col>
			<v-col sm="4" cols="6">
				<download-card :machine="machine" :loading="loading" />
			</v-col>
			<v-col sm="4" cols="6">
				<delete-card :machine="machine" :loading="loading" />
			</v-col>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title>
						<v-dialog ref="dialog" v-model="modal" :return-value.sync="date" persistent width="290px">
							<template v-slot:activator="{ on, attrs }">
								<v-text-field v-model="date" :label="$t('glial.machines.selector.at')" prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
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
							<v-col sm="8" cols="12">
								<stopped-seconds-by-color-graph :entries="entries" />
							</v-col>
							<v-col sm="4" cols="12">
								<stopped-seconds-by-color-pie :entries="entries" />
							</v-col>
						</v-row>
						<v-divider />
						<produced-objective-graph :entries="entries" />
					</template>
					<empty v-else />
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import ProducedObjectivePercentCard from "./components/ProducedObjectivePercentCard";
import DownloadCard from "./components/DownloadCard";
import DeleteCard from "./components/DeleteCard";
import StoppedSecondsByColorGraph from "./components/StoppedSecondsByColorGraph";
import StoppedSecondsByColorPie from "./components/StoppedSecondsByColorPie";
import ProducedObjectiveGraph from "./components/ProducedObjectiveGraph";
import colors from "./colors";

export default {
	name: "MachineDetails",
	components: {
		ProducedObjectivePercentCard,
		DownloadCard,
		DeleteCard,
		StoppedSecondsByColorGraph,
		StoppedSecondsByColorPie,
		ProducedObjectiveGraph,
	},
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
			return this.$route.params.name || "";
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
				.get(`/glial/dates/${this.machine}`)
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
						.get(`/glial/entries/${this.machine}`, {
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
	},
	mounted() {
		this.refresh();
	},
};
</script>
