(ns modular.log.appenders
  (:require
   [taoensso.timbre :as timbre]
   [taoensso.timbre.appenders.core :refer [println-appender console-appender]]))

(def appender-list
  {:console println-appender
   :console-color console-appender})

(def default-appenders
  {:default {:type :console-color}})