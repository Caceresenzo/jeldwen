<template>
	<v-list-item @click="$emit('click')">
		<v-list-item-action>
			<v-icon>mdi-translate</v-icon>
		</v-list-item-action>
		<v-list-item-content>
			<v-select v-model="$i18n.locale" :items="Object.keys($i18n.messages)" :label="$t('settings.language')">
				<template slot="selection" slot-scope="{ item }">
					{{ $i18n.messages[item].name }}
				</template>
				<template slot="item" slot-scope="{ item }">
					{{ $i18n.messages[item].name }}
					<v-spacer />
					<v-img :src="require(`@/assets/flags/${item}.svg`)" max-height="32" max-width="32" />
				</template>
			</v-select>
		</v-list-item-content>
	</v-list-item>
</template>

<script>
export default {
	name: "i18n-settings",
	watch: {
		"$i18n.locale"(val) {
			localStorage.setItem("language", val);
		},
	},
};
</script>