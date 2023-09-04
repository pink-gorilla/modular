(ns modular.encoding.transit
  (:require
   [cognitect.transit :as transit]
   [modular.encoding.bidi :as bidi]
   [modular.encoding.time2 :as time])
  #?(:clj (:import [java.io ByteArrayInputStream ByteArrayOutputStream])))

; another encoding option:
; https://nextjournal.com/schmudde/java-time

; transit encoding is used in
; - ring middleware (muuntaja)
; - websocket (sente packer)
; - cljs ajax requests ()

(def decode
  {:handlers
   (merge
    (:handlers time/time-deserialization-handlers)
    (:handlers bidi/bidi-deserialization-handlers))})

(def encode
  {:handlers
   (merge (:handlers time/time-serialization-handlers)
          (:handlers bidi/bidi-serialization-handlers))})

; todo: 
; (def ^:private default-readers {'ig/ref ref, 'ig/refset refset})
; (defn read-string "Read a config from a string of edn. Refs may be denotied by tagging keywords with #ig/ref." ([s] (read-string {:eof nil} s)) ([opts s] (let [readers (merge default-readers (:readers opts {}))] (edn/read-string (assoc opts :readers readers) s))))

#?(:clj
   (defn write-transit [data]
     (let [out (ByteArrayOutputStream. 4096)
           writer (transit/writer out :json encode)]
       (transit/write writer data)
       (.toString out))))

#?(:clj
   (defn string->stream
     ([s] (string->stream s "UTF-8"))
     ([s encoding]
      (-> s
          (.getBytes encoding)
          (ByteArrayInputStream.)))))

#?(:clj
   (defn read-transit [json]
     (let [in (string->stream json)
           reader (transit/reader in :json decode)]
       (transit/read reader))))

#?(:clj
   (defn spit-transit [filename data]
     (->> (write-transit data)
          (spit filename))))

#?(:clj
   (defn slurp-transit [filename]
     (let [json (slurp filename)]
       (read-transit json))))

