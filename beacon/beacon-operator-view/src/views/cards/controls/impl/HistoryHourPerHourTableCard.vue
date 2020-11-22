<template>
	<hour-per-hour-table-card getter="data" :selected="now">
		<template v-slot:bottom v-if="max">
			<v-divider />
			<v-slider class="mt-4 mx-6 mb-0" v-model="offset" height="12" :min="0" :max="max" thumb-size="26"></v-slider>
		</template>
	</hour-per-hour-table-card>
</template>

<script>
import HourPerHourTableCard from "@/views/cards/controls/HourPerHourTableCard";

export default {
	name: "history-hour-per-hour-table-card",
	components: {
		HourPerHourTableCard,
	},
	data: () => ({
		now: [],
		timerId: null,
	}),
	computed: {
		offset: {
			get() {
				return this.$store.state.hourPerHour.offset.value;
			},
			set(val) {
				this.$store.commit("hourPerHour/setOffset", val);
			},
		},
		max() {
			return this.$store.state.hourPerHour.offset.max;
		},
	},
	methods: {
		updateDateTime() {
			const hour = new Date().getHours();

			if (hour != this.now[0]?.hour) {
				this.now = [{ hour: new Date().getHours() }];
			}

			this.nextTimer();
		},
		nextTimer() {
			this.timerId = window.setTimeout(this.updateDateTime, 1000);
		},
	},
	mounted() {
		this.nextTimer();
	},
	beforeDestroy() {
		if (this.timerId !== null) {
			window.clearTimeout(this.timerId);
		}
	},
};
</script>