(ns modular.writer
  (:require
   [clojure.java.io :as io]
   [fipp.clojure]))

; fast, but no pretty-print (makes it difficult to detect bugs)

#_(defn write [filename data]
    (spit filename  (pr-str data)))

(defn write [filename data]
  (let [comment "; auto-generated \r\n"
        s (with-out-str
            (fipp.clojure/pprint data {:width 60}))
        s (str comment s)]
    (spit filename s)))

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