(ns config
  (:require
   [taoensso.timbre :refer [info debug warn error with-context]]
   [modular.config :refer [load-config!]]))


(defn demo [{:keys [custom-config]}]
  (println "demo how a config gets loaded..")
  (load-config! "webly/config.edn")
  
  
  )