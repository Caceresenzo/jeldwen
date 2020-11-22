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
		state: "waiting",
		color: "warning",
	}),
	computed: {
		message() {
			return this.$t(`socket.state.${this.state}`);
		}
	},
	methods: {
		onSocketConnect() {
			this.state = "connecting";
			this.color = "warning";
		},
		onSocketOpen() {
			this.state = "connected";
			this.color = "success";
		},
		onSocketError(error) {
			this.state = "error";
			this.color = "error";
		},
		onSocketClose(event) {
			if (this.state !== "error") {
				this.state = "close";
				this.color = "error";
			}
		},
	},
};
</script>