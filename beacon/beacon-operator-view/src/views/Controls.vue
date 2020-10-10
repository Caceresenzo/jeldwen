<template>
	<v-container fluid style="user-select: none">
		<v-row>
			<v-col sm="12" md="4" lg="2">
				<v-card style="z-index: 2;" class="text-center white--text" @click="closeWorkstation()" outlined color="success">
					<v-card-title style="display: block">WORKSTATION OPEN</v-card-title>
					<v-card-subtitle class="white--text">CLICK TO CLOSE</v-card-subtitle>
				</v-card>
				<v-card style="z-index: 2;" class="mt-4" outlined>
					<v-list class="py-0">
						<div v-for="(family, index) in config.productFamilies" :key="family.id">
							<v-divider v-if="index != 0" />
							<v-list-item>
								<v-list-item-content>
									<v-list-item-title class="font-weight-bold">{{ family.name }}</v-list-item-title>
									<v-list-item-title>{{ family.cycleTime }} sec</v-list-item-title>
								</v-list-item-content>
								<v-btn class="ma-2" elevation="0" large fab :color="cycle.activeFamily === family.id ? 'success' : 'primary'" @click="changeFamily(family.id)" :loading="loading.family">
									<v-icon>mdi-{{ cycle.activeFamily === family.id ? "play" : "pause" }}</v-icon>
								</v-btn>
							</v-list-item>
						</div>
						<empty v-if="!config.productFamilies.length" />
					</v-list>
				</v-card>
				<current-cycle-card :seconds="cycle.seconds" :max="currentCycleMax" />
			</v-col>
			<v-col sm="12" md="8" lg="5">
				<v-card outlined>
					<v-data-table :headers="headers" :items="hourPerHourData" item-key="hour" :value="hourPerHour.now" :disable-sort="true" :hide-default-footer="true" class="fixed-table">
						<template v-for="h in headers" v-slot:[`header.${h.value}`]="{ header }">
							<h2>{{ header.text }}</h2>
						</template>
						<template v-slot:item.hour="{ item }">
							{{ item.hour.toString().padStart(2, '0') }}h
							<br />
							{{ (item.hour + 1).toString().padStart(2, '0') }}h
						</template>
						<template v-slot:item.open="{ item }">
							<div v-if="item.open">{{ toTime(item.open) }}</div>
						</template>
						<template v-slot:item.stop="{ item }">
							<div v-if="item.stop">{{ toTime(item.stop) }}</div>
						</template>
						<template v-slot:item.percent="{ item }">
							<v-chip v-if="item.percent" :color="item.percent > config.syntheticpercentRateThreshold ? 'success' : 'error'">{{ item.percent * 100 }}%</v-chip>
						</template>
					</v-data-table>
					<v-divider />
					<v-slider class="mt-4 mx-6 mb-0" v-model="hourPerHour.offset" height="12" :min="0" :max="12" thumb-size="26"></v-slider>
				</v-card>
				<v-card class="mt-4" outlined>
					<v-data-table hide-default-header :headers="headers" :items="hourPerHourDayResume" item-key="hour" :value="hourPerHour.now" :disable-sort="true" :hide-default-footer="true" class="fixed-table">
						<template v-slot:item.open="{ item }">
							<div v-if="item.open">{{ toTime(item.open) }}</div>
						</template>
						<template v-slot:item.stop="{ item }">
							<div v-if="item.stop">{{ toTime(item.stop) }}</div>
						</template>
						<template v-slot:item.percent="{ item }">
							<v-chip v-if="item.percent" :color="item.percent > config.syntheticPerformanceRateThreshold ? 'success' : 'error'">{{ item.percent * 100 }}%</v-chip>
						</template>
					</v-data-table>
				</v-card>
				<v-card class="mt-4" outlined>
					<v-data-table hide-default-header :headers="headers" :items="hourPerHourTotal" item-key="hour" :value="hourPerHour.now" :disable-sort="true" :hide-default-footer="true" class="fixed-table">
						<template v-slot:item.open="{ item }">
							<div v-if="item.open">{{ toTime(item.open) }}</div>
						</template>
						<template v-slot:item.stop="{ item }">
							<div v-if="item.stop">{{ toTime(item.stop) }}</div>
						</template>
						<template v-slot:item.percent="{ item }">
							<v-chip v-if="item.percent" :color="item.percent > config.syntheticPerformanceRateThreshold ? 'success' : 'error'">{{ item.percent * 100 }}%</v-chip>
						</template>
					</v-data-table>
				</v-card>
			</v-col>
			<v-col sm="12" md="12" lg="5">
				<v-card class="text-center" outlined>
					<div v-for="(category, index) in createCategoryTree()" :key="category.id" :class="{ 'mt-2': index != 0 }">
						<v-card outlined :color="category.color" tile>
							<h2 :style="`color: ${category.color}; filter: invert(1);`">{{ category.name }}</h2>
							<v-divider />
							<v-row>
								<template v-for="(stopReason, index2) in category.stopReasons">
									<v-divider v-if="index2 != 0" :key="-stopReason.id" vertical />
									<v-col :key="stopReason.id" :class="{ 'px-0': index2 != 0 && index2 != category.stopReasons.length - 1 }">
										<v-card class="m-0 p-0" outlined :color="category.color">
											<v-btn class="ma-2" elevation="0" fab :color="category.color" style="filter: invert(1)" :disabled="!stopReasonEnabled" @click="report(stopReason.id)"> </v-btn>
											<h3 class="px-0 py-0" :style="`display: block; color: ${category.color}; filter: invert(1);`">{{ stopReason.name }}</h3>
										</v-card>
									</v-col>
								</template>
							</v-row>
						</v-card>
					</div>
				</v-card>
			</v-col>
		</v-row>
		<v-overlay :z-index="50" :value="(!firstConnect || connected) && !opened" :opacity="1">
			<v-card v-if="firstConnect" class="text-center" @click="openWorkstation()" outlined>
				<v-card-title class="mb-5">
					<h1>WORKSTATION CLOSED</h1>
				</v-card-title>
				<v-card-subtitle>
					<h1>CLICK TO OPEN</h1>
				</v-card-subtitle>
			</v-card>
		</v-overlay>
		<v-overlay :z-index="50" :value="!connected">
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
		<v-overlay :z-index="1" :value="!cycle.activeFamily"></v-overlay>
		<v-overlay :z-index="500" :value="connected && config && !config.name" :opacity="1">
			<v-card class="text-center" outlined>
				<v-card-title class="mb-5">
					<h1>WORKSTATION NOT CONFIGURED</h1>
				</v-card-title>
				<v-card-subtitle>
					<h1>AVAILABLE AT THE JELDWEN PANEL</h1>
				</v-card-subtitle>
			</v-card>
		</v-overlay>
	</v-container>
</template>

<script>
import SocketState from "@/views/components/SocketState";
import CurrentCycleCard from "@/views/components/controls/CurrentCycleCard";

export default {
	components: {
		SocketState,
		CurrentCycleCard,
	},
	name: "Controls",
	data: () => ({
		opened: false,
		firstConnect: false,
		connected: false,
		hourPerHour: {
			offset: 12,
			now: [],
			current: {},
		},
		cycle: {
			seconds: -1,
			activeFamily: 0,
		},
		loading: {
			family: false,
		},
		config: {
			name: null,
			stopReasons: [],
			stopReasonCategories: [],
			productFamilies: [],
			syntheticPerformanceRateThreshold: 0.0,
		},
		desserts: [
			{
				hour: 5,
				opened: 654,
				closed: 231,
				produced: 12,
				objective: 46,
				performance: 0.26,
			},
			{
				hour: 6,
				opened: 654,
				closed: 231,
				produced: 12,
				objective: 46,
				performance: 0.26,
			},
			{
				hour: 7,
				opened: 654,
				closed: 231,
				produced: 12,
				objective: 46,
				performance: 0.26,
			},
			{
				hour: 8,
			},
			{
				hour: 9,
			},
		],
		headers: [
			{
				align: "center",
				divider: true,
				/*                       */ value: "hour",
				width: "10%",
			},
			{
				align: "center",
				divider: true,
				text: "Open",
				/*       */ value: "open",
			},
			{
				align: "center",
				divider: true,
				text: "Stop",
				/*       */ value: "stop",
			},
			{
				align: "center",
				divider: true,
				text: "Produced",
				/*     */ value: "produced",
			},
			{
				align: "center",
				divider: true,
				text: "Objective",
				/*    */ value: "objective",
			},
			{
				align: "center",
				divider: true,
				text: "TRS",
				/*          */ value: "percent",
			},
		],
		timerId: null,
	}),
	computed: {
		currentCycleMax() {
			if (this.cycle.activeFamily) {
				for (let productFamily of this.config?.productFamilies) {
					if (productFamily.id == this.cycle.activeFamily) {
						return productFamily.cycleTime;
					}
				}
			}

			return 0;
		},
		stopReasonEnabled() {
			return this.cycle.seconds > this.currentCycleMax;
		},
		hourPerHourData() {
			let entries = [];

			const hourNow = new Date().getHours();
			for (let offset = -4; offset < 3; offset++) {
				let newHour = hourNow + offset + (this.hourPerHour.offset - 12);
				let hour;

				if (newHour > 23) {
					hour = newHour - 24;
				} else if (newHour < 0) {
					hour = newHour + 23;
				} else {
					hour = newHour;
				}

				if (newHour > hourNow) {
					entries.push({
						hour,
					});
				} else {
					/*entries.push({
						hour,
						opened: 654,
						closed: 231,
						produced: 12,
						objective: 46,
						performance: Number(
							Math.random().toFixed(2).substring(0, 3)
						),
					});*/
					entries.push({
						...this.hourPerHour.current,
						hour,
					});
				}
			}

			return entries;
		},
		hourPerHourDayResume() {
			return [
				{
					hour: "morning",
					opened: 654,
					closed: 231,
					produced: 12,
					objective: 46,
					performance: Math.random().toFixed(2),
				},
				{
					hour: "evening",
					opened: 654,
					closed: 231,
					produced: 12,
					objective: 46,
					performance: Math.random().toFixed(2),
				},
				{
					hour: "night",
					opened: 654,
					closed: 231,
					produced: 12,
					objective: 46,
					performance: Math.random().toFixed(2),
				},
			];
		},
		hourPerHourTotal() {
			return [
				{
					hour: "total",
					opened: 654,
					closed: 231,
					produced: 12,
					objective: 46,
					performance: Math.random().toFixed(2),
				},
			];
		},
	},
	watch: {
		x() {
			console.log(this.x);
		},
	},
	methods: {
		changeFamily(id) {
			if (this.cycle.activeFamily == id) {
				return;
			}

			this.loading.family = true;

			this.$beacon.changeFamily(id);
		},
		report(id) {
			this.loading.stopReason = true;

			this.$beacon.report(id);
		},
		createCategoryTree() {
			let out = {};

			for (let category of this.config.stopReasonCategories) {
				out[category.id] = {
					...category,
					stopReasons: [],
				};
			}

			for (let stopReason of this.config.stopReasons) {
				out[stopReason.categoryId].stopReasons.push(stopReason);
			}

			return Object.values(out);
		},
		toTime(seconds) {
			let seconds60 = seconds % 60;
			let minutes = (seconds - seconds60) / 60;

			return `${minutes
				.toString()
				.padStart(2, "0")}:${seconds60.toString().padStart(2, "0")}`;
		},
		updateDateTime() {
			this.hourPerHour.now = [{ hour: new Date().getHours() }];
			this.nextTimer();
		},
		nextTimer() {
			this.timerId = window.setTimeout(this.updateDateTime, 1000);
		},
		closeWorkstation() {
			this.$beacon.close();
		},
		openWorkstation() {
			this.$beacon.open();
		},
		onSocketOpen() {
			this.connected = true;
			this.firstConnect = true;
		},
		onSocketClose() {
			this.connected = false;
		},
		onWorkstationOpen() {
			console.log("open");
			this.opened = true;
		},
		onWorkstationClose() {
			console.log("close");
			this.opened = false;
		},
		onRhythmSync(seconds, currentHourPerHour) {
			this.cycle.seconds = seconds;
			this.hourPerHour.current = currentHourPerHour;
		},
		onFamilyChanged(id) {
			this.cycle.activeFamily = id;
			this.loading.family = false;
		},
		onReported(id) {
			this.loading.stopReason = false;
		},
		onConfig(config) {
			this.config = config;
		},
	},
	mounted() {
		this.nextTimer();
	},
	beforeDestroy() {
		if (this.timerId !== null) {
			window.clearTimeout(this.timerId);
		}
	},
};
</script>

<style scoped>
	.fixed-table >>> table {
		table-layout: fixed;
	}
</style>