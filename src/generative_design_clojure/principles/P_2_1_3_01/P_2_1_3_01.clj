; P_2_1_3_01.clj
;
; Licensed under the Apache License, Version 2.0

; changing size of circles in a rad grid depending on mouse position
;
; MOUSE
; position x      : circle amount and size
; position y      : circle position
; left click      : random position
;
; KEYS
; s               : save png

(ns generative-design-clojure.principles.P_2_1_3_01.P_2_1_3_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (background 255)
  (smooth)
  (set-state!
    :tile-count-x (atom 10.0)
    :tile-count-y (atom 10.0)
    :tile-width (atom 0)
    :tile-height (atom 0)
    :count (atom 0)
    :color-step (atom 15)
    :end-size (atom 0)
    :end-offset(atom 0)
    :act-random-seed (atom 0))
  (reset! (state :tile-width) (/ (width) @(state :tile-count-x)))
  (reset! (state :tile-height) (/ (height) @(state :tile-count-y))))

(defn draw []
  (smooth)
  (no-fill)
  (stroke 0 128)
  (background 255)
  (random-seed @(state :act-random-seed))

  (translate (/ (/ (width) @(state :tile-count-x)) 2)
             (/ (/ (height) @(state :tile-count-y)) 2))
  
  (let [circle-count (+ (/ (mouse-x) 30) 1),
        end-size (map-range (mouse-x) 0 (width) (/ @(state :tile-width) 2.0) 0),
        end-offset (map-range
                     (mouse-y) 0 (height) 0 (/ (- @(state :tile-width) end-size) 2))]

    (doseq [grid-y (range-incl @(state :tile-count-y)),
            grid-x (range-incl @(state :tile-count-x))
            :let [toggle (int (random 0 4))]]
      (push-matrix)
      (translate (* @(state :tile-width) grid-x) (* @(state :tile-height) grid-y))
      (scale 1 (/ @(state :tile-width) @(state :tile-height)))

      (when (= toggle 0) (rotate (* HALF-PI -1))) 
      (when (= toggle 1) (rotate 0)) 
      (when (= toggle 2) (rotate HALF-PI)) 
      (when (= toggle 3) (rotate PI)) 

      ; draw module
      (doseq [i (range circle-count)
              :let [diameter (map-range i 0 (- circle-count 1)
                                        @(state :tile-width) end-size),
                    offset (map-range i 0 (- circle-count 1) 0 end-offset)]]
        (ellipse offset 0 diameter diameter)
        )
      (pop-matrix))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil))

(defn mouse-press []
  (reset! (state :act-random-seed) (random 100000)))

(defsketch P_2_1_3_01
           :title "P_2_1_3_01"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-pressed mouse-press
           :size [800 800])
