; P_2_0_02.clj
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
; del, backspace  : erase
; s               : save png

(ns generative-design-clojure.principles.P_2_0_02.P_2_0_02
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(def DELETE 127)
(def BACKSPACE 8)

(defn setup []
  (smooth)
  (no-fill)
  (background 255)
  )

(defn draw []
  (when (mouse-state)
    (do
      (push-matrix)
      (translate (/ (width) 2) (/ (height) 2))

      (let [circle-resolution (int (map-range (+ 100 (mouse-y)) 0 (height) 2 10)),
            radius (+ 0.5 (- (mouse-x) (/ (width) 2))),
            angle (/ TWO-PI circle-resolution)]

        (stroke-weight 2)
        (stroke 0 25)
        
        (begin-shape)
        (doseq [i (range-incl circle-resolution)
                :let [x (+ 0 (* (cos (* angle i)) radius)),
                      y (+ 0 (* (sin (* angle i)) radius))]]
          (vertex x y))
        (end-shape)
        (pop-matrix)))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil)
  (when (or (= (key-code) DELETE) (= (key-code) BACKSPACE))
    (background 255)))

(defsketch P_2_0_02
           :title "P_2_0_02"
           :setup setup
           :draw draw
           :key-released key-release
           :size [720 720])
