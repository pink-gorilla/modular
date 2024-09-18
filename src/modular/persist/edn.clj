(ns modular.persist.edn
  (:require
   [taoensso.timbre :refer [debug info warnf]]
   [clojure.java.io :as io]
   [fipp.clojure]
   [clojure.edn]
   [modular.encoding.edn :refer [read-edn]]
   [modular.helper.date :refer [now-str]]
   [modular.persist.protocol :refer [save loadr]]
   [modular.encoding.fipp] ; side-effects
   )
  (:import (java.io StringWriter)))

; fast, but no pretty-print (makes it difficult to detect bugs)
#_(defn write [filename data]
    (spit filename  (pr-str data)))

; redirect std out is NOT a good idea in an edn writer
; because every console output will be printed to the edn doucment
(defn pprint [data opts]
  (with-out-str
    (fipp.clojure/pprint data opts)))

(defn pprint-str [data]
  (let [sw (StringWriter.)]
    (fipp.clojure/pprint data {:width 60 :writer sw :print-meta true})
    (str sw)))

(defmethod save  :edn [_ file-name data]
  (info "saving edn file: " file-name)
  (let [comment (str "; saved on " (now-str) "\r\n")
        sedn (pprint-str data)
        s (str comment sedn)]
    (spit file-name s)
    data  ; important to be here, as save-study is used often in a threading macro
    ))

(defmethod loadr :edn [_ file-name]
  (debug "loading edn file: " file-name)
  (when (.exists (io/file file-name))
    (-> (slurp file-name)
        (read-edn))))

(comment
  (save "/tmp/test3.edn" {:a 1 :b [1 3 4]})
  (-> (loadr "/tmp/test3.edn")
      :b)

  (loadr "document/notebook.image/notebook.edn")
  (loadr "document/notebook.apple/notebook.edn")

;  
  )