<template>
	<v-card>
		<v-card-text class="text-center">
			<small class="mb-2">{{ $t("glial.machines.card.csv") }}</small>
			<br />
			<v-btn icon :loading="loading" x-large color="green" :href="downloadLink" target="_blank">
				<v-icon>mdi-download</v-icon>
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
	computed: {
		downloadLink() {
			let base = application.api.baseUrl;
			if (!base.endsWith('/')) {
				base += "/"
			}

			return `${base}glial/download/${this.machine}`;
		},
	},
};
</script>