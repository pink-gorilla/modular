(ns modular.encoding.demo-data
  (:require
   [tick.core :as t]
   [modular.date :refer [now-local now-date now-instant]]
   [modular.encoding.bidi :refer [demo-bidi-tag]]))

(def demo-data {:a 34
                :date {:date-local (t/date-time) ; (now-local)
                       :date (t/date) ;(now-date)
                       :date-instant (t/instant) ; (now-instant)
                       :date-zoned (t/zoned-date-time)}
                :bidi {:b demo-bidi-tag}})