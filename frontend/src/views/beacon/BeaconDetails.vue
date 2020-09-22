<template>
	<v-container fluid>
		<v-row>
			<v-col v-if="beacon && !beacon.name" cols="12">
				<v-alert class="my-0" type="warning">This beacon is not configured</v-alert>
			</v-col>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title>Beacon</v-card-title>
					<v-list v-if="beacon">
						<v-list-item @click>
							<v-list-item-content>
								<v-list-item-title v-text="beacon.name" />
								<v-list-item-subtitle v-text="beacon.unique" />
							</v-list-item-content>
							<v-list-item-avatar>
								<beacon-connected-state :unique="beacon.unique" />
							</v-list-item-avatar>
						</v-list-item>
						<v-list-item @click>
							<v-list-item-content>Synthetic Performance Rate Threshold</v-list-item-content>
							<v-list-item-avatar v-text="beacon.syntheticPerformanceRateThreshold * 100 + '%'"></v-list-item-avatar>
						</v-list-item>
					</v-list>
					<v-divider></v-divider>
					<v-list v-if="beacon" subheader>
						<v-subheader>Product Families</v-subheader>
						<v-list-item v-for="productFamily of beacon.productFamilies" :key="productFamily.id" @click>
							<v-list-item-content v-text="productFamily.name"></v-list-item-content>
							<v-list-item-avatar v-text="productFamily.cycleTime + 's'"></v-list-item-avatar>
						</v-list-item>
					</v-list>
					<v-divider></v-divider>
					<v-list v-if="beacon" subheader>
						<v-subheader>Stop Reason Groups</v-subheader>
						<v-list-item v-for="stopReasonGroup of beacon.stopReasonGroups" :key="stopReasonGroup.id" @click>
							<v-list-item-content>
								<v-list-item-title v-text="stopReasonGroup.name" />
								<v-list-item-subtitle v-text="stopReasonGroup.children.length + ' child(ren)'" />
							</v-list-item-content>
						</v-list-item>
					</v-list>
					<v-divider></v-divider>
					<v-list v-if="beacon" subheader>
						<v-subheader>Stop Reasons</v-subheader>
						<v-list-item v-for="stopReason of beacon.stopReasons" :key="stopReason.id" @click>
							<v-list-item-content v-text="stopReason.name"></v-list-item-content>
						</v-list-item>
					</v-list>
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
import BeaconConnectedState from "./components/BeaconConnectedState";

export default {
	components: {
		BeaconConnectedState,
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
		productFamiliesSummary() {
			return this.countOr(this.beacon?.productFamilies?.length, "Product Families: ", "No Product Family");
		},
		stopReasonGroupsSummary() {
			return this.countOr(this.beacon?.stopReasonGroups?.length, "Stop Reason Groups: ", "No Stop Reason Groups");
		},
		stopReasonsSummary() {
			return this.countOr(this.beacon?.stopReasons?.length, "Stop Reasons: ", "No Stop Reasons");
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
				this.$router.push(`/beacon/${this.beacon.id}/edit`)
			}
		}
	},
	created() {
		this.refresh();
	},
};
</script>