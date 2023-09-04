(ns modular.encoding.transit-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [bidi.bidi :as bidi]
   ;[modular.encoding.transit :refer [write-transit read-transit]]
   [modular.encoding.edn :refer [read-edn]]
   [modular.encoding.demo-data :refer [demo-data]]))

(println "encoding test running.")

(deftest encoding-edn-test []
  (let [t (pr-str demo-data)]
    (println "encoded data: " t)
    (is (= demo-data (read-edn t)))))

#_(deftest encoding-transit-test []
    (let [t (write-trnsit demo-data)]
      (is (= demo-data (read-transit t)))))

