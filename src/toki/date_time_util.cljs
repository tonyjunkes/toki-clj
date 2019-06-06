(ns toki.date-time-util
  (:require [cljs-time.core :as t]
            [cljs-time.coerce :as tc]))

(defn now->utc
      "Returns the current UTC date/time in yyyy-MM-dd'T'HH:mm:ss.SSSZZ"
      []
      (tc/to-string (t/now)))