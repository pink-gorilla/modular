(ns modular.log.appenders
  (:require
   [taoensso.timbre :as timbre]
   [taoensso.timbre.appenders.core :refer [println-appender spit-appender]]
   [taoensso.timbre.appenders.community.rolling :refer [rolling-appender]]))

(defn color-appender [& [opts]]
  (let [colors {:info :green,
                :warn :yellow
                :error :red
                :fatal :purple
                :report :blue}]
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

(defn rolling-appender-context [& [opts]]
  (let [context-target (:context opts)
        ra (rolling-appender opts)
        ra-fn (:fn ra)]
    {:enabled?   true
     :async?     false
     :min-level  nil
     :rate-limit nil
     :output-fn  :inherit
     :fn (fn [{:keys [context] :as data}]
           (when (= context context-target)
             ;(println "context driven data: " data)
             (ra-fn data)))}))

(def appender-list
  {:console println-appender
   :console-color color-appender
   :file spit-appender
   :file-rolling rolling-appender
   :file-rolling-context rolling-appender-context})

(def default-appenders
  {:default {:type :console-color}})

