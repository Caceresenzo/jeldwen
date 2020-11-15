<template>
	<v-card>
		<v-card-text class="text-center">
			<small class="mb-2">{{ $t("glial.machines.card.delete") }}</small>
			<br />
			<v-dialog ref="dialog" v-model="modal" persistent width="400px">
				<template v-slot:activator="{ on, attrs }">
					<v-btn v-bind="attrs" v-on="on" icon :loading="loading" x-large color="red">
						<v-icon>mdi-delete</v-icon>
					</v-btn>
				</template>
				<v-date-picker v-model="dates"  full-width :allowed-dates="(date) => availableDates.indexOf(date) != -1" :events="availableDates" scrollable multiple>
					<v-btn v-if="availableDates.length" text color="primary" @click="everythingSelected ? unselectAll() : selectAll()" :disabled="loading">{{ $t(`common.${everythingSelected ? 'unselect' : 'select'}-all`) }}</v-btn>
					<v-spacer></v-spacer>
					<v-btn text color="primary" @click="modal = false" :disabled="loading">Cancel</v-btn>
					<v-btn text color="primary" @click="askConfirmDelete()" :disabled="loading || dates.length == 0">{{ $t('common.delete') }}</v-btn>
				</v-date-picker>
			</v-dialog>
		</v-card-text>
		<v-dialog v-model="dialog" persistent max-width="290">
			<v-card :loading="loading2">
				<v-card-title>{{ $t("glial.machines.confirmDelete.title") }}</v-card-title>
				<v-card-text>{{ $t("glial.machines.confirmDelete.phrase") }} {{ $tc("glial.machines.confirmDelete.entries", preview, { n: preview }) }}</v-card-text>
				<v-card-text v-if="error">{{ error }}</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn color="primary" text @click="dialog = false" :disabled="loading2">{{ $t('common.cancel') }}</v-btn>
					<v-btn color="primary" text @click="confirmDelete()" :disabled="loading2">{{ $t('common.confirm') }}</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
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
		availableDates: {
			type: Array,
			default: () => [],
		},
	},
	data: () => ({
		modal: false,
		dialog: false,
		loading2: false,
		error: null,
		dates: [],
		preview: 0,
	}),
	computed: {
		everythingSelected() {
			return this.dates.length == this.availableDates.length;
		}
	},
	watch: {
		machine() {
			Object.assign(this.$data, this.$options.data.apply(this));
		}
	},
	methods: {
		askConfirmDelete() {
			this.verify();

			this.request(true);
			this.dialog = true;
		},
		confirmDelete() {
			this.request(false).then(() => {
				if (!this.error) {
					this.modal = false;
					this.dialog = false;
					this.dates = [];

					this.$emit("deleted");
				}
			});
		},
		request(preview) {
			this.error = null;
			this.loading2 = true;

			return this.$http
				.post(`/glial/delete/${this.machine || ""}`, this.dates, {
					params: {
						real: !preview,
					},
				})
				.then((response) => (this.preview = response.data.payload))
				.catch((error) => (this.error = error))
				.then(() => (this.loading2 = false));
		},
		selectAll() {
			this.dates = [...this.availableDates];
		},
		unselectAll() {
			this.dates = [];
		},
		verify() {
			const nw = [];

			for (let date of this.dates) {
				if (this.availableDates.indexOf(date) != -1) {
					nw.push(date);
				}
			}

			this.dates = nw;
		},
	},
};
</script>