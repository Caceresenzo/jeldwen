<template>
	<v-container fluid>
		<v-row>
			<v-col>
				<v-card>
					<v-card-title>{{ $t("settings.general._") }}</v-card-title>
					<v-divider></v-divider>
					<dark-mode-setting />
					<i18n-setting />
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import DarkModeSetting from "./components/DarkModeSetting";
import I18nSetting from "./components/I18nSetting";

export default {
	name: "Settings",
	components: {
		DarkModeSetting,
		I18nSetting,
	},
};
</script>
