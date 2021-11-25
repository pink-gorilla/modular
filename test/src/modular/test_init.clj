(ns modular.test-init
  (:require
   [clojure.test :refer [deftest is]]
   [modular.log :refer [timbre-config!]]
   [modular.persist.json] ; side effects
   [modular.persist.edn] ; side effects
   ))

(timbre-config!
 {:timbre-loglevel  [[#{"webly.*"} :info]
                     [#{"*"} :info]]})




