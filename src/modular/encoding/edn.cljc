(ns modular.encoding.edn
  (:require
   [modular.encoding.bidi :refer [bidi-edn-tag]]
   [modular.encoding.time :as time]
   #?(:clj  [time-literals.data-readers] ;; For literals
      :cljs [time-literals.data-readers-cljs])
   [time-literals.read-write] ;; For printing/writing
   #?(:clj [clojure.edn :as edn]
      :cljs [cljs.reader :as edn])))

; com.widdindustries/time-literals
; https://github.com/henryw374/time-literals/tree/master/src/time_literals

(defn default-reader
  "A default reader, for when we don't know what's coming in."
  [t v]
  {:tag t :value v})

; this would be an option too. 
#_(defrecord TaggedValue [tag value])

(def data-readers
  (merge bidi-edn-tag
         time-literals.read-write/tags))

#?(:clj
   (defn read-edn [s]
     (edn/read-string {:default default-reader
                       :readers data-readers} s)))

#?(:cljs
   (defn read-edn [s]
     (edn/read-string {:default default-reader
                       :readers data-readers} s)))

#?(:clj
   (time-literals.read-write/print-time-literals-clj!))

#?(:cljs
   (time-literals.read-write/print-time-literals-cljs!))

#?(:clj
   (defn spit-edn [filename data]
     (spit filename (pr-str data))))

#?(:clj
   (defn slurp-edn [filename]
     (-> filename slurp read-edn)))

; [clojure.tagged-literals]
; (set! *default-data-reader-fn* tagged-literal)

(defn pr-str-with-meta [data]
  (binding [*print-meta* true]
    (pr-str data)))

(comment

  (time-literals.read-write/print-time-literals-clj!)

  (clojure.edn/read-string "#inst \"1985-04-12T23:20:50.52Z\"")
  (clojure.edn/read-string  "#time/date \"2021-11-04\"")

  (def x (read-str "#time/date \"2011-01-01\""))
  (def x (read-str "#time/date-time \"2021-11-04T00:52:59.694154533\""))

  x
  (class x)

  (pprint-str x)

  (str x)
  (pr-str x)
  (type x)

  (tagged-literal 'time/date (str x))

;
  )
