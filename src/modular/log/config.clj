(ns modular.log.config
  (:require
   [taoensso.timbre :as timbre]
   [taoensso.timbre.appenders.core :as appenders]
   [taoensso.timbre.appenders.3rd-party.rolling :refer [rolling-appender]]))

(def color-appender
  (let [colors {:info :green, :warn :yellow, :error :red, :fatal :purple, :report :blue}]
    {:enabled?   true
     :async?     false
     :min-level  nil
     :rate-limit nil
     :output-fn  :inherit
     :fn (fn [{:keys [error? level output-fn] :as data}]
           (binding [*out* (if error? *err* *out*)]
             (if-let [color (colors level)]
               (println (timbre/color-str color (output-fn data)))
               (println (output-fn data)))))}))

; in config:
; :timbre-file {:path "log/workflow.log" :pattern :monthly}

(defn appender-config [rf]
  (if rf
    {:appenders
     {:color-appender color-appender
        ;:spit (appenders/spit-appender {:fname "log/my-file.log"})
      :rolling (rolling-appender rf)}}
    {:appenders
     {:color-appender color-appender}}))