(ns modular.config
  (:require
   [taoensso.timbre  :refer [debug info warn error]]
   [modular.config.cprop :refer [load-config-cprop]]
   [modular.config.watch :refer [watch-config!]]
   [modular.log :refer [timbre-config!]]
   [modular.writer :refer [write-status]]))

(defonce config-atom (atom {}))

(defmacro get-in-config-cljs [path]
  (get-in @config-atom path))

(defn get-in-config [path]
  (get-in @config-atom path))

(defn set!
  "The config normally gets configured at an application level:
   On app start the config(s) get loaded.
   In the repl or in unit tests we want to be able to set configs programmatically.
   Therefore set! exists"
  [kw config]
  (swap! config-atom assoc kw config))

;; ns require 

; the config is an edn file that can contain clojure symbols.
; In order for symbols to be resolveable, the namespace of the symbol has to be loaded.
; therefore we have a require section here.

(defn require-log [n]
  (info "clj-require:" n)
  (require [n]))

(defn require-ns-clj []
  (let [ns-clj (get-in-config [:webly :ns-clj])]
    (if ns-clj
      (try
        (info "requiring ns-clj:" ns-clj)
        (doall
         (map require-log ns-clj))
        (catch Exception e
          (error "Exception requiring ns-clj: " (pr-str e))))
      (warn "no ns-clj defined."))))

(defn resolve-symbol [path]
  (let [s (get-in-config path)]
    (if (symbol? s)
      (try
        (debug "resolving symbol: " s)
        (if-let [r (var-get (resolve s))]
          (do
            (debug "symbol " s " resolved to: " r)
            (swap! config-atom assoc-in path r)
            r)
          (do (error  "symbol in path [" path "] as: " s " could not get resolved!")
              nil))
        (catch Exception e
          (error "Exception resolving symbol in path: " path " ex:" (pr-str e))
          nil))
      s)))

(defn load-config!
  [app-config]
  (let [config (load-config-cprop app-config)]
    (reset! config-atom config)
    (timbre-config! @config-atom) ; set timbre config as soon as possible
    (require-ns-clj) ; requiring ns needs to happen before resolving symbols
    (resolve-symbol [:keybindings])
    (resolve-symbol [:webly :routes])
    (write-status "config" @config-atom)
    (write-status "keybindings" (get-in @config-atom [:keybindings]))
    (watch-config! config-atom)))

(defn add-config [app-config user-config]
  (let [app-config (if (vector? app-config) app-config [app-config])
        user-config (if (vector? user-config) user-config [user-config])]
    (into [] (concat app-config user-config))))
