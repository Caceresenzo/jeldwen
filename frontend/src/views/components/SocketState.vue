<template>
	<v-btn :color="color" v-text="message">CONNECTED</v-btn>
</template>
<script>
export default {
	name: "socket-state",
	data: () => ({
		message: "WAITING",
		color: "warning",
	}),
	methods: {
		onSocketConnect() {
			this.message = "CONNECTING...";
			this.color = "warning";
		},
		onSocketOpen() {
			this.message = "CONNECTED";
			this.color = "success";
		},
		onSocketClose() {
			this.message = "NOT CONNECTED";
			this.color = "error";
		},
		onSocketError(error) {
			this.message = "ERROR: " + error;
			this.color = "error";
		},
	},
};
</script>