(ns modular.helper.date
  (:require
   [tick.core :as t])
  (:import
   [java.util Date]
   [java.text SimpleDateFormat]
   [java.time LocalDateTime])) ; ParseException

(defn now [] (Date.))

(defn tostring [dt]
  (.format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") dt))

(defn now-str []
  (tostring (now)))

(defn now-local []
  (-> (t/now)
      (t/date-time)))

; (local-date-time 2015 9 28 10 15)

(defn now-unix []
  (quot (System/currentTimeMillis) 1000))