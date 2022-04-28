(ns modular.log
  (:require
   [taoensso.timbre :as timbre]
   [modular.log.appenders :refer [appender-list default-appenders]]))

(defn- create-appender [{:keys [type] :as opts}]
  (let [opts (dissoc opts :type)
        appender (get appender-list type)]
    (if appender
      (appender opts)
      (do (println "appender not found: " type)
          nil))))

(defn- create-appenders [appenders]
  {:appenders
   (->> (map (fn [[name opts]]
               (let [a (create-appender opts)]
                 (if a
                   [name a]
                   nil))) appenders)
        (remove nil?)
        (into {}))})

(defn timbre-config! [{:keys [min-level appenders]
                       :or {min-level :info
                            appenders default-appenders}
                       :as config}]
    ; use println, because at timbre configuration it is not sure where logging
    ; output would be written to.
  (println "timbre config min-level: " min-level "appenders: "  appenders)
    ;(println "appender setup: " (create-appenders appenders))
  (timbre/set-config!
   (merge timbre/default-config
          (create-appenders appenders)
          {;:output-fn default-output-fn
              ; :middleware [(fn [data] 
              ;                (println "context: " (:context data))
              ;                ;(println "middleware: " data)
              ;                ;(assoc data :vargs ["qux"])
              ;                data
              ;                )]
           :min-level min-level})))


