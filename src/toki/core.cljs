(ns toki.core
  (:require [toki.logger :as logger]
            ["dotenv" :as dotenv]
            ["tmi.js" :as tmi]))

;; Test the logger
(logger/log "info" "Logger initialized.")

;; Load env settings
(dotenv/config (js-obj "path" "./.env.toki"))

;; Set env settings to usable object
(def opts (clj->js {:identity {:username (js* "process.env.BOT_USERNAME")
                               :password (js* "process.env.OAUTH_TOKEN")}
                    :channels [(js* "process.env.CHANNEL_NAME")]}))

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
      (logger/log "info" (str "* Connected to " addr ":" port)))

;; Message listener
(.on client "message" on-message-handler)

;; Connection listener
(.on client "connected" on-connected-handler)

(defn reload!
      "Called when the dev environment re-compiles."
      []
      (.connect client)
      (println "Meow? Reloaded and ready!"))

(defn -main
      "Main entry point for the client to connect."
      [& cli-args]
      (.connect client)
      (println "Process started! Purring initialized."))