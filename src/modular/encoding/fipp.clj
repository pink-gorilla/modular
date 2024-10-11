(ns modular.encoding.fipp
  (:require
   [time-literals.data-readers] ;; For literals
   [time-literals.read-write] ;; For printing/writing
   [fipp.ednize])
  (:import
   [java.time Period
    LocalDate
    LocalDateTime
    ZonedDateTime
    OffsetTime
    Instant
    OffsetDateTime
    ZoneId
    DayOfWeek
    LocalTime
    Month
    Duration
    Year
    YearMonth
    MonthDay]))

(extend-protocol fipp.ednize/IEdn
  LocalDate
  (fipp.ednize/-edn [x]
    (tagged-literal 'time/date (str x)))
  LocalDateTime
  (fipp.ednize/-edn [x]
    (tagged-literal 'time/date-time (str x)))
  ZonedDateTime
  (fipp.ednize/-edn [x]
    (tagged-literal 'time/zoned-date-time (str x)))
  Instant
  (fipp.ednize/-edn [x]
    (tagged-literal 'time/instant (str x))))