<template>
	<div v-if="items && items.length">
		<v-tabs v-model="tab">
			<v-tab v-for="(category, id) of byCategories" :key="id">
				{{ category.name }}
				<v-icon class="ml-1" :color="category.color">mdi-square-rounded</v-icon>
			</v-tab>
		</v-tabs>
		<v-divider></v-divider>
		<v-tabs-items class="pt-0" v-model="tab">
			<v-tab-item v-for="(category, id) of byCategories" :key="id">
				<slot v-bind:category="category" />
			</v-tab-item>
		</v-tabs-items>
	</div>
	<div v-else-if="items">
		<slot name="no-item">
			<empty />
		</slot>
	</div>
</template>

<script>
import Empty from "@/components/Empty"

export default {
	components: {
		Empty
	},
	name: "stop-reason-by-categories",
	props: {
		items: {
			type: Array,
			required: true,
		},
	},
	data: () => ({
		tab: null,
	}),
	computed: {
		byCategories() {
			let out = {};

			for (let stopReason of this.items) {
				let category = stopReason.category;
				let object = out[category.id] || {};

				object.name = category.name;
				object.color = category.color;
				(object.children = object.children || []).push(stopReason);

				out[category.id] = object;
			}

			return out;
		},
	},
};
</script>