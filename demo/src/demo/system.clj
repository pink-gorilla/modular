(ns demo.system
  (:require 
   [juxt.clip.core :as clip]
   [demo.super]
   [modular.system]
   ))

(-> 'demo.super/secret requiring-resolve)

(-> 'demo.super/secret requiring-resolve deref)

(defn lookup [s]
  (when-let [v (requiring-resolve s)]
    (when (var? v)
      (deref v))))

(+ 1000000
(lookup 'demo.super/secret)   
   )

(binding [demo.super/secret 8]
  (+ 1000000
     (lookup 'demo.super/secret))
  )

(def system-config
  {:components
   {:c {:start '(concat (clip/ref :b) [:c])}
    :a {:start {:a 1}}
    :b {:start [(clip/ref :a) :b]}
    }})



(clip/orderby-start system-config)

(clip/start system-config)

(modular.system/start-system system-config)