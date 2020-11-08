<template>
	<v-card :loading="loading">
		<template v-if="title">
			<v-card-title>{{ title }}</v-card-title>
			<v-divider />
		</template>
		<slot v-bind:payload="payload">{{ payload }}</slot>
		<v-divider />
		<v-card-actions>
			<v-btn v-if="error" text color="error" :disabled="loading" v-text="error" @click="refresh()"></v-btn>
			<v-spacer></v-spacer>
			<slot name="actions" v-bind:payload="payload"></slot>
			<v-btn text color="primary" :disabled="loading" @click="refresh()">{{ $t("common.refresh") }}</v-btn>
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
