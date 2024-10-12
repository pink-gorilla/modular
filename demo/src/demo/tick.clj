(ns demo.tick
  (:require 
    [cljc.java-time.zone-id :refer [get-available-zone-ids]]
   )
  )

 
 
(get-available-zone-ids)
;; => #{"Asia/Aden" "America/Cuiaba" "Etc/GMT+9" "Etc/GMT+8" "Africa/Nairobi" 
;; "America/Marigot" "Asia/Aqtau" "Pacific/Kwajalein" "America/El_Salvador" "Asia/Pontianak"
;; "Africa/Cairo" "Pacific/Pago_Pago" "Africa/Mbabane" "Asia/Kuching" "Pacific/Honolulu" "Pacific/Rarotonga" 
;; "America/Guatemala" "Australia/Hobart" "Europe/London" "America/Belize" "America/Panama" "Asia/Chungking" "America/Managua" "America/Indiana/Petersburg" "Asia/Yerevan" "Europe/Brussels" "GMT" "Europe/Warsaw" "America/Chicago" "Asia/Kashgar" "Chile/Continental" "Pacific/Yap" "CET" "Etc/GMT-1" "Etc/GMT-0" "Europe/Jersey" "America/Tegucigalpa" "Etc/GMT-5" "Europe/Istanbul" "America/Eirunepe" "Etc/GMT-4" "America/Miquelon" "Etc/GMT-3" "Europe/Luxembourg" "Etc/GMT-2" "Etc/GMT-9" "America/Argentina/Catamarca" "Etc/GMT-8" "Etc/GMT-7" "Etc/GMT-6" ...}

