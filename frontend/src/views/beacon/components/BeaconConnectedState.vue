<template>
	<div v-if="show">
		<v-icon v-if="isConnected()" color="success">mdi-wifi</v-icon>
		<v-icon v-else color="error">mdi-wifi-off</v-icon>
	</div>
</template>

<script>
import beaconService from "@/service/beaconService";

export default {
	name: "beacon-connected-state",
	props: {
		unique: {
			type: String,
			required: true,
		},
	},
	data() {
		return {
			show: true,
		};
	},
	methods: {
		isConnected() {
			return beaconService.isConnected(this.unique);
		},
		onBeaconConnected(unique) {
			this.rerender();
		},
		onBeaconDisconnected(unique) {
			this.rerender();
		},
		rerender() {
			this.show = false;
			this.$nextTick(() => {
				this.show = true;
				this.$nextTick(() => {});
			});
		},
	},
};
</script>