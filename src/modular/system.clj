(ns modular.system
  (:require
   [taoensso.timbre  :refer [debug info warn error]]
   [juxt.clip.core :as clip]
   [clojure.repl]
   [modular.config :refer [get-in-config]]))

(defn log-uncaught-exceptions []
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ thread ex]
       (error "uncaught exception in thread: " (.getName thread) "exception: " ex)))))

;(clojure.repl/set-break-handler! stop-server-repl)

(defn start [system-config]
  (info "starting clip services: services: " system-config)
  (let [running-system (clip/start system-config)
        on-stop (fn []
                  (info "shutting down clip services ...")
                  (shutdown-agents)
                  (clip/stop system-config running-system))]
    (.addShutdownHook
     (Runtime/getRuntime)
        ;(Thread. on-stop))
     (new Thread on-stop))
    {:config system-config :running-system running-system}))

(defn stop [{:keys [system-config running-system]}]
  (clip/stop system-config running-system))

(defn start!
  ([]
   (start! (get-in-config [:services])))
  ([system-config]
   (start system-config)
   @(promise)))