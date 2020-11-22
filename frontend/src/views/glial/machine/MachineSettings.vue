<template>
	<v-container fluid>
		<v-row>
			<v-col cols="12">
				<v-card :loading="loading">
					<v-card-title>
						{{ $t("glial.machines.settings.urls._") }}
						<v-spacer />
						<v-btn icon :disabled="loading" @click="dialogs.create = true">
							<v-icon>mdi-plus</v-icon>
						</v-btn>
						<v-btn icon :loading="loading" @click="refresh(true)">
							<v-icon>mdi-refresh</v-icon>
						</v-btn>
					</v-card-title>
					<v-divider />
					<v-data-table v-if="items" :headers="headers" :items="items" hide-default-footer disable-pagination>
						<template v-slot:item.url="{ item }">
							<a v-if="item.url" :href="item.url">{{ item.url }} </a>
							<v-icon v-else>mdi-bee</v-icon>
						</template>
						<template v-slot:item.action="{ item }">
							<v-btn icon @click="openEditDialog(item)">
								<v-icon>mdi-pencil</v-icon>
							</v-btn>
							<v-btn icon @click="openDeleteDialog(item)">
								<v-icon>mdi-delete</v-icon>
							</v-btn>
						</template>
					</v-data-table>
				</v-card>
			</v-col>
		</v-row>
		<v-dialog v-model="dialogs.create" max-width="500">
			<v-card>
				<v-card-title class="headline">{{ $t("glial.machines.settings.urls.dialogs.create._") }}</v-card-title>
				<v-card-text>
					<v-alert v-if="errors.create._ != null" dense color="error">{{ errors.create._ }}</v-alert>
					<v-text-field v-model="inputs.create.machine" :error="errors.create.machine !== null" :error-messages="errors.create.machine" :label="$t('glial.machines.settings.urls.fields.machine')"></v-text-field>
					<v-text-field v-model="inputs.create.url" :error="errors.create.url !== null" :error-messages="errors.create.url" :label="$t('glial.machines.settings.urls.fields.url')" :hint="$t('glial.machines.settings.urls.hint')" persistent-hint></v-text-field>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn text @click="dialogs.create = false">{{ $t("common.cancel") }}</v-btn>
					<v-btn text @click="create()">{{ $t("common.create") }}</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
		<v-dialog v-model="dialogs.edit" max-width="500">
			<v-card>
				<v-card-title class="headline">{{ inputs.edit.machine }}</v-card-title>
				<v-card-text>
					<v-alert v-if="errors.edit._ != null" dense color="error">{{ errors.edit._ }}</v-alert>
					<v-text-field v-model="inputs.edit.url" :error="errors.edit.url !== null" :error-messages="errors.edit.url" :label="$t('glial.machines.settings.urls.fields.url')" :hint="$t('glial.machines.settings.urls.hint')" persistent-hint></v-text-field>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn text @click="dialogs.edit = false">{{ $t("common.cancel") }}</v-btn>
					<v-btn text @click="edit()">{{ $t("common.edit") }}</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
		<v-dialog v-model="dialogs.delete" max-width="290">
			<v-card>
				<v-card-title class="headline">{{ inputs.delete.machine }}</v-card-title>
				<v-card-text>
					<v-alert v-if="errors.delete != null" dense color="error">{{ errors.delete }}</v-alert>
					{{ $t("glial.machines.settings.urls.dialogs.delete.text") }}
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn text @click="dialogs.delete = false">{{ $t("common.cancel") }}</v-btn>
					<v-btn text @click="deleteEntry()">{{ $t("common.delete") }}</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</v-container>
</template>

<script>
export default {
	name: "MachineSettings",
	data: () => ({
		beaconURLs: [],
		loading: false,
		errors: {
			refresh: null,
			create: {
				_: null,
				machine: null,
				url: null,
			},
			edit: {
				_: null,
				machine: null,
				url: null,
			},
			delete: null,
		},
		dialogs: {
			create: false,
			edit: false,
			delete: false,
		},
		inputs: {
			create: {
				machine: null,
				url: null,
			},
			edit: {
				machine: null,
				url: null,
			},
			delete: {
				machine: null,
			},
		},
	}),
	computed: {
		items() {
			const out = [...this.$store.state.glial.machine.items];
			const names = out.map((x) => x.machine);

			out.push(...this.beaconURLs.filter((x) => names.indexOf(x.machine) == -1));

			return out;
		},
		headers() {
			return [
				{
					text: this.$t("glial.machines.settings.urls.columns.machines"),
					align: "start",
					sortable: false,
					value: "machine",
				},
				{
					text: this.$t("glial.machines.settings.urls.columns.urls"),
					align: "start",
					sortable: false,
					value: "url",
				},
				{
					text: this.$t("glial.machines.settings.urls.columns.actions"),
					align: "end",
					sortable: false,
					value: "action",
				},
			];
		},
	},
	methods: {
		openEditDialog(item) {
			this.resetErrors();

			this.inputs.edit.machine = item.machine;
			this.inputs.edit.url = item.url;

			this.dialogs.edit = true;
		},
		openDeleteDialog(item) {
			this.resetErrors();

			this.inputs.delete.machine = item.machine;

			this.dialogs.delete = true;
		},
		refresh(withMachines = false) {
			if (this.loading) {
				return;
			}

			this.loading = true;

			if (withMachines) {
				this.$store.dispatch("glial/machine/fetch");
			}

			return this.$http
				.get(`/glial/beacon-urls/`)
				.then((response) => (this.beaconURLs = response.data.payload))
				.catch((error) => (this.errors.refresh = error))
				.then(() => (this.loading = false));
		},
		create() {
			return this.set("create");
		},
		edit() {
			return this.set("edit");
		},
		// TODO Rework
		set(inputsKey) {
			if (this.loading) {
				return;
			}

			this.resetErrors();
			this.loading = true;

			this.$http
				.post(`/glial/beacon-urls`, this.inputs[inputsKey])
				.then((response) => {
					this.dialogs[inputsKey] = false;
					this.inputs[inputsKey].machine = null;
					this.inputs[inputsKey].url = null;
				})
				.catch((error) => {
					switch (error.response?.status) {
						case 422: {
							this.findValidationError(this.errors[inputsKey], "machine", error.response.data.subErrors);
							this.findValidationError(this.errors[inputsKey], "url", error.response.data.subErrors);
							break;
						}

						default: {
							this.errors[inputsKey]._ = error;
							break;
						}
					}
					this.errors.delete = error;
				})
				.then(() => {
					this.loading = false;

					const errors = this.errors[inputsKey];
					if (!errors._ && !errors.machine && !errors.url) {
						this.inputs[inputsKey].machine = null;
						this.inputs[inputsKey].url = null;
						this.dialogs[inputsKey] = false;
						setTimeout(() => this.refresh(true), 0);
					}
				});
		},
		deleteEntry() {
			if (this.loading) {
				return;
			}

			this.resetErrors();
			this.loading = true;

			this.$http
				.delete(`/glial/beacon-urls`, {
					params: {
						machine: this.inputs.delete.machine,
					},
				})
				.then((response) => {
					this.dialogs.delete = false;
					this.inputs.delete.machine = null;
					this.inputs.delete.url = null;
				})
				.catch((error) => (this.errors.delete = error))
				.then(() => {
					this.loading = false;

					if (!this.errors.delete) {
						this.dialogs.delete = false;
						setTimeout(() => this.refresh(true), 0);
					}
				});
		},
		resetErrors() {
			this.errors = {
				refresh: null,
				create: {
					_: null,
					machine: null,
					url: null,
				},
				edit: {
					_: null,
					machine: null,
					url: null,
				},
				delete: null,
			};
		},
		findValidationError(from, key, violations) {
			let errors = [];
			for (let violation of violations) {
				if (violation.field === key) {
					errors.push(violation.message);
				}
			}
			this.$set(from, key, errors.length ? errors : null);
		},
	},
	mounted() {
		this.refresh();
	},
};
</script>
