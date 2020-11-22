<template>
	<v-overlay :z-index="zIndex" :value="value">
		<v-card class="text-center" outlined>
			<v-card-title class="mb-5">
				<socket-state>
					<template v-slot="{ color, message }">
						<v-btn :color="color" depressed large>
							<h1 v-text="message"></h1>
						</v-btn>
					</template>
				</socket-state>
			</v-card-title>
			<v-card-text>
				<v-progress-circular indeterminate size="64"></v-progress-circular>
			</v-card-text>
		</v-card>
	</v-overlay>
</template>

<script>
import SocketState from "./SocketState";

export default {
	name: "socket-state-overlay",
	components: {
		SocketState,
	},
	props: {
		zIndex: {
			type: Number,
			default: 5
		},
		value: {
			type: Boolean,
			default: false
		}
	}
}
</script>