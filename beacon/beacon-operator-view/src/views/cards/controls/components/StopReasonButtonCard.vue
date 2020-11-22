<template>
	<v-card class="m-0 p-0" outlined :color="color">
		<v-btn class="ma-2" elevation="0" fab :color="color" style="filter: invert(1)" :disabled="!clickable" :loading="loading" @click="$emit('click')">
			<template v-slot:loader>
				<span class="v-btn__loader">
					<v-progress-circular style="filter: invert(1)" indeterminate :size="23" :width="2" :color="color" />
				</span>
			</template>
		</v-btn>
		<h3 class="px-0 py-0" :style="`display: block; color: ${color}; filter: invert(1);`">
			{{ name }}
		</h3>
	</v-card>
</template>

<script>
export default {
	name: "stop-reason-button-card",
	props: {
		color: {
			type: String,
			required: true,
		},
		clickable: {
			type: Boolean,
			default: true
		},
		loading: {
			type: Boolean,
			default: false
		},
		name: {
			type: String,
			required: true,
		}
	}
}
</script>