(ns modular.date
  (:require
   [tick.core :as tick]
   #?(:clj [java-time]))
  #?(:clj (:import
           [java.util Date]
           [java.text SimpleDateFormat]
           [java.time LocalDateTime]))) ; ParseException

;; instant

#?(:clj
   (defn now [] (Date.)))

#?(:clj
   (defn tostring [dt]
     (.format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") dt)))

#?(:clj
   (defn now-str []
     (tostring (now))))

;; local datetime

#_(defn now-local []
    (java-time/truncate-to
     (LocalDateTime/now) :seconds))

(defn now-local []
  (-> (tick/now)
      ;tick/date-time
      ))
; (local-date-time 2015 9 28 10 15)

#?(:clj
   (defn now-unix []
     (quot (System/currentTimeMillis) 1000)))


