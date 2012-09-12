; P_2_1_2_02.clj
;
; Licensed under the Apache License, Version 2.0

; changing module color and positions in a grid
;
; MOUSE
; position x      : offset x
; position y      : offset y
; left click      : random position
;
; KEYS
; 1-3             : different sets of colors
; 0               : default
; arrow up/down   : background module size
; arrow left/right: foreground module size
; s               : save png

(ns generative-design-clojure.principles.P_2_1_2_02.P_2_1_2_02
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (background 255)
  (smooth)
  (set-state!
    :tile-count (atom 20.0)
    :module-color-background (atom (color 0))
    :module-color-foreground (atom (color 255))
    :module-alpha-background (atom 100)
    :module-alpha-foreground (atom 100)
    :module-radius-background (atom 30.0)
    :module-radius-foreground (atom 15.0)
    :back-color (atom (color 255))
    :act-random-seed (atom 0)))

(defn draw []
  (translate (/ (/ (width) @(state :tile-count)) 2)
             (/ (/ (height) @(state :tile-count)) 2))
  (color-mode :hsb 360 100 100 100)
  (background @(state :back-color))
  (smooth)
  (no-stroke)

  (random-seed @(state :act-random-seed))

  (let [tile-count @(state :tile-count)]
    (doseq [grid-y (range tile-count),
            grid-x (range tile-count),
            :let [pos-x (* (/ (width) tile-count) grid-x),
                  pos-y (* (/ (height) tile-count) grid-y),
                  shift-x (* (random -1 1) (/ (mouse-x) 20)),
                  shift-y (* (random -1 1) (/ (mouse-y) 20))]]
      (fill @(state :module-color-background) @(state :module-alpha-background))
      (ellipse (+ pos-x shift-x)
               (+ pos-y shift-y)
               @(state :module-radius-background)
               @(state :module-radius-background)))
    (doseq [grid-y (range tile-count),
            grid-x (range tile-count),
            :let [pos-x (* (/ (width) tile-count) grid-x),
                  pos-y (* (/ (height) tile-count) grid-y)]]
      (fill @(state :module-color-foreground) @(state :module-color-foreground))
      (ellipse pos-x
               pos-y
               @(state :module-radius-foreground)
               @(state :module-radius-foreground)))
    ))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    "1" (if (= @(state :module-color-background) (color 0))
          (reset! (state :module-color-background) (color 273 73 51))
          (reset! (state :module-color-background) (color 0)))
    "2" (if (= @(state :module-color-foreground) (color 360))
          (reset! (state :module-color-foreground) (color 323 100 77))
          (reset! (state :module-color-foreground) (color 360)))
    "3" (if (= @(state :module-alpha-background) 100);
          (do (reset! (state :module-alpha-background) 50)
              (reset! (state :module-alpha-foreground) 50))
          (do (reset! (state :module-alpha-background) 100)
              (reset! (state :module-alpha-foreground) 100)))
    "0" (do (reset! (state :module-color-background) (color 0))
            (reset! (state :module-color-foreground) (color 360))
            (reset! (state :module-alpha-background) 100)
            (reset! (state :module-alpha-foreground) 100)
            (reset! (state :module-radius-background) 20)
            (reset! (state :module-radius-foreground) 10))
    nil))

(defn key-press []
  (case (key-as-keyword)
    :up (reset! (state :module-radius-background)
                (+ @(state :module-radius-background) 2))
    :down (reset! (state :module-radius-background)
                  (max (- @(state :module-radius-background) 2) 10))
    :left (reset! (state :module-radius-foreground)
                  (max (- @(state :module-radius-foreground) 2) 5))
    :right (reset! (state :module-radius-foreground)
                (+ @(state :module-radius-foreground) 2))
    nil))

(defn mouse-press []
  (reset! (state :act-random-seed) (random 100000)))

(defsketch P_2_1_2_02
           :title "P_2_1_2_02"
           :setup setup
           :draw draw
           :key-released key-release
           :key-pressed key-press
           :mouse-pressed mouse-press
           :size [600 600])
