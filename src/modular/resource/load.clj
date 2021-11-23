(ns modular.resource.load
  (:require
   [taoensso.timbre :refer [debug info warnf]]
   [clojure.java.io :as io]
   [babashka.fs :refer [create-dirs]]
   [modular.resource.explore :refer [describe-recursive-files]]))

(defn slurp-res [name-full]
  (let [file-content (try
                       (slurp name-full)
                       (catch Exception _
                         nil))]
    (if file-content
      file-content
      (try (let [r (io/resource name-full)]
             (slurp r))
           (catch Exception _
             nil)))))

(defn write-res [dir name-full]
  (let [filename-out (str dir "/" name-full)
        file-out (io/file filename-out)
        dir-out (.getParent file-out)
         ;file-in (io/file name-full)
         ;file-in (if (.exists file-in)
         ;            file-in
         ;            (io/file 
         ;             (io/resource name-full)
         ;             )
         ;          )
        ]
    (create-dirs dir-out)
    ;(println "writing to: " dir-out filename-out)
    ;(io/copy file-in file-out)
    (->> (slurp-res name-full)
         (spit filename-out))))

(defn recursive-filenames [resource-dir]
  (->> (describe-recursive-files resource-dir)
       (map :name-full)
       (into [])))

(defn write-resources-to [target-dir resource-dir]
  (doall (map #(write-res target-dir %)
              (recursive-filenames resource-dir))))

(comment

  (slurp-res "public/css/custom_style.css")
  (slurp-res "public/ag-grid-community/dist/styles/ag-theme-blue.min.css")
  (write-res ".webly" "public/css/custom_style.css")

  (write-res ".webly" "public/ag-grid-community/dist/styles/ag-theme-blue.min.css")

  (describe-recursive-files "public")
  (recursive-filenames "public")
  (write-resources-to ".webly" "public")

  (require '[clojure.string :as str])
  (def path "www.hoertlehner.com/bongo/index.html")

  (defn config-path [path]
    (str/replace path
                 #"(.*\/)(.*)$"
                 #(str (second %1) "config.edn")))

  (str/replace path #"(.*\/)(.*)$" #(str  (second %1) "config.edn"))

  (config-path path)

; 
  )









