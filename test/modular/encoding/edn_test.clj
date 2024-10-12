(ns modular.encoding.edn-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [modular.encoding.edn :refer [spit-edn slurp-edn read-edn]]
   [modular.persist.edn :refer [pprint-str]]
   [modular.encoding.demo-data :refer [demo-data]]))

(def filename "/tmp/edn-test.edn")

(deftest encoding-edn-test []
  (spit-edn filename demo-data)
  (is (= demo-data (slurp-edn filename))))

(deftest localdate-reload-test
  (let [sdate (str "#time/date \"2011-01-01\"" "\n")
        stime (str "#time/date-time \"2021-11-04T00:52:59.694154533\"" "\n")]
    (is (= sdate (-> sdate read-edn pprint-str)))
    (is (= stime (-> stime read-edn pprint-str)))))
