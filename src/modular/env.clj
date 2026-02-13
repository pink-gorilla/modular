(ns modular.env
  (:require
   [clojure.string :as str]))

(defn env [s]
  (str/replace s #"\$\{([^}]+)\}"
               (fn [[match var]]
                 (or (System/getenv var) (throw (ex-info (str "ENV-VAR not found: " var) {}))))))