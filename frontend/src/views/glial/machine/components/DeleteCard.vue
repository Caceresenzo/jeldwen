<template>
	<v-card>
		<v-card-text class="text-center">
			<small class="mb-2">{{ $t("glial.machines.card.delete") }}</small>
			<br />
			<v-btn icon :loading="loading" x-large color="red" @click="confirmDelete()">
				<v-icon>mdi-delete</v-icon>
			</v-btn>
		</v-card-text>
	</v-card>
</template>

<script>
import application from "@/../application";

export default {
	name: "download-card",
	props: {
		loading: {
			type: Boolean,
			default: false,
		},
		machine: {
			type: String,
			default: true,
		},
	},
	methods: {
		confirmDelete() {
			this.$confirm({
				title: "Deletion confirmation",
				text: `This will delete data for the date: ${this.date}`,
			})
				.then(() => {
					/*this.loading = true;

					this.$http // TODO
						.post(`/beacon/${this.id}/force-trigger`, {})
						.then((response) => {
							let success = response.data.payload;

							if (success === null) {
								this.$error("Trigger failed: Beacon not found");
							} else if (success) {
								this.$inform("Beacon's sensor triggered!");
							} else {
								this.$warn("Trigger failed: Beacon not connected");
							}
						})
						.catch((error) => {
							this.error = error;
						})
						.then(() => {
							this.loading = false;
						});*/
				})
				.catch(() => {});
		},
	}
};
</script>