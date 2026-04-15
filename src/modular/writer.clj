(ns modular.writer
  (:require
   [clojure.java.io :as io]
   [ednx.fipp :refer [spit-fipp-comment]]
   [ednx.tick.edn :refer [add-tick-edn-handlers!]]
   [ednx.tick.fipp :refer [add-tick-fipp-printers!]]))

(add-tick-edn-handlers!)
(add-tick-fipp-printers!)

(defn write [filename data]
  (spit-fipp-comment filename data "auto-generated"))

(defn ensure-directory [path]
  (when-not (.exists (io/file path))
    (.mkdir (java.io.File. path))))

(defn ensure-directory-webly []
  (ensure-directory ".webly"))

(defn write-status [name data]
  (ensure-directory-webly)
  (let [filename (str ".webly/" name ".edn")]
    (write filename data)))

(defn write-target [name data]
  (ensure-directory "./target")
  (ensure-directory "./target/webly")
  (ensure-directory "./target/webly/public")
  (let [filename (str "./target/webly/public/" name ".edn")]
    (write filename data)))

(defn write-edn-private [name data]
  (ensure-directory "./.gorilla")
  (let [filename (str "./.gorilla/" name ".edn")]
    (write filename data)))

(defn write-edn-public [name data]
  (ensure-directory "./.gorilla")
  (ensure-directory "./.gorilla/public")
  (let [filename (str "./.gorilla/public/" name ".edn")]
    (write filename data)))