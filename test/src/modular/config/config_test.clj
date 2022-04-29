(ns modular.config.config-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [modular.config :as config :refer [load-config! config-atom add-config require-namespaces]]
   [modular.config.cprop :refer [load-config-cprop]]))

(defn remove-modular [c]
  (dissoc c :_modular))

(defn remove-but-oauth [c]
  (dissoc c  :_modular
          :jetty-ws
          :settings
          :web-server-api
          :google-analytics
          :webly
          :prefix
              ;:oauth2
          :web-server
          :shadow
          :keybindings
          :timbre/clj
          :timbre/cljs))

(defn c [c]
  (reset! config-atom {})
  (load-config! c)
  (-> @config-atom
      remove-modular))

(deftest config []
  (is (= {}  (c nil))) ; nil works
  (is (= {:a 1}  (c {:a 1}))) ; map is taken as-is
  (is (= {:a 1 :b 2}  (c [{:a 1 :b 1} {:a 1 :b 2}]))) ; vec -> later config overrides
  (is (= {:c 3}  (c "bongo.edn"))) ; strings as resources
  (is (= {:a 7 :c 3} (c [{:a 7} "bongo.edn"]))) ; strings as resources
  )

(deftest add []
  (is (= [{:a 1} {:a 2}]  (add-config {:a 1} {:a 2})))
  (is (= [{:a 1} {:a 2}]  (add-config [{:a 1}] {:a 2})))
  (is (= [{:a 1} {:a 2}]  (add-config [{:a 1}] [{:a 2}])))
  (is (= [{:a 1} {:a 2}]  (add-config {:a 1} [{:a 2}]))))

(deftest set-config-test []
  (config/set! :demo/service3 {:a 1 :b 2})
  (is (= {:a 1 :b 2}  (config/get-in-config [:demo/service3])))
  (is (= 1  (config/get-in-config [:demo/service3 :a]))))

(deftest require-namespaces-test []
  (let [require-result (require-namespaces ['demo.panama])]
    (is (not (= :clj-require/error require-result)))))

(deftest config-cpro-merge-test []
  (let [lca #(-> (load-config-cprop %) remove-modular)
        lc #(-> (load-config-cprop %) remove-but-oauth)]
    (is (= {:a 1 :b 2 :c 3}
           (lca [{:a 1} {:b 2} {:c 3}])))
    (is (= {:a 1 :b {:c 2 :d 3}}
           (lca [{:a 1} {:b {:c 2}} {:b {:d 3}}])))

    (is (= {:oauth2 {:github {:client-id "1" :client-secret "2"
                              :scopes ["user:email" "gist" "repo"]}
                     :google {:client-id "3" :client-secret "4"
                              :scopes ["https://www.googleapis.com/auth/spreadsheets.readonly"
                                       "https://www.googleapis.com/auth/drive.readonly"
                                       "https://www.googleapis.com/auth/userinfo.email"]}}}

           (lc ["webly/config.edn" "test/file_config.edn"
                {:oauth2 {:github {:client-id "1" :client-secret "2"}
                          :google {:client-id "3" :client-secret "4"}}}])))

;
    ))
