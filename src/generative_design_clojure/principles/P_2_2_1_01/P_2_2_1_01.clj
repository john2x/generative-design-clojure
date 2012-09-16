; P_2_2_1_01.clj
;
; Licensed under the Apache License, Version 2.0

; draw the path of a stupid agent
;
; MOUSE
; position x      : drawing speed
;
; KEYS
; BACKSPACE       : delete display
; s               : save png

(ns generative-design-clojure.principles.P_2_2_1_01.P_2_2_1_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (background 255)
  (smooth)
  (no-stroke)
  (set-state!
    :pos-x (atom (/ (width) 2))
    :pos-y (atom (/ (height) 2))
    :step-size (atom 1)
    :diameter (atom 1)))

(defn draw []
  (doseq [i (range-incl (mouse-x))
          :let [direction (int (random 0 8))]]
    (case direction
      0 (do (swap! (state :pos-y) - @(state :step-size)))
      1 (do (swap! (state :pos-x) + @(state :step-size))
                    (swap! (state :pos-y) - @(state :step-size)))
      2 (do (swap! (state :pos-x) + @(state :step-size)))
      3 (do (swap! (state :pos-x) + @(state :step-size))
                    (swap! (state :pos-y) + @(state :step-size)))
      4 (do (swap! (state :pos-y) + @(state :step-size)))
      5 (do (swap! (state :pos-x) - @(state :step-size))
                    (swap! (state :pos-y) + @(state :step-size)))
      6 (do (swap! (state :pos-x) - @(state :step-size)))
      7 (do (swap! (state :pos-x) - @(state :step-size))
                    (swap! (state :pos-y) - @(state :step-size)))
      nil)

    (when (> @(state :pos-x) (width))
      (reset! (state :pos-x) 0))
    (when (< @(state :pos-x) 0)
      (reset! (state :pos-x) (width)))
    (when (> @(state :pos-y) (height))
      (reset! (state :pos-x) 0))
    (when (< @(state :pos-y) 0)
      (reset! (state :pos-x) (height)))

    (fill 0 40)
    (ellipse (+ @(state :pos-x) (/ @(state :step-size) 2))
             (+ @(state :pos-y) (/ @(state :step-size) 2))
             @(state :diameter) @(state :diameter))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil))

(defn key-press []
  (case (key-code)
    157 (background 255)
    127 (background 255)
    nil))

(defsketch P_2_2_1_01
           :title "P_2_2_1_01"
           :setup setup
           :draw draw
           :key-released key-release
           :key-pressed key-press
           :size [800 800])
