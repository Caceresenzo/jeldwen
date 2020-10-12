import Vue from 'vue'
import VueI18n from 'vue-i18n'

import application from "@/../application"

Vue.use(VueI18n)

let messages = {
	en: {
		name: "English",
		version: `OPERATOR VIEW v${application.version}`,
		socket: {
			state: {
				waiting: "WAITING",
				connecting: "CONNECTING...",
				connected: "CONNECTED",
				error: "ERROR",
				close: "NOT CONNECTED"
			}
		},
		controls: {
			emptyName: "(unnamed)",
			workstation: {
				close: {
					title: "WORKSTATION CLOSED",
					subtitle: "CLICK TO OPEN"
				},
				open: {
					title: "WORKSTATION OPEN",
					subtitle: "CLICK TO CLOSE"
				},
				unconfigured: {
					title: "WORKSTATION NOT CONFIGURED",
					subtitle: "AVAILABLE AT THE JELDWEN PANEL"
				}
			},
			cycle: {
				current: "CURRENT CYCLE"
			},
			hourPerHour: {
				table: {
					hour: "HOUR",
					open: "OPEN",
					stop: "STOP",
					produced: "PRODUCED",
					objective: "OBJECTIVE",
					percent: "%",
				},
				sums: {
					morning: "MORNING",
					evening: "EVENING",
					night: "NIGHT",
					total: "TOTAL",
				}
			}
		},
		settings: {
			"update-config": "Update the configuration",
			language: "Language",
			dark: "Dark mode",
		}
	},
	fr: {
		name: "Français",
		version: `VUE OPÉRATEUR v${application.version}`,
		socket: {
			state: {
				waiting: "EN ATTENTE",
				connecting: "CONNECTION EN COURS...",
				connected: "CONNECTÉE",
				error: "ERREUR",
				close: "NON CONNECTÉE"
			}
		},
		controls: {
			emptyName: "(sans nom)",
			workstation: {
				close: {
					title: "STATION DE TRAVAIL FERMÉE",
					subtitle: "CLIQUER POUR OUVRIR"
				},
				open: {
					title: "STATION DE TRAVAIL OUVERTE",
					subtitle: "CLIQUER POUR FERMER"
				},
				unconfigured: {
					title: "STATION DE TRAVAIL NON CONFIGURÉE",
					subtitle: "DISPONIBLE SUR LE PANNEAU DE CONFIGURATION JELDWEN"
				}
			},
			cycle: {
				current: "CYCLE ACTUEL"
			},
			hourPerHour: {
				table: {
					hour: "HEURE",
					open: "OUVERTURE",
					stop: "ARRÊT",
					produced: "PRODUIT",
					objective: "OBJECTIF",
					percent: "TRS",
				},
				sums: {
					morning: "MATIN",
					evening: "SOIR",
					night: "NUIT",
					total: "TOTAL",
				}
			}
		},
		settings: {
			"update-config": "Mettre à jour la configuration",
			language: "Langage",
			dark: "Mode sombre",
		}
	}
};

let locale = localStorage.getItem("language") || application.defaultLocale;
const availables = Object.keys(messages);

if (!availables.includes(locale)) {
	locale = availables[0];
	localStorage.setItem("language", locale);
}

export default new VueI18n({
	locale: locale,
	messages
})