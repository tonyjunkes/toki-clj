(ns toki.util.database
  (:require ["pg" :as pg]))

;; DB spec and connection
(def spec (new pg/Pool))
