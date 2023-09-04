(ns modular.encoding.time2
  "Connect time-literals to transit."
  (:require [time-literals.read-write]
            [cognitect.transit :as transit]
            #?(:cljs [java.time :refer [Period
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
                                        YearMonth]]))
  #?(:clj (:import (java.time Period
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
                              YearMonth))))

(def time-classes
  {'period Period
   'date LocalDate
   'date-time LocalDateTime
   'zoned-date-time ZonedDateTime
   'offset-time OffsetTime
   'instant Instant
   'offset-date-time OffsetDateTime
   'time LocalTime
   'duration Duration
   'year Year
   'year-month YearMonth
   'zone ZoneId
   'day-of-week DayOfWeek
   'month Month})

(def time-serialization-handlers
  {:handlers
   (into {}
         (for [[tick-class host-class] time-classes]
           [host-class (transit/write-handler (constantly (name tick-class)) str)]))})

(def time-deserialization-handlers
  {:handlers
   (into {} (for [[sym fun] time-literals.read-write/tags]
              [(name sym) (transit/read-handler fun)]))}) ; omit "time/" for brevity
