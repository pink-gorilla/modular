(ns system
  (:require
   [taoensso.timbre :refer [info debug warn error with-context]]))


(defn myrunner [arguments system-config running-system]
  (warn "my runner. args: " arguments))

