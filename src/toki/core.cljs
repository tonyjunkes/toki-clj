(ns toki.core
  (:require [promesa.core :as p]
            [toki.logger :as logger]
            [toki.util.env :as env]
            [toki.util.database :as db]
            ["tmi.js" :as tmi]))

;; Set env settings to usable object
(def opts (clj->js {:identity {:username (js* "process.env.BOT_USERNAME")
                               :password (js* "process.env.OAUTH_TOKEN")}
                    :channels [(js* "process.env.CHANNEL_NAME")]}))

;; Test db query
;(-> (.query db/spec "SELECT * FROM commands.game_wins")
;    (p/then #(.log js/console %))
;    (p/catch #(logger/log "error" (str %))))

;; Create client
(def client (tmi/Client opts))

(defn roll-dice
      "Simple 6 sided dice roll function."
      []
      (let [sides 6]
           (+ (Math/floor (* (Math/random) sides)) 1)))

(defn on-message-handler
      "Handler for all message events that come in from the channel."
      [target context msg self]
      (try
        (when-not self
                  (let [command-name (.trim msg)]
                       (case command-name
                             "!dice" (.then (.say client target (str "You rolled a " (roll-dice))))
                             :no-command)))
        (catch js/Object e
          (logger/log "error" (str e)))))

(defn on-connected-handler
      "Handler for connection events to the channel."
      [addr port]
      (logger/log "info" (str "Connected to " addr ":" port)))

;; Message listener
(.on client "message" on-message-handler)

;; Connection listener
(.on client "connected" on-connected-handler)

(defn reload!
      "Called when the dev environment re-compiles."
      []
      (env/load)                                            ; Load env
      (logger/log "info" "Logger initialized.")             ; Init logger
      (.connect client)                                     ; Reconnect to Twitch client
      (println "Meow? Reloaded and ready!"))

(defn -main
      "Main entry point for the client to connect."
      [& cli-args]
      (env/load)                                            ; Load env
      (logger/log "info" "Logger initialized.")             ; Init logger
      (.connect client)                                     ; Connect to Twitch client
      (println "Process started! Purring initialized."))