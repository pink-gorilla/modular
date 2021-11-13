(ns modular.encoding.edn-test
  (:require
   [taoensso.timbre :as log :refer [info infof]]
   ;[taoensso.sente.packers.transit :as sente-transit :refer [get-transit-packer]]
   [clojure.test :refer [deftest is testing]]
   [clojure.edn :as edn]
   [bidi.bidi :as bidi]
   [modular.encoding.edn :as e]
   [modular.date :refer [now-local]])
  (:import [bidi.bidi TaggedMatch]))

;(def p (get-transit-packer :json e/encode e/decode))

(def filename "/tmp/edn-test.edn")

(defn save-data [data]
  (spit filename (pr-str data)))

(defn load-data []
  (->> (slurp filename)
       (edn/read-string e/readers) ; (edn/read-string {:readers {'path absolute}} config)
       ))
(def data {:a 34
           ;:date (now-local) 
           :b (bidi/tag :demo/job :wunderbar) ; #bidi.bidi.TaggedMatch{:matched :demo/job, :tag :wunderbar}
           })

(save-data data)

(deftest encoding-edn-test []
  (is (= data (load-data)))

  ;
  )