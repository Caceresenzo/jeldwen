<template>
	<creation-modal ref="modal" :title="title" :edit="editId !== null" :endpoint="endpoint" :payload="inputs" :on-close="resetInputs" @loaded="payloadToInputs" @created="(object) => $emit('created', object)" @updated="(object) => $emit('updated', object)">
		<template v-slot="{ loading }">
			<v-list>
				<v-list-item>
					<v-text-field v-model="inputs.name" :disabled="loading" label="Name*" required></v-text-field>
				</v-list-item>
				<v-list-item>
					<v-list-item-content>
						<v-select label="Category*" v-model="inputs.categoryId" :items="items.categories" item-text="name" item-value="id" :loading="dependencyLoading"></v-select>
					</v-list-item-content>
					<v-list-item-avatar>
						<v-icon @click="openStopReasonCategoryCreateModal()">mdi-plus</v-icon>
					</v-list-item-avatar>
				</v-list-item>
			</v-list>
		</template>
		<template v-slot:actions="{ loading }">
			<v-btn text color="primary" :disabled="loading" @click="refresh()">Refresh</v-btn>
		</template>
		<template v-slot:other>
			<stop-reason-category-create-modal ref="stopReasonCategoryCreateModal" @created="onCategoryCreated" />
		</template>
	</creation-modal>
</template>

<script>
import CreationModal from "./CreationModal";
import StopReasonCategoryCreateModal from "./StopReasonCategoryCreateModal";

export default {
	components: {
		CreationModal,
		StopReasonCategoryCreateModal,
	},
	name: "stop-reason-create-modal",
	data: () => ({
		dependencyLoading: false,
		items: {
			categories: [],
		},
		inputs: {
			name: null,
			categoryId: null,
		},
		editId: null,
	}),
	computed: {
		title() {
			return this.editId ? "Stop Reason Edition" : "Stop Reason Creation";
		},
		endpoint() {
			return this.editId ? `/beacon/stop-reason/${this.editId}` : "/beacon/stop-reason/";
		},
	},
	methods: {
		payloadToInputs(payload) {
			this.inputs = {
				name: payload.name,
				categoryId: payload.category?.id,
			};
		},
		open() {
			this.$refs.modal.open();
		},
		edit(id) {
			this.editId = id;

			setTimeout(() => this.open(), 5);
		},
		close() {
			this.$refs.modal.close();
		},
		refresh() {
			this.dependencyLoading = true;

			return this.$http
				.get("/beacon/stop-reason/category")
				.then((response) => {
					this.items.categories = response.data.payload;
				})
				.catch((error) => {
					this.$refs.modal.error = error;
				})
				.then(() => {
					this.dependencyLoading = false;
				});
		},
		openStopReasonCategoryCreateModal() {
			if (!this.dependencyLoading) {
				this.$refs.stopReasonCategoryCreateModal.open();
			}
		},
		onCategoryCreated(category) {
			this.items.categories.push(category);
			this.inputs.categoryId = category.id;
		},
		resetInputs() {
			this.editId = null;

			this.inputs = {
				name: null,
				categoryId: null,
			};

			this.error = null;
		},
	},
	created() {
		this.refresh();
	},
};
</script>