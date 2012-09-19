; P_2_2_1_02.clj
;
; Licensed under the Apache License, Version 2.0

; draw the path of an intelligent agent
;
; MOUSE
; position x      : drawing speed
;
; KEYS
; 1-3             : draw mode of the agent
; BACKSPACE       : delete display
; s               : save png

(ns generative-design-clojure.principles.P_2_2_1_02.P_2_2_1_02
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (smooth)
  (background 360)
  (no-stroke)
  (set-state!
    :step-size (atom 1.0)
    :diameter (atom 1.0)
    :draw-mode (atom 1)
    :direction (atom 1)
    :pos-x (atom (/ (width) 2.0))
    :pos-y (atom (/ (height) 2.0))))

(defn draw []
  (let [counter (atom 0)]
    (doseq [i (range-incl (mouse-x))]
      (swap! counter inc)
      (if (= @(state :draw-mode) 2)
        (reset! (state :direction) (round (random 0 3))) ; only NORTH, NORTHEAST, EAST possible
        (reset! (state :direction) (int (random 0 7)))) ; all directions without NORTHWEST

      (case @(state :direction)
        0 (do (swap! (state :pos-y) - @(state :step-size))) ; NORTH
        1 (do (swap! (state :pos-x) + @(state :step-size))  ; NORTHEAST
              (swap! (state :pos-y) - @(state :step-size)))
        2 (do (swap! (state :pos-x) + @(state :step-size))) ; EAST
        3 (do (swap! (state :pos-x) + @(state :step-size))  ; SOUTHEAST
              (swap! (state :pos-y) + @(state :step-size)))
        4 (do (swap! (state :pos-y) + @(state :step-size))) ; SOUTH
        5 (do (swap! (state :pos-x) - @(state :step-size))  ; SOUTHWEST
              (swap! (state :pos-y) + @(state :step-size)))
        6 (do (swap! (state :pos-x) - @(state :step-size))) ; WEST
        7 (do (swap! (state :pos-x) - @(state :step-size))  ; NORTHWEST
              (swap! (state :pos-y) - @(state :step-size)))
        nil)

      (when (> @(state :pos-x) (width)) (reset! (state :pos-x) 0))
      (when (< @(state :pos-x) 0) (reset! (state :pos-x) (width)))
      (when (< @(state :pos-y) 0) (reset! (state :pos-y) (height)))
      (when (> @(state :pos-y) (height)) (reset! (state :pos-y) 0))

      (when (= @(state :draw-mode) 3)
        (when (>= @counter 100)
          (do (reset! counter 0)
              (fill 192 100 64 80)
              (ellipse (+ @(state :pos-x) (/ @(state :step-size) 2))
                       (+ @(state :pos-y) (/ @(state :step-size) 2))
                       (+ @(state :diameter) 7)
                       (+ @(state :diameter) 7)))))

      (fill 0 40)
      (ellipse (+ @(state :pos-x) (/ @(state :step-size) 2))
               (+ @(state :pos-y) (/ @(state :step-size) 2))
               @(state :diameter)
               @(state :diameter)))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    "1" (do (reset! (state :draw-mode) 1)
            (reset! (state :step-size) 1)
            (reset! (state :diameter) 1))
    "2" (do (reset! (state :draw-mode) 2)
            (reset! (state :step-size) 1)
            (reset! (state :diameter) 1))
    "3" (do (reset! (state :draw-mode) 3)
            (reset! (state :step-size) 10)
            (reset! (state :diameter) 5))
    nil))

(defn key-press []
  (case (key-code)
    157 (background 360)
    127 (background 360)
    nil))

(defsketch P_2_2_1_02
           :title "P_2_2_1_02"
           :setup setup
           :draw draw
           :key-released key-release
           :key-pressed key-press
           :size [550 550])
