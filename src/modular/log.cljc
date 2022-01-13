(ns modular.log
  (:require
   [taoensso.timbre :as timbre]
   #?(:clj [modular.log.config :refer [appender-config]])))

#?(:cljs
   (defn appender-config [rf]
     {}))

(defn timbre-config! [config]
  (let [timbre-loglevel (:timbre-loglevel config)
        timbre-file (:timbre-file config)]
    (println "timbre config: " timbre-loglevel)
    (when timbre-loglevel
      (timbre/set-config!
       (merge timbre/default-config
              (appender-config timbre-file)
              {;:output-fn default-output-fn
               :min-level timbre-loglevel})))))


