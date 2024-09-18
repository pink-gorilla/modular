(ns modular.encoding.transit-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [modular.encoding.transit :refer [spit-transit slurp-transit]]
   [modular.encoding.demo-data :refer [demo-data]]))

(def filename "/tmp/transit-test.json")

(deftest encoding-transit-test []
  (spit-transit filename demo-data)
  (is (= demo-data (slurp-transit filename)))

  ;
  )