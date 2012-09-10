; P_2_1_1_04.clj
;
; Licensed under the Apache License, Version 2.0

; shapes in a grid, that are always facing the mouse
;
; MOUSE
; position x/y    : position to face
;
; KEYS
; 1-7             : choos shapes
; arrow up/down   : scale of shapes
; arrow left/right: additional rotation of shapes
; c               : toggle. color mode
; d               : toggle. size depending on distance
; g               : toggle. grid resolution
; s               : save png

(ns generative-design-clojure.principles.P_2_1_1_04.P_2_1_1_04
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(def data-folder "P_2_1_1_04/")

(defn setup []
  (background 255)
  (smooth)
  (set-state!
    :current-shape (atom (load-shape (str data-folder "module_1.svg")))
    :tile-count (atom 10)
    :tile-width (atom (/ (width) 10.0))
    :tile-height (atom (/ (height) 10.0))
    :shape-size (atom 50)
    :new-shape-size (atom 0)
    :shape-angle (atom 0)
    :max-dist (atom (sqrt (+ (sq (width)) (sq (height)))))
    :shape-color (atom (color 0 130 164))
    :fill-mode (atom 0)
    :size-mode (atom 0)))

(defn draw []
  (background 255)
  (smooth)

  (let [tile-count @(state :tile-count)]
    (doseq [grid-y (range tile-count),
            grid-x (range tile-count),
            :let [pos-x (+ (* @(state :tile-width) grid-x) (/ @(state :tile-width) 2)),
                  pos-y (+ (* @(state :tile-height) grid-y) (/ @(state :tile-width) 2)),
                  ; calculate angle between mouse position and actual position
                  ; of the shape
                  angle (+ (atan2 (- (mouse-y) pos-y) (- (mouse-x) pos-x))
                           (radians @(state :shape-angle)))]]

      (when (= @(state :size-mode) 0)
        (reset! (state :new-shape-size) @(state :shape-size)))
      (when (= @(state :size-mode) 1)
        (reset! (state :new-shape-size) (- (* @(state :shape-size) 1.5)
                                           (map-range
                                             (dist (mouse-x) (mouse-y) pos-x pos-y)
                                             0 500 5 @(state :shape-size)))))
      (when (= @(state :size-mode) 2)
        (reset! (state :new-shape-size) (map-range
                                             (dist (mouse-x) (mouse-y) pos-x pos-y)
                                             0 500 5 @(state :shape-size))))

      (case @(state :fill-mode)
        0 (.enableStyle @(state :current-shape))
        1 (do
            (.disableStyle @(state :current-shape))
            (fill @(state :shape-color)))
        2 (do
            (.disableStyle @(state :current-shape))
            (let [a (map-range
                      (dist (mouse-x) (mouse-y) pos-x pos-y)
                      0 @(state :max-dist) 255 0)]
              (fill @(state :shape-color) a)))
        3 (do
            (.disableStyle @(state :current-shape))
            (let [a (map-range
                      (dist (mouse-x) (mouse-y) pos-x pos-y)
                      0 @(state :max-dist) 0 255)]
              (fill @(state :shape-color) a))))

      (push-matrix)
      (translate pos-x pos-y)
      (rotate angle)
      (shape-mode :center)
      (no-stroke)
      (shape
        @(state :current-shape) 0 0 @(state :new-shape-size) @(state :new-shape-size))
      (pop-matrix)
      )))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    ("c" "C") (reset! (state :fill-mode) (mod (+ @(state :fill-mode) 1) 4))
    ("d" "D") (reset! (state :size-mode) (mod (+ @(state :size-mode) 1) 3))
    ("g" "G") (do 
                (reset! (state :tile-count) (+ @(state :tile-count) 5))
                (when (> @(state :tile-count) 20)
                  (reset! (state :tile-count) 10))
                (reset! (state :tile-width) (/ (width) (float @(state :tile-count))))
                (reset! (state :tile-height) (/ (height) (float @(state :tile-count)))))
    ("1") (reset! (state :current-shape) (load-shape (str data-folder "module_1.svg")))
    ("2") (reset! (state :current-shape) (load-shape (str data-folder "module_2.svg")))
    ("3") (reset! (state :current-shape) (load-shape (str data-folder "module_3.svg")))
    ("4") (reset! (state :current-shape) (load-shape (str data-folder "module_4.svg")))
    ("5") (reset! (state :current-shape) (load-shape (str data-folder "module_5.svg")))
    ("6") (reset! (state :current-shape) (load-shape (str data-folder "module_6.svg")))
    ("7") (reset! (state :current-shape) (load-shape (str data-folder "module_7.svg")))
    nil))

(defn key-press []
  (case (key-as-keyword)
    :up (reset! (state :shape-size) (+ @(state :shape-size) 5))
    :down (reset! (state :shape-size) (max (- @(state :shape-size) 5) 5))
    :left (reset! (state :shape-angle) (- @(state :shape-angle) 5))
    :right (reset! (state :shape-angle) (+ @(state :shape-angle) 5))
    nil))

(defsketch P_2_1_1_04
           :title "P_2_1_1_04"
           :setup setup
           :draw draw
           :key-released key-release
           :key-pressed key-press
           :size [600 600])
