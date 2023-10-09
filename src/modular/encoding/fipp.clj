(ns modular.encoding.fipp
  (:require
   [time-literals.data-readers] ;; For literals
   [time-literals.read-write] ;; For printing/writing
   [fipp.ednize]))

(extend-protocol fipp.ednize/IEdn
  java.time.LocalDate
  (fipp.ednize/-edn [x]
    (tagged-literal 'time/date (str x)))
  java.time.LocalDateTime
  (fipp.ednize/-edn [x]
    (tagged-literal 'time/date-time (str x)))
  java.time.Instant
  (fipp.ednize/-edn [x]
    (tagged-literal 'time/instant (str x))))