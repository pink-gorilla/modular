(ns demo.run
  (:require
   [taoensso.timbre :refer [info warn]]))


(defn run27 [arguments]
  (warn "run27 args: " (keys arguments))
  (warn "running task:" (:task arguments))
  )
