(ns modular.encoding.bidi
  (:require
   #?(:clj [bidi.bidi]
      :cljs [bidi.bidi :refer [TaggedMatch]]))
  #?(:clj (:import [bidi.bidi TaggedMatch])))

;; EDN

(def bidi-edn-tag
  {'bidi.bidi.TaggedMatch bidi.bidi/map->TaggedMatch})

;; DEMO DATA

(def demo-bidi-tag
  (bidi.bidi/tag :demo/job :wunderbar) ; #bidi.bidi.TaggedMatch{:matched :demo/job, :tag :wunderbar}  
  )


