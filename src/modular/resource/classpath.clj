(ns modular.resource.classpath
  (:require
   [clojure.string :as str]
   [clojure.java.io :as io]
   [resauce.core :as rs])
  (:import
   [java.net JarURLConnection URI]))

(defn- url-scheme [url]
  ;; Using URI instead of URL to support arguments without schema.
  (.getScheme (URI. (str url))))

(defmulti url-name
  {:arglists '([url])}
  url-scheme)

(defmethod url-name "file" [url]
  (let [file (io/as-file url)]
    ;(.getPath file)
    (.getName file)))

(defmethod url-name "jar" [url]
  (let [conn (.openConnection url)
        path (.getEntryName ^JarURLConnection conn)]
    path))

(defn file-dir? [url]
  (let [file (io/as-file url)
        dir?  (.isDirectory file)]
    dir?))

(defn file-path [url]
  (let [file (io/as-file url)]
    (.getPath file)))

(defn describe-url [res-path url]
  (let [scheme (url-scheme url)
        name (url-name url)
        ;  names for file: ("apple.clj" "banana.clj" 
        ;  names for jar:  "demo/notebook/site_template.cljs" "demo/notebook/ipsum_sidebar.cljs")
        ; so this needs to be corrected.
        name (if (= scheme "jar")
               (subs name  (if (str/ends-with? res-path "/")
                             (count res-path)
                             (inc (count res-path))))
               name) ; 
        name-full (str res-path
                       (if (str/ends-with? res-path "/") "" "/")
                       name)
        both {:scheme scheme
              :name name
              :name-full name-full
              :dir? (case scheme
                      "jar" (if (rs/directory? url) true false)
                      "file" (file-dir? url)
                      :bongo)}]

    (if (= scheme "file")
      (assoc both :path (file-path url)) ; path is important for saving.
      both)))