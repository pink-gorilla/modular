(ns modular.config
  (:require
   [taoensso.timbre  :refer [debug info warn error]]
   [modular.config.cprop :refer [load-config-cprop]]
   [modular.config.watch :refer [watch-config!]]
   [modular.require :refer [resolve-symbol]]
   [modular.writer :refer [write-status]]))

(defonce config-atom (atom {}))

(defmacro get-in-config-cljs [path]
  (get-in @config-atom path))

(defn get-in-config [path]
  (get-in @config-atom path))

;(swap! a assoc :comparator comparator)

;; RESOLVE


(defn resolve-config-key [_ path]
  (if-let [s (get-in-config path)]
    (if-let [r (resolve-symbol s)]
      (swap! config-atom assoc-in path r)
      (error "resolve-error: resolve failed in path: " path))
    (error "resolve-error: path path not found: " path)))


(defn load-config!
  [app-config]
  (let [config (load-config-cprop app-config)]
    (reset! config-atom config)
    (write-status "config" @config-atom)
    (watch-config! config-atom)))

(defn add-config [app-config user-config]
  (let [app-config (if (vector? app-config) app-config [app-config])
        user-config (if (vector? user-config) user-config [user-config])]
    (into [] (concat app-config user-config))))

#_(defn get-in [path]
    (get-in @config-atom path))

(defn set!
  "The config normally gets configured at an application level:
   On app start the config(s) get loaded.
   In the repl or in unit tests we want to be able to set configs programmatically.
   Therefore set! exists"
  [kw config]
  (swap! config-atom assoc kw config))