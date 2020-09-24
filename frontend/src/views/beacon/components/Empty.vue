<template>
	<v-row align="center" justify="center">
		<v-col class="shrink">
			<slot>
				<v-icon v-on="on" large>mdi-bee</v-icon>
			</slot>
		</v-col>
	</v-row>
</template>