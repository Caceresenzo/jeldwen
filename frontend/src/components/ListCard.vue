<template>
	<loading-card :title="title" :endpoint="endpoint">
		<template v-slot="{ payload }">
			<v-list v-if="payload" two-line subheader>
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
