(ns modular.encoding.demo-data
  (:require
   [modular.date :refer [now-local]]
   [modular.encoding.bidi :refer [demo-bidi-tag]]))

(def demo-data {:a 34
                :date (now-local)
                :b demo-bidi-tag})