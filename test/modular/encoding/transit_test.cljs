(ns modular.encoding.transit-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [cljc.java-time.zone-id :refer [get-available-zone-ids]]
   [tick.core :as t]
   [bidi.bidi :as bidi]
   [modular.encoding.transit :refer [write-transit read-transit]]
   [modular.encoding.edn :refer [read-edn]]
   [modular.encoding.demo-data :refer [demo-data]]))

(println "encoding test running.")

(deftest encoding-edn-test []
  (let [t (pr-str demo-data)]
    (println "EDN encoded data: " t)
    (is (= demo-data (read-edn t)))))

(deftest encoding-transit-simple-test []
  (let [data (dissoc demo-data :date :bidi)
        t (write-transit data)]
    (println "transit encoded: " t)
    (is (= data (read-transit t)))))

(deftest encoding-transit-date-test []
  (let [data (dissoc demo-data :bidi)
        t (write-transit data)]
    (println "transit encoded: " t)
    (is (= data (read-transit t)))))

(deftest encoding-transit-zoned-test []
  (let [data (t/zoned-date-time)
        t (write-transit data)]
    (println "transit encoded: " t)
    (is (= data (read-transit t)))))

#_(deftest encoding-transit-bidi-test []
    (let [t (write-transit demo-data)]
      (is (= demo-data (read-transit t)))))

(println "available timezones in cljs: "
         (get-available-zone-ids))
