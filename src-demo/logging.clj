(ns logging
  (:require
   [taoensso.timbre :refer [info debug warn error with-context]]
   [modular.log :refer [timbre-config!]]))

(defn timbre-custom-config []
  (println "setting custom logging config!")
  (timbre-config!
   {:min-level [[#{"org.eclipse.jetty.*"} :warn]
                [#{"modular.oauth2.token.refresh"} :warn]
                [#{"*"} :info]]
    :appenders {:default {:type :console-color}
                :file {:type :file
                       :fname "target/file.log"}
                :rolling {:type :file-rolling
                          :path "target/rolling.log"
                          :pattern :monthly}
                :workflow {:type :file-rolling-context
                           :context :scheduled-data-import
                           :path "target/workflow.log"
                           :pattern :monthly}}}))

(defn demo [{:keys [custom-config]}]
  (if custom-config
    (timbre-custom-config)
    (println "using default timbre config!"))

  (debug "debug!")
  (info "info!")
  (warn "warn!")
  (error "error!")

  (with-context :scheduled-data-import
    (debug "debug!")
    (info "info!")
    (warn "warn!")
    (error "error!"))

 ;
  )