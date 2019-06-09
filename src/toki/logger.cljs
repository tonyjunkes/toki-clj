(ns toki.logger
  (:require [toki.util.datetime :as dt]
            ["winston" :as winston]))

;; Setup logger
(def instance (winston/createLogger
              (clj->js {:transports [(new winston/transports.Console)
                                     (new winston/transports.File (js-obj "filename" "logs/toki.log"))]})))

(defn log
      "Log a general message to the base log with a provided level."
      [level msg]
      (.log instance (js-obj "level" level "message" msg "timestamp" (dt/now->utc))))