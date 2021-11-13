(ns modular.persist.unknown
  (:require
   [taoensso.timbre :refer [debug info warnf error]]
   [modular.persist.protocol :refer [save loadr]]))

(defmethod save :default [t filename _]
  (error "unknown format: " t " file: " filename))
