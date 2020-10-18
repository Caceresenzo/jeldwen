<template>
	<v-list-item @click="$emit('click')">
		<v-list-item-action>
			<v-icon>mdi-theme-light-dark</v-icon>
		</v-list-item-action>
		<v-list-item-content>
			<v-switch v-model="$vuetify.theme.dark" :label="$t('settings.general.dark')"></v-switch>
		</v-list-item-content>
	</v-list-item>
</template>

<script>
export default {
	name: "dark-mode-setting",
	watch: {
		"$vuetify.theme.dark"(val) {
			localStorage.setItem("dark", val);
		},
	},
};
</script>