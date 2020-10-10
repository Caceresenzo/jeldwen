<template>
	<v-row align="center" justify="center">
		<v-col class="shrink">
			<slot>
				<v-icon large>mdi-bee</v-icon>
			</slot>
		</v-col>
	</v-row>
</template>

<script>
export default {
	name: "empty"
}
</script>