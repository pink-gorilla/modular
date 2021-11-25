(ns modular.file.explore
  (:require
   [taoensso.timbre :refer [trace debug debugf info infof warn warnf error errorf]]
   [clojure.java.io :as io]))

(defn file-dir? [filename]
  (debug "checking: " filename)
  (-> (io/file filename)
      (.isDirectory)))

(defn- describe-file [dir filename]
  (let [name-full (str dir "/" filename)]
    {:scheme "file"
     :name filename
     :dir? (file-dir? name-full)
     :name-full name-full}))

(defn describe [dir]
  (let [dir (io/file dir)
        files (if (.exists dir)
                (->> (.list dir)
                     (map (partial describe-file dir))
                     doall)
                (do
                  (warnf "describe (filesystem) path %s not found." dir)
                  '()))]
    (debug "describe (filesystem) dir: " files)
    files))

(defn dir? [{:keys [dir?]}]
  dir?)

(defn describe-files [res-path]
  (->> (describe res-path)
       (remove dir?)))

(defn describe-directories [res-path]
  (->> (describe res-path)
       (filter dir?)))