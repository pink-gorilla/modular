(ns modular.clip-patch)

(in-ns 'juxt.clip.core)

;; since safely-derive-parts is a private var, we need to extend clip with in-ns

(defn orderby-start
  ([system-config]
   (orderby-start system-config (keys (:components system-config))))
  ([system-config component-ks]
   (let [{:keys [components]} system-config
         [_ component-chain] (safely-derive-parts components [] component-ks)
         component-ks-sorted (map first component-chain)]
     component-ks-sorted)))

;(in-ns 'modular.system)