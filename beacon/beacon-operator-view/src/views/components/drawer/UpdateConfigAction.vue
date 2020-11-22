<template>
	<v-list-item @click="clicked()">
		<v-list-item-action>
			<v-icon>mdi-update</v-icon>
		</v-list-item-action>
		<v-list-item-content>
			<v-list-item-title>{{ $t("settings.update-config") }}</v-list-item-title>
		</v-list-item-content>
	</v-list-item>
</template>

<script>
export default {
	name: "update-config-action",
	methods: {
		clicked() {
			this.$emit("click");

			console.log("TODO"); // TODO
		},
	},
};
</script>