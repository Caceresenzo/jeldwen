<template>
	<loading-card :title="title" :endpoint="endpoint">
		<template v-slot="{ payload }">
			<empty v-if="payload && !payload.length" />
			<v-list v-else-if="payload" class="py-0">
				<v-list-item v-for="item in payload" :key="item.id" :link="linkFormatter != null" :to="linkFormatter ? linkFormatter(item) : null">
					<slot v-bind:item="item">{{ item }}</slot>
				</v-list-item>
			</v-list>
		</template>
		<template v-if="$scopedSlots.actions" v-slot:actions="{ payload }">
			<slot name="actions" v-bind:payload="payload"></slot>
		</template>
	</loading-card>
</template>

<script>
export default {
	name: "list-card",
	props: {
		title: {
			type: String,
			default: null,
		},
		endpoint: {
			type: String,
		},
		linkFormatter: {
			type: Function,
			default: null,
		},
	},
};
</script>
