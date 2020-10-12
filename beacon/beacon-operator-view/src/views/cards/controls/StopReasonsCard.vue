<template>
	<v-card class="text-center" outlined>
		<div v-for="(category, index) in createCategoryTree()" :key="category.id" :class="{ 'mt-2': index != 0 }">
			<v-card outlined :color="category.color" tile>
				<h2 :style="`color: ${category.color}; filter: invert(1);`">
					{{ category.name }}
				</h2>
				<v-divider />
				<v-row>
					<template v-for="(stopReason, index2) in category.stopReasons">
						<v-divider v-if="index2 != 0" :key="-stopReason.id" vertical />
						<v-col :key="stopReason.id" :class="{ 'px-0': index2 != 0 && index2 != category.stopReasons.length - 1 }">
							<stop-reason-button-card :color="category.color" :name="stopReason.name" :clickable="clickable" :loading="loading" @click="report(stopReason.id)" />
						</v-col>
					</template>
				</v-row>
			</v-card>
		</div>
	</v-card>
</template>

<script>
import StopReasonButtonCard from "@/views/cards/controls/components/StopReasonButtonCard";

export default {
	name: "stop-reasons-card",
	components: {
		StopReasonButtonCard,
	},
	props: {
		loading: {
			type: Boolean,
			default: false,
		},
	},
	computed: {
		config() {
			return this.$store.state.config;
		},
		clickable() {
			return this.$store.state.cycle.seconds > this.$store.getters["cycle/max"];
		},
	},
	methods: {
		createCategoryTree() {
			let out = {};

			for (let category of this.config.stopReasonCategories) {
				out[category.id] = {
					...category,
					stopReasons: [],
				};
			}

			for (let stopReason of this.config.stopReasons) {
				out[stopReason.categoryId].stopReasons.push(stopReason);
			}

			return Object.values(out);
		},
		report(stopReasonId) {
			this.$emit("click", stopReasonId);
		},
	},
};
</script>