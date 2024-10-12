(ns modular.encoding.transit-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [tick.core :as t]
   [modular.encoding.transit :refer [spit-transit slurp-transit write-transit read-transit]]
   [modular.encoding.demo-data :refer [demo-data]]))

(def filename "/tmp/transit-test.json")

(deftest encoding-transit-test []
  (spit-transit filename demo-data)
  (is (= demo-data (slurp-transit filename)))

  ;
  )

(deftest encoding-transit-zoned-test []
  (let [data (t/zoned-date-time)
        t (write-transit data)]
    (println "transit encoded: " t)
    (is (= data (read-transit t)))))

(deftest decoding-transit-zoned-test []
  (let [json "[\"~#zoned-date-time\",\"2024-10-11T18:35:15.154763401-05:00[America/Panama]\"]"
        dt (read-transit json)
        json2 (write-transit dt)]
    (is (= json json2))))

#_(deftest decoding-transit-zoned2-test []
    (let [json "[\"~#zoned-date-time\",\"2024-10-11T18:36:31.396-05:00[SYSTEM]\"]"
          dt (read-transit json)
          json2 (write-transit dt)]
      (is (= json json2))))

; '2024-10-11T18:36:31.396-05:00[SYSTEM]' could not be parsed, unparsed text found at index 29

(deftest decoding-transit-zoned2-test []
  (let [json "[\"~#zoned-date-time\",\"2024-10-11T18:36:31.396-05:00[America/Panama]\"]"
        dt (read-transit json)
        json2 (write-transit dt)]
    (is (= json json2))))
