<template>
	<div>
		<v-dialog v-model="dialog" :persistent="loading" max-width="800px">
			<v-card :loading="loading">
				<v-card-title v-if="title">{{ title }}</v-card-title>
				<v-divider v-if="title"></v-divider>
				<v-container>
					<slot v-bind:loading="loading" />
				</v-container>
				<v-divider></v-divider>
				<v-card-actions>
					<v-btn text color="error" v-show="error" :disabled="loading" @click="put()">{{ error }}</v-btn>
					<v-spacer></v-spacer>
					<slot name="actions" v-bind:loading="loading" v-bind:error="error" />
					<v-btn text color="primary" :disabled="loading" @click="close()">Close</v-btn>
					<v-btn text color="primary" :disabled="loading" @click="put()">{{ edit ? "EDIT" : "CREATE" }}</v-btn>
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
		onClose: {
			type: Function,
		},
		edit: {
			type: Boolean,
			default: false,
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

			this.load();

			this.dialog = true;
		},
		close() {
			if (this.onClose) {
				this.onClose();
			}

			this.dialog = false;
		},
		load() {
			if (this.edit) {
				this.loading = true;

				return this.$http
					.get(this.endpoint)
					.then((response) => {
						this.error = null;

						this.$emit("loaded", response.data.payload);
					})
					.catch((error) => {
						this.error = error;
					})
					.then(() => {
						this.loading = false;
					});
			}
		},
		put() {
			this.loading = true;

			return this.$http[this.edit ? "post" : "put"](this.endpoint, this.payload)
				.then((response) => {
					this.error = null;

					this.$emit(this.edit ? "updated" : "created", response.data.payload);

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