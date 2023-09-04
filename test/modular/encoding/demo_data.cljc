(ns modular.encoding.demo-data
  (:require
   [modular.date :refer [now-local now-date]]
   [modular.encoding.bidi :refer [demo-bidi-tag]]))

(def demo-data {:a 34
                :date-local (now-local)
                :date (now-date)
                :b demo-bidi-tag})