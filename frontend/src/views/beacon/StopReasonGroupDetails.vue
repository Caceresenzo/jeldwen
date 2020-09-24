<template>
	<v-container fluid>
		<v-row>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title v-text="name" />
					<v-divider></v-divider>
					<stop-reason-by-categories :items="(stopReasonGroup || {}).children || []">
						<template v-slot="{ category }">
							<v-list>
								<v-list-item v-for="(stopReason, i) in category.children" :key="i" @click>
									<v-list-item-content>
										<v-list-item-title>{{ stopReason.name }}</v-list-item-title>
									</v-list-item-content>
									<v-list-item-icon>
										<v-tooltip top @click.prevent.self>
											<template v-slot:activator="{ on, attrs }">
												<v-btn v-bind="attrs" v-on="on" :disabled="loading" color="warning" fab small depressed @click="detachChild(stopReason.id)">
													<v-icon>mdi-vector-polyline-remove</v-icon>
												</v-btn>
											</template>
											Detach
											<br />(free from group)
										</v-tooltip>
										<v-tooltip top @click.prevent.self>
											<template v-slot:activator="{ on, attrs }">
												<v-btn v-bind="attrs" v-on="on" :disabled="loading" color="warning" fab small depressed class="ml-2" @click="deleteChild(stopReason.id)">
													<v-icon>mdi-delete</v-icon>
												</v-btn>
											</template>
											Remove form everywhere
										</v-tooltip>
										<v-tooltip top @click.prevent.self>
											<template v-slot:activator="{ on, attrs }">
												<v-btn v-bind="attrs" v-on="on" :disabled="loading" color="warning" fab small depressed class="ml-2" @click="editChild(stopReason.id)">
													<v-icon>mdi-pencil</v-icon>
												</v-btn>
											</template>
											Edit
										</v-tooltip>
									</v-list-item-icon>
								</v-list-item>
							</v-list>
						</template>
					</stop-reason-by-categories>
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
		<stop-reason-create-modal @updated="refresh" ref="stopReasonCreateModal" edit />
		<stop-reason-group-create-modal @updated="refresh" ref="stopReasonGroupCreateModal" edit />
	</v-container>
</template>

<script>
import StopReasonByCategories from "./components/StopReasonByCategories";
import StopReasonCreateModal from "./modals/StopReasonCreateModal";
import StopReasonGroupCreateModal from "./modals/StopReasonGroupCreateModal";

export default {
	components: {
		StopReasonByCategories,
		StopReasonCreateModal,
		StopReasonGroupCreateModal,
	},
	data() {
		return {
			loading: false,
			error: null,
			stopReasonGroup: null,
		};
	},
	computed: {
		id() {
			return this.$route.params.id;
		},
		name() {
			return this.stopReasonGroup?.name || "Stop Reason Group";
		},
		byCategories() {
			let out = {};

			for (let stopReason of this.stopReasonGroup.children) {
				let category = stopReason.category;
				let object = out[category.id] || {};

				object.name = category.name;
				object.color = category.color;
				(object.children = object.children || []).push(stopReason);

				out[category.id] = object;
			}

			return out;
		},
	},
	methods: {
		refresh() {
			this.loading = true;

			this.$http
				.get(`/beacon/stop-reason/group/${this.id}`)
				.then((response) => {
					this.error = null;
					this.stopReasonGroup = response.data.payload;
				})
				.catch((error) => {
					this.error = error;
				})
				.then((values) => {
					this.loading = false;
				});
		},
		edit() {
			this.$refs.stopReasonGroupCreateModal.edit(this.id);
		},
		editChild(stopReasonId) {
			this.$refs.stopReasonCreateModal.edit(stopReasonId);
		},
		detachChild(stopReasonId) {
			this.$confirm({
				title: "Detach Confirmation",
				text: "Do you want to detach this stop reason from this group?",
			})
				.then(() => {
					this.loading = true;

					this.$http
						.post(`/beacon/stop-reason/${stopReasonId}/detach`, {})
						.then((response) => {
							this.refresh();
						})
						.catch((error) => {
							this.error = error;

							this.loading = false;
						});
				})
				.catch(() => {});
		},
		deleteChild(stopReasonId) {
			this.$confirm({
				title: "Delete Confirmation",
				text: "Do you want to delete this stop reason completly?\nThis will cause data loss when displaying graphs!",
			})
				.then(() => {
					this.loading = true;

					this.$http
						.delete(`/beacon/stop-reason/${stopReasonId}`, {})
						.then((response) => {
							this.refresh();
						})
						.catch((error) => {
							this.error = error;

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