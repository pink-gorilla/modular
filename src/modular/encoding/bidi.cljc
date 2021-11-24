(ns modular.encoding.bidi
  (:require
   #?(:clj [bidi.bidi]
      :cljs [bidi.bidi :refer [TaggedMatch]])
   [cognitect.transit :as transit]
   [incognito.transit :refer [incognito-write-handler incognito-read-handler]])
  #?(:clj (:import [bidi.bidi TaggedMatch])))

;; TRANSIT

(def write-handlers2 (atom {'bidi.bidi.TaggedMatch (fn [bar] bar)}))
(def read-handlers2 (atom {'bidi.bidi.TaggedMatch bidi.bidi/map->TaggedMatch}))

(def bidi-serialization-handlers
  {:handlers {TaggedMatch
              (incognito-write-handler
               write-handlers2)}})

(def bidi-deserialization-handlers
  {:handlers {"incognito"
              (incognito-read-handler
               read-handlers2)}})

;; EDN

(def bidi-edn-tag
  {'bidi.bidi.TaggedMatch bidi.bidi/map->TaggedMatch})

;; DEMO DATA

(def demo-bidi-tag
  (bidi.bidi/tag :demo/job :wunderbar) ; #bidi.bidi.TaggedMatch{:matched :demo/job, :tag :wunderbar}  
  )


