(ns demo.fipp
  (:require
   [modular.fipp :refer [pprint-str]]
   [tick.core :as t]))

(def data
  {:a 1
   :b [1 2 3]
   :c [1.0 2.0 3.0]
   :inst (t/instant)
   :zdt (t/zoned-date-time)})

data
;; => {:a 1,
;;     :b [1 2 3],
;;     :c [1.0 2.0 3.0],
;;     :inst #time/instant "2024-10-11T17:18:54.857927482Z",
;;     :zdt #time/zoned-date-time "2024-10-11T12:18:54.858196816-05:00[America/Panama]"}

(-> data pr-str read-string)
;; => {:a 1,
;;     :b [1 2 3],
;;     :c [1.0 2.0 3.0],
;;     :inst #time/instant "2024-10-11T17:18:54.857927482Z",
;;     :zdt #time/zoned-date-time "2024-10-11T12:18:54.858196816-05:00[America/Panama]"}

(-> data pprint-str read-string)
;; => {:a 1,
;;     :b [1 2 3],
;;     :c [1.0 2.0 3.0],
;;     :inst #time/instant "2024-10-11T17:18:54.857927482Z",
;;     :zdt #time/zoned-date-time "2024-10-11T12:18:54.858196816-05:00[America/Panama]"}
