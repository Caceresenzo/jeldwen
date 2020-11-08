import Vue from 'vue'
import VueI18n from 'vue-i18n'

import application from "@/../application"

Vue.use(VueI18n);

let messages = {
	en: {
		name: "English",
		version: `JELDWEN v${application.version}`,
		route: {
			overview: {
				_: "OVERVIEW",
				home: "Home"
			},
			machines: {
				_: "MACHINES",
			},
			others: {
				_: "OTHERS",
				settings: "Settings",
			},
		},
		machines: {
			selector: {
				at: "At"
			},
			card: {
				percent: "produced/objective",
				csv: "csv",
				delete: "delete"
			},
			fields: {
				yellowStop: "Yellow stop",
				orangeStop: "Orange stop",
				pinkStop: "Pink stop",
				greenStop: "Green stop",
				blueStop: "Blue stop",
				otherStop: "Other stop",
				totalStop: "Total stop",
				nonQualifiedStop: "Non-Qualified stop",
				objective: "Objective",
				produced: "Produced",
			}
		},
		settings: {
			general: {
				_: "General",
				language: "Language",
				dark: "Dark mode",
			},
		}
	},
	fr: {
		name: "Français",
		version: `JELDWEN v${application.version}`,
		route: {
			overview: {
				_: "VU D'ENSEMBLE",
				home: "Acceuil"
			},
			machines: {
				_: "MACHINES",
			},
			others: {
				_: "AUTRES",
				settings: "Paramètres",
			},
		},
		machines: {
			selector: {
				at: "À"
			},
			card: {
				percent: "produit/objectif",
				csv: "csv",
				delete: "supprimer"
			},
			fields: {
				yellowStop: "Arret jaune",
				orangeStop: "Arret orange",
				pinkStop: "Arret rose",
				greenStop: "Arret vert",
				blueStop: "Arret blue",
				otherStop: "Arret other",
				totalStop: "Total arret",
				nonQualifiedStop: "Arret non-qualifié",
				objective: "Objectif",
				produced: "Produit",
			},
		},
		settings: {
			general: {
				_: "Générale",
				language: "Langage",
				dark: "Mode sombre",
			}
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