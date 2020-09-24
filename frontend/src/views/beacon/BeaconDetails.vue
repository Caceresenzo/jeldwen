<template>
	<v-container fluid>
		<v-row>
			<v-col v-if="beacon && !configured" cols="12">
				<v-alert class="my-0" type="warning">This beacon is not configured</v-alert>
			</v-col>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title>{{ name }}</v-card-title>
					<v-divider></v-divider>
					<v-list v-if="beacon">
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
						<v-list subheader>
							<v-subheader>Product Families</v-subheader>
							<v-list-item v-for="productFamily of beacon.productFamilies" :key="productFamily.id" @click>
								<v-list-item-content v-text="productFamily.name"></v-list-item-content>
								<v-list-item-avatar v-text="productFamily.cycleTime + 's'"></v-list-item-avatar>
							</v-list-item>
							<empty v-if="!beacon.productFamilies.length" />
						</v-list>
						<v-divider></v-divider>
						<v-list subheader>
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
						<v-list subheader>
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
		</v-row>
	</v-container>
</template>

<script>
import Empty from "./components/Empty";
import BeaconConnectedState from "./components/BeaconConnectedState";
import StopReasonByCategories from "./components/StopReasonByCategories";

export default {
	components: {
		Empty,
		BeaconConnectedState,
		StopReasonByCategories,
	},
	data() {
		return {
			beacon: null,
			loading: false,
			error: null,
		};
	},
	computed: {
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
	methods: {
		countOr(n, message, or) {
			if (n) {
				return message + n;
			}

			return or;
		},
		refresh() {
			this.loading = true;

			return this.$http
				.get("/beacon/" + this.$route.params.id)
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
	},
	created() {
		this.refresh();
	},
};
</script>