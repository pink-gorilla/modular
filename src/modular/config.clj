(ns modular.config
  (:require
   [modular.config.cprop :refer [load-config-cprop]]
   [modular.writer :refer [write-edn-private]]))

(defonce config-atom (atom {}))

(defn get-in-config [path]
  (get-in @config-atom path))

(defn load-config!
  [app-config]
  (let [config (load-config-cprop app-config)]
    (write-edn-private "config" config)
    (reset! config-atom config)
    config-atom))

(defn add-config [app-config user-config]
  (let [app-config (if (vector? app-config) app-config [app-config])
        user-config (if (vector? user-config) user-config [user-config])]
    (into [] (concat app-config user-config))))

(defn set!
  "The config normally gets configured at an application level:
   On app start the config(s) get loaded.
   In the repl or in unit tests we want to be able to set configs programmatically.
   Therefore set! exists"
  [kw config]
  (swap! config-atom assoc kw config))