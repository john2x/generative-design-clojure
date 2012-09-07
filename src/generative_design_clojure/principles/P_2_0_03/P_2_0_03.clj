; P_2_0_03.clj
;
; Licensed under the Apache License, Version 2.0

; drawing a filled circle with lines
;
; MOUSE
; position x      : length
; position y      : thickness and number of lines
; drag            : draw
;
; KEYS
; 1-3             : stroke color
; del, backspace  : erase
; s               : save png

(ns generative-design-clojure.principles.P_2_0_03.P_2_0_03
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(def DELETE 127)
(def BACKSPACE 8)

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (smooth)
  (no-fill)
  (background 360)
  (set-state! :stroke-color (atom (color 0 10))))

(defn draw []
  (if (mouse-state)
    (do
      (push-matrix)
      (translate (/ (width) 2) (/ (height) 2))

      (let [circle-resolution (int (map-range (+ (mouse-y) 100) 0 (height) 2 10)),
            radius (- (mouse-x) (/ (width) 2)),
            angle (/ TWO-PI circle-resolution)]

        (stroke-weight 2)
        (stroke @(state :stroke-color))
        
        (begin-shape)
        (doseq [i (range-incl circle-resolution)
                :let [x (* (cos (* angle i)) radius),
                      y (* (sin (* angle i)) radius)]]
          (vertex x y))
        (end-shape)
        (pop-matrix)))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    ("1") (reset! (state :stroke-color) (color 0 10))
    ("2") (reset! (state :stroke-color) (color 192 100 64 10))
    ("3") (reset! (state :stroke-color) (color 52 100 71 10))
    nil)
  (when (or (= (key-code) DELETE) (= (key-code) BACKSPACE))
    (background 360)))

(defsketch P_2_0_03
           :title "P_2_0_03"
           :setup setup
           :draw draw
           :key-released key-release
           :size [720 720])
