import Vue from 'vue'
import VueI18n from 'vue-i18n'

import application from "@/../application"

Vue.use(VueI18n);

let messages = {
	en: {
		name: "English",
		version: `JELDWEN v${application.version}`,
		route: {
			home: "Home",
			beacons: {
				_: "BEACONS",
				editor: "Editor"
			},
			"glial-machines": {
				_: "MACHINES GLIAL",
				overview: "Overview",
			},
			others: {
				_: "OTHERS",
				settings: "Settings",
			},
		},
		common: {
			edit: "EDIT",
			refresh: "REFRESH",
			update: "UPDATE",
			cancel: "CANCEL",
			close: "CLOSE",
			create: "CREATE",
			"go-back": "GO BACK",
			trigger: "TRIGGER",
			reconfigure: "RECONFIGURE",
			fields: {
				name: "Name",
				category: "Category",
				children: "Children",
			}
		},
		socket: {
			state: {
				waiting: "WAITING",
				connecting: "CONNECTING...",
				connected: "CONNECTED",
				error: "ERROR",
				close: "NOT CONNECTED"
			}
		},
		beacon: {
			_: "Beacon",
			plural: "Beacons",
			connection: "Connection",
			"synthetic-performance-rate-threshold": "Synthetic Performance Rate Threshold",
			"product-family": {
				plural: "Product Families"
			},
			"stop-reason": {
				_: "Raison d'Arrêt",
				plural: "Stop Reasons",
				group: {
					_: "Group de Raison d'Arrêt",
					plural: "Stop Reason Groups",
					"child-count": "no children | 1 child | {count} children"
				},
				action: {
					detach: {
						_: "Detach (free from group)",
						confirm: {
							title: "Detach confirmation",
							text: "Do you want to detach this stop reason from this group?"
						}
					},
					delete: {
						_: "Delete (remove from everywhere)",
						confirm: {
							title: "Delete confirmation",
							text: "Do you want to delete this stop reason completly? This will cause data loss when displaying graphs!"
						}
					},
					edit: {
						_: "Edit"
					}
				}
			},
			"action-zone": {
				_: "Action Zone",
				"force-trigger": {
					_: "Force sensor trigger",
					description: "Fake a sensor trigger",
				},
				"force-config": {
					_: "Force configuration",
					description: "Don't wait the beacon to restart, push a configuration by force",
				}
			}
		},
		glial: {
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
				},
				tooltips: {
					seconds: "second | second | seconds",
					times: "time | time | times",
				}
			},
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
			home: "Home",
			beacons: {
				_: "BALISES",
				editor: "Éditeur"
			},
			"glial-machines": {
				_: "MACHINES GLIAL",
				overview: "Vu d'ensemble",
			},
			others: {
				_: "AUTRES",
				settings: "Paramètres",
			},
		},
		common: {
			edit: "ÉDITER",
			refresh: "RAFRAICHIR",
			update: "METTRE À JOUR",
			cancel: "ANNULER",
			close: "FERMER",
			create: "CRÉER",
			"go-back": "RETOURNER EN ARRIÈRE",
			trigger: "DÉCLANCHER",
			reconfigure: "RECONFIGURER",
			fields: {
				name: "Nom",
				category: "Catégorie",
				children: "Enfants",
			}
		},
		socket: {
			state: {
				waiting: "EN ATTENTE",
				connecting: "CONNECTION EN COURS...",
				connected: "CONNECTÉE",
				error: "ERREUR",
				close: "NON CONNECTÉE"
			}
		},
		beacon: {
			_: "Balise",
			plural: "Balises",
			connection: "Connexion",
			"synthetic-performance-rate-threshold": "Seuil du Taux de Performance Synthétique",
			"product-family": {
				plural: "Familles de Produit"
			},
			"stop-reason": {
				_: "Raison d'Arrêt",
				plural: "Raisons d'Arrêt",
				group: {
					_: "Group de Raison d'Arrêt",
					plural: "Groups de Raison d'Arrêt",
					"child-count": "aucun enfant | 1 enfant | {count} enfants",
				},
				action: {
					detach: {
						_: "Détacher (libérer du groupe)",
						confirm: {
							title: "Confirmation de détachement",
							text: "Voulez-vous détacher cette raison d'arrêt de ce groupe?"
						}
					},
					delete: {
						_: "Supprimer (supprimer de partout)",
						confirm: {
							title: "Confirmation de suppression",
							text: "Voulez-vous supprimer complètement cette raison d'arrêt? Cela entraînera une perte de données lors de l'affichage des graphiques!"
						}
					},
					edit: {
						_: "Éditer"
					}
				},
			},
			"action-zone": {
				_: "Zone d'Action",
				"force-trigger": {
					_: "Forcer le déclenchement du capteur",
					description: "Fausser un déclenchement du capteur",
				},
				"force-config": {
					_: "Forcer la configuration",
					description: "N'attendez pas que la balise redémarre, poussez une configuration par la force",
				}
			}
		},
		glial: {
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
				tooltips: {
					seconds: "seconde | seconde | secondes",
					times: "fois | fois | fois",
				}
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