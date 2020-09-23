<template>
	<div>
		<v-dialog v-model="dialog" :persistent="loading" max-width="600px">
			<v-card :loading="loading">
				<v-card-title v-if="title" v-text="title" />
				<v-card-text>
					<v-container>
						<slot v-bind:loading="loading" />
					</v-container>
					<small>*indicates required field</small>
				</v-card-text>
				<v-card-actions>
					<v-btn text color="error" v-show="error" :disabled="loading" @click="create()">{{ error }}</v-btn>
					<v-spacer></v-spacer>
					<slot name="actions" v-bind:loading="loading" v-bind:error="error" />
					<v-btn text color="primary" :disabled="loading" @click="close()">Close</v-btn>
					<v-btn text color="primary" :disabled="loading" @click="create()">Create</v-btn>
				</v-card-actions>
			</v-card>
			<slot name="other" />
		</v-dialog>
	</div>
</template>

<script>
export default {
	name: "creation-modal",
	props: {
		title: {
			type: String,
		},
		endpoint: {
			type: String,
			required: true,
		},
		payload: {
			type: [Object, Array, String, Number],
			required: true,
		},
		onOpen: {
			type: Function,
		},
	},
	data: () => ({
		dialog: false,
		loading: false,
		error: null,
	}),
	methods: {
		open() {
			this.error = null;

			if (this.onOpen) {
				this.onOpen();
			}

			this.dialog = true;
		},
		close() {
			this.dialog = false;
		},
		create() {
			this.loading = true;

			return this.$http
				.put(this.endpoint, this.payload)
				.then((response) => {
					this.error = null;

					this.$emit("created", response.data.payload);

					this.close();
				})
				.catch((error) => {
					this.error = error;
				})
				.then(() => {
					this.loading = false;
				});
		},
	},
};
</script>