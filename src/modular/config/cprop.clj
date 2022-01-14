(ns modular.config.cprop
  (:require
   [clojure.java.io :as io]
   [taoensso.timbre  :refer [debug info warn error]]
   [cprop.core :refer [load-config]]
   [cprop.source :refer [from-env from-system-props from-resource from-file]]
   [modular.encoding.edn :as e]))

;; cprop
(defn- load-from-file [filename]
  (info "loading config from file:" filename)
  (binding [*data-readers* e/data-readers]
    (from-file filename)))

(defn- load-from-resource [name]
  (info "loading config from resource:" name)
  (binding [*data-readers* e/data-readers]
    (from-resource name)))

(defn- from-map-file-res [config]
  (cond
    (nil? config)
    {}

    (map? config)
    config

    (.exists (io/file config))
    (load-from-file config)

    (io/resource config)
    (load-from-resource config)

    :else
    {}))

; https://github.com/tolitius/cprop

(defn load-config-cprop [app-config]
  (let [app-config-vec (if (vector? app-config)
                         app-config
                         [app-config])
        app (into [] (map from-map-file-res app-config-vec))
        ;_ (info "app configs: " app)
        config1 (load-config
                 :resource "modular/empty-config.edn"
                 :merge app)
        ;_ (info "cf: " config-files)
        ks (keys config1)
        config2 (load-config
                 :resource "modular/empty-config.edn"
                 :merge
                 [config1
                  (from-system-props)
                  (from-env) ; env otherwise has way too many settings
                  ])
        config2 (select-keys config2 ks)]
    config2))