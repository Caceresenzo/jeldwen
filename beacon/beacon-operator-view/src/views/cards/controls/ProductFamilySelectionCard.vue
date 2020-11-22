<template>
	<v-card outlined>
		<v-list class="py-0">
			<div v-for="(family, index) in config.productFamilies" :key="family.id">
				<v-divider v-if="index != 0" />
				<v-list-item>
					<v-list-item-content>
						<v-list-item-title class="font-weight-bold">{{ family.name }}</v-list-item-title>
						<v-list-item-title>{{ family.cycleTime }} sec</v-list-item-title>
					</v-list-item-content>
					<v-btn class="ma-2" elevation="0" large fab :color="active === family.id ? 'success' : 'primary'" @click="$emit('click', family.id)" :loading="loading">
						<v-icon>mdi-{{ active === family.id ? "play" : "pause" }}</v-icon>
					</v-btn>
				</v-list-item>
			</div>
			<empty v-if="!config.productFamilies.length" />
		</v-list>
	</v-card>
</template>

<script>
export default {
	name: "product-family-selection-card",
	props: {
		loading: {
			type: Boolean,
			default: () => false
		}
	},
	computed: {
		config() {
			return this.$store.state.config;
		},
		active() {
			return this.$store.state.cycle.activeFamily;
		}
	}
}
</script>