<template>
	<v-card :loading="loading">
		<v-card-title v-if="title" v-text="title"></v-card-title>
		<slot v-bind:payload="payload">{{ payload }}</slot>
		<v-card-actions>
			<v-btn v-if="error" text color="error" :disabled="loading" v-text="error" @click="refresh()"></v-btn>
			<v-spacer></v-spacer>
			<slot name="actions" v-bind:payload="payload"></slot>
			<v-btn text color="primary" :disabled="loading" @click="refresh()">REFRESH</v-btn>
		</v-card-actions>
	</v-card>
</template>

<script>
export default {
	name: "loading-card",
	props: {
		title: {
			type: String,
			default: null,
		},
		endpoint: {
			type: String,
			required: true,
		},
	},
	data: () => ({
		payload: null,
		loading: false,
		error: null,
	}),
	methods: {
		refresh() {
			this.loading = true;

			this.$http
				.get(this.endpoint)
				.then((response) => {
					this.error = null;
					this.payload = response.data.payload;
				})
				.catch((error) => {
					this.error = error;
				})
				.then((values) => {
					this.loading = false;
				});
		},
	},
	created() {
		this.refresh();
	},
};
</script>
