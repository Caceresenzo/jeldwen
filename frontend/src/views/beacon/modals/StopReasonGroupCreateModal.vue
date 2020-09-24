<template>
	<creation-modal ref="stopReasonGroupCreateModalModal" :title="title" :edit="editId !== null" :endpoint="endpoint" :payload="inputs" :on-open="refresh" :on-close="resetInputs" @loaded="payloadToInputs" @created="(object) => $emit('created', object)" @updated="(object) => $emit('updated', object)">
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
		editId: null,
	}),
	computed: {
		title() {
			return this.editId ? "Stop Reason Group Edition" : "Stop Reason Group Creation";
		},
		endpoint() {
			return this.editId ? `/beacon/stop-reason/group/${this.editId}` : "/beacon/stop-reason/group/";
		},
	},
	methods: {
		payloadToInputs(payload) {
			this.inputs = {
				name: payload.name,
				childrenIds: payload.children.map((stopReason) => stopReason.id),
			};
		},
		open() {
			this.$refs.stopReasonGroupCreateModalModal.open();
		},
		close() {
			this.$refs.stopReasonGroupCreateModalModal.close();
		},
		edit(id) {
			this.editId = id;

			setTimeout(() => this.open(), 5);
		},
		refresh() {
			this.dependencyLoading = true;

			return this.$http
				.get("/beacon/stop-reason", {
					params: {
						includeFree: true,
						includeParent: "GROUP",
						parentId: this.editId,
					},
				})
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