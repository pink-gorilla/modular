(ns modular.resource.explore-test
  (:require
   [clojure.test :refer [deftest is]]
   [modular.file.explore :as filesystem]
   [modular.resource.explore :as resource]
   [modular.test-init] ; side effects
   ))

(deftest filesystem-explore-test
  (let [expected-files '({:scheme "file", :name "b.html", :name-full "test/resources/public/b.html", :dir? false}
                         {:scheme "file", :name "a.txt", :name-full "test/resources/public/a.txt", :dir? false})
        expected-directories '({:scheme "file", :name "css", :name-full "test/resources/public/css", :dir? true}
                               {:scheme "file", :name "js", :name-full "test/resources/public/js", :dir? true})]
    ; filesystem 
    (is (= expected-files (filesystem/describe-files "test/resources/public/")))
    ;(is (= expected-directories (filesystem/describe-directories "test/resources/public/")))
    ))
(deftest resource-explore-test
  (let [expected-files '({:scheme "file", :name "b.html", :name-full "public/b.html", :dir? false}
                         {:scheme "file", :name "a.txt", :name-full "public/a.txt", :dir? false})
        expected-directories '({:scheme "file", :name "css", :name-full "public/css", :dir? true}
                               {:scheme "file", :name "js", :name-full "public/js", :dir? true})
        expected-recursive '({:dir? true, :name "public", :name-full "public", :scheme "-"}
                             {:scheme "file", :name "b.html", :name-full "public/b.html", :dir? false}
                             {:scheme "file", :name "css", :name-full "public/css", :dir? true}
                             {:scheme "file", :name "custom_style.css", :name-full "public/css/custom_style.css", :dir? false}
                             {:scheme "file", :name "a.txt", :name-full "public/a.txt", :dir? false}
                             {:scheme "file", :name "js", :name-full "public/js", :dir? true}
                             {:scheme "file", :name "b.js", :name-full "public/js/b.js", :dir? false}
                             {:scheme "file", :name "a.js", :name-full "public/js/a.js", :dir? false})]
    ; resources (one directory)
    (is (= expected-files (resource/describe-files "public")))
    (is (= expected-files (resource/describe-files "public/")))
    (is (= expected-directories (resource/describe-directories "public")))
    ; resources (recursive)    
    (is (=  expected-recursive (resource/describe-recursive "public")))))

