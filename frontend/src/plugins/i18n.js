import Vue from 'vue'
import VueI18n from 'vue-i18n'

import application from "@/../application"

Vue.use(VueI18n);

let messages = {
	en: {
		name: "English",
		version: `JELDWEN v${application.version}`,
		copyright: {
			by: "Created by",
			for: "for",
			"source-code": "source code"
		},
		abandoned: "This project has been abandoned",
		route: {
			home: "Home",
			beacons: {
				_: "BEACONS",
				editor: "Editor"
			},
			"glial-machines": {
				_: "GLIAL MACHINES",
				overview: "Overview",
				settings: "Settings",
			},
			others: {
				_: "OTHERS",
				settings: "Settings",
			},
		},
		common: {
			edit: "EDIT",
			confirm: "CONFIRM",
			delete: "DELETE",
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
			},
			"select-all": "SELECT ALL",
			"unselect-all": "UNSELECT ALL",
		},
		home: {
			welcome: "Welcome on the JELD-WEN dashboard",
			"no-purpose": "This page is currently serving no purpose."
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
				},
				confirmDelete: {
					title: "Confirm",
					phrase: "This operation will delete",
					entries: "no entry | 1 entry | {n} entries"
				},
				settings: {
					urls: {
						_: "URLs",
						hint: "e.g.: http://mybeacon.jeld-wen.com or http://1.2.3.4",
						dialogs: {
							create: {
								_: "New Entry"
							},
							delete: {
								text: "Do you want to delete this entry?"
							}
						},
						columns: {
							machines: "Machines",
							urls: "URLs",
							actions: "Actions"
						},
						fields: {
							url: "URL",
							machine: "Machine",
						}
					}
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
		copyright: {
			by: "Crée par",
			for: "pour",
			"source-code": "code source"
		},
		abandoned: "Ce projet à été abandonné",
		route: {
			home: "Home",
			beacons: {
				_: "BALISES",
				editor: "Éditeur"
			},
			"glial-machines": {
				_: "MACHINES GLIAL",
				overview: "Vu d'ensemble",
				settings: "Paramètres",
			},
			others: {
				_: "AUTRES",
				settings: "Paramètres",
			},
		},
		common: {
			edit: "ÉDITER",
			refresh: "RAFRAICHIR",
			confirm: "CONFIRMER",
			delete: "SUPPRIMER",
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
			},
			"select-all": "SELECTIONER TOUT",
			"unselect-all": "DÉSELECTIONER TOUT",
		},
		home: {
			welcome: "Bienvenue sur le tableau de bord JELD-WEN",
			"no-purpose": "Cette page ne sert actuellement à rien."
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
					yellowStop: "Arrêt jaune",
					orangeStop: "Arrêt orange",
					pinkStop: "Arrêt rose",
					greenStop: "Arrêt vert",
					blueStop: "Arrêt blue",
					otherStop: "Arrêt autre",
					totalStop: "Total arrêt",
					nonQualifiedStop: "Arrêt non-qualifié",
					objective: "Objectif",
					produced: "Produit",
				},
				tooltips: {
					seconds: "seconde | seconde | secondes",
					times: "fois | fois | fois",
				},
				confirmDelete: {
					title: "Confirmation",
					phrase: "Cette opération va supprimer",
					entries: "aucune entrée | 1 entrée | {n} entrées"
				},
				settings: {
					urls: {
						_: "Liens",
						hint: "exemple: http://mabalise.jeld-wen.com ou http://1.2.3.4",
						dialogs: {
							create: {
								_: "Nouvelle Entrée"
							},
							delete: {
								text: "Voulez vous supprimer cette entrée ?"
							}
						},
						columns: {
							machines: "Machines",
							urls: "Liens",
							actions: "Actions"
						},
						fields: {
							url: "Lien",
							machine: "Machine",
						}
					}
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