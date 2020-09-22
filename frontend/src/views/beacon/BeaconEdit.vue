<template>
	<v-container fluid>
		<v-row>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title v-text="title"></v-card-title>
					<v-list v-if="!loading">
						<v-list-item>
							<v-text-field label="Name" v-model="inputs.name"></v-text-field>
						</v-list-item>
						<v-list-item>
							<v-select label="Product Families" multiple v-model="inputs.productFamilyIds" :items="items.productFamilies" item-text="name" item-value="id" :disabled="subloading.productFamilyIds"></v-select>
						</v-list-item>
						<v-list-item>
							<v-select label="Stop Reason Groups" multiple v-model="inputs.stopReasonGroupIds" :items="items.stopReasonGroups" item-text="name" item-value="id" :disabled="subloading.stopReasonGroupIds"></v-select>
						</v-list-item>
						<v-list-item>
							<v-select label="Stop Reasons" multiple v-model="inputs.stopReasonIds" :items="items.stopReasons" item-text="name" item-value="id" :disabled="subloading.stopReasonIds"></v-select>
						</v-list-item>
						<v-list-item>
							<v-input label="Synthetic Performance Rate Threshold" value="qs">
								<v-slider v-model="inputs.syntheticPerformanceRateThreshold" class="align-center" hide-details>
									<template v-slot:append>
										<v-text-field v-model="inputs.syntheticPerformanceRateThreshold" class="mt-0 pt-0" hide-details single-line dense>
											<v-icon slot="append">mdi-percent</v-icon>
										</v-text-field>
									</template>
								</v-slider>
							</v-input>
						</v-list-item>
					</v-list>
					<v-card-actions>
						<v-btn v-if="error" text color="error" :disabled="loading" v-text="error" @click="refresh()"></v-btn>
						<v-spacer></v-spacer>
						<v-btn text color="primary" :disabled="loading" @click="cancel()" v-text="updated === null ? 'CANCEL' : 'GO BACK'"></v-btn>
						<v-btn text color="primary" :disabled="loading" @click="update()">UPDATE</v-btn>
						<v-btn text color="primary" :disabled="loading" @click="refresh()">REFRESH</v-btn>
					</v-card-actions>
				</v-card>
			</v-col>
			<v-col v-if="updated !== null" cols="12">
				<v-alert v-if="updated" type="success">Updated.</v-alert>
				<v-alert v-else type="error">Failed to update.</v-alert>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import BeaconConnectedState from "./components/BeaconConnectedState";

export default {
	components: {
		BeaconConnectedState,
	},
	data() {
		return {
			loading: false,
			error: {},
			beacon: null,
			updated: null,
			items: {
				productFamilies: [],
				stopReasonGroups: [],
				stopReasons: [],
			},
			inputs: {
				name: null,
				productFamilyIds: [],
				stopReasonGroupIds: [],
				stopReasonIds: [],
				syntheticPerformanceRateThreshold: null,
			},
			subloading: {
				productFamilyIds: false,
				stopReasonGroupIds: false,
				stopReasonIds: false,
			},
		};
	},
	computed: {
		title() {
			return "Beacon: " + (this.beacon?.unique || "???");
		},
	},
	methods: {
		beaconToInputs(beacon) {
			this.beacon = beacon;

			this.inputs = {
				name: beacon.name,
				productFamilyIds: beacon.productFamilies.map((productFamily) => productFamily.id),
				stopReasonGroupIds: beacon.stopReasonGroups.map((stopReasonGroup) => stopReasonGroup.id),
				stopReasonIds: beacon.stopReasons.map((stopReason) => stopReason.id),
				syntheticPerformanceRateThreshold: beacon.syntheticPerformanceRateThreshold * 100,
			};
		},
		refresh() {
			this.loading = true;

			this.error = {};

			return Promise.allSettled([
				this.$http
					.get("/beacon/product-family")
					.then((response) => {
						this.items.productFamilies = response.data.payload;
					})
					.catch((error) => {
						this.error.productFamily = error;
					}),
				this.$http
					.get("/beacon/stop-reason")
					.then((response) => {
						this.items.stopReasons = response.data.payload;
					})
					.catch((error) => {
						this.error.stopReason = error;
					}),
				this.$http
					.get("/beacon/stop-reason/group")
					.then((response) => {
						this.items.stopReasonGroups = response.data.payload;
					})
					.catch((error) => {
						this.error.stopReasonGroup = error;
					}),
			])
				.then(() => {
					return this.$http
						.get("/beacon/" + this.$route.params.id)
						.then((response) => {
							this.beaconToInputs(response.data.payload);
						})
						.catch((error) => {
							this.error.beacon = error;
						});
				})
				.then(() => {
					this.loading = false;
				});
		},
		update() {
			this.loading = true;

			delete this.error.update;

			return this.$http
				.post(`/beacon/${this.$route.params.id}`, {
					name: this.inputs.name,
					productFamilyIds: this.inputs.productFamilyIds,
					stopReasonGroupIds: this.inputs.stopReasonGroupIds,
					stopReasonIds: this.inputs.stopReasonIds,
					syntheticPerformanceRateThreshold: this.inputs.syntheticPerformanceRateThreshold / 100,
				})
				.then((response) => {
					this.beaconToInputs(response.data.payload);

					this.updated = true;
				})
				.catch((error) => {
					console.log(error);
					this.error.update = error;

					this.updated = false;
				})
				.then(() => {
					this.loading = false;
				});
		},
		cancel() {
			this.$router.push(`/beacon/${this.$route.params.id}`);
		},
	},
	created() {
		this.refresh();
	},
};
</script>