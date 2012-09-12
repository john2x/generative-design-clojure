; P_2_1_2_03.clj
;
; Licensed under the Apache License, Version 2.0

; changing size of circles in a rad grid depending on mouse position
;
; MOUSE
; position x/y    : module size and offset z
;
; KEYS
; s               : save png

(ns generative-design-clojure.principles.P_2_1_2_03.P_2_1_2_03
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
    :module-color (atom (color 0))
    :module-alpha (atom 180)
    :max-distance (atom 500)
    :act-random-seed (atom 0)))

(defn draw []
  (background 255)
  (smooth)
  (no-fill)

  (random-seed @(state :act-random-seed))

  (stroke @(state :module-color) @(state :module-alpha))
  (stroke-weight 3)

  (doseq [grid-y (range 0 (width) 25),
          grid-x (range 0 (height) 25),
          :let [diameter (* (/ (dist (mouse-x) (mouse-y) grid-x grid-y)
                               @(state :max-distance))
                            40)]]
    (push-matrix)
    (translate grid-x grid-y (* diameter 5))
    (rect 0 0 diameter diameter)
    (pop-matrix)))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil))

(defn mouse-press []
  (reset! (state :act-random-seed) (random 100000)))

(defsketch P_2_1_2_03
           :title "P_2_1_2_03"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-pressed mouse-press
           :renderer :opengl
           :size [600 600])
