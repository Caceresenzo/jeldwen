<template>
	<v-card class="text-center white--text" @click="$emit('click')" outlined color="success">
		<v-card-title style="display: block">{{ $t("controls.workstation.close.title") }}</v-card-title>
		<v-card-subtitle class="white--text">{{ $t("controls.workstation.close.subtitle") }}</v-card-subtitle>
	</v-card>
</template>

<script>
export default {
	name: "close-workstation-card"
}
</script>