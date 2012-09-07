; P_2_0_01.clj
;
; Licensed under the Apache License, Version 2.0

; drawing a filled circle with lines
;
; MOUSE
; position x      : length
; position y      : thickness and number of lines
;
; KEYS
; s               : save png

(ns generative-design-clojure.principles.P_2_0_01.P_2_0_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  )

(defn draw []
  (stroke-cap :square)
  (smooth)
  (no-fill)
  (background 255)
  (translate (/ (width) 2) (/ (height) 2))

  (let [circle-resolution (int (map-range (mouse-y) 0 (height) 2 80)),
        radius (+ 0.5 (- (mouse-x) (/ (width) 2))),
        angle (/ TWO-PI circle-resolution)]
    (stroke-weight (/ (mouse-y) 20))
    
    (begin-shape)
    (doseq [i (range-incl circle-resolution)
            :let [x (* (cos (* angle i)) radius),
                  y (* (sin (* angle i)) radius)]]
      (line 0 0 x y))
    (end-shape)))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil
  ))

(defsketch P_2_0_01
           :title "P_2_0_01"
           :setup setup
           :draw draw
           :key-released key-release
           :size [550 550])
