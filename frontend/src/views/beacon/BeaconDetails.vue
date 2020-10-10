<template>
	<v-container fluid>
		<v-row>
			<v-col cols="12">
				<v-alert v-if="beacon && !configured" type="warning">This beacon is not configured</v-alert>
				<v-card :loading="loading">
					<v-card-title>{{ name }}</v-card-title>
					<v-divider></v-divider>
					<v-list v-if="beacon" class="py-0">
						<v-list-item @click>
							<v-list-item-content>
								<v-list-item-title>Connection</v-list-item-title>
								<v-list-item-subtitle v-text="beacon.unique" />
							</v-list-item-content>
							<v-list-item-avatar>
								<beacon-connected-state :unique="beacon.unique" />
							</v-list-item-avatar>
						</v-list-item>
						<v-list-item v-if="configured" @click>
							<v-list-item-content>Synthetic Performance Rate Threshold</v-list-item-content>
							<v-list-item-avatar v-text="beacon.syntheticPerformanceRateThreshold * 100 + '%'"></v-list-item-avatar>
						</v-list-item>
					</v-list>
					<div v-if="beacon && configured">
						<v-divider></v-divider>
						<v-list subheader class="py-0">
							<v-subheader>Product Families</v-subheader>
							<v-list-item v-for="productFamily of beacon.productFamilies" :key="productFamily.id" @click>
								<v-list-item-content v-text="productFamily.name"></v-list-item-content>
								<v-list-item-avatar v-text="productFamily.cycleTime + 's'"></v-list-item-avatar>
							</v-list-item>
							<empty v-if="!beacon.productFamilies.length" />
						</v-list>
						<v-divider></v-divider>
						<v-list subheader class="py-0">
							<v-subheader>Stop Reason Groups</v-subheader>
							<v-list-item v-for="stopReasonGroup of beacon.stopReasonGroups" :key="stopReasonGroup.id" @click="openStopReasonGroup(stopReasonGroup.id)">
								<v-list-item-content>
									<v-list-item-title v-text="stopReasonGroup.name" />
									<v-list-item-subtitle v-text="stopReasonGroup.children.length + ' child(ren)'" />
								</v-list-item-content>
							</v-list-item>
							<empty v-if="!beacon.stopReasonGroups.length" />
						</v-list>
						<v-divider></v-divider>
						<v-list subheader class="py-0">
							<v-subheader>Stop Reasons</v-subheader>
							<stop-reason-by-categories class="mx-4" :items="(beacon || {}).stopReasons || []">
								<template v-slot="{ category }">
									<v-list>
										<v-list-item v-for="(stopReason, i) in category.children" :key="i" @click>
											<v-list-item-content>
												<v-list-item-title>{{ stopReason.name }}</v-list-item-title>
											</v-list-item-content>
										</v-list-item>
									</v-list>
								</template>
							</stop-reason-by-categories>
						</v-list>
					</div>
					<v-divider></v-divider>
					<v-card-actions>
						<v-btn v-if="error" text color="error" :disabled="loading" v-text="error" @click="refresh()"></v-btn>
						<v-spacer></v-spacer>
						<v-btn text color="primary" :disabled="loading" @click="edit()">EDIT</v-btn>
						<v-btn text color="primary" :disabled="loading" @click="refresh()">REFRESH</v-btn>
					</v-card-actions>
				</v-card>
			</v-col>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title>Action Zone</v-card-title>
					<v-divider></v-divider>
					<v-list-item multiline>
						<v-list-item-content>
							<v-list-item-title>Force sensor trigger</v-list-item-title>
							<v-list-item-subtitle>Fake a sensor trigger</v-list-item-subtitle>
						</v-list-item-content>
						<v-list-item-icon>
							<v-btn :disabled="!connected" color="warning" depressed @click="forceSensorTrigger">TRIGGER</v-btn>
						</v-list-item-icon>
					</v-list-item>
					<v-divider />
					<v-list-item multiline>
						<v-list-item-content>
							<v-list-item-title>Force configuration</v-list-item-title>
							<v-list-item-subtitle>Don't wait the beacon to restart, push a configuration by force</v-list-item-subtitle>
						</v-list-item-content>
						<v-list-item-icon>
							<v-btn :disabled="!connected" color="warning" depressed @click="reconfigure">RECONFIGURE</v-btn>
						</v-list-item-icon>
					</v-list-item>
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import beaconService from "@/service/beaconService";

import BeaconConnectedState from "./components/BeaconConnectedState";
import StopReasonByCategories from "./components/StopReasonByCategories";

export default {
	components: {
		BeaconConnectedState,
		StopReasonByCategories,
	},
	data() {
		return {
			beacon: null,
			loading: false,
			error: null,
			connected: false,
		};
	},
	computed: {
		id() {
			return this.$route.params.id;
		},
		name() {
			return this.beacon?.name || this.beacon?.unique || "Beacon";
		},
		unique() {
			return this.beacon?.unique;
		},
		configured() {
			return this.beacon.name;
		},
	},
	watch: {
		beacon(val) {
			this.connected = beaconService.isConnected(val.unique);
		},
	},
	methods: {
		onBeaconDisconnected(unique) {
			if (this.beacon?.unique === unique) {
				this.connected = false;
			}
		},
		onBeaconConnected(unique) {
			if (this.beacon?.unique === unique) {
				this.connected = true;
			}
		},
		refresh() {
			this.loading = true;

			return this.$http
				.get(`/beacon/${this.id}`)
				.then((response) => {
					this.beacon = response.data.payload;
				})
				.catch((error) => {
					this.error = error;
				})
				.then(() => {
					this.loading = false;
				});
		},
		edit() {
			if (this.beacon) {
				this.$router.push(`/beacon/${this.beacon.id}/edit`);
			}
		},
		openStopReasonGroup(id) {
			this.$router.push(`/beacon/stop-reason/group/${id}`);
		},
		reconfigure() {
			this.$confirm({
				title: "Reconfiguration Confirmation",
				text: "If the beacon is being reconfigured, all data not send by the beacon will be lost.",
			})
				.then(() => {
					this.loading = true;

					this.$http
						.post(`/beacon/${this.id}/reconfigure`, {})
						.then((response) => {
							let success = response.data.payload;

							if (success === null) {
								this.$error(
									"Reconfiguration failed: Beacon not found"
								);
							} else if (success) {
								this.$inform("Beacon reconfigured!");
							} else {
								this.$warn(
									"Reconfiguration failed: Beacon not connected"
								);
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
		forceSensorTrigger() {
			this.$confirm({
				title: "Trigger Confirmation",
				text: "Statistics might be wrong if you force a sensor trigger!",
			})
				.then(() => {
					this.loading = true;

					this.$http
						.post(`/beacon/${this.id}/force-trigger`, {})
						.then((response) => {
							let success = response.data.payload;

							if (success === null) {
								this.$error(
									"Trigger failed: Beacon not found"
								);
							} else if (success) {
								this.$inform("Beacon's sensor triggered!");
							} else {
								this.$warn(
									"Trigger failed: Beacon not connected"
								);
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
	created() {
		this.refresh();
	},
};
</script>