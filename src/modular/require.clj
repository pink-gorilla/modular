(ns modular.require
  (:require
   [taoensso.timbre  :refer [debug info warn error]]))

;; ns require 

; the config is an edn file that can contain clojure symbols.
; In order for symbols to be resolveable, the namespace of the symbol has to be loaded.
; therefore we have a require section here.

(defn require-log [n]
  (info "clj-require:" n)
  (try
    (require [n])
    (catch Exception e
      (error "ns-clj: could not require: " n)
      (error "Exception requiring ns-clj: " (pr-str e)))))

(defn require-namespaces [ns-clj-vec]
  (try
    (if (vector? ns-clj-vec)
      (do (info "requiring ns-clj:" ns-clj-vec)
          (doall
           (map require-log ns-clj-vec))
          ns-clj-vec)
      (error "require-namespaces ns-clj-vec should be a vector. not requiring!"))
    (catch Exception e
      (error "Exception requiring ns-clj-vec: " (pr-str e))
      :clj-require/error)))

(defn resolve-symbol [s]
  (when (symbol? s)
    (try
      (debug "resolving symbol: " s)
      (if-let [r (var-get (resolve s))]
        (do
          (debug "symbol " s " resolved to: " r)
          r)
        (do (error  "symbol [" s "] could not get resolved!")
            nil))
      (catch Exception e
        (error "Exception resolving symbol " s " ex:" (pr-str e))
        nil))))

