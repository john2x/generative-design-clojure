; P_1_2_3_01.clj
;
; Licensed under the Apache License, Version 2.0

; generates specific color palettes
;
; MOUSE
; position x/y    : row and column count
;
; KEYS
; 0-9             : creates specific color palettes
; s               : save png
; c               : save color palette

(ns generative-design-clojure.principles.P_1_2_3_01.P_1_2_3_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  )

(defn draw []
  )

(defn key-release []
  )

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-press []
  (if (= (str (raw-key)) "s")
    (save-frame (str (timestamp) "_##.png"))))

(defsketch P_1_2_3_01
           :title "P_1_2_3_01"
           :setup setup
           :draw draw
           :key-released key-release
           :size [800 800])
