<template>
	<div>
		<slot :message="message" :color="color">
			<v-btn :color="color" v-text="message" depressed />
		</slot>
	</div>
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
		onSocketError(error) {
			this.message = "ERROR";
			this.color = "error";
		},
		onSocketClose(event) {
			if (this.color !== "error") {
				this.message = "NOT CONNECTED";
				this.color = "error";
			}
		},
	},
};
</script>