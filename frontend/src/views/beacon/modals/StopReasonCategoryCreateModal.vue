<template>
	<creation-modal ref="modal" title="Stop Reason Category Creation" endpoint="/beacon/stop-reason/category" :payload="inputs" :on-open="resetInputs" @created="(object) => $emit('created', object)">
		<template v-slot="{ loading }">
			<v-row>
				<v-col cols="12">
					<v-text-field v-model="inputs.name" :disabled="loading" label="Name*" required></v-text-field>
				</v-col>
				<v-col cols="12">
					<v-color-picker v-model="inputs.color" :disabled="loading" hide-alpha hide-canvas class="ma-2" width="100%" dot-size="30"></v-color-picker>
				</v-col>
			</v-row>
		</template>
	</creation-modal>
</template>

<script>
import CreationModal from "./CreationModal";

export default {
	components: {
		CreationModal,
	},
	name: "stop-reason-category-create-modal",
	data: () => ({
		inputs: {
			name: null,
			color: "#FFFFFF",
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
			let color = (
				"#" +
				Math.floor(Math.random() * 2 ** 24)
					.toString(16)
					.padStart(0, 6)
					.substring(0, 6)
			).padEnd(7, "0");

			this.inputs = {
				name: null,
				color,
			};

			this.error = null;
		},
	},
};
</script>