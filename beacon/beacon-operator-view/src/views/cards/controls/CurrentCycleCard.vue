<template>
	<v-card v-show="seconds !== null" class="mt-4 text-center white--text" outlined :color="color">
		<v-card-title style="display: block">{{ $t("controls.cycle.current") }}</v-card-title>
		<v-card-subtitle class="white--text">
			<h2>{{ formatted }}</h2>
		</v-card-subtitle>
		<div class="mx-4 mb-4">
			<v-progress-linear :value="Math.min(percent, 100)" height="30" :color="$vuetify.theme.dark ? 'black' : 'white'">{{ percent.toFixed(0) }} % </v-progress-linear>
		</div>
	</v-card>
</template>

<script>
export default {
	name: "current-cycle-card",
	computed: {
		seconds() {
			return this.$store.state.cycle.seconds;
		},
		max() {
			return this.$store.getters["cycle/max"];
		},
		percent() {
			return (this.seconds / this.max) * 100;
		},
		formatted() {
			let seconds60 = this.seconds % 60;
			let minutes = (this.seconds - seconds60) / 60;

			return `${minutes.toString().padStart(2, "0")}:${seconds60.toString().padStart(2, "0")}`;
		},
		color() {
			return this.seconds > this.max ? "red" : "green";
		},
	},
};
</script>