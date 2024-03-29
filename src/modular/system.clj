(ns modular.system
  (:require
   [clojure.java.io :as io]
   [clojure.tools.cli :refer [parse-opts]]
   [taoensso.timbre  :refer [debug info warn error]]
   [aero.core :refer [read-config]]
   [juxt.clip.core :as clip]
   [clojure.repl])
  (:gen-class))

; run a function on startup

(defn run-safe [run-fn arguments system-config running-system]
  (try
    (info "running: " run-fn)
    (if-let [f (-> run-fn requiring-resolve var-get)]
      (f (merge {:system-config system-config
                 :running-system running-system}
                arguments))
      (error "run-fn [" run-fn "] could not get resolved!"))
    (catch Exception e
      (error "Exception running run-fn: " (pr-str e)))))

(defn ^:private run-fn
  [run-fn arguments system-config running-system]
  (run-safe run-fn arguments system-config running-system)
  (System/exit 0)
;(stop-system {:running-system running-system :system-config system-config})
  )
(defn log-uncaught-exceptions []
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ thread ex]
       (error "uncaught exception in thread: " (.getName thread) "exception: " ex)))))

(defn on-repl-break [_]
  (warn "on-repl-break ..")
  (Thread/sleep 100)
  (System/exit 0))

(defn stop-system [{:keys [system-config running-system]}]
  (info "stopping clip services: " (-> running-system keys)) ; :components
  (clip/stop system-config running-system)
  (shutdown-agents))

(def system nil)

(defn start-system [system-config]
  (info "starting clip services: " (-> system-config :components keys))
  (let [running-system (clip/start system-config)
        on-stop (fn []
                  (stop-system {:system-config system-config
                                :running-system running-system}))]
    (alter-var-root #'system (constantly running-system))
    (.addShutdownHook
     (Runtime/getRuntime)
     (new Thread on-stop)) ; equivalent to: (Thread. on-stop))
    (clojure.repl/set-break-handler! on-repl-break)
    {:config system-config :running-system running-system}))

(defonce modular-env-atom (atom {}))

(defn modular-env []
  @modular-env-atom)

(defn ^:private load-config
  [services-edn modular-opts aero-opts]
  (reset! modular-env-atom modular-opts)
  (-> (io/resource services-edn)
      (read-config aero-opts))) ; opts: :profile :user :resolve

(defn start!
  [{:keys [services config profile run]
    :or {profile :default}
    :as arguments}]
  (info "start! services:" services "config:" config "profile: " profile "run: " run)
  (let [system-config (load-config services {:config config
                                             :profile profile}
                                   {:profile profile})
        {:keys [running-system]} (start-system system-config)]
    (if run ;(seq arguments)
      (run-fn run arguments system-config running-system) ;; application run from the command line with arguments.
      @(promise) ;; application run from the command line, no arguments, keep webserver running.
      )))

;; CLI ENTRYPOINT

(def ; ^:private
  cli-options
  [["-s" "--services FILE" "Config file, found on the classpath, to use."
    :default "services.edn"]
   ["-c" "--config FILE" "Config file, found on the classpath, to use."
    :default "config.edn"]
   ["-p" "--profile PROFILE" "Profile to use, i.e., local."
    :default :default
    :parse-fn #(keyword %)]
   ["-r" "--run ARGS" "Run a function, then exit"
    :default nil]])

(defn -main
  [& args]
  (let [{{:keys [profile services config]} :options :keys [arguments]} (parse-opts args cli-options)]
    (start! {:services services
             :profile profile
             :config config
             :run arguments})))
