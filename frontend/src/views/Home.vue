<template>
	<v-container fluid>
		<v-row>
			<v-col>
				<v-card>
					<v-card-title>{{ $t('home.welcome') }}</v-card-title>
					<v-card-text>{{ $t('home.no-purpose') }}</v-card-text>
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
export default {
	name: "Home",
};
</script>
