(ns system
  (:require
   [taoensso.timbre :refer [info debug warn error with-context]]
   [modular.config :refer [load-config!]]
   [modular.system :as system]
   ))


(defn demo [{:keys [custom-config]}]
  (println "demo how to start systems..") 
  (load-config! ["webly/config.edn" "services.edn"])
  (system/start!))