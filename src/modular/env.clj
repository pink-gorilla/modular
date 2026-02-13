(ns modular.env
  (:require
   [clojure.string :as str])
  (:gen-class)
  )

(defn env [s]
  (str/replace s #"\$\{([^}]+)\}"
               (fn [[_match var]]
                 (or (System/getenv var) (throw (ex-info (str "ENV-VAR not found: " var) {}))))))