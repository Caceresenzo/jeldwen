<template>
	<creation-modal ref="modal" title="Stop Reason Creation" endpoint="/beacon/stop-reason/group" :payload="inputs" :on-open="resetInputs" @created="(object) => $emit('created', object)">
		<template v-slot="{ loading }">
			<v-list>
				<v-list-item>
					<v-text-field v-model="inputs.name" :disabled="loading" label="Name*" required></v-text-field>
				</v-list-item>
				<v-list-item>
					<v-list-item-content>
						<v-select label="Children*" multiple v-model="inputs.childrenIds" :items="items.stopReasons" item-text="name" item-value="id" :loading="dependencyLoading"></v-select>
					</v-list-item-content>
					<v-list-item-avatar>
						<v-icon @click="openStopReasonCreateModal()">mdi-plus</v-icon>
					</v-list-item-avatar>
				</v-list-item>
			</v-list>
		</template>
		<template v-slot:actions="{ loading }">
			<v-btn text color="primary" :disabled="loading" @click="refresh()">Refresh</v-btn>
		</template>
		<template v-slot:other>
			<stop-reason-create-modal ref="stopReasonCreateModal" @created="onStopReasonCreated" />
		</template>
	</creation-modal>
</template>

<script>
import CreationModal from "./CreationModal";
import StopReasonCreateModal from "./StopReasonCreateModal";

export default {
	components: {
		CreationModal,
		StopReasonCreateModal,
	},
	name: "stop-reason-group-create-modal",
	data: () => ({
		dependencyLoading: false,
		items: {
			stopReasons: [],
		},
		inputs: {
			name: null,
			childrenIds: [],
		},
	}),
	methods: {
		open() {
			this.$refs.modal.open();
		},
		close() {
			this.$refs.modal.close();
		},
		refresh() {
			console.log(this.inputs.categoryId);

			this.dependencyLoading = true;

			return this.$http
				.get("/beacon/stop-reason")
				.then((response) => {
					this.items.stopReasons = response.data.payload;
				})
				.catch((error) => {
					this.$refs.modal.error = error;
				})
				.then(() => {
					this.dependencyLoading = false;
				});
		},
		openStopReasonCreateModal() {
			if (!this.dependencyLoading) {
				this.$refs.stopReasonCreateModal.open();
			}
		},
		onStopReasonCreated(stopReason) {
			this.items.stopReasons.push(stopReason);
			this.inputs.childrenIds.push(stopReason.id);
		},
		resetInputs() {
			this.inputs = {
				name: null,
				childrenIds: [],
			};
		},
	},
	created() {
		this.refresh();
	},
};
</script>