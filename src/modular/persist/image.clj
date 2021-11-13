(ns modular.persist.image
  (:refer-clojure :exclude [read])
  (:require
   [clojure.java.io :as io]
   [modular.persist.protocol :refer [save loadr]])
  (:import
   java.io.File
   java.awt.image.BufferedImage
   javax.imageio.ImageIO))

(defmethod save :png [_ file-name ^BufferedImage buffered-image]
  (ImageIO/write buffered-image
                 "png"
                 ^java.io.File (io/file file-name)))

(defmethod loadr :png
  [_ file-name]
  (ImageIO/read (io/file file-name)))