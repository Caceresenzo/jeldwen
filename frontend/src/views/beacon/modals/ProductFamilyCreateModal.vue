<template>
	<creation-modal ref="modal" title="Product Family Creation" endpoint="/beacon/product-family" :payload="inputs" :on-open="resetInputs" @created="(object) => $emit('created', object)">
		<template v-slot="{ }">
			<v-list>
				<v-list-item>
					<v-text-field v-model="inputs.name" label="Name*" required></v-text-field>
				</v-list-item>
				<v-list-item>
					<v-text-field v-model="inputs.cycleTime" label="Cycle Time*" required></v-text-field>
				</v-list-item>
			</v-list>
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
	name: "product-family-create-modal",
	data: () => ({
		inputs: {
			name: null,
			cycleTime: null,
		},
	}),
	methods: {
		open() {
			this.$refs.modal.open();
		},
		close() {
			this.$refs.modal.close();
		},
		resetInputs() {
			this.inputs = {
				name: null,
				categoryId: null,
			};
		},
	},
};
</script>