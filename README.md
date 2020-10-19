<p align="center">
	<img width="460" src="https://www.jeld-wen.fr/App_Themes/JeldWen/img/fr-FR/logo.svg">
</p>

- [Security](#security)
- [Back-end](#back-end)
	- [API](#api)
- [Front-end](#front-end)
	- [Pages](#pages)
	- [Internationalization](#internationalization)
		- [Supported Languages](#supported-languages)
- [Beacon](#beacon)
	- [Authentication](#authentication)
	- [Client](#client)
	- [Operator View](#operator-view)
- [Messages](#messages)
	- [Events](#events)
		- [event/beacon-connected](#eventbeacon-connected)
		- [event/beacon-disconnected](#eventbeacon-disconnected)
		- [event/rhythm-sync](#eventrhythm-sync)
	- [Requests](#requests)
		- [request/auth](#requestauth)
		- [request/config](#requestconfig)
		- [request/force-sensor-trigger](#requestforce-sensor-trigger)
		- [request/workstation-open](#requestworkstation-open)
		- [request/workstation-close](#requestworkstation-close)
		- [request/family-change](#requestfamily-change)
		- [request/report](#requestreport)
		- [request/stop-reason-report](#requeststop-reason-report)
	- [Responses](#responses)
		- [response/auth](#responseauth)
		- [response/config](#responseconfig)
		- [response/connected-beacon-list](#responseconnected-beacon-list)
		- [response/family-changed](#responsefamily-changed)
		- [response/reported](#responsereported)
		- [response/workstation-state](#responseworkstation-state)
		- [response/reported-stop-reason](#responsereported-stop-reason)

# Security

There is no security anywhere. Anyone can do anything as long as it has access to the URL.

This is to avoid unnecessary work on a private network and speed up development time.

# Back-end

Running with Spring Boot.

## API

| Endpoint                          | Method | Description                          |
|-----------------------------------|--------|--------------------------------------|
| /beacon                           | GET    | List beacons                         |
| /beacon/{id}                      | GET    | Get a beacon                         |
| /beacon/{id}                      | POST   | Update a beacon                      |
| /beacon/{id}/force-trigger        | POST   | Force sensor trigger of this beacon  |
| /beacon/{id}/reconfigure          | POST   | Reconfigure of this beacon           |
| /beacon/product-family            | GET    | List product families                |
| /beacon/product-family            | POST   | Create a product family              |
| /beacon/product-family/{id}       | POST   | Update a product family              |
| /beacon/stop-reason               | GET    | List stop reasons                    |
| /beacon/stop-reason               | POST   | Create a stop reason                 |
| /beacon/stop-reason/{id}          | GET    | Get a stop reason                    |
| /beacon/stop-reason/{id}          | POST   | Update a strop reason                |
| /beacon/stop-reason/{id}          | DELETE | Delete a stop reason                 |
| /beacon/stop-reason/{id}/detach   | POST   | Detach a stop reason from his parent |
| /beacon/stop-reason/category      | GET    | Get stop reason categories           |
| /beacon/stop-reason/category      | POST   | Create a stop reason categorie       |
| /beacon/stop-reason/category/{id} | GET    | Get a stop reason categorie          |
| /beacon/stop-reason/group         | GET    | Get stop reason groups               |
| /beacon/stop-reason/group         | POST   | Create a stop reason group           |
| /beacon/stop-reason/group/{id}    | GET    | Get a stop reason group              |
| /beacon/stop-reason/group/{id}    | POST   | Update a stop reason group           |
| /beacon/stop-reason/group/{id}    | DELETE | Delete a stop reason group           |

# Front-end

Running with Vue.js and Node.js.

## Pages

| Route                          | Description                |
|--------------------------------|----------------------------|
| /                              | Home                       |
| /beacons                       | Beacons list               |
| /beacon/{id}                   | Beacon details and actions |
| /beacon/{id}/edit              | Beacon edition             |
| /beacon/stop-reason/group/{id} | Stop reason group details  |

## Internationalization

Every page will have a full internationalization (or i18n for short) support to enable anyone to fully understand what going on.

### Supported Languages

| Short                         | Completeness  |
| ----------------------------- | ------------- |
| raw (remain to be translated) | ~80%          |
| en                            | ~20%          |
| fr                            | ~20%          |

Usually both language are applied at the same time to avoid loosing time of what has been already translated and what is remaining. But to speed up developement process, language are only applied to module that are in a working state.

Adding a language is a easy process: for every graphical interface, there is a file i18n.js contained in the /plugins/ directory. Just copy-paste one of the currently defined languages to add another one.

# Beacon

## Authentication

A beacon authenticate itself by using the MAC address of the first physical network interface available by the hardware.

Without being identified, the `back-end` will refuse any request/response/event coming from this unauthenticated connection. This is to ensure that a packet is not being randomly sent without knowing the origin.

## Client

The `beacon client` is running on a Raspberry Pi, allowing to wire sensors and access to a network. It will responsible to manage the current state of the graphical interface (a.k.a the operator view).

Running with Spring Boot, it is connected to the `back-end` through a WebSocket, allowing for instant bi-directional communication. In case where the link is not connected or has been broken, the `beacon` must buffer the information that is has to send back to the `back-end`. Once the link is connected (or restored), the `beacon` must authenticate first and then flush his awaiting requests.

## Operator View

The operator view is a page where a person being can see exactly what is currently being done in real-time through a WebSocket. To ensure that everyone is seeing the same data, all the message coming from the `beacon` are being broadcast to all currently connected sockets.

# Messages

| Type     | Name                  | From 🡒 To              | Description                                          |
|----------|-----------------------|-------------------------|------------------------------------------------------|
| Event    | beacon-connected      | Back-end 🡒 Front-end   | When a beacon connect                                |
| Event    | beacon-disconnected   | Back-end 🡒 Front-end   | When a beacon disconnect (himself, or network error) |
| Event    | rhythm-sync           | Beacon 🡒 Operator View | Update seconds                                       |
| Request  | auth                  | Beacon 🡒 Back-end      | Identify a connection to a beacon                    |
| Request  | config                | Beacon 🡒 Back-end      | Request the beacon's configuration                   |
| Request  | force-sensor-trigger  | Back-end 🡒 Beacon      | Force a fake sensor trigger                          |
| Request  | workstation-open      | Operator View 🡒 Beacon | Open a workstation                                   |
| Request  | workstation-close     | Operator View 🡒 Beacon | Close a workstation                                  |
| Request  | family-change         | Operator View 🡒 Beacon | Change the currently active family product           |
| Request  | report                | Operator View 🡒 Beacon | Report a stop reason                                 |
| Request  | stop-reason-report    | Beacon 🡒 Back-end      | Report a stop reason to the back-end                 |
| Response | auth                  | Back-end 🡒 Beacon      | Confirm the authentication                           |
| Response | config                | Back-end 🡒 Beacon      | Receive beacon's configuration                       |
| Response | connected-beacon-list | Back-end 🡒 Front-end   | Get the list of connected beacon                     |
| Response | family-changed        | Beacon 🡒 Operator View | Know when the product family has been changed        |
| Response | reported              | Beacon 🡒 Operator View | Know when a report has been received by the beacon   |
| Response | workstation-state     | Beacon 🡒 Operator View | Receive the full workstation's state                 |
| Response | reported-stop-reason  | Back-end 🡒 Beacon      | Confirm stop reason report reception                 |

## Events

Events are a type of message that does not need to wait for an answer. They are sent to whoever want to hear about them.

### event/beacon-connected

This `event` is being send by the `back-end` to the `front-end` to indicate that a beacon has been connected and its authentication has been successful.

| Property | Type   | Description                |
|----------|--------|----------------------------|
| unique   | String | Unique's beacon identifier |

### event/beacon-disconnected

This `event` is being send by the `back-end` to the `front-end` to indicate that a beacon has been disconnected. No reason are provided, so this might be a network issue or a normal stop.

| Property | Type   | Description                |
|----------|--------|----------------------------|
| unique   | String | Unique's beacon identifier |

### event/rhythm-sync

This `event` is being broadcast by the `beacon` to the `operator view` to indicate that a second has passed, allowing the views to update accordingly.

| Property           | Type                        | Description                                                          |
|--------------------|-----------------------------|----------------------------------------------------------------------|
| seconds            | Long                        | The number of seconds since the start or the last sensor trigger     |
| currentHourPerHour | SimpleHourPerHourDescriptor | Current hour per hour line data (including produced, objective, ...) |

## Requests

### request/auth

Authentication request. Will fail if there is already a beacon connected with the same unique identifier.

| Property | Type   | Description                |
|----------|--------|----------------------------|
| unique   | String | Unique's beacon identifier |

### request/config

Requested by the `beacon`, ask the `back-end` for the configuration of this `beacon`.

### request/force-sensor-trigger

Usually requested for debug purposes, the message is transmitted to the `beacon` to fake a sensor trigger.

### request/workstation-open

Usually requested by the `operator view`, the message ask the `beacon` to start recording timings.

### request/workstation-close

Usually requested by the `operator view`, the message ask the `beacon` to stop recording timings.

### request/family-change

Change the currently active product family.

| Property | Type | Description                           |
|----------|------|---------------------------------------|
| familyId | Long | Target product family id to change to |

### request/report

Report a stop reason and restart timings.

| Property     | Type | Description                     |
|--------------|------|---------------------------------|
| stopReasonId | Long | Target stop reason id to report |

### request/stop-reason-report

Report a stop reason the the `back-end`, it will be stored allowing for quick overview of previous performances. A confirm response will be sent back with the same `id` property.

| Property        | Type     | Description                                                 |
|-----------------|----------|-------------------------------------------------------------|
| id              | Long     | Beacon's entry database's ID, will be used for confirmation |
| stopReasonId    | Long     | Stop reason ID (can be null)                                |
| message         | String   | Custom message for `other` reason                           |
| productFamilyId | Long     | Product family ID                                           |
| duration        | Long     | Duration of the stop in seconds                             |
| at              | DateTime | Date and time of the event                                  |

## Responses

Responses are usually answers to requests but can also provide initial data when you are not expecting them.

### response/auth

Authentication result. In some case, the server might return an error while trying to recognize the beacon. If such error append, the beacon retry in a few seconds. In the case where there is already a locally saved configuration file stored, this file will be used but the authentication retries will continue.

| Property | Type                                                                 | Description                                |
|----------|----------------------------------------------------------------------|--------------------------------------------|
| reason   | Enum {<br/>&emsp;ALREADY_CONNECTED,<br/>&emsp;SERVER_EXCEPTION<br/>} | Authentication result. Null if successful. |

### response/config

Sent by the `back-end`, the `beacon` get his current (or new) configuration to use.

| Property | Type                                                                                            | Description                                          |
|----------|-------------------------------------------------------------------------------------------------|------------------------------------------------------|
| config   | BeaconConfig                                                                                    | The beacon's new configuration                       |
| forced   | Boolean                                                                                         | If the configuration has been forced to be received. |
| reason   | Enum {<br/>&emsp;NOT_AUTHENTICATED,<br/>&emsp;NOT_CONFIGURED,<br/>&emsp;SERVER_EXCEPTION;<br/>} | The failing reason, or null if everything is fine.   |

### response/connected-beacon-list

Sent by the `back-end`, the receiver get the list of the currently connected beacons' unique. The `front-end` will update views depending on the result.

| Property | Type               | Description                         |
|----------|--------------------|-------------------------------------|
| uniques  | List&lt;String&gt; | Currently connected beacons' unique |

### response/family-changed

Sent by the `beacon`, the `operator view` will unlock the buttons and continue as normal. But if the `familyId` is `0`, that mean the change has failed because the target product family does not exists: the `operator view` will have to ask for a new product family selection.

| Property | Type | Description                                                           |
|----------|------|-----------------------------------------------------------------------|
| familyId | Long | Now current active product family, or 0 if the target does not exists |

### response/reported

Sent by the `beacon`, the `operator view` will unlock the buttons and continue as normal. But if the `success` is `false`, that mean the report has failed because the target stop reason does not exists: the `operator view` will not stop, but the incident will be reported to the `back-end` that will maybe have more chance to identify where the problem is.

| Property | Type | Description                                                           |
|----------|------|-----------------------------------------------------------------------|
| familyId | Long | Now current active product family, or 0 if the target does not exists |

### response/workstation-state

Sent by the `beacon`, the `operator view` will update views based on provided information. This message is usually send to synchronize all currently connected `operator views`.

| Property           | Type                                    | Description                                                                      |
|--------------------|-----------------------------------------|----------------------------------------------------------------------------------|
| opened             | Boolean                                 | Workstation's opened state                                                       |
| beaconConfig       | BeaconConfig                            | Current configuration                                                            |
| activeFamilyId     | Long                                    | Current active product family, or 0 if none                                      |
| seconds            | Long                                    | The number of seconds since the start or the last sensor trigger                 |
| currentHourPerHour | SimpleHourPerHourDescriptor             | Current hour per hour line data (including produced, objective, ...)             |
| hourPerHourHistory | List&lt;SimpleHourPerHourDescriptor&gt; | History including previous hour per hour data (can be null to save up on memory) |

### response/reported-stop-reason

Confirm that a stop reason has successfully been reported to the `back-end`. The returned `id` will confirm the beacon that he can remove the local `id` of his buffered storage.

| Property | Type | Description                            |
|----------|------|----------------------------------------|
| id       | Long | Confirmed beacon's entry database's ID |