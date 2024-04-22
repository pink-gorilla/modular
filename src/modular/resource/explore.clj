(ns modular.resource.explore
  (:require
   [resauce.core :as rs]
   [modular.resource.classpath :refer [describe-url url-name]]))

(defn describe [res-path]
  (->> (rs/resource-dir res-path)
       (map (partial describe-url res-path))))

(defn dir? [{:keys [dir?]}]
  dir?)

(defn describe-files [res-path]
  (->> (describe res-path)
       (remove dir?)))

(defn describe-directories [res-path]
  (->> (describe res-path)
       (filter dir?)))

;(tree-seq branch? children root)
;; Use tree-seq to recursively find all files 
;; given a root directory (here for didactic purposes. See file-seq)
; (let [directory (clojure.java.io/file "/path/to/directory/")
;      dir? #(.isDirectory %)]
;    ;we want only files, therefore filter items that are not directories.
;  (filter (comp not dir?)
;          (tree-seq dir? #(.listFiles %) directory)))

(defn describe-recursive [res-path]
  (let [root  {:dir? true
               :name res-path
               :name-full res-path
               :scheme "-"}
        describe-entry (fn [itm]
                         ;(println "describing: " itm)
                         (let [{:keys [name-full]} itm]
                           (describe name-full)))
        all (tree-seq dir? describe-entry root)]
   ; (filter dir? all)
    all))

(defn describe-recursive-files [res-path]
  (->> (describe-recursive res-path)
       (remove dir?)))

(comment

  (describe-files "public")
  (describe-files "public/")
  (describe-files "public/js")
  (describe-directories "public")
  (describe "public/ag-grid-community/dist/styles")
  (describe "public/ag-grid-community")
  (describe  "public/ag-grid-community/dist/")
  (describe "public/ag-grid-community/")

  (describe-files "modular/encoding")

  (describe-recursive "public")

  (require '[clojure.pprint :refer [print-table]])
  (->> ;(describe "public")
       ;(describe "public/ag-grid-community/dist/styles")
       ;(describe-recursive "public")
   (describe-recursive-files "public")

      ;second
      ;(describe-url "public")
   print-table)

;  
  )