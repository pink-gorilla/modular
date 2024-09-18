(ns modular.date
  (:require
   [tick.core :as t]
   ;#?(:clj [java-time])
   )
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

(defn now-local []
  (-> (t/now)
      (t/date-time)))
; (local-date-time 2015 9 28 10 15)

#?(:clj
   (defn now-unix []
     (quot (System/currentTimeMillis) 1000)))

(defn now-date []
  (-> (t/now)
      (t/date)))

(defn now-instant []
  (t/now))