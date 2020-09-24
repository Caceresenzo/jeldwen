<template>
	<v-container fluid>
		<v-row>
			<v-col cols="12">
				<list-card title="Availables" endpoint="/beacon" :linkFormatter="(item) => '/beacon/' + item.id">
					<template v-slot="{ item }">
						<v-list-item-avatar>
							<v-icon>mdi-car-light-high</v-icon>
						</v-list-item-avatar>
						<v-list-item-content v-if="item.name">
							<v-list-item-title v-text="item.name"></v-list-item-title>
							<v-list-item-subtitle v-text="item.unique"></v-list-item-subtitle>
						</v-list-item-content>
						<v-list-item-content v-else>
							<v-list-item-title v-text="item.unique"></v-list-item-title>
							<v-list-item-subtitle>Not configured</v-list-item-subtitle>
						</v-list-item-content>
						<v-list-item-action>
							<beacon-connected-state :unique="item.unique" />
						</v-list-item-action>
					</template>
				</list-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import BeaconConnectedState from "./components/BeaconConnectedState";

export default {
	components: {
		BeaconConnectedState,
	},
};
</script>
