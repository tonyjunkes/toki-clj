(ns toki.util.env
  (:require ["dotenv" :as dotenv]))

;; Load env settings
(defn load
      "Load app specific environment variables."
      []
      (dotenv/config (js-obj "path" "./.env.toki")))