<template>
	<v-container fluid>
		<v-row>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title v-text="name" />
					<v-tabs v-if="stopReasonGroup" v-model="tab">
						<v-tab v-for="(category, id) of byCategories" :key="id">
							{{ category.name }}
							<v-icon class="ml-1" :color="category.color">mdi-square-rounded</v-icon>
						</v-tab>
					</v-tabs>
					<v-divider></v-divider>
					<v-card-text>
						<v-tabs-items v-if="stopReasonGroup" class="pt-0" v-model="tab">
							<v-tab-item v-for="(category, id) of byCategories" :key="id">
								<v-list>
									<v-list-item v-for="(stopReason, i) in category.children" :key="i" @click>
										<v-list-item-content>
											<v-list-item-title v-text="stopReason.name"></v-list-item-title>
										</v-list-item-content>
										<v-list-item-icon>
											<v-tooltip top @click.prevent.self>
												<template v-slot:activator="{ on, attrs }">
													<v-btn v-bind="attrs" v-on="on" color="warning" fab small depressed>
														<v-icon>mdi-vector-polyline-remove</v-icon>
													</v-btn>
												</template>
												Remove from Group
												<br />(and set as orphane)
											</v-tooltip>
											<v-tooltip top @click.prevent.self>
												<template v-slot:activator="{ on, attrs }">
													<v-btn v-bind="attrs" v-on="on" color="warning" fab small depressed class="ml-2">
														<v-icon>mdi-delete</v-icon>
													</v-btn>
												</template>
												Remove form everywhere
											</v-tooltip>
											<v-tooltip top @click.prevent.self>
												<template v-slot:activator="{ on, attrs }">
													<v-btn v-bind="attrs" v-on="on" color="warning" fab small depressed class="ml-2">
														<v-icon>mdi-pencil</v-icon>
													</v-btn>
												</template>
												Edit
											</v-tooltip>
										</v-list-item-icon>
									</v-list-item>
								</v-list>
							</v-tab-item>
						</v-tabs-items>
					</v-card-text>
					<v-card-actions>
						<v-btn v-if="error" text color="error" :disabled="loading" v-text="error" @click="refresh()"></v-btn>
						<v-spacer></v-spacer>
						<v-btn text color="primary" :disabled="loading" @click="refresh()">REFRESH</v-btn>
					</v-card-actions>
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
export default {
	data() {
		return {
			tab: null,
			loading: false,
			error: null,
			stopReasonGroup: null,
		};
	},
	computed: {
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
				.get(`/beacon/stop-reason/group/${this.$route.params.id}`)
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
		newDialog() {},
	},
	created() {
		this.refresh();
	},
};
</script>