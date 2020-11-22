<template>
	<v-container fluid style="user-select: none">
		<v-row>
			<v-col sm="12" md="4" lg="2">
				<close-workstation-card style="z-index: 2" @click="closeWorkstation()" />
				<product-family-selection-card :loading="loading.family" style="z-index: 2" class="mt-4" @click="changeFamily" />
				<current-cycle-card />
			</v-col>
			<v-col sm="12" md="8" lg="5">
				<history-hour-per-hour-table-card />
				<hour-per-hour-table-card class="mt-4" getter="summary" />
				<hour-per-hour-table-card class="mt-4" getter="total" />
			</v-col>
			<v-col sm="12" md="12" lg="5">
				<stop-reasons-card :loading="loading.stopReason" @click="report" />
			</v-col>
		</v-row>
		<v-overlay :z-index="50" :value="(!firstConnect || connected) && !opened" :opacity="1">
			<v-card v-if="firstConnect" class="text-center" @click="openWorkstation()" outlined>
				<v-card-subtitle v-if="config && config.name">
					<h1>{{ (config || {}).name }}</h1>
				</v-card-subtitle>
				<v-card-title class="mb-5">
					<h1>{{ $t("controls.workstation.close.title") }}</h1>
				</v-card-title>
				<v-card-subtitle>
					<h1>{{ $t("controls.workstation.close.subtitle") }}</h1>
				</v-card-subtitle>
			</v-card>
		</v-overlay>
		<socket-state-overlay :z-index="50" :value="!connected" />
		<v-overlay :z-index="1" :opacity="0.8" :value="activeFamily === null"></v-overlay>
		<v-overlay :z-index="500" :value="connected && config && !config.name" :opacity="1">
			<v-card class="text-center" outlined>
				<v-card-title class="mb-5">
					<h1>{{ $t("controls.workstation.unconfigured.title") }}</h1>
				</v-card-title>
				<v-card-subtitle>
					<h1>
						{{ $t("controls.workstation.unconfigured.subtitle") }}
					</h1>
				</v-card-subtitle>
			</v-card>
		</v-overlay>
	</v-container>
</template>

<script>
import SocketState from "@/views/components/SocketState";
import SocketStateOverlay from "@/views/components/SocketStateOverlay";
import CloseWorkstationCard from "@/views/cards/controls/CloseWorkstationCard";
import ProductFamilySelectionCard from "@/views/cards/controls/ProductFamilySelectionCard";
import CurrentCycleCard from "@/views/cards/controls/CurrentCycleCard";
import HourPerHourTableCard from "@/views/cards/controls/HourPerHourTableCard";
import HistoryHourPerHourTableCard from "@/views/cards/controls/impl/HistoryHourPerHourTableCard";
import StopReasonsCard from "@/views/cards/controls/StopReasonsCard";

export default {
	components: {
		SocketState,
		SocketStateOverlay,
		CloseWorkstationCard,
		ProductFamilySelectionCard,
		CurrentCycleCard,
		HourPerHourTableCard,
		HistoryHourPerHourTableCard,
		StopReasonsCard,
	},
	name: "Controls",
	data: () => ({
		loading: {
			family: false,
			stopReason: false,
		},
	}),
	computed: {
		config() {
			return this.$store.state.config;
		},
		opened() {
			return this.$store.state.opened;
		},
		firstConnect() {
			return this.$store.state.firstConnect;
		},
		connected() {
			return this.$store.state.connected;
		},
		activeFamily() {
			return this.$store.state.cycle.activeFamily;
		}
	},
	methods: {
		changeFamily(id) {
			if (this.activeFamily == id) {
				return;
			}

			this.loading.family = true;

			this.$beacon.changeFamily(id);
		},
		report(id) {
			this.loading.stopReason = true;

			this.$beacon.report(id);
		},
		closeWorkstation() {
			this.$beacon.close();
		},
		openWorkstation() {
			this.$beacon.open();
		},
		onFamilyChanged(id) {
			this.loading.family = false;
		},
		onReported(success) {
			this.loading.stopReason = false;
		},
	},
};
</script>