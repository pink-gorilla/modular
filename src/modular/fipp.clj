(ns modular.fipp
  (:require
   [modular.encoding.fipp] ; side-effects
   [fipp.clojure])
  (:import
   [java.io StringWriter]))

; redirect std out is NOT a good idea in an edn writer
; because every console output will be printed to the edn doucment
(defn pprint [data opts]
  (with-out-str
    (fipp.clojure/pprint data opts)))

(defn pprint-str [data]
  (let [sw (StringWriter.)]
    (fipp.clojure/pprint data {:width 60 :writer sw :print-meta true})
    (str sw)))
