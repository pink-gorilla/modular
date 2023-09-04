(ns modular.encoding.edn-test
  (:require
   [taoensso.timbre :as log :refer [info infof]]
   [clojure.test :refer [deftest is testing]]
   [modular.encoding.edn :refer [spit-edn slurp-edn]]
   [modular.encoding.demo-data :refer [demo-data]]))

(def filename "/tmp/edn-test.edn")

(deftest encoding-edn-test []
  (spit-edn filename demo-data)
  (is (= demo-data (slurp-edn filename))))


